package cn.coostack.datagen

import cn.coostack.items.CooItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Model
import net.minecraft.data.client.Models

class ItemModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(gen: BlockStateModelGenerator) {

    }

    override fun generateItemModels(gen: ItemModelGenerator) {
        gen.apply {
            register(CooItems.testParticle, Models.GENERATED)
        }
    }
}