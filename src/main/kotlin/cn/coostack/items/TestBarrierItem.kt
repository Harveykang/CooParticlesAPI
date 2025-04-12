package cn.coostack.items

import cn.coostack.barriers.BarrierManager
import cn.coostack.barriers.BarrierOption
import cn.coostack.barriers.HitBox
import cn.coostack.test.barrier.SwordBarrier
import cn.coostack.test.particle.server.BarrierSwordGroupServer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.function.Predicate

class TestBarrierItem : Item(Settings().maxCount(1)) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val res = TypedActionResult.success(user.getStackInHand(hand))
        if (world.isClient) {
            return res
        }
        val box = HitBox.of(2.0, 2.0, 2.0)
        val search = HitBox.of(50.0, 50.0, 50.0)
        val filter = Predicate<LivingEntity> {
            return@Predicate it.uuid != user.uuid
        }
        val barrier = SwordBarrier(
            user.eyePos, world as ServerWorld,
            box, BarrierSwordGroupServer(search, filter,user.rotationVector),
            BarrierOption().apply {
                acrossBlock = true
                maxLivingTick = 150
                enableSpeed = true
                speed = 1.5
            }, filter, search
        )
        barrier.direction = user.rotationVector
        BarrierManager.spawn(barrier)
        return res
    }
}