package cn.coostack.barriers

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.Vec3d

class BarrierHitResult {
    var hitBlockState: BlockState? = null

    val entities = ArrayList<LivingEntity>()
}