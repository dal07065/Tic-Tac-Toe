package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Spot {
    private Button button;      //button in the spot
    private boolean pressed;    //if button has been pressed or not yet
    private String player;        //X or O

    Spot(Button b)
    {
        button = b;
        pressed = false;
        player = "-";
    }

    public void pressButton(Label label_player)
    {
        pressed = true;
        player = label_player.getText();
    }

    public boolean isPressed()
    {
        return pressed;
    }



}
