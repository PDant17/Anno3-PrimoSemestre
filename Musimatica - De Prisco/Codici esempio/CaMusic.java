package it.unisa.di.music.camus;

import java.util.Random;
import org.jfugue.player.Player;

public class Game {
	
	int COLORS;
	int THRESHOLD;
	int DIMENSION;
	int ccaStatus[][];
	boolean golStatus[][];
	int iteration;
	
	String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4",
					  "C5", "D5", "E5", "F5", "G5", "A5", "B5", "C6"};

	String[] durations = {"w", "h", "q", "i", "s", "t"};

	public Game(int d, int c, int t) {
		// TODO Auto-generated constructor stub
		DIMENSION = d;
		THRESHOLD = t;
		COLORS = c;
		ccaStatus = new int[DIMENSION][DIMENSION];
		golStatus = new boolean[DIMENSION][DIMENSION];
		randomInit();
	}

	protected void setThreshold(int t) {
		THRESHOLD = t;
	}

	protected void setColors(int c) {
		COLORS = c;
	}
	
	protected void randomInit() {
		iteration = 0;
		Random random = new Random();   
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				int r = random.nextInt(COLORS);   
				ccaStatus[i][j]=r;
				r = random.nextInt(2);   
				if (r==0) golStatus[i][j]=false; else golStatus[i][j]=true;
			}
		}
	}

	protected void gliderInit() {
		iteration = 0;
		Random random = new Random();   
		iteration = 0;
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				golStatus[i][j]=false;
				int r = random.nextInt(COLORS);   
				ccaStatus[i][j]=r;
			}
		}
		int c=DIMENSION/2;
		golStatus[c-1][c]=true;
		golStatus[c][c]=true;
		golStatus[c+1][c]=true;
		golStatus[c][c-2]=true;
		golStatus[c+1][c-1]=true;
	}

	protected void oneStep() {
		//Compute new status
		int newCcaStatus[][] = new int[DIMENSION][DIMENSION];
		boolean newGolStatus[][] = new boolean[DIMENSION][DIMENSION];
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				newCcaStatus[i][j]=computeNextCcaStatus(i, j);
				newGolStatus[i][j]=computeNextGolStatus(i, j);
				if (newGolStatus[i][j]) {
					String music = notes[(i+j)%notes.length]+durations[newCcaStatus[i][j]%durations.length];
					Player player = new Player();
					System.out.println("Play music for i="+i+" j="+j+" color="+newCcaStatus[i][j]+":"+music);
					player.play(music);
				}
			}
		}
		//Copy in status
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				ccaStatus[i][j]=newCcaStatus[i][j];
				golStatus[i][j]=newGolStatus[i][j];
			}
		}
		iteration++;
	}
	
	private int computeNextCcaStatus(int i, int j) {
		int news = ccaStatus[i][j];
		//Count number of adjacent cell with dominant color
		int count= 0;
		int dominant_color = (ccaStatus[i][j]+1) % COLORS;
		//System.out.println("Checking i="+i+" j="+j);
		if (ccaCheck( (i-1+DIMENSION)%DIMENSION, (j-1+DIMENSION)%DIMENSION, 	i,j)) count++;
		if (ccaCheck( (i-1+DIMENSION)%DIMENSION, j, 							i,j)) count++;
		if (ccaCheck( (i-1+DIMENSION)%DIMENSION, (j+1)%DIMENSION,				i,j)) count++;
		if (ccaCheck( i, 						  (j-1+DIMENSION)%DIMENSION,	i,j)) count++;
		if (ccaCheck( i,						  (j+1)%DIMENSION,			  	i,j)) count++;
		if (ccaCheck( (i+1)%DIMENSION,			  (j-1+DIMENSION)%DIMENSION,	i,j)) count++;
		if (ccaCheck( (i+1)%DIMENSION,			  j,  							i,j)) count++;
		if (ccaCheck( (i+1)%DIMENSION,			  (j+1)%DIMENSION,				i,j)) count++;
		//update
		if (count >= THRESHOLD) news=dominant_color;
		return news;
	}
	
	private boolean computeNextGolStatus(int i, int j) {
		boolean news = false;
		//Count number of live adjacent cells
		int count= 0;
		//System.out.println("Checking i="+i+" j="+j);
		if (golCheck( (i-1+DIMENSION)%DIMENSION, (j-1+DIMENSION)%DIMENSION, 	i,j)) count++;
		if (golCheck( (i-1+DIMENSION)%DIMENSION, j, 							i,j)) count++;
		if (golCheck( (i-1+DIMENSION)%DIMENSION, (j+1)%DIMENSION,				i,j)) count++;
		if (golCheck( i, 						  (j-1+DIMENSION)%DIMENSION,	i,j)) count++;
		if (golCheck( i,						  (j+1)%DIMENSION,			  	i,j)) count++;
		if (golCheck( (i+1)%DIMENSION,			  (j-1+DIMENSION)%DIMENSION,	i,j)) count++;
		if (golCheck( (i+1)%DIMENSION,			  j,  							i,j)) count++;
		if (golCheck( (i+1)%DIMENSION,			  (j+1)%DIMENSION,				i,j)) count++;
		//update status
		if (!golStatus[i][j] && count==3) news=true;
		if (golStatus[i][j] && (count==2 || count==3)) news=true;
		return news;
	}
	
	
	private boolean ccaCheck(int x, int y, int i, int j) {
		//System.out.println(" x="+x+" y="+y);
		if (ccaStatus[x][y] == (ccaStatus[i][j] + 1) % COLORS) return true;
		return false;
	}
	
	private boolean golCheck(int x, int y, int i, int j) {
		//System.out.println(" x="+x+" y="+y);
		if (golStatus[x][y]) return true;
		return false;
	}
	
	protected int getCcaStatus(int x, int y) {
		return ccaStatus[x][y];
	}

	protected boolean getGolStatus(int x, int y) {
		return golStatus[x][y];
	}

	protected int getDimension() {
		return DIMENSION;
	}
	
	protected int getIteration() {
		return iteration;
	}

}