package it.unisa.di.musimatica.musicadiguido;

import java.util.HashMap;
import java.util.Random;

import org.jfugue.player.Player;


public class MusicaDiGuido {

	static String testo_da_suonare = "dalle parole si creano le note";
	
	static String vocali = "aeiou";
	
	static String lettere = "ABCDEFG";
	static String ottave = "345";

	//static String[] armonia = new String[] {"Cmaj", "Fmaj", "Gdom7", "Dmin", "Emin", "Amin", "Bdim7"};
	static String[] armonia = new String[] {"Cmaj", "Amin", "Fmaj", "Gmaj", "Cmaj", "Amin", "Dmin", "Gdom7"};

	//static String durate = "whqistxo";
	static String durate = "qis";

	static HashMap<String,String[]> note = new HashMap<String,String[]>();
	
	static Random random = new Random();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player = new Player();
		
		note.put("a", new String[] {"E3", "C4", "A5", "F5", "R"});
		note.put("e", new String[] {"F3", "D4", "B5", "G5", "R"});
		note.put("i", new String[] {"G3", "E4", "C5", "A6"});
		note.put("o", new String[] {"A4", "F4", "D5", "B6"});
		note.put("u", new String[] {"B4", "G4", "E5", "C6"});
		
		String music = "";
				
		int j=0;
		
		for (int i=0; i<testo_da_suonare.length(); i++) {
			String c = testo_da_suonare.charAt(i)+"";
			if (vocali.contains(c)) {
				int d = random.nextInt(durate.length());
				//int a = random.nextInt(armonia.length);

				int r = random.nextInt(note.get(c).length);
				music = music + " "+note.get(c)[r]+"+"+armonia[j]; //+durate.charAt(d);
				j++;
				if (j>=armonia.length) j=0;
			}
		}
		System.out.println("music="+music);
		player.play(music);	
	}

}