package TPFinal.GivenClasses;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends Mover {
    private float lifetime;
    private int color;
    private float timer;

    public Particle(PVector pos, PVector vel, int color, float radius, float lifetime) {
        super(pos, vel, 0f, radius);
        this.color = color;
        this.lifetime = lifetime;
        this.timer = 0;
    }

    @Override
    public void move(float dt) {
        super.move(dt);
        timer += dt;
    }

    public boolean isDead() {
        return (timer > lifetime);
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float alpha = PApplet.map(timer, 0, lifetime, 255, 0);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getPixelVector(radius, radius);
        p.fill(color, alpha);
        p.noStroke();
        //p.circle(pp[0], pp[1], 2*r[0]);
        p.ellipse(pp[0], pp[1], 2 * r[0], 2 * r[0]);
        p.popStyle();
    }
}
