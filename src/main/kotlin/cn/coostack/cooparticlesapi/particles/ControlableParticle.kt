package cn.coostack.cooparticlesapi.particles

import cn.coostack.cooparticlesapi.particles.control.ControlParticleManager
import cn.coostack.cooparticlesapi.particles.control.ParticleControler
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.particle.SpriteBillboardParticle
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import org.joml.Vector2f
import org.joml.Vector3f
import java.util.UUID

@Environment(EnvType.CLIENT)
abstract class ControlableParticle(
    world: ClientWorld,
    pos: Vec3d,
    velocity: Vec3d,
    val controlUUID: UUID
) : SpriteBillboardParticle(world, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z) {
    val controler: ParticleControler = ControlParticleManager.getControl(controlUUID)!!

    /**
     * 是否调用 net.minecraft.client.particle.Particle中的tick方法
     */
    var minecraftTick: Boolean = false

    /**
     * @see x
     * @see y
     * @see z
     */
    var pos: Vec3d
        get() = Vec3d(x, y, z)
        set(value) {
            this.x = value.x
            this.y = value.y
            this.z = value.z
        }

    /**
     * @see prevPosX
     * @see prevPosY
     * @see prevPosZ
     */
    var prevPos: Vec3d
        get() = Vec3d(prevPosX, prevPosY, prevPosZ)
        set(value) {
            this.prevPosX = value.x
            this.prevPosY = value.y
            this.prevPosZ = value.z
        }

    /**
     * @see velocityX
     * @see velocityY
     * @see velocityZ
     */
    var velocity: Vec3d
        get() = Vec3d(velocityX, velocityY, velocityZ)
        set(value) {
            this.velocityX = value.x
            this.velocityY = value.y
            this.velocityZ = value.z
        }

    /**
     * @see boundingBox
     */
    var bounding: Box
        get() = boundingBox
        set(value) {
            boundingBox = value
        }

    /**
     * @see onGround
     */
    var onTheGround: Boolean
        get() = onGround
        set(value) {
            onGround = value
        }

    /**
     * @see collidesWithWorld
     */
    var collidesWithTheWorld: Boolean
        get() = collidesWithWorld
        set(value) {
            collidesWithWorld = value
        }

    /**
     * @see dead
     */
    var death: Boolean
        get() = dead
        set(value) {
            dead = value
        }

    /**
     * @see spacingXZ
     * @see spacingY
     */
    var spacing: Vector2f
        get() = Vector2f(spacingXZ, spacingY)
        set(value) {
            spacingXZ = value.x
            spacingY = value.y
        }

    /**
     * @see random
     */
    val rand: Random
        get() = random

    /**
     * @see age
     */
    var currentAge: Int
        get() = age
        set(value) {
            age = value
        }


    /**
     * @see gravityStrength
     */
    var gravity: Float
        get() = gravityStrength
        set(value) {
            gravityStrength = value
        }

    var color: Vector3f
        get() = Vector3f(red, green, blue)
        set(value) {
            red = value.x
            green = value.y
            blue = value.z
        }

    var particleAlpha: Float
        get() = alpha
        set(value) {
            alpha = value
        }

    /**
     * @see prevAngle
     */
    var previewAngle: Float
        get() = prevAngle
        set(value) {
            prevAngle = value
        }

    /**
     * @see angle
     */
    var currentAngle: Float
        get() = angle
        set(value) {
            angle = value
        }

    /**
     * @see velocityMultiplier
     */
    var velocityMulti: Float
        get() = velocityMultiplier
        set(value) {
            velocityMultiplier = value
        }


    /**
     * @see ascending
     * 让粒子乱飘的罪恶源头?
     */
    var canAscending: Boolean
        get() = ascending
        set(value) {
            ascending = value
        }


    private var lastPreview = cloneVec(pos)
    private var update = false
    fun teleportTo(pos: Vec3d) {
        lastPreview = cloneVec(pos)
        update = true
    }

    fun teleportTo(x: Double, y: Double, z: Double) {
        lastPreview = Vec3d(x, y, z)
        update = true
    }

    init {
        controler.loadParticle(this)
        controler.particleInit()
    }

    /**
     * 请使用做为tick方法
     * @see ParticleControler.addPreTickAction
     */
    final override fun tick() {
        if (age > maxAge) {
            age = maxAge
        }

        if (minecraftTick) {
            super.tick()
        }
        controler.doTick()
        if (update) {
            prevPos = this.pos
            this.pos = lastPreview
            update = false
        }
    }

    private fun cloneVec(vec: Vec3d): Vec3d {
        return Vec3d(vec.x, vec.y, vec.z)
    }

}
