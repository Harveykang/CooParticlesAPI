package cn.coostack

import cn.coostack.datagen.LanguageProvider
import cn.coostack.datagen.ItemModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object CooParticleAPIDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::LanguageProvider)
        pack.addProvider(::ItemModelProvider)
    }
}