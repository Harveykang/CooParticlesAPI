package cn.coostack.mixin;


import cn.coostack.CooParticleAPI;
import cn.coostack.config.APIConfigManager;
import com.google.common.collect.EvictingQueue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Accessor("newParticles")
    public abstract Queue<Particle> getNewParticles();

    @Accessor("particles")
    public abstract Map<ParticleTextureSheet, Queue<Particle>> getParticles();

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Queue;poll()Ljava/lang/Object;"))
    public Object changeMaxParticles(Queue<Object> queue) {
        if (APIConfigManager.getConfig().getEnabledParticleCountInject()) {
            Particle particle;
            while ((particle = this.getNewParticles().poll()) != null) {
                Map<ParticleTextureSheet, Queue<Particle>> particles = this.getParticles();
                particles.computeIfAbsent(particle.getType(),
                                sheet -> EvictingQueue.create(APIConfigManager.getConfig().getParticleCountLimit()))
                        .add(particle);
            }
            return null;
        } else {
            // 恢复原来的功能
            return this.getNewParticles().poll();
        }
    }
}
