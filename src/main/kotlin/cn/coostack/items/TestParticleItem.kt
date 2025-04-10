package cn.coostack.items

import cn.coostack.network.buffer.ParticleControlerDataBuffers
import cn.coostack.network.particle.ScaleCircleGroupServer
import cn.coostack.network.particle.ServerParticleGroupManager
import cn.coostack.network.particle.TestParticleGroup
import cn.coostack.particles.control.group.impl.ScaleCircleGroupClient
import cn.coostack.particles.control.group.impl.TestGroupClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class TestParticleItem(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (world.isClient) {
            return TypedActionResult.success(user.getStackInHand(hand))
        }
        val serverGroup = ScaleCircleGroupServer(user.uuid)
        ServerParticleGroupManager.addParticleGroup(
            ScaleCircleGroupClient::class.java, serverGroup, user.pos, world as ServerWorld
        )
        return super.use(world, user, hand)
    }

}