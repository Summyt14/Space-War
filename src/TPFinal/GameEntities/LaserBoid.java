package TPFinal.GameEntities;

import TPFinal.GivenClasses.SubPlot;
import TPFinal.Sounds.MediaPlayer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class LaserBoid extends Boid {

    private double[] window;
    private int color;
    private PShape shape;

    public LaserBoid(PVector pos, PVector vel, float mass, float radius, int color, double[] window) {
        super(pos, mass, radius, color, window);
        this.vel = vel;
        this.color = color;
        this.window = window;
        super.setAttackDamage(1);
    }

    public void setShape(PApplet p, SubPlot plt) {
        float[] r = plt.getPixelVector(radius, radius);
        PShape s = p.createShape();
        s.setStroke(color);
        s.beginShape(p.LINES);
        s.strokeWeight(2f);

        s.vertex(r[0], r[1]);
        s.vertex(r[0] * 2, r[1]);
        s.endShape(PConstants.CLOSE);
        this.shape = s;
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.pushMatrix();
        p.fill(0, 0, 0, 0);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-vel.heading());
        p.shape(shape, 0, 0);
        p.popMatrix();
        p.popStyle();
    }
}
