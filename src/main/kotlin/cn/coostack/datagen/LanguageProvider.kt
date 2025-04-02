package cn.coostack.datagen

import cn.coostack.items.CooItems
import cn.coostack.items.group.CooItemGroup
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class LanguageProvider(
    dataOutput: FabricDataOutput?,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?
) : FabricLanguageProvider(dataOutput, "zh_cn", registryLookup) {
    override fun generateTranslations(lookup: RegistryWrapper.WrapperLookup, builder: TranslationBuilder) {
        builder.apply {
            add("item.coo_group", "§b粒子测试分组")
            add(CooItems.testParticle, "测试粒子物品")
        }
    }
}