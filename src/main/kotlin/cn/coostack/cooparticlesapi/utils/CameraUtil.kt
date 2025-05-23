package cn.coostack.cooparticlesapi.utils

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import kotlin.random.Random

@Environment(EnvType.CLIENT)
object CameraUtil {
    var currentYawOffset = 0f
    var currentPitchOffset = 0f

    var currentXOffset = 0.0
    var currentYOffset = 0.0
    var currentZOffset = 0.0

    /**
     * @param tick 修改相机的位置
     */
    fun startShakeCamera(
        tick: Int, amplitude: Double
    ) {
        val random = Random((System.currentTimeMillis()))
        CooParticleAPI.scheduler.runTaskTimerMaxTick(tick) {
//            currentYawOffset = random.nextDouble(-amplitude, amplitude).toFloat()
//            currentPitchOffset = random.nextDouble(-amplitude, amplitude).toFloat()
            this.currentXOffset = random.nextDouble(-amplitude, amplitude)
            this.currentYOffset = random.nextDouble(-amplitude, amplitude)
            this.currentZOffset = random.nextDouble(-amplitude, amplitude)
        }.setFinishCallback {
            this.currentYawOffset = 0f
            this.currentPitchOffset = 0f
            this.currentXOffset = 0.0
            this.currentYOffset = 0.0
            this.currentZOffset = 0.0
        }
    }
}