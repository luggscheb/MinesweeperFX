package com.example;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Controller implements EventHandler<MouseEvent> {

Playground p ;

    public Controller(Playground p) {
        this.p = p;
    }
    

    @Override
    public void handle(MouseEvent e)
    {
        Button b = (Button) e.getSource();
        String[] xy = b.getId().split(" ");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);

        if(e.getButton() == MouseButton.PRIMARY){
            //TODO: left click
            System.out.println("left click|"+x+"|"+y);
            p.show(x, y);
        }
        if(e.getButton() == MouseButton.SECONDARY){
            //TODO: right click
            p.flagging(x, y);
        }



    }

    
}
