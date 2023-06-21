package gov.soultwist.syshud

import gov.soultwist.syshud.client.hud.HUDConstraints
import gov.soultwist.syshud.client.hud.object_45438_d
import gov.soultwist.syshud.util.ModConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import org.slf4j.LoggerFactory

object SyshudMain : ClientModInitializer {
    private val logger = LoggerFactory.getLogger("syshud")

	override fun onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Kotlin Mod SystemHUD (id: syshud) loaded")
		val hrc = HudRenderCallback.EVENT

		hrc.register(object_45438_d)
		ModConfig.loadFromFile()
	}
}