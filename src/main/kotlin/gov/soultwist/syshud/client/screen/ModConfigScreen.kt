package gov.soultwist.syshud.client.screen

import gov.soultwist.syshud.util.ModConfig
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

object ModConfigScreen {
    fun getConfigScreen(parent: Screen?): ConfigBuilder {
        val builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.translatable("syshud.config_title"))
            .setSavingRunnable {
                ModConfig.saveToFile()
                ModConfig.loadFromFile()
            }

        //Catagories
        val general = builder.getOrCreateCategory(Text.translatable("syshud.config_title"))
        val customization = builder.getOrCreateCategory(Text.translatable("syshud.advanced_hud_title"))

        //Entries by Category
        //General
        addBooleanEntry(general, builder, ModConfig.ENABLE_SYSTEM_TIME)
        addBooleanEntry(general, builder, ModConfig.ENABLE_CLIENT_VERSION)
        addBooleanEntry(general, builder, ModConfig.ENABLE_PC_SPECS)
        addBooleanEntry(general, builder, ModConfig.HIDE_JRE_ARCHITECTURE)
        addBooleanEntry(general, builder, ModConfig.HIDE_JRE_VENDOR)

        //Advanced HUD Settings
        addBooleanEntry(customization, builder, ModConfig.ENABLE_MULTILINE)
        if (!ModConfig.ENABLE_MULTILINE.value()) {
            addStringEntry(customization, builder, ModConfig.DATE_AND_TIME_FORMATTING)
        } else {
            addStringEntry(customization, builder, ModConfig.DATE_FORMATTING)
            addStringEntry(customization, builder, ModConfig.TIME_FORMATTING)
            addBooleanEntry(customization, builder, ModConfig.FLIP_DATE_AND_TIME)
        }
        addIntEntry(customization, builder, ModConfig.HUD_HSTACK_PADDING, 0, 20)
        addIntEntry(customization, builder, ModConfig.HUD_VSTACK_PADDING, 0, 20)
        addBooleanEntry(customization, builder, ModConfig.TEXT_SHADOW)

        return builder
    }

    private fun addStringEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<String>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startStrField(Text.translatable("syshud.configs." + value.name() + ".label"), value.value())
                .setDefaultValue(value.defaultValue())
                .setTooltip(Text.translatable("syshud.configs." + value.name() + ".description"))
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

    private fun addBooleanEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<Boolean>
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startBooleanToggle(Text.translatable("syshud.configs." + value.name() + ".label"), value.value())
                .setDefaultValue(value.defaultValue())
                .setTooltip(Text.translatable("syshud.configs." + value.name() + ".description"))
                .setSaveConsumer(value::setValue)
                .build()
        )
    }

    private fun addIntEntry(
        category: ConfigCategory, builder: ConfigBuilder,
        value: ModConfig.Value<Int>, min: Int, max: Int
    ) {
        category.addEntry(
            builder.entryBuilder()
                .startIntSlider(
                    Text.translatable("syshud.configs." + value.name() + ".label"),
                    value.value(),
                    min,
                    max
                )
                .setDefaultValue(value.defaultValue())
                .setTooltip(Text.translatable("syshud.configs." + value.name() + ".description"))
                .setSaveConsumer(value::setValue)
                .build()
        )
    }
}