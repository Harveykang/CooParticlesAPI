package cn.coostack.cooparticlesapi.test.particle.style.sub

import cn.coostack.cooparticlesapi.particles.ParticleDisplayer
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroup
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudEffect
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodEffect
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder
import java.util.UUID
import kotlin.math.PI

class MagicSubGroup(uuid: UUID, val bindPlayer: UUID) : ControlableParticleGroup(uuid) {
    override fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation> {
        return PointsBuilder()
            .addCircle(4.0, 120)
            .pointsOnEach { it.y += 0.5 }
            .addCircle(3.5, 120)
            .addPolygonInCircle(3, 60, 2.0)
            .rotateAsAxis(PI / 3)
            .addPolygonInCircle(3, 60, 2.0)
            .addCircle(2.0, 120)
            .addWith {
                connectLines(
                    getCircleXZ(4.0, 16).onEach { it.y += 0.5 },
                    getCircleXZ(3.5, 16),
                    10
                ).flatten()
            }
            .addLine(
                RelativeLocation(), RelativeLocation.yAxis().multiplyClone(8.0), 40
            )
            .createWithParticleEffects {
                withEffect(
                    {
                        ParticleDisplayer.withSingle(
                            TestEndRodEffect(it)
                        )
                    }
                ) {
                    colorOfRGB(255, 190, 212)
                    maxAge = 120
                }
            }
    }

    override fun onGroupDisplay() {
        addPreTickAction {
            val player = world!!.getPlayerByUuid(bindPlayer) ?: return@addPreTickAction
            val playerPos = player.pos
            val to = origin.relativize(playerPos)
            rotateToWithAngle(RelativeLocation.of(to), PI / 72)
        }
    }
}