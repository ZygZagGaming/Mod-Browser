@file:Suppress("unused")
package com.zygzag.modbrowser

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import com.terraformersmc.modmenu.ModMenu
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.gui.widget.AbstractButtonWidget

import net.minecraft.client.MinecraftClient

import com.terraformersmc.modmenu.config.ModMenuConfig
import com.terraformersmc.modmenu.gui.ModMenuOptionsScreen
import com.terraformersmc.modmenu.gui.ModsScreen

import com.terraformersmc.modmenu.gui.widget.ModMenuButtonWidget
import com.zygzag.modbrowser.widgets.ModBrowserButtonWidget

import net.fabricmc.fabric.api.client.screen.v1.Screens

import net.minecraft.client.gui.screen.TitleScreen

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents.AfterInit
import net.fabricmc.loader.FabricLoader
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

private val FABRIC_ICON_BUTTON_LOCATION: Identifier = Identifier(ModMenu.MOD_ID, "textures/gui/mods_button.png")

fun init() {
    ScreenEvents.AFTER_INIT.register(AfterInit { client: MinecraftClient?, screen: Screen?, scaledWidth: Int, scaledHeight: Int ->
        if (screen != null) {
            afterScreenInit(
                client,
                screen,
                scaledWidth,
                scaledHeight
            )
        }
    })
    main()
}

fun afterScreenInit(client: MinecraftClient?, screen: Screen, scaledWidth: Int, scaledHeight: Int) {
    if (screen is TitleScreen) {
        afterTitleScreenInit(screen)
    }
}

private fun afterTitleScreenInit(screen: Screen) {
    if (ModMenuConfig.MODIFY_TITLE_SCREEN.value) {
        val buttons = Screens.getButtons(screen)
        val spacing = 24
        val buttonsY: Int = screen.height / 4
        buttons.add(ModBrowserButtonWidget(
                screen.width / 2 + 2,
                buttonsY + spacing * 5 - 12,
                98,
                20,
                TranslatableText("Mod Browser (WIP)"),
                screen
            )
        )

    }
}

private fun shiftButtons(button: AbstractButtonWidget, shiftUp: Boolean, spacing: Int) {
    if (shiftUp) {
        button.y -= spacing / 2
    } else {
        button.y += spacing - spacing / 2
    }
}

fun getJSONObjectFromURL(url: URL): String {
    val sb = StringBuffer()
    val stream = Scanner(url.openStream())
    while (stream.hasNext()) {
        sb.append(stream.next())
    }
    return sb.toString()
}

fun main() {
    val parser = JsonParser()
    val url = URL("https://api.github.com/repos/ZygZagGaming/Mod-Browser-Files/contents/mods.json")
    val connection = url.openConnection()
    connection.getOutputStream()
    val s = getJSONObjectFromURL(url)
    val content = parser.parse(String(Base64.getDecoder().decode(parser.parse(JsonReader(StringReader(s))).asJsonObject.get("content").asString.replace("\n", ""))))
}
