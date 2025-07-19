#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include "midistrings.h"

#define MAXBUF 1024

typedef int BOOL;

int counter =0;
int event_counter = 0;
int tr_togo = 14; //After 14 bytes we will find the data for the first track
			      //not really needed  -- this is only an internal counter
			      //and is used only to print out the number of bytes left before
				  //the end of the track.
int BPM = 0;
int PPQN = 0;
float beat_duration_us = 0;
float tick_duration_us = 0;

/*
 * Print an error mesage and exit with an exit code
 */
void error_exit(int rc, char* msg) {
  fprintf(stderr,"%s. Exiting.\n",msg);
  exit(rc);
}

/*
 * Read one byte from the midifile, update and print counters 
 */
int read_one_byte(FILE* file) {
	  int byte;
      byte=fgetc(file);
	  if(feof(file)) error_exit(1,"Unexpected end of file");
      counter++;
      tr_togo--;
      fprintf(stderr,"(tr_togo=%d),%6d] %3d (%2x): %c\n",tr_togo,counter,byte,byte,byte);
	  return byte;
}

/*
 * Read extra bytes at the end of file
 */
int read_extra_byte(FILE* file) {
	  int byte;
      byte=fgetc(file);
	  if(feof(file)) return -1; 
      counter++;
      fprintf(stderr,"%d] %3d (%2x): %c\n",counter,byte,byte,byte);
	  return byte;
}


/*
 * Read one fixed length integer value from the midifile, MSB first
 * Returns value
 */
int read_nbyte_value(int n, FILE* file) {
	  int i, byte, value = 0;
      for (i=0; i<n; i++) {
	      byte=read_one_byte(file);
          value=(value<<8)+byte;
      }
	  return value;
}

/*
 * Read a variable length integer value from the midifile
 * Midifile variable length values should never use more than 4 bytes
 * Returns value
 */
unsigned int read_variable_length(FILE* file) {
   unsigned int deltatime = 0;  
   // unsigned int is enough since delta-times can span at most 4 bytes
   int byte;
   int c = 1; 
   while (( byte=read_one_byte(file)) & 0x80) {  // is byte>127 ?
	 //fprintf(stderr,"++++ byte =%u\n",byte);
     byte=byte & 0x7F; //unset MSB, that is byte=byte-128
	 //fprintf(stderr,"++++ byte-128 =%u\n",byte);
	 //fprintf(stderr,"++++ deltatime prima=%u\n",deltatime);
	 deltatime=deltatime+(byte<<7); //len=len+byte*128
	 //fprintf(stderr,"++++ deltatime dopo=%u\n",deltatime);
	 c++;
   }
   deltatime=deltatime+byte;
   if (c > 4) error_exit(1,"Delta time uses more than 4 bytes.");
   return deltatime;
}

/*
 * Read an expected data from the midifile
 * Returns 0 if data is not found
 *         1 if data is found
 */
int read_expected_data(FILE* file, const int edata[], const int n) {
	int i;
	int byte;
	for (i=0; i<n; i++) {
		byte=read_one_byte(file);
		//fprintf(stderr,"ExpData[%d]=%d (expecting %d)\n",i,byte,edata[i]);
		if (edata[i] != byte) return 0;
    }
	return 1;
}

/*
 * Is text event?
 */
BOOL is_text_event(int type) {
	if (type > 0 && type < 10) return 1;
	return 0;
}

/*
 * Prints an event
 */
