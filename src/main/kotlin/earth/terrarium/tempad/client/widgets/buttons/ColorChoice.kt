package earth.terrarium.tempad.client.widgets.buttons

import com.teamresourceful.resourcefullib.common.color.Color
import earth.terrarium.olympus.client.components.base.ListWidget.Item
import earth.terrarium.tempad.Tempad
import earth.terrarium.tempad.client.widgets.HorizontalListWidget
import earth.terrarium.tempad.common.utils.bedrockButton
import earth.terrarium.tempad.common.utils.darken
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractButton
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class ColorChoice(val color: Color, val updateColor: (ColorChoice) -> Unit): AbstractButton(0, 0, 14, 14, CommonComponents.EMPTY) {
    var selected = false

    override fun renderWidget(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val fillColor = Color(color.intRed, color.intGreen, color.intBlue, 255)
        graphics.bedrockButton(x, y, width, height, isHovered, active, selected, fillColor.value)
    }

    override fun updateWidgetNarration(pNarrationElementOutput: NarrationElementOutput) {
        this.defaultButtonNarrationText(pNarrationElementOutput)
    }

    override fun onPress() {
        updateColor(this)
    }

    override fun getRectangle(): ScreenRectangle = ScreenRectangle(x, y, width, height)
}