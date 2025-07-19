package it.unisa.di.music.fibo;

import org.jfugue.player.Player;

public class Fibo {
	static int SIZE = 20;
	static int fib[] = new int[SIZE];
	
	static String[] note = new String[] {"B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5"};
	static String[][] chords = new String[][] { 
		{"Emin", "Gmaj", "Bdim"},  //B
		{"Cmaj", "Amin", "Fmaj"},  //C
		{"Dmin", "Bdim", "Gmaj"},  //D
		{"Emin", "Cmaj", "Amin"},  //E
		{"Fmaj", "Dmin", "Bdim"},  //F
		{"Gmaj", "Emin", "Cmaj"},  //G
		{"Amin", "Fmaj", "Ddim"},  //A
		{"Emin", "Gmaj", "Bdim"},  //B
		{"Cmaj", "Amin", "Fmaj"},  //C
		{"Dmin", "Bdim", "Gmaj"}  //D
		};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fib[0]=0;
		fib[1]=1;
		String digits = "01";
		for (int i=2; i<SIZE; i++) {
			fib[i]=fib[i-2]+fib[i-1];
			digits = digits + fib[i];
		}
		System.out.format("digits=%s\n",digits);
		
		String music = "";
		int chord_index = 0;
		for (int i=0; i<digits.length(); i++) {
			int index = Integer.parseInt(""+digits.charAt(i));
			music = music + " " + note[index];
			if (index==1 || index==8) music += "w";
			if (i%4==0) {
				music = music + "+" + chords[index][chord_index];
				chord_index = (chord_index+1)%3;
			}
		}
		
		Player player = new Player();
		System.out.format("music=%s\n",music);
		player.play(music);
		
	}

}