void print_event(int c, float time, int status_byte, int metatype, int len, int data[]) {
	int msg_type = status_byte & 0xF0;
	int channel = status_byte & 0x0F;
	int byte1 = (int)data[0];  // Will be used only for MIDI events
	int byte2 = (int)data[1];  // Will be used only for MIDI events
	int i;
	//printf("Print EVENT: status_byte %d, metatype %d\n",status_byte,metatype);
	printf("%6d] %7.2f ",c,time);
	switch(msg_type) {
	case 0x80: //NOTE OFF
	case 0x90: //NOTE ON
			printf(" MIDI ");
		    if ((msg_type == 0x90) && (byte2 == 0)) {
			//This is the NOTE OFF trick with the running byte, change msg type
			   	  msg_type = 0x80;
			}
			printf("%d (%20s) %2d %3d (%6s) %3d\n",msg_type,midi_msg_name[msg_type>>4],
												   channel,
												   byte1,midinote[byte1],
												   byte2);
			break;
	case 0xA0: //NOTE AFTERTOUCH
	case 0xB0: //CONTROLLER
	case 0xE0: //PITCH BEND
			printf(" MIDI ");
			printf("%d (%20s) %2d %3d %3d\n",msg_type,midi_msg_name[msg_type>>4],
									    channel,
										byte1,
										byte2);
			break;
	case 0xC0: //PROGRAM CHANGE
	case 0xD0: //CHANNEL AFTERTOUCH
			printf(" MIDI ");
			printf("%d (%20s) %2d %3d\n",msg_type,midi_msg_name[msg_type>>4],
									 channel,
									 byte1);
		    break;
	case 0xF0: //All SYS and META EVENTS, need to check status byte
		    //printf("Print EVENT: status_byte %d, metatype %d\n",status_byte,metatype);
			switch(status_byte) {
			case 0xF0: //SYSEVENT 
			case 0xF7: //SYSEVENT 
				//just print hex value
				printf(" SYS  %3d (%s) ",metatype,metaevent[metatype]);
				for (i=0; i<len; i++) printf(" %X",data[i]);
				printf("\n");
		    	break;
			case 0xFF: //METAEVENT 
				printf(" META %3d (%s) ",metatype,metaevent[metatype]);
				// print ASCII if text event
				if (is_text_event(metatype)) for (i=0; i<len; i++) printf("%c",data[i]);
				// or just print hex value
				else for (i=0; i<len; i++) printf(" %X",data[i]);
				printf("\n");
		    	break;
    		default: fprintf(stderr,"ERROR  %s: Unknown SYS message type %d\n",__func__,status_byte);
			}
			break;
    default: fprintf(stderr,"ERROR  %s: Unknown MIDI message type %d (%X)\n",__func__,msg_type,msg_type);
	}
}

/*
 * Handles a metaevent from the midifile
 */
void gestisci_meta_evento(FILE* file, unsigned short sb, unsigned long time_in_secs) {
	 int byte, metatype; 
	 unsigned long len, tempo;
	 int i, num, den;
	 int data[MAXBUF]; //for printing event
	 fprintf(stderr,"**** Status byte=%d. Detected a meta-event\n",sb);
	 //Read type
     metatype=read_one_byte(file);
	 fprintf(stderr,"****   Meta-event number=%d (%s)\n",metatype,metaevent[metatype]);
     //Process it
	 switch(metatype) {
	     case 81: //Tempo
       		    len = read_variable_length(file);
				if (len != 3) error_exit(1,"Corrupted midi file. Len of Tempo meta-event != 3.\n");
				byte = read_one_byte(file); // byte 1
				data[0]=byte;
				tempo = byte<<16; // tempo = byte*256*256
				byte = read_one_byte(file); // byte 2
				data[1]=byte;
				tempo = tempo+(byte<<8); // tempo = tempo+byte*256;
				byte = read_one_byte(file); // byte 3
				data[2]=byte;
				tempo = tempo+byte;
				beat_duration_us = tempo;
				BPM=60000000/tempo;
				fprintf(stderr,"beat duration in microsec=%f, BPM=%d\n",beat_duration_us,BPM);
				tick_duration_us = tempo/PPQN;
				fprintf(stderr,"tick duration in microsec=%f\n",tick_duration_us);
				break;
	     case 88: //Time Signature
       		    len = read_variable_length(file);
				if (len != 4) error_exit(1,"Corrupted midi file. Len of Time Signature != 4.\n");
				data[0]= read_one_byte(file); //Numeratore
				num = data[0]; 
				data[1]= read_one_byte(file); //Numeratore
				den = pow(2,data[1]); //Denominatore
				fprintf(stderr,"Time Signature: %d/%d\n",num,den);
				data[2]= read_one_byte(file); //Metronome clicks
				data[3]= read_one_byte(file); //32esimi per quarter-note
				break;
	  default: //Just read length and skip data
       			len = read_variable_length(file);
	   			fprintf(stderr,"****   Meta-event length=%lu. Skipping.\n",len);
       			for (i=0; i<len; i++) {
	     			data[i]=read_one_byte(file);
	   			}
	  }
      print_event(event_counter,time_in_secs,sb,metatype,len,data);
      return;
}

/*
 * Handles a sysevent from the midifile
 */
