package com.example;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;



public class Playground extends Observable{
	private static Field[][] matrix;
	static int w, h;

	public Playground(int width, int height, int bombs) {
		
		init(width, height, bombs);
		
		
	}

	public Field[][] getMatrix() {
		return matrix;
	}
	
	public void  init(int width, int height, int anzBomb) {
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
	
	public void show(int x, int y) {
		

		
		setChanged();
		if(finished()){
			notifyObservers(new Nachricht(Nachricht.Actions.WIN));
			return;
		}
		if(matrix[x][y] instanceof EmptyField){

			checkNearFields(x, y);
			return;
		}
		notifyObservers(new Nachricht(Nachricht.Actions.LOST, x, y));

	}
	
	public void flagging(int x, int y) {
		if(matrix[x][y].getOpen()) return;
		
		matrix[x][y].setFlag(true);
		setChanged();
		notifyObservers(new Nachricht(Nachricht.Actions.FLAG, x, y));
		if(finished()){
			setChanged();
			notifyObservers(new Nachricht(Nachricht.Actions.WIN));
			return;
		}
	}
	
	public static boolean finished() {
		for(Field[] fa : matrix){
			for(Field f : fa) {
				if(f instanceof BombField && f.getOpen()) {
					return true;
				}
				if(f instanceof BombField && !f.getFlag()) return false;
			}
		}
		return true;
	}
	
	
	public void checkNearFields(int x, int y) {
		try{
			//TODO: Beim Flagggen Bild setzten und fragen ob Emptyfield
			//TODO: Win einbauen
			
			if(matrix[x][y] instanceof EmptyField && !matrix[x][y].getOpen()) {
				if(((EmptyField)matrix[x][y]).getBombsCnt() == 0) {
					matrix[x][y].setOpen(true);
					setChanged();
					notifyObservers(new Nachricht(Nachricht.Actions.SET,x,y,((EmptyField)matrix[x][y]).getBombsCnt()));

					for(int i = -1; i < 2; i++) {
						for(int j = -1; j < 2; j++) {
							
							checkNearFields(i+x, j+y);
						}
					}
				}else{
					matrix[x][y].setOpen(true);
					setChanged();
					notifyObservers(new Nachricht(Nachricht.Actions.SET,x,y,((EmptyField)matrix[x][y]).getBombsCnt()));

				}
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}
	
	
	
	}
