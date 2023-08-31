package TPFinal.Buttons;

import processing.core.PApplet;
import processing.core.PImage;

public class PlayButton extends GeneralButton {

    public PlayButton(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    public PImage buttonImage(PApplet p) {
        return p.loadImage("play.png");
    }
}
