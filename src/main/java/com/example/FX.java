package com.example;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FX extends Application {
String any;
    public FX(String any) {
        this.any = any;
    }

    private Button[][] cells = new Button[20][20];
    Playground p;
    Controller controller;

public void start(Stage primaryStage) {
    startFX(primaryStage);
}

    public void startFX(Stage stage) {

        p = new Playground();
        Playground.init(20, 20, 5);
        Field[][] field = p.getMatrix();

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        root.setCenter(grid);

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                String imgName;
                if(field[i][j] instanceof BombField){

                    imgName = "Bomb.png";
                }else{
                    imgName = "empty.png";
                    
                }
                cells[i][j] = new Button();
                // File file = new File("src/main/resources/img/"+imgName);
                // Image img = new Image("../../../resources/img/"+imgName);
                // ImageView iv = new ImageView(img);
                try {
                    
                    // cells[i][j].setGraphic(iv);
                    cells[i][j].setGraphic(new ImageView(new Image(new File("../../../resources/img/"+imgName).toURI().toString())));
                    cells[i][j].setPrefSize(50, 50);
                    cells[i][j].setText(imgName);
                    cells[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
                    cells[i][j].setId(i +" "+j);
                    


                } catch (Exception e) {
                    e.printStackTrace();
                }
                grid.add(cells[i][j], i, j);
            }
        }

        Scene scene = new Scene(root, 940, 980);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        FX view = new FX("Servus");
        Platform.runLater(() -> {
			try {
				view.start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
                System.out.println("DEI AMAM");
			}
		});
    }

}