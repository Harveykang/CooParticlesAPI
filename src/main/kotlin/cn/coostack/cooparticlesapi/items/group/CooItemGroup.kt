package cn.coostack.cooparticlesapi.items.group

import cn.coostack.cooparticlesapi.CooParticleAPI
import cn.coostack.cooparticlesapi.items.CooItems
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object CooItemGroup {

    val COO_GROUP: ItemGroup = Registry.register(
        Registries.ITEM_GROUP, Identifier.of(CooParticleAPI.MOD_ID, "coo_group"),
        ItemGroup.Builder(null, -1)
            .displayName(Text.translatable("item.coo_group"))
            .icon { ItemStack(Items.BOW) }
            .entries { _, entries ->
                CooItems.items.forEach(entries::add)
            }
            .build()
    )


    fun reg(){
        CooParticleAPI.logger.info("注册物品分组成功")
    }
}