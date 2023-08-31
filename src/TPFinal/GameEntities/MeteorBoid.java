package TPFinal.GameEntities;

import TPFinal.GivenClasses.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class MeteorBoid extends Boid {

    private float angle;

    public MeteorBoid(float radius, int color, double[] window) {
        super(new PVector(), 1f, radius, color, window);
        this.pos = super.genRandPosOutside();
        this.mass = super.random(3, 5);
        super.setHealth(5);
        super.setAttackDamage(3);
    }

    @Override
    public void setShape(PApplet p, SubPlot plt) {
        float randPercentage = (float) Math.random();
        int randImg;
        if (randPercentage < 0.6)
            randImg = (int) super.random(1, 5);
        else
            randImg = (int) super.random(5, 8);

        PImage img = p.loadImage("Meteor/meteorBrown" + randImg + ".png");
        super.img = img;
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.pushMatrix();
        angle = p.atan2(pp[0], pp[1]);
        p.translate(pp[0], pp[1]);
        p.rotate((float) (Math.PI / 2 + angle));
        p.imageMode(p.CENTER);
        p.image(super.img, 0, 0, img.width / 2, img.height / 2);
        p.popMatrix();
    }
}
