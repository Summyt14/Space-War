package TPFinal.GameEntities;

import TPFinal.GivenClasses.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class UfoBoid extends Boid {

    private float angle;

    public UfoBoid(float mass, float radius, int color, double[] window) {
        super(new PVector(), mass, radius, color, window);
        this.pos = super.genRandPosOutside();
        super.setHealth(10);
        super.setAttackDamage(7);
    }

    @Override
    public void setShape(PApplet p, SubPlot plt) {
        int rand = (int) super.random(1, 5);
        PImage img = p.loadImage("UFO/ufo" + rand + ".png");
        super.img = img;
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.pushMatrix();
        angle = p.atan2(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate((float) (Math.PI / 2 + angle));
        p.imageMode(p.CENTER);
        p.image(super.img, 0, 0, img.width / 2, img.height / 2);
        p.popMatrix();
    }
}
