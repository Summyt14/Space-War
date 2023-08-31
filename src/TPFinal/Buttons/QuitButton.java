package TPFinal.Buttons;

import processing.core.PApplet;
import processing.core.PImage;

public class QuitButton extends GeneralButton {
    public QuitButton(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    public PImage buttonImage(PApplet p) {
        return p.loadImage("quit.png");
    }

    public void clickAction(PApplet p) {
        p.exit();
    }
}
