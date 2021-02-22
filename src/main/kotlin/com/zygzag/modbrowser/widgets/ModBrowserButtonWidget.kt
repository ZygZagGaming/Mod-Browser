package com.zygzag.modbrowser.widgets

import com.terraformersmc.modmenu.gui.ModsScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text

class ModBrowserButtonWidget(x: Int, y: Int, width: Int, height: Int, text: Text?, screen: Screen?): ButtonWidget(x, y, width, height, text, { MinecraftClient.getInstance().openScreen(ModsScreen(screen)) })