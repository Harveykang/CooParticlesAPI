package cn.coostack.cooparticlesapi.test.entity

import net.minecraft.client.render.entity.animation.Animation
import net.minecraft.client.render.entity.animation.AnimationHelper
import net.minecraft.client.render.entity.animation.Keyframe
import net.minecraft.client.render.entity.animation.Transformation


object TestPlayerEntityAnimation {

    val IDLE: Animation = Animation.Builder.create(6f).looping()
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1.5f, AnimationHelper.createRotationalVector(-5f, 0f, 7.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    3f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    4.5f, AnimationHelper.createRotationalVector(5f, 0f, 7.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    6f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1.5f, AnimationHelper.createRotationalVector(5f, 0f, -7.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    3f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    4.5f, AnimationHelper.createRotationalVector(-5f, 0f, -7.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    6f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
    val STEP: Animation = Animation.Builder.create(1f)
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(-37.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(-42.5f, 0f, 27.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(15.25f, -5.06f, 21.96f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createRotationalVector(15.25f, -5.06f, 21.96f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(-89.41f, -17.07f, -16.4f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(30.19f, -9.76f, -14.69f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createRotationalVector(30.19f, -9.76f, -14.69f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createTranslationalVector(0f, 4f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.6766666f, AnimationHelper.createRotationalVector(24.2f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.8343334f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
    val SWEEP: Animation = Animation.Builder.create(1f)
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(-15.89f, 19.3f, -2.82f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createRotationalVector(31.71f, -28.44f, -13.85f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(-132.61f, 29.39f, -19.48f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createRotationalVector(-78.64f, -15.65f, -79.19f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(5f, 0f, -15f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createRotationalVector(47.09f, 5.72f, -23.22f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 1f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(-13.77f, 30.14f, 0.98f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createRotationalVector(24.78f, -3.18f, 9.42f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(0f, 5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.4167667f, AnimationHelper.createRotationalVector(-0.88f, -9.96f, 0.08f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
    val PUNCH: Animation = Animation.Builder.create(0.5f)
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.20834334f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(0f, -37.5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.20834334f, AnimationHelper.createTranslationalVector(-1f, 0f, 2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.20834334f, AnimationHelper.createRotationalVector(-90f, -15f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.3433333f, AnimationHelper.createRotationalVector(-90f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
    val WALK: Animation = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(32.48f, -1.34f, -10.39f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(-32.5f, 0f, -12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(32.48f, -1.34f, -10.39f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(-32.5f, 0f, -12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(32.48f, -1.34f, -10.39f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(-17.5f, 0f, 12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(17.5f, 0f, 12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(-17.5f, 0f, 12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(17.5f, 0f, 12.5f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(-17.5f, 0f, 12.5f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "Head",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, -2f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(27.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
    val RUN: Animation = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "Body",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(15.22f, -9.66f, -2.61f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(20.28f, 9.39f, 3.45f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(15.22f, -9.66f, -2.61f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(20.28f, 9.39f, 3.45f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(15.22f, -9.66f, -2.61f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, -1f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, -1f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(26.94f, 5.74f, -11.13f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(-17.68f, 1.62f, -12.4f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(-39.79f, -1.43f, -5.08f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(2.27f, 2.15f, -12.32f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(26.94f, 5.74f, -11.13f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(-17.68f, 1.62f, -12.4f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(-39.79f, -1.43f, -5.08f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(2.27f, 2.15f, -12.32f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(26.94f, 5.74f, -11.13f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, -1f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, -1f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 2f, -2f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, -1f, -2f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightArm",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(-39.65f, 3.25f, 3.92f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(2.96f, 0.99f, 12.67f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(26.94f, -5.74f, 11.13f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(-15.44f, 8.85f, 11.65f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(-39.65f, 3.25f, 3.92f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(2.96f, 0.99f, 12.67f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(26.94f, -5.74f, 11.13f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(-15.44f, 8.85f, 11.65f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(-39.65f, 3.25f, 3.92f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "Head",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 3f, -3f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, -1.22f, -2.37f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 3f, -3f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, -1.22f, -2.37f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "RightLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0f, AnimationHelper.createTranslationalVector(0f, -1.22f, -2.37f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createTranslationalVector(0f, 3f, -3f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createTranslationalVector(0f, -1.22f, -2.37f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 1f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createTranslationalVector(0f, 3f, -3f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createTranslationalVector(0f, -1.22f, -2.37f),
                    Transformation.Interpolations.CUBIC
                )
            )
        )
        .addBoneAnimation(
            "LeftLeg",
            Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.125f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.25f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.375f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.5f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.625f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.75f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    0.875f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                ),
                Keyframe(
                    1f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                    Transformation.Interpolations.CUBIC
                )
            )
        ).build()
}