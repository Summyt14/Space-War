package TPFinal.GameStates;

import TPFinal.GameEntities.*;
import TPFinal.GivenClasses.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Game {
    private SubPlot plt;
    private PImage background;
    private PlayerBoid playerBoid;
    private ArrayList<UfoBoid> ufos;
    private ArrayList<MeteorBoid> meteors;
    private ArrayList<ParticleSystem> gameParticles;
    private float radius = 0.5f;
    private float mass = 0.3f;
    private PVector f;
    private double lastTimeUfo, lastTimeMeteor, lastTimeDifficulty, difficulty;
    private int ufosDestroyed;

    public Game(PApplet p, SubPlot plt) {
        this.plt = plt;
        background = p.loadImage("space.jpg");
        background.resize(p.width, p.height);

        playerBoid = new PlayerBoid(new PVector(), mass, radius, p.color(255, 0, 0), plt.window);
        playerBoid.setShape(p, plt);

        ufos = new ArrayList<>();
        meteors = new ArrayList<>();
        gameParticles = new ArrayList<>();
        difficulty = 1;
    }

    public void draw(PApplet p, float dt) {
        p.pushStyle();
        p.background(background);

        for (LaserBoid laser : playerBoid.laserBoids) {
            checkCollisions(laser);
        }
        ufos.forEach(ufo -> playerBoid.laserBoids.removeIf(laser -> laser.collided(ufo)));
        meteors.forEach(meteor -> playerBoid.laserBoids.removeIf(laser -> laser.collided(meteor)));

        for (UfoBoid ufo : ufos) {
            ufo.display(p, plt);
            f = ufo.pursuit(playerBoid);
            ufo.applyForce(f);
            ufo.move(dt);
            checkCollisions(ufo);
        }
        if (ufos.removeIf(ufo -> ufo.collided(playerBoid)))
            ufosDestroyed++;

        for (MeteorBoid meteor : meteors) {
            meteor.display(p, plt);
            f = new PVector(meteor.pos.x, meteor.pos.y);
            f.mult(-1);
            meteor.applyForce(f);
            meteor.move(dt);
            checkCollisions(meteor);
        }
        meteors.removeIf(meteor -> meteor.collided(playerBoid));

        for (ParticleSystem particles : gameParticles) {
            particles.display(p, plt);
        }

        playerBoid.display(p, plt);
        playerBoid.displayHealthBar(p);
        playerBoid.displayPoints(p, ufosDestroyed);
        playerBoid.move(dt);

        timerEventTrigger(p);
        p.popStyle();
    }

    public void checkMousePressed(PApplet p) {
        playerBoid.shoot(p);
    }

    public void checkKeyPressed(PApplet p) {
        playerBoid.setMove(p.keyCode, true);
    }

    public void checkKeyReleased(PApplet p) {
        playerBoid.setMove(p.keyCode, false);
    }

    public void timerEventTrigger(PApplet p) {
        if ((double) System.currentTimeMillis() - lastTimeUfo > 3000 - difficulty) {
            UfoBoid ufoBoid = new UfoBoid(mass, radius, p.color(255, 0, 0), plt.window);
            ufoBoid.setShape(p, plt);
            ufos.add(ufoBoid);
            lastTimeUfo = (double) System.currentTimeMillis();
        }
        if ((double) System.currentTimeMillis() - lastTimeMeteor > 1500) {
            MeteorBoid meteorBoid = new MeteorBoid(radius, p.color(255, 0, 0), plt.window);
            meteorBoid.setShape(p, plt);
            meteors.add(meteorBoid);
            lastTimeMeteor = (double) System.currentTimeMillis();
        }
        if((double) System.currentTimeMillis() - lastTimeDifficulty > 10000) {
            difficulty += 200;
            lastTimeDifficulty = (double) System.currentTimeMillis();
        }
    }

    private void checkCollisions(Boid b) {
        if (!b.getClass().equals(TPFinal.GameEntities.LaserBoid.class) && b.collided(playerBoid)) {
            gameParticles.add(b.createNewParticles());
        } else if (b.getClass().equals(TPFinal.GameEntities.LaserBoid.class)) {
            for (UfoBoid ufo : ufos) {
                if (b.collided(ufo)) {
                    ufo.setHealth(ufo.getHealth() - b.getAttackDamage());
                }
            }
            if (ufos.removeIf(ufo -> ufo.getHealth() <= 0))
                ufosDestroyed++;

            for (MeteorBoid meteor : meteors) {
                if (b.collided(meteor)) {
                    meteor.setHealth(meteor.getHealth() - b.getAttackDamage());
                }
            }
            meteors.removeIf(meteor -> meteor.getHealth() <= 0);

            gameParticles.add(b.createNewParticles());
        }
    }

    public boolean gameEnded(){
        return playerBoid.getHealth() <= 0;
    }

    public int getScore(){
        return ufosDestroyed;
    }
}
