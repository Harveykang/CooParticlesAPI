package cn.coostack.items

import cn.coostack.CooParticleAPI
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object CooItems {
    val items = ArrayList<Item>()

    val testParticle = register(
        "test_particle",
        TestParticleItem(Item.Settings())
    )

    fun register(id: String, item: Item): Item {
        val res = Registry.register(
            Registries.ITEM, Identifier.of(CooParticleAPI.MOD_ID, id), item
        )
        items.add(res)
        return res
    }

    fun reg(){
        CooParticleAPI.logger.info("注册物品成功")
    }

}