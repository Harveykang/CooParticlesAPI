package cn.coostack.cooparticlesapi.network.particle.emitters.impl

import cn.coostack.cooparticlesapi.network.particle.emitters.ClassParticleEmitters
import cn.coostack.cooparticlesapi.network.particle.emitters.ControlableParticleData
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmitters
import cn.coostack.cooparticlesapi.network.particle.emitters.PhysicConstant
import cn.coostack.cooparticlesapi.network.particle.emitters.environment.wind.GlobalWindDirection
import cn.coostack.cooparticlesapi.network.particle.emitters.environment.wind.WindDirections
import cn.coostack.cooparticlesapi.particles.control.ParticleControler
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.UUID
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

class FireClassParticleEmitters(var player: UUID, pos: Vec3d, world: World?) : ClassParticleEmitters(pos, world) {
    var templateData = ControlableParticleData()
    var fireSize = 0.5
    var fireForce = 1.0

    init {
        airDensity = PhysicConstant.SEA_AIR_DENSITY
//        mass = 1000.0
        wind = GlobalWindDirection(
            Vec3d(0.0, fireForce * 5, 0.0)
        )
            .loadEmitters(this)
    }

    companion object {
        const val ID = "fire-class-particle-emitters"

        @JvmStatic
        val CODEC = PacketCodec.ofStatic<RegistryByteBuf, ParticleEmitters>(
            { buf, data ->
                data as FireClassParticleEmitters
                buf.writeUuid(data.player)
                encodeBase(data, buf)
                buf.writeDouble(data.fireSize)
                buf.writeDouble(data.fireForce)
                ControlableParticleData.PACKET_CODEC.encode(buf, data.templateData)
            }, {
                val player = it.readUuid()
                val instance = FireClassParticleEmitters(player, Vec3d.ZERO, null)
                decodeBase(instance, it)
                instance.fireSize = it.readDouble()
                instance.fireForce = it.readDouble()
                instance.templateData = ControlableParticleData.PACKET_CODEC.decode(it)
                instance
            }
        )
    }

    override fun doTick() {
        val player = world!!.getPlayerByUuid(player)!!
        pos = player.eyePos
        val size = wind.direction.length()
        wind.direction = player.rotationVector.normalize().multiply(size)
    }

    override fun genParticles(): Map<ControlableParticleData, RelativeLocation> {
        val velocityList = PointsBuilder()
            .addRoundShape(fireSize, 0.25, 10, (120 * fireSize).roundToInt())
            .pointsOnEach { it.y += 1.0 }
            .rotateTo(
                world!!.getPlayerByUuid(player)!!.rotationVector
            )
            .create()
        val res = HashMap<ControlableParticleData, RelativeLocation>()
        val random = Random(System.currentTimeMillis())
        val count = random.nextInt(20, 120)
        for (i in 0 until count) {
            val it = velocityList.random()
            res[templateData.clone().apply {
                this.velocity = it.normalize().multiply(fireForce).toVector()
            }] = RelativeLocation()
        }
        return res
    }

    val random = Random(System.currentTimeMillis())
    override fun singleParticleAction(
        controler: ParticleControler,
        data: ControlableParticleData,
        spawnPos: Vec3d,
        spawnWorld: World
    ) {
        data.velocity = data.velocity.add(
            Vec3d(
                random.nextDouble(-fireForce, fireForce),
                random.nextDouble(-fireForce, fireForce),
                random.nextDouble(-fireForce, fireForce)
            ).normalize().multiply(0.25)
        )
        data.color = Math3DUtil.colorOf(
            random.nextInt(200,255),
            random.nextInt(200,255),
            random.nextInt(200,255),
        )
        controler.addPreTickAction {
            updatePhysics(controler.particle.pos, data)
        }
    }


    override fun getEmittersID(): String {
        return ID
    }

    override fun getCodec(): PacketCodec<RegistryByteBuf, ParticleEmitters> {
        return CODEC
    }
}