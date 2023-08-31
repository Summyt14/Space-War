package TPFinal.Buttons;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class GeneralButton {
    private int posX, posY, width, height;
    public boolean mouseIsOver = false;

    public GeneralButton(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void update(PApplet p) {
        if (overButton(posX, posY, p)) {
            mouseIsOver = true;
        } else {
            mouseIsOver = false;
        }
    }

    private boolean overButton(int x, int y, PApplet p) {
        return p.mouseX >= x + width / 2 && p.mouseX <= x + width + 25 &&
                p.mouseY >= y + height / 2 && p.mouseY <= y + height + 25;
    }

    public abstract PImage buttonImage(PApplet p);

    public void display(PApplet p) {
        p.pushMatrix();
        p.pushStyle();
        p.textAlign(p.CENTER, p.CENTER);
        PImage img = buttonImage(p);
        p.image(img, posX + width / 2, posY + height / 2 - 5);
        p.popStyle();
        p.popMatrix();
    }
}
