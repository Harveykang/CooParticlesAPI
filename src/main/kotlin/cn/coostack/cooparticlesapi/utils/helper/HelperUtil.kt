package cn.coostack.cooparticlesapi.utils.helper

import cn.coostack.cooparticlesapi.utils.helper.impl.GroupProgressSequencedHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.GroupScaleHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.ParticleAlphaHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleProgressSequencedHelper
import cn.coostack.cooparticlesapi.utils.helper.impl.StyleScaleHelper

object HelperUtil {

    fun sequencedStyle(maxCount: Int, progressMaxTick: Int): StyleProgressSequencedHelper {
        return StyleProgressSequencedHelper(
            maxCount, progressMaxTick,
        )
    }

    fun sequencedGroup(maxCount: Int, progressMaxTick: Int): GroupProgressSequencedHelper {
        return GroupProgressSequencedHelper(
            maxCount, progressMaxTick,
        )
    }

    fun scaleStyle(minScale: Double, maxScale: Double, scaleTick: Int): StyleScaleHelper =
        StyleScaleHelper(minScale, maxScale, scaleTick)

    fun scaleGroup(minScale: Double, maxScale: Double, scaleTick: Int): GroupScaleHelper =
        GroupScaleHelper(minScale, maxScale, scaleTick)

    fun particleAlpha(minAlpha: Double, maxAlpha: Double, alphaTick: Int): ParticleAlphaHelper {
        return ParticleAlphaHelper(minAlpha, maxAlpha, alphaTick)
    }

}