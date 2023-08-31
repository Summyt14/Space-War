package TPFinal.GameEntities;

import TPFinal.GivenClasses.SubPlot;
import processing.core.*;

import java.util.ArrayList;

public class PlayerBoid extends Boid {
    private double[] window;

    private float xvel = 0;
    private float yvel = 0;
    private float frict = 0.97f;
    private float vel = 0.004f;
    private float angle;
    private boolean isLeft, isRight, isUp, isDown;
    private PImage pointsImg;

    public ArrayList<LaserBoid> laserBoids;

    public PlayerBoid(PVector pos, float mass, float radius, int color, double[] window) {
        super(pos, mass, radius, color, window);
        this.window = window;
        laserBoids = new ArrayList<>();
        super.setHealth(100);
    }

    public void shoot(PApplet p) {
        PVector pos = this.pos.copy();
        PVector vel = PVector.mult(PVector.fromAngle(angle), 10f);
        vel.y = vel.y * -1;
        if (vel.y > 0) {
            pos.x += 0.35f;
            pos.y -= 0.2f;
        } else {
            pos.x -= 0.35f;
            pos.y += 0.2f;
        }
        LaserBoid laserBoid = new LaserBoid(pos, vel, 1f, 0.5f, p.color(255, 0, 0), window);
        laserBoids.add(laserBoid);
    }

    @Override
    public void setShape(PApplet p, SubPlot plt) {
        int rand = (int) super.random(1, 13);
        PImage img = p.loadImage("Player/playerShip" + rand + ".png");
        super.img = img;
        pointsImg = p.loadImage("UFO/ufo1.png");
    }

    public void displayHealthBar(PApplet p) {
        int health;
        if (this.getHealth() > 0)
            health = this.getHealth();
        else
            health = 0;

        p.pushMatrix();
        p.pushStyle();
        p.fill(69, 172, 0);
        p.noStroke();
        p.rect(p.width / 20, p.height * 0.95f, p.map(health, 0, 100, 0, p.width / 4), p.height * 0.01f);

        p.noFill();
        p.stroke(1, 158, 253);
        p.rect(p.width / 20, p.height * 0.95f, p.width / 4, p.height * 0.01f);
        p.noStroke();
        p.popStyle();
        p.popMatrix();
    }

    public void displayPoints(PApplet p, int ufosDestroyed) {
        p.pushMatrix();
        p.pushStyle();
        p.image(pointsImg, p.width * 0.97f, p.height * 0.945f, pointsImg.width / 3, pointsImg.height / 3);
        p.fill(69, 172, 0);
        p.textSize(28);
        p.textAlign(p.RIGHT, p.CENTER);
        p.text(ufosDestroyed, p.width * 0.95f, p.height * 0.94f);
        p.popStyle();
        p.popMatrix();
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.pushMatrix();
        angle = p.atan2(p.mouseY - pp[1], p.mouseX - pp[0]);
        p.translate(pp[0], pp[1]);
        p.rotate((float) (Math.PI / 2 + angle));
        p.imageMode(p.CENTER);
        p.image(super.img, 0, 0, img.width / 2, img.height / 2);
        p.popMatrix();

        for (LaserBoid laserBoid : laserBoids) {
            laserBoid.setShape(p, plt);
            laserBoid.display(p, plt);
        }

        laserBoids.removeIf((b) -> (b.pos.x > window[1] || b.pos.x < window[0] ||
                b.pos.y > window[3] || b.pos.y < window[2]));
    }

    @Override
    public void move(float dt) {
        pos.x = pos.x + xvel * vel;
        pos.y = pos.y + yvel * vel;
        if (isLeft) xvel = xvel - frict;
        if (isRight) xvel = xvel + frict;
        if (isDown) yvel = yvel + frict;
        if (isUp) yvel = yvel - frict;
        yvel = yvel * frict;
        xvel = xvel * frict;

        while (pos.x < window[0])
            pos.x += (window[1] - window[0]);
        while (pos.y < window[2])
            pos.y += (window[3] - window[2]);
        while (pos.x >= window[1])
            pos.x -= (window[1] - window[0]);
        while (pos.y >= window[3])
            pos.y -= (window[3] - window[2]);

        for (LaserBoid laserBoid : laserBoids) {
            laserBoid.move(dt);
        }
    }

    public boolean setMove(int k, boolean b) {
        switch (k) {
            case 83:
                return isUp = b;
            case 87:
                return isDown = b;
            case 65:
                return isLeft = b;
            case 68:
                return isRight = b;
            default:
                return b;
        }
    }
}
