package TPFinal.GameParticles;

import TPFinal.GivenClasses.ParticleSystem;
import processing.core.PVector;

public class ParticleUfoExplosion extends ParticleSystem {

    public ParticleUfoExplosion(PVector pos, PVector vel, float mass, PVector particleSpeed, int particleColor, float particleRadius, float lifetime) {
        super(pos, vel, mass, particleSpeed, particleColor, particleRadius, lifetime);
    }
}
