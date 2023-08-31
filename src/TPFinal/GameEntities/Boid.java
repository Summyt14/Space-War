package TPFinal.GameEntities;

import TPFinal.GameParticles.ParticleLaserHit;
import TPFinal.GameParticles.ParticleMeteorExplosion;
import TPFinal.GameParticles.ParticleUfoExplosion;
import TPFinal.GivenClasses.Mover;
import TPFinal.GivenClasses.ParticleSystem;
import TPFinal.GivenClasses.SubPlot;
import TPFinal.Sounds.MediaPlayer;
import processing.core.*;

import java.util.ArrayList;

public abstract class Boid extends Mover {
    public int color;
    public PImage img;
    private BoidDNA dna;
    private double[] window;
    private float phiWander;
    private int health, attackDamage;

    public Boid(PVector pos, float mass, float radius, int color, double[] window) {
        super(pos, new PVector(), mass, radius);
        this.dna = new BoidDNA();
        this.color = color;
        this.window = window;
    }

    public float random(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

    public BoidDNA getDNA() {
        return dna;
    }

    public abstract void setShape(PApplet p, SubPlot plt);

    @Override
    public void applyForce(PVector f) {
        if (f != null) super.applyForce(f.limit(dna.maxForce));
    }

    public ArrayList<Boid> inCone(ArrayList<Boid> allBoids) {
        ArrayList<Boid> boidsInSight = new ArrayList<Boid>();
        for (Boid b : allBoids)
            if (inSight(b.pos, dna.visionDistance, dna.visionAngle))
                boidsInSight.add(b);
        return boidsInSight;
    }

    public boolean inSight(PVector t, float maxDistance, float maxAngle) {
        PVector r = PVector.sub(t, pos);
        float d = r.mag();
        float angle = PVector.angleBetween(vel, r);
        return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
    }

    public PVector seek(PVector target) {
        PVector vd = PVector.sub(target, this.pos);
        vd.normalize().mult(dna.maxSpeed);
        return PVector.sub(vd, vel);
    }

    public PVector flee(PVector target) {
        return seek(target).mult(-1);
    }

    public PVector pursuit(Boid b) {
        PVector d = PVector.mult(b.vel, dna.deltaTPursuit);
        PVector target = PVector.add(b.pos, d);
        return seek(target);
    }

    public PVector evade(Boid b) {
        return pursuit(b).mult(-1);
    }

    public PVector brake() {
        PVector vd = new PVector();
        return PVector.sub(vd, vel);
    }

    public PVector separate(ArrayList<Boid> boids) {
        PVector vd = this.vel.copy();
        float d = dna.visionSafeDistance;
        for (Boid b : boids) {
            if (inSight(b.pos, d, (float) Math.PI)) {
                PVector r = PVector.sub(pos, b.pos);
                d = r.mag();
                vd = PVector.div(r, d * d);
            }
        }
        // vd.normalize().mult(dna.maxSpeed);
        return PVector.sub(vd, vel);
    }

    public PVector align(ArrayList<Boid> boids) {
        PVector vd = this.vel.copy();
        for (Boid b : boids)
            vd.add(b.vel);
        vd.normalize().mult(dna.maxSpeed);
        return PVector.sub(vd, vel);
    }

    public PVector cohesion(ArrayList<Boid> boids) {
        PVector target = this.pos.copy();
        for (Boid b : boids)
            target.add(b.pos);
        target.div(boids.size() + 1);
        return seek(target);
    }

    public float getDistance(PVector target) {
        float x = target.x - this.pos.x;
        float y = target.y - this.pos.y;
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) + .01f;
    }

    public void setSpeedToTarget(PVector target, float maxDist, float maxSpeed) {
        float normalDist = this.getDistance(target) / maxDist;
        this.getDNA().maxSpeed = maxSpeed * normalDist;
    }

    public ArrayList<PVector> definePath(SubPlot plt) {
        ArrayList<PVector> definePath = new ArrayList<PVector>();

        int[] x = new int[]{300, 300, 900, 900};
        int[] y = new int[]{200, 700, 200, 700};

        for (int i = 0; i < x.length; i++) {
            double[] pp = plt.getWorldCoord(x[i], y[i]);
            PVector target = new PVector((float) pp[0], (float) pp[1]);
            definePath.add(target);
        }
        return definePath;
    }

    public PVector patrol(ArrayList<PVector> path) {
        if (this.getDistance(path.get(0)) < 0.3) {
            path.add(path.get(0));
            path.remove(0);
        }
        return this.seek(path.get(0));
    }

    public PVector wander() {
        PVector center = pos.copy();
        center.add(PVector.mult(vel, dna.deltaTWander));
        PVector target = new PVector(dna.radiusWander * PApplet.cos(dna.deltaPhiWander),
                dna.radiusWander * PApplet.sin(phiWander) * 3);
        target.add(center);
        phiWander += random(-dna.deltaPhiWander, dna.deltaPhiWander);
        return seek(target);
    }

    public PVector genRandPosOutside() {
        int randSide = (int) random(0, 4);
        float x = 0, y = 0;
        switch (randSide) {
            // Up
            case 0:
                x = random((float) window[0], (float) window[1]);
                y = (float) window[2] - 1;
                break;
            // Right
            case 1:
                x = (float) window[1] + 1;
                y = random((float) window[2], (float) window[3]);
                break;
            // Down
            case 2:
                x = random((float) window[0], (float) window[1]);
                y = (float) window[3] + 1;
                break;
            // Left
            case 3:
                x = (float) window[0] - 1;
                y = random((float) window[2], (float) window[3]);
                break;
        }
        return new PVector(x, y);
    }

    public boolean collided(Boid b) {
        float distSq = (pos.x - b.pos.x) * (pos.x - b.pos.x) + (pos.y - b.pos.y) * (pos.y - b.pos.y);
        float radSumSq = (radius + b.radius) * (radius + b.radius);
        if (distSq <= radSumSq) {
            b.setHealth(b.getHealth() - this.getAttackDamage());
            return true;
        }
        return false;
    }

    public ParticleSystem createNewParticles() {
        if (this.getClass().equals(TPFinal.GameEntities.UfoBoid.class)) {
            return new ParticleUfoExplosion(pos, new PVector(), mass, new PVector(), 255, radius, 5);
        } else if (this.getClass().equals(TPFinal.GameEntities.MeteorBoid.class)) {
            return new ParticleMeteorExplosion(pos, new PVector(), mass, new PVector(), 255, radius, 5);
        } else if (this.getClass().equals(TPFinal.GameEntities.LaserBoid.class)) {
            return new ParticleLaserHit(pos, new PVector(), mass, new PVector(), 255, radius, 5);
        }
        return null;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    protected void setAttackDamage(int dmg) {
        this.attackDamage = dmg;
    }

    public int getHealth() {
        return this.health;
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public abstract void display(PApplet p, SubPlot plt);

}
