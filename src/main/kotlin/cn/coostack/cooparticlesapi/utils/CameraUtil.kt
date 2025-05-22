package cn.coostack.cooparticlesapi.utils

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.Camera
import net.minecraft.entity.player.PlayerEntity
import java.awt.Canvas
import kotlin.random.Random

object CameraUtil {
    //public static float nextFloat(Random random, float min, float max) {
    //       return min >= max ? min : random.nextFloat() * (max - min) + min;
    //    }
    //
    //    @SubscribeEvent
    //    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
    //        float random = nextFloat(new Random(), -1.0F, 1.0F);
    //        setAngles(event, event.getPitch() + random, event.getRoll() + random, event.getYaw() + random);
    //    }
    //
    //    public static void setAngles(ViewportEvent.ComputeCameraAngles event, float pitch, float roll, float yaw) {
    //        event.setPitch(pitch);
    //        event.setRoll(roll);
    //        event.setYaw(yaw);
    //    }

    fun randomCamera() {
        val camera = MinecraftClient.getInstance().gameRenderer.camera
        val random = Random((System.currentTimeMillis()))
        CooParticleAPI.scheduler.runTaskTimerMaxTick(40) {
            camera.rotation.rotateXYZ(
                random.nextDouble(-1.0, 1.0).toFloat(),
                random.nextDouble(-1.0, 1.0).toFloat(),
                random.nextDouble(-1.0, 1.0).toFloat(),
            )
        }
    }
}