package cn.coostack.cooparticlesapi.utils.helper

import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.helper.impl.GroupBezierValueScaleHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.GroupProgressSequencedHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.GroupScaleHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.ParticleAlphaHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleAlphaHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleBezierValueScaleHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleProgressSequencedHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleScaleHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleStatusHelper

object HelperUtil {

    fun sequencedStyle(maxCount: Int, progressMaxTick: Int): ProgressSequencedHelper {
        return StyleProgressSequencedHelper(
            maxCount, progressMaxTick,
        )
    }

    fun sequencedGroup(maxCount: Int, progressMaxTick: Int): ProgressSequencedHelper {
        return GroupProgressSequencedHelper(
            maxCount, progressMaxTick,
        )
    }

    fun scaleStyle(minScale: Double, maxScale: Double, scaleTick: Int): ScaleHelper =
        StyleScaleHelper(minScale, maxScale, scaleTick)

    fun bezierValueScaleStyle(
        minScale: Double,
        maxScale: Double,
        scaleTick: Int,
        c1: RelativeLocation,
        c2: RelativeLocation
    ): BezierValueScaleHelper {
        return StyleBezierValueScaleHelper(scaleTick, minScale, maxScale, c1, c2)
    }
    fun bezierValueScaleGroup(
        minScale: Double,
        maxScale: Double,
        scaleTick: Int,
        c1: RelativeLocation,
        c2: RelativeLocation
    ): BezierValueScaleHelper {
        return GroupBezierValueScaleHelper(scaleTick, minScale, maxScale, c1, c2)
    }

    fun scaleGroup(minScale: Double, maxScale: Double, scaleTick: Int): ScaleHelper =
        GroupScaleHelper(minScale, maxScale, scaleTick)

    fun scaleStatus(closedInterval: Int): StatusHelper =
        StyleStatusHelper().apply { this.closedInternal = closedInterval }

    fun alphaStyle(minAlpha: Double, maxAlpha: Double, alphaTick: Int): AlphaHelper {
        return StyleAlphaHelper(minAlpha, maxAlpha, alphaTick)
    }

    fun particleAlpha(minAlpha: Double, maxAlpha: Double, alphaTick: Int): AlphaHelper {
        return ParticleAlphaHelper(minAlpha, maxAlpha, alphaTick)
    }

}