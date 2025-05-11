package cn.coostack.cooparticlesapi.network.particle.style

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer
import cn.coostack.cooparticlesapi.particles.ParticleDisplayer
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleScaleHelper
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.Vec3d
import java.util.UUID

/**
 * 为了方便制作子图形作用于父图形 (进行不同方向的旋转, rotate)
 * 专门创建此类
 *
 * 此类没有Provider 因此无法单独生成
 *
 * 继承此类的目的是为这个类创建一个专属形状的子类
 * 这种子类可以拥有Provider
 */
open class ParticleShapeStyle(uuid: UUID) :
    ParticleGroupStyle(64.0, uuid) {
    val scaleHelper = StyleScaleHelper(1.0, 1.0, 1)
    private var onDisplayInvoke: ParticleShapeStyle.() -> Unit = {}
    private var beforeDisplayInvoke: ParticleShapeStyle.(Map<StyleData, RelativeLocation>) -> Unit = {}
    private val pointBuilders = LinkedHashMap<PointsBuilder, (RelativeLocation) -> StyleData>()

    /**
     * 设置为true时 会利用scaleHelper 每tick增长一点
     */
    var scalePreTick = false
        private set

    /**
     * 设置为true时 利用scaleHelper 每tick减弱一点
     */
    var scaleReversed = false
        private set

    fun appendBuilder(pointsBuilder: PointsBuilder, dataBuilder: (RelativeLocation) -> StyleData): ParticleShapeStyle {
        pointBuilders[pointsBuilder] = dataBuilder
        return this
    }

    fun appendPoint(point: RelativeLocation, dataBuilder: (RelativeLocation) -> StyleData): ParticleShapeStyle {
        pointBuilders[
            PointsBuilder().also { it.addPoint(point) }
        ] = dataBuilder
        return this
    }

    /**
     * @param max 直接让scale从最大开始
     */
    fun scaleReversed(max: Boolean): ParticleShapeStyle {
        scaleReversed = true
        if (max) {
            scaleHelper.toggleScale(scaleHelper.maxScale)
        }
        return this
    }

    fun loadScaleHelper(minScale: Double, maxScale: Double, scaleTick: Int): ParticleShapeStyle {
        scaleHelper.minScale = minScale
        scaleHelper.maxScale = maxScale
        scaleHelper.scaleTick = scaleTick
        scalePreTick = true
        scaleHelper.loadControler(this)
        scaleHelper.recalculateStep()
        return this
    }

    /**
     * 在display之前执行
     */
    fun toggleOnDisplay(toggleMethod: ParticleShapeStyle.() -> Unit): ParticleShapeStyle {
        onDisplayInvoke = toggleMethod
        return this
    }

    /**
     * 在生成粒子之前执行
     */
    fun toggleBeforeDisplay(toggleMethod: ParticleShapeStyle.(Map<StyleData, RelativeLocation>) -> Unit): ParticleShapeStyle {
        beforeDisplayInvoke = toggleMethod
        return this
    }

    override fun beforeDisplay(styles: Map<StyleData, RelativeLocation>) {
        beforeDisplayInvoke(styles)
    }

    override fun getCurrentFrames(): Map<StyleData, RelativeLocation> {
        val res = HashMap<StyleData, RelativeLocation>()
        pointBuilders.forEach { entry ->
            res.putAll(entry.key.createWithStyleData { entry.value(it) })
        }
        return res
    }

    override fun onDisplay() {
        onDisplayInvoke()
        addPreTickAction {
            if (!scalePreTick) {
                return@addPreTickAction
            }

            if (scaleReversed) {
                scaleHelper.doScaleReversed()
            } else {
                scaleHelper.doScale()
            }
        }
    }

    override fun writePacketArgs(): Map<String, ParticleControlerDataBuffer<*>> {
        return mapOf()
    }

    override fun readPacketArgs(args: Map<String, ParticleControlerDataBuffer<*>>) {
    }

    fun fastRotateToPlayerView(player: PlayerEntity) {
        rotateParticlesToPoint(RelativeLocation.of(player.rotationVector))
    }

    fun fastStyleData(color: Vec3d, displayer: (UUID) -> ParticleDisplayer): StyleData {
        return StyleData(displayer).withParticleHandler {
            this.colorOfRGB(color.x.toInt(), color.y.toInt(), color.z.toInt())
        }
    }

    fun fastStyleData(color: Vec3d, sheet: ParticleTextureSheet, displayer: (UUID) -> ParticleDisplayer): StyleData {
        return StyleData(displayer).withParticleHandler {
            this.colorOfRGB(color.x.toInt(), color.y.toInt(), color.z.toInt())
            this.textureSheet = sheet
        }
    }

    fun fastStyleData(
        color: Vec3d,
        sheet: ParticleTextureSheet,
        size: Float,
        displayer: (UUID) -> ParticleDisplayer
    ): StyleData {
        return StyleData(displayer).withParticleHandler {
            this.colorOfRGB(color.x.toInt(), color.y.toInt(), color.z.toInt())
            this.textureSheet = sheet
            this.size = size
        }
    }

    fun fastStyleData(
        color: Vec3d,
        sheet: ParticleTextureSheet,
        size: Float,
        alpha: Float,
        displayer: (UUID) -> ParticleDisplayer
    ): StyleData {
        return StyleData(displayer).withParticleHandler {
            this.colorOfRGB(color.x.toInt(), color.y.toInt(), color.z.toInt())
            this.textureSheet = sheet
            this.size = size
            this.particleAlpha = alpha
        }
    }

    fun fastStyleData(sheet: ParticleTextureSheet, displayer: (UUID) -> ParticleDisplayer): StyleData {
        return StyleData(displayer).withParticleHandler {
            this.textureSheet = sheet
        }
    }
}