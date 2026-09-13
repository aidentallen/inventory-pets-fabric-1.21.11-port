/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.fx;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class QuantumParticles
extends TextureSheetParticle {
    private final double coordX;
    private final double coordY;
    private final double coordZ;

    protected QuantumParticles(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.friction = 0.4f;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.coordX = xCoord;
        this.coordY = yCoord;
        this.coordZ = zCoord;
        this.quadSize = 0.1f * (this.random.nextFloat() * 0.2f + 0.1f);
        this.rCol = 0.54f;
        this.gCol = 0.0f;
        this.bCol = 1.74f;
        this.lifetime = (int)(Math.random() * 4.0) + 30;
        this.setSpriteFromAge(spriteSet);
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    public int getLightColor(float partialTick) {
        int i = super.getLightColor(partialTick);
        float f = (float)this.age / (float)this.lifetime;
        f *= f;
        f *= f;
        int j = i & 0xFF;
        int k = i >> 16 & 0xFF;
        if ((k += (int)(f * 15.0f * 16.0f)) > 240) {
            k = 240;
        }
        return j | k << 16;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        float f = this.age / this.lifetime;
        float f1 = -f + f * f * 2.0f;
        float f2 = 1.0f - f1;
        this.x = this.coordX + this.xd * (double)f2;
        this.y = this.coordY + this.yd * (double)f2 + (double)(1.0f - f);
        this.z = this.coordZ + this.zd * (double)f2;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            QuantumParticles quantumparticle = new QuantumParticles(level, x, y, z, this.sprites, dx, dy, dz);
            quantumparticle.pickSprite(this.sprites);
            return quantumparticle;
        }
    }
}

