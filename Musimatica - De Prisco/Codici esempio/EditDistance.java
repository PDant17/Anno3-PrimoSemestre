package it.unisa.di.music.editdistance;

public class EditDistance {
	
	public static void main(String[] args) {
		System.out.println("Ciao!");
		
		String alpha = "torta";
		String beta = "parlare";
		
		compute_edit_distance(alpha,beta);
		
	}
	
	private static int compute_edit_distance(String s1, String s2) {
		int len1 = s1.length(); 
		int len2 = s2.length();
		
		//System.out.format("len1=%d, len2=%d\n", len1,len2);

		int[][] d = new int[len1+1][len2+1];
		int[][] s = new int[len1+1][len2+1];
		
		for (int i = 0; i<= len1; i++) {
			for (int j=0; j<=len2; j++) {
				d[i][j]=0;
				s[i][j]='*';
			}
		}
		
		for (int i=0; i<=len2; i++) d[0][i]=i;
		for (int j=0; j<=len1; j++) d[j][0]=j;
		
		//stampa_matrice(d,len1, len2);
		
		for (int i = 1; i<= len1; i++) {
			for (int j=1; j<=len2; j++) {
				int d1,d2,d3;
				d1 = d[i][j-1]+1;
				d2 = d[i-1][j]+1;
				//System.out.format("i=%d, j=%d\n", i,j);
				if (s1.charAt(i-1) == s2.charAt(j-1)) d3 = d[i-1][j-1];
				else d3 = d[i-1][j-1]+1;
				int min;
				if (d1 < d2) {
					if (d1 < d3) {
						min = d1;
						s[i][j] = 's';
					}
					else {
						min = d3;
						s[i][j] = '\\';
					}
				}
				else {
					if (d2 < d3) {
						min = d2;
						s[i][j] = 'r';
					}
					else {
						min = d3;
						s[i][j] = '\\';
					}
				}
				d[i][j]=min;
			}
		}	
		
		stampa_matrice(d,len1, len2);
		stampa_matrice_char(s,len1, len2);
			
		return 0;
	}
	
	private static void stampa_matrice(int[][] matrice, int len1, int len2) {
		for (int i = 0; i<=len1; i++) {
			for (int j=0; j<=len2; j++) {
				System.out.format("%d",matrice[i][j]);
			}
			System.out.println("");
		}		
	}

	private static void stampa_matrice_char(int[][] matrice, int len1, int len2) {
		for (int i = 0; i<=len1; i++) {
			for (int j=0; j<=len2; j++) {
				System.out.format("%c",(char)matrice[i][j]);
			}
			System.out.println("");
		}		
	}

}