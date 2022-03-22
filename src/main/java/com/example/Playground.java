package com.example;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Playground {
	private static Field[][] matrix;
	static int w, h;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		init(20, 20, 30);
		while(!finished()) {
			printField();
			System.out.print("next: ");
			parseInput(s.nextLine());
		}
		
	}

	public Field[][] getMatrix() {
		return matrix;
	}
	
	public static void  init(int width, int height, int anzBomb) {
		matrix = new Field[20][20];
		h = height;
		w = width;
		for(int i = 0; i<anzBomb; i++) {
			int x = ThreadLocalRandom.current().nextInt(0, width);
			int y = ThreadLocalRandom.current().nextInt(0, height);
			while((matrix[x][y] instanceof BombField)){
				x = ThreadLocalRandom.current().nextInt(0, width);
				y = ThreadLocalRandom.current().nextInt(0, height);
			}
				matrix[y][x] = new BombField(false, false);
			
		}
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(matrix[i][j] instanceof BombField) {
					continue;
				}else{
					matrix[i][j] = new EmptyField(false,false);
				}
			}
		}
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(matrix[i][j] instanceof EmptyField) {
					int anzahl = 0;
					for(int k = -1; k < 2; k++) {
						for(int l = -1; l < 2; l++) {
							try {
								if(matrix[i+k][j+l] instanceof BombField) {
									anzahl++;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
						}
					}
					((EmptyField)matrix[i][j]).setBombsCnt(anzahl);
					
				}
			}
		}
	}
	public static void printField() {
		for(int i = -1; i<matrix.length;i++) {  
            if(i>=0){ System.out.print(i + "	");}else{System.out.print("        ");}
			for(int j = 0; j<matrix[0].length;j++) {
				if(i == -1) {
                    if(j<10){
                    System.out.print(" "+j+"  "); 
                }else{
                    System.out.print(" "+j+" ");
                }

                    continue;}
				if(j == 0) System.out.print("| ");
                if(matrix[i][j].getFlag()){
				System.out.print("F" + " | ");
                }else if(matrix[i][j].getOpen()){
                    
                    int num = 0;
                    try {
                        
                   
                    for (int o = -1; o <= 1; o++) {
                        for (int p = -1; p <=1; p++) {
                            if(matrix[i+o][j+p] instanceof BombField){
                                num++;
                        }
                    }
                }
                if (num==0) {
                    System.out.print(" " + " | "); 
                }else{

                    System.out.print(num + " | ");
                }
                
                } catch (Exception e) {
                    System.out.print(" " + " | ");
                }
                

                
                


                }else if(matrix[i][j] instanceof EmptyField||matrix[i][j] instanceof BombField){
                    System.out.print("?" + " | ");
                }
            }
            System.out.println();
			for(int a = 0; a < (w*4+1); a++) {
				if(a == 0) System.out.print("	");
                
				System.out.print("_");
			}
            System.out.println();
			System.out.println();
		}
	}
	public static boolean show(int x, int y) {
		matrix[y][x].setOpen(true);
		return matrix[y][x] instanceof BombField;
	}
	
	public static void flagging(int x, int y) {
		matrix[y][x].setFlag(true);
	}
	
	public static boolean finished() {
		for(Field[] fa : matrix){
			for(Field f : fa) {
				if(f instanceof BombField && f.getOpen()) {
					System.out.println("Verloren!");
					return true;
				}
				if(f instanceof BombField && !f.getFlag()) return false;
			}
		}
		return true;
	}
	
	public static void parseInput(String input) {
		String[] uinput = input.split("\\s");
		int x = 0;
        int y = 0;
		try {
			x = Integer.parseInt(uinput[1]);
			y = Integer.parseInt(uinput[2]);
		}catch(Exception e) {
			System.out.println("versuche es erneut");
		}
		switch(uinput[0]) {
			case "Flagge":
				matrix[y][x].setFlag(true);
				break;
			case "Offnen":
				if(matrix[y][x] instanceof EmptyField) {
					if(((EmptyField)matrix[y][x]).getBombsCnt() == 0) {
						checkNearFields(x,y);
					}
				}
				matrix[y][x].setOpen(true);
				break;
			default:
				System.out.println("nur Flagge oder Offnen möglich");
		}
        System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	public static void checkNearFields(int x, int y) {
		try{
			if(matrix[y][x] instanceof EmptyField && !matrix[y][x].getOpen()) {
				if(((EmptyField)matrix[y][x]).getBombsCnt() == 0) {
					matrix[y][x].setOpen(true);
					for(int i = -1; i < 2; i++) {
						for(int j = -1; j < 2; j++) {
							checkNearFields(j+x, i+y);
						}
					}
				}else{
					matrix[y][x].setOpen(true);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	
}