package cn.coostack.cooparticlesapi.utils.helper

import cn.coostack.cooparticlesapi.particles.Controlable
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

abstract class ScaleHelper(var minScale: Double, var maxScale: Double, var scaleTick: Int) : ParticleHelper {
    var current = 0
        protected set
    protected var step = abs(maxScale - minScale) / scaleTick

    init {
        val temp = min(minScale, maxScale)
        maxScale = max(minScale, maxScale)
        minScale = temp
    }

    fun recalculateStep(): ScaleHelper {
        val temp = min(minScale, maxScale)
        maxScale = max(minScale, maxScale)
        minScale = temp
        step = abs(maxScale - minScale) / scaleTick
        return this
    }

    fun toggleScale(scale: Double) {
        if (scale <= minScale) {
            resetScaleMin()
            return
        }
        if (scale >= maxScale) {
            current = scaleTick
            resetScaleMax()
            return
        }
        val point = scale - minScale
        val tick = (point / step).roundToInt()
        current = tick
        doScaleTo(current)
    }

    fun resetScaleMin() {
        if (getLoadedGroup() == null) {
            return
        }
        current = 0
        scale(minScale)
    }

    fun resetScaleMax() {
        if (getLoadedGroup() == null) {
            return
        }
        current = scaleTick
        scale(maxScale)
    }

    fun doScaleTo(current: Int) {
        if (getLoadedGroup() == null) {
            return
        }
        val enter = current.coerceAtLeast(0)
        this.current = enter
        if (current >= scaleTick) {
            resetScaleMax()
            return
        }
        if (current <= 0) {
            resetScaleMin()
            return
        }
        scale(minScale + step * enter)
    }

    fun doScale() {
        if (getLoadedGroup() == null) {
            return
        }
        if (over()) {
            return
        }
        current++
        scale(getGroupScale() + step)
    }

    fun doScaleReversed() {
        if (getLoadedGroup() == null) {
            return
        }
        if (isZero()) {
            return
        }
        current--
        scale(getGroupScale() - step)
    }

    fun over(): Boolean = scaleTick <= current
    fun isZero(): Boolean = current <= 0

    abstract fun getLoadedGroup(): Controlable<*>?

    abstract fun getGroupScale(): Double

    abstract fun scale(scale: Double)
}