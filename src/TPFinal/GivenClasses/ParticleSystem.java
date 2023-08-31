package TPFinal.GivenClasses;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class ParticleSystem extends Mover {
    private ArrayList<Particle> particles;
    private PVector particleSpeed;
    private int particleColor;
    private float lifetime;

    public ParticleSystem(PVector pos, PVector vel, float mass, PVector particleSpeed,
                          int particleColor, float particleRadius, float lifetime) {
        super(pos, vel, mass, particleRadius);
        this.particleSpeed = particleSpeed;
        this.particleColor = particleColor;
        this.lifetime = lifetime;
        this.particles = new ArrayList<>();
    }

    private double random(double min, double max) {
        return (min + (max - min) * Math.random());
    }

    private void addParticle() {
        float vx = (float) (particleSpeed.x * 2 * (Math.random() - 0.5));
        float vy = (float) (particleSpeed.y * 2 * (Math.random() - 0.5));

        PVector ppos = new PVector((float) (pos.x + random(-0.3, 0.3)),
                (float) (pos.y + random(-0.3, 0.3)));

        particles.add(new Particle(ppos, new PVector(vx, vy), particleColor, radius, lifetime));
    }

    @Override
    public void move(float dt) {
        super.move(dt);
        addParticle();
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.move(dt);
            if (p.isDead()) particles.remove(i);
        }
    }

    public void display(PApplet p, SubPlot plt) {
        for (Particle particle : particles) {
            particle.display(p, plt);
        }
    }
}