package earth.terrarium.tempad.common.registries

import com.teamresourceful.resourcefullib.common.menu.MenuContentHelper
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry
import com.teamresourceful.resourcefullibkt.common.getValue
import earth.terrarium.tempad.Tempad
import earth.terrarium.tempad.common.apps.*
import earth.terrarium.tempad.common.menu.AbstractTempadMenu
import earth.terrarium.tempad.common.menu.FuelMenu
import earth.terrarium.tempad.common.utils.RecordCodecMenuContentSerializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.MenuType
import java.util.Optional

object ModMenus {
    val REGISTRY: ResourcefulRegistry<MenuType<*>> = ResourcefulRegistries.create(BuiltInRegistries.MENU, Tempad.MOD_ID)

    class TeleportMenu(id: Int, inv: Inventory, data: Optional<TeleportData>): AbstractTempadMenu<TeleportData>(id, inv, TELEPORT_MENU, data)

    val TELEPORT_MENU: MenuType<TeleportMenu> by REGISTRY.register("teleport") {
        MenuContentHelper.create(
            ::TeleportMenu,
            RecordCodecMenuContentSerializer(TeleportData.CODEC)
        )
    }

    class SettingsMenu(id: Int, inv: Inventory, data: Optional<SettingsData>): AbstractTempadMenu<SettingsData>(id, inv, SETTINGS_MENU, data)

    val SETTINGS_MENU: MenuType<SettingsMenu> by REGISTRY.register("settings") {
        MenuContentHelper.create(
            ::SettingsMenu,
            RecordCodecMenuContentSerializer(SettingsData.CODEC)
        )
    }

    class NewLocationMenu(id: Int, inv: Inventory, data: Optional<NewLocationData>): AbstractTempadMenu<NewLocationData>(id, inv, NEW_LOCATION_MENU, data)

    val NEW_LOCATION_MENU: MenuType<NewLocationMenu> by REGISTRY.register("new_location") {
        MenuContentHelper.create(
            ::NewLocationMenu,
            RecordCodecMenuContentSerializer(NewLocationData.CODEC)
        )
    }

    val FUEL_MENU: MenuType<FuelMenu> by REGISTRY.register("fuel") {
        MenuContentHelper.create(
            ::FuelMenu,
            RecordCodecMenuContentSerializer(BasicAppContent.CODEC)
        )
    }

    class TimelineMenu(id: Int, inv: Inventory, data: Optional<TimelineData>): AbstractTempadMenu<TimelineData>(id, inv, TIMELINE_MENU, data)

    val TIMELINE_MENU: MenuType<TimelineMenu> by REGISTRY.register("timeline") {
        MenuContentHelper.create(
            ::TimelineMenu,
            RecordCodecMenuContentSerializer(TimelineData.CODEC)
        )
    }
}