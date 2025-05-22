package cn.coostack.cooparticlesapi.test.entity

import net.minecraft.client.render.entity.animation.Animation
import net.minecraft.client.render.entity.animation.AnimationHelper
import net.minecraft.client.render.entity.animation.Keyframe
import net.minecraft.client.render.entity.animation.Transformation


object TestEntityAnimation {
    val rotate: Animation = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "head",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, -360f, 0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        ).build()
}