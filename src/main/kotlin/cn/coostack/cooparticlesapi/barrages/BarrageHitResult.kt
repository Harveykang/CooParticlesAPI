package cn.coostack.cooparticlesapi.barrages

import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity

class BarrageHitResult {
    var hitBlockState: BlockState? = null

    val entities = ArrayList<LivingEntity>()

}