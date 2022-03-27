package com.example;

import java.util.Observer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class FX extends Application implements Observer{
String any;
    

    private Button[][] cells = new Button[20][20];
    Playground p;
    Controller controller;
    GridPane grid;

    public FX(Playground p, Controller controller)  {
        this.p = p;
        this.controller = controller;
    }

public void start(Stage primaryStage) {
    startFX(primaryStage);      //Einfach nicht fragen warum ich das so geschrieben habe
}

    public void startFX(Stage stage) {

        
        Field[][] field = p.getMatrix();

        BorderPane root = new BorderPane();
        grid = new GridPane();

        root.setCenter(grid);


        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                
                cells[i][j] = new Button();
               
                try {
                    
                    cells[i][j].setPrefSize(50, 50);
                    cells[i][j].setMaxSize(50, 50);
                    
                    cells[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
                    
                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cells[i][j].setId(i +" "+j);
                grid.add(cells[i][j], i, j);
            }
        }
        p.addObserver(this);
        Scene scene = new Scene(root, 940, 980);
        
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        Nachricht n = (Nachricht) arg;    
        Button b = getButtonFromMatrix(n.getX(), n.getY());   

        if(n.getA().equals(Nachricht.Actions.SET)){
            int numB = n.getB();
            b.setText(Integer.toString(numB));
            b.setGraphic(null);

            if(numB > 0){
                b.setStyle("-fx-background-color: #ff0000;");
            }else{

                b.setStyle("-fx-background-color: #ffffff;");
            }

        }else if(n.getA().equals(Nachricht.Actions.FLAG)){
            b.setGraphic(new ImageView(new Image("https://cdn.countryflags.com/thumbs/austria/flag-button-square-250.png", 34,34,false,false)));

        }else if(n.getA().equals(Nachricht.Actions.WIN)){
            Alert alert = new Alert(AlertType.INFORMATION);  //Dieses coole fenster habe ich bei Marcel gesehen
			alert.setTitle("Sehr Cooles Fenster");
			alert.setHeaderText(null);
			alert.setContentText("Bravo 👏🎉! Du hast gewonnen!");

			alert.showAndWait();
			((Stage) b.getScene().getWindow()).close();
        }else if(n.getA().equals(Nachricht.Actions.LOST)){
        setAllBombsImage();
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Sehr Cooles Fenster");
         alert.setHeaderText(null);
         alert.setContentText("Lern Minesweeper bro🤦‍♂️🤦‍♀️!");
         alert.showAndWait();
         ((Stage) b.getScene().getWindow()).close();
         //Mögliche erweiterung damit das Spiel neu gestartet wird
        }
    }
    private void setAllBombsImage() {
        Field[][] f = p.getMatrix();
        Image img = new Image("https://cdn.countryflags.com/thumbs/austria/flag-button-square-250.png", 34,34,false,false);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(f[i][j] instanceof BombField){
                    cells[i][j].setGraphic(new ImageView(img));
                }
            }
        }


    }

    private Button getButtonFromMatrix(int x, int y) {
        ObservableList<Node> list = grid.getChildren();
        for (Node node : list) {
            if (node instanceof Button) {
                Button b = (Button) node;
                String[] xy = b.getId().split(" ");
                int x1 = Integer.parseInt(xy[0]);
                int y1 = Integer.parseInt(xy[1]);
                if (x1 == x && y1 == y) {
                    return b;
                }
            }
        }return null;
    }

    public static void main(String[] args) {
        Playground model = new Playground(20,20,30);
        Controller controller = new Controller(model);
        
        FX view = new FX(model, controller);

        Platform.runLater(() -> {
			try {
				view.start(new Stage());
			} catch (Exception e) {
				// e.printStackTrace();
                System.out.println("DEI AMAM");
			}
		});
    }

}