void gestisci_sysevent(FILE* file, int status_byte,float time_in_secs) {
	 int byte; 
	 unsigned int len;
	 int i;
	 int data[MAXBUF]; //for printing event
	 fprintf(stderr,"**** Midi status byte code=%d. Detected a system-event\n",status_byte);
	 //Read length of event 
     len = read_variable_length(file);
	 fprintf(stderr,"**** Length of sysevent=%u. Skipping\n",len);
	 //Skip len bytes
	 for (i=0; i<len; i++) {
		data[i]=read_one_byte(file);
	 }
     //Last byte of sys-event hast be 0xF7 (247)
	 if (data[i-1] != 247) error_exit(1,"Corrupted midi file. Last byte of sysevent is not 0xF7\n");
	 fprintf(stderr,"**** Sysevent ended with 0xF7\n");
     print_event(event_counter,time_in_secs,status_byte,0,len,data);
     return;
}

/**********************************************************************
 * MAIN PROGRAM
 **********************************************************************/
int main (int argc, char *argv[]) {
  FILE* file;
  int status_byte, data_byte, byte;
  int byte1, byte2;
  int tot_tracks, midifile_format;
  int current_track, track_length;
  unsigned long current_time;
  float current_time_in_secs;
  unsigned int delta_time;
  int running_byte = 0;
  int running_status_on = 0;
  int i;
  int buf[MAXBUF];

  /*
   * Check number of arguments, need the name of the midifile
   */

  if (argc == 2 && !strncmp(argv[1],"-h",2)) {
    fprintf(stderr,"Usage: %s [file]\n",argv[0]);
    exit(1);
  }

  /*
   * Open midifile
   */

  printf("Opening midifile %s\n",argv[1]);

  if (! (file = fopen (argv[1],"r"))) {
     fprintf(stderr,"Could not open file %s\n",argv[1]);
     exit(1);
  }
  
  /*
   * Read byte 1-4: MThd
   */
  buf[0]='M';
  buf[1]='T';
  buf[2]='h';
  buf[3]='d';
  if (!read_expected_data(file,buf,4)) {
  		error_exit(1,"This is not a midi file. MThd not found.");
  }

  fprintf(stderr,"**** MThd detected\n");

  /*
   * Read byte 5-8: 00 00 00 06 
   */
  buf[0]=0;
  buf[1]=0;
  buf[2]=0;
  buf[3]=6;
  if (!read_expected_data(file,buf,4)) {
        error_exit(1,"Constant 00 00 00 06 not found.");
  }

  fprintf(stderr,"**** Constant 00 00 00 06 detected\n");

  /*
   * Read byte 9-10: file format 
   */
  midifile_format=read_nbyte_value(2,file);

  switch (midifile_format) {
      case 0: fprintf(stderr,"**** Midifile format 0\n"); break;
      case 1: fprintf(stderr,"**** Midifile format 1\n"); break;
      case 2: fprintf(stderr,"**** Midifile format 2\n"); break;
	  default: error_exit(1,"Unknown midifile format (>2).");
  }

  /*
   * Read byte 11-12: number of tracks 
   */
  tot_tracks=read_nbyte_value(2,file);

  fprintf(stderr,"**** Number of tracks: %d\n",tot_tracks);

  /*
   * Read byte 13-14: PPQN 
   */
  PPQN=read_nbyte_value(2,file);

  fprintf(stderr,"**** PPQN: %d\n",PPQN);

  /*
   * Start scanning data, a loop for each track
   */

  current_track=1;

  while (!feof(file)) {

	fprintf(stderr,"\n");

	if (current_track > tot_tracks) break;
    fprintf(stderr,"Track number: %d\n", current_track);
    current_track++;

	/*
	 * Process track data, start counting time from 0
	 */

	current_time = 0;
	current_time_in_secs = 0.0f;
	fprintf(stderr,"**** START track time - current time %f\n",current_time_in_secs);

    /*
     * Read next 4 byte: should be MTrk
     */
    buf[0]='M';
    buf[1]='T';
    buf[2]='r';
    buf[3]='k';
    if (!read_expected_data(file,buf,4)) {
			error_exit(1,"Corrupted midi file. MTrk not found.");
	}
    
    fprintf(stderr,"**** Detected MTrk\n");
    /*
     * Read next 4 byte: length of track 
     */
	track_length = read_nbyte_value(4,file);
    fprintf(stderr,"**** Track length = %d\n",track_length);
    tr_togo = track_length;

  
    while (tr_togo > 0 ) {
      /*
	   * Now read all the events in this track, starting with a delta time
       */
	  
	  event_counter++;

	  fprintf(stderr,"\nEvent number %d\n",event_counter);
    
      delta_time = read_variable_length(file);
	  float duration = delta_time*tick_duration_us/1000000;
      fprintf(stderr,"**** Detected delta time: %u (%3.2f sec)\n",delta_time,duration);

	  current_time += delta_time;
	  current_time_in_secs = tick_duration_us*current_time/1000000.0f;
	  fprintf(stderr,"*** Current time: %lu ticks (%f secs) - tick duration: %f\n",current_time,current_time_in_secs, tick_duration_us);

	  /*
	   * Read status byte
	   */

	  if (!running_status_on) {
	      running_byte=status_byte; /* save previous value of the status byte */
	      //fprintf(stderr,"***** saved running_byte = %d\n",running_byte);
	  }
      status_byte=read_one_byte(file);
	  int msg_type, channel;

	  switch(status_byte) {
	    case 240: //System event, skip until 0xF7 (247)
	    case 247: //Same as for 240
		  		gestisci_sysevent(file,status_byte,current_time_in_secs);
				break;
		case 255: //Meta-event or system-event
		  		gestisci_meta_evento(file,status_byte,current_time_in_secs);
		  		break;
	    case 241:	
	    case 242:	
	    case 243:	
	    case 244:	
	    case 245:	
	    case 246:	
	      		fprintf(stderr,"**** Status byte=%d. Unknown.\n",status_byte);
		  		error_exit(1,"Don't know what to do ...");
        default: //MIDI event 
		  /*
		   * TODO Recognize MIDI EVENT HERE
		   */
          
		  if (status_byte<128) { //Running status mode
				 running_status_on = 1;
				 ungetc(status_byte,file);
				 counter--;
				 tr_togo++;
				 fprintf(stderr,"**** putting back %u - Retrieving running byte %d\n",status_byte,running_byte);
				 status_byte=running_byte;
				 fprintf(stderr,"**** Status byte =%u\n",status_byte);
		  }
		  else {
				 running_status_on = 0;
		  }

		  msg_type = status_byte & 0xF0;
		  channel = status_byte & 0x0F;
		  fprintf(stderr,"MIDI MESSAGE TYPE=%d (0x%X), channel=%d\n", msg_type, msg_type, channel);
		  switch(msg_type) {
			 case 0x80: //NOTE OFF
		               byte1=read_one_byte(file);
		               byte2=read_one_byte(file);
					   fprintf(stderr,"**** Note Off %s - velocity %u - channel %d - time %f\n",midinote[byte1],byte2,channel,current_time_in_secs);
					   break;
			 case 0x90: //NOTE ON
		               byte1=read_one_byte(file);
		               byte2=read_one_byte(file);
					   if (byte2>0) {
					      fprintf(stderr,"**** Note On %s - velocity %u - channel %d - time %f \n",midinote[byte1],byte2,channel,current_time_in_secs);
					   } 
					   else {
					      fprintf(stderr,"**** Note Off %s (Note On velocity TRICK) - velocity %u -channel %d - time %f\n",midinote[byte1],byte2,channel,current_time_in_secs);
					   }
					   break;
		     case 0xA0: //NOTE AFTERTOUCH
		               byte1=read_one_byte(file);
		               byte2=read_one_byte(file);
					   fprintf(stderr,"**** Note Aftertouch%s - value %u - channel %d\n",midinote[byte1],byte2,channel);
					   break;
		     case 0xB0: //CONTROLLER
		               byte1=read_one_byte(file);
		               byte2=read_one_byte(file);
					   fprintf(stderr,"**** Controller: number=%d, value=%d - channel %d\n",byte1,byte2,channel);
					   break;
		     case 0xC0: //PROGRAM CHANGE
		               byte1=read_one_byte(file);
					   fprintf(stderr,"**** Program Change: %u - channel %d\n",byte1,channel);
					   break;
		     case 0xD0: //CHANNEL AFTERTOUCH
		               byte1=read_one_byte(file);
					   fprintf(stderr,"**** Channel aftertouch. value=%d (channel %d)\n", byte1, channel);
					   break;
		     case 0xE0: //PITCH BEND
		               byte1=read_one_byte(file);
		               byte2=read_one_byte(file);
					   fprintf(stderr,"**** Pitch bend channel %d: LSB=%d, MSB=%d\n",channel,byte1,byte2);
					   break;
			 default: fprintf(stderr,"**** Unknown\n");
	      }
	      int data[2];
    	  data[0]=byte1;
    	  data[1]=byte2;
    	  print_event(event_counter,current_time_in_secs,status_byte,0,2,data);
	  }
    }
  }

  fprintf(stderr,"************** TRACKS ENDED *************************\n");
  fprintf(stderr,"Reading extra bytes at the end of the file:\n");

  while (!feof(file)) {
		 read_extra_byte(file);
  }

  fprintf(stderr,"\nTOT CHAR = %d\n",--counter);
  exit(0);
}
