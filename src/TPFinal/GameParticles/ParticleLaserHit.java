package TPFinal.GameParticles;

import TPFinal.GivenClasses.ParticleSystem;
import processing.core.PVector;

public class ParticleLaserHit extends ParticleSystem {

    public ParticleLaserHit(PVector pos, PVector vel, float mass, PVector particleSpeed, int particleColor, float particleRadius, float lifetime) {
        super(pos, vel, mass, particleSpeed, particleColor, particleRadius, lifetime);
    }
}
