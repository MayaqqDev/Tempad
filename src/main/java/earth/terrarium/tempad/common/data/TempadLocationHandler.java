package earth.terrarium.tempad.common.data;

import com.teamresourceful.resourcefullib.common.utils.SaveHandler;
import earth.terrarium.tempad.api.locations.LocationData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;

import java.util.*;

public class TempadLocationHandler extends SaveHandler {

    private static final TempadLocationHandler CLIENT_ONLY = new TempadLocationHandler();

    private final Map<UUID, Map<UUID, LocationData>> locations = new HashMap<>();
    private final Map<UUID, UUID> favorites = new HashMap<>();

    public void addLocation(UUID player, LocationData location) {
        if (!locations.containsKey(player)) {
            locations.put(player, new LinkedHashMap<>());
        }
        locations.get(player).put(location.id(), location);
    }

    public static void addLocation(Level level, UUID player, LocationData location) {
        TempadLocationHandler handler = read(level);
        handler.addLocation(player, location);
    }

    public static void removeLocation(Level level, UUID player, UUID location) {
        TempadLocationHandler handler = read(level);
        if (handler.locations.containsKey(player)) {
            handler.locations.get(player).remove(location);

            if (handler.favorites.containsKey(player) && handler.favorites.get(player).equals(location)) {
                handler.favorites.remove(player);
            }
        }
    }

    public static void favoriteLocation(Level level, UUID player, UUID location) {
        TempadLocationHandler handler = read(level);
        handler.favorites.put(player, location);
    }

    public static void unfavoriteLocation(Level level, UUID player) {
        TempadLocationHandler handler = read(level);
        handler.favorites.remove(player);
    }

    public static UUID getFavorite(Level level, UUID player) {
        return read(level).favorites.get(player);
    }

    public static Map<UUID, LocationData> getLocations(Level level, UUID player) {
        TempadLocationHandler handler = read(level);
        return new HashMap<>(handler.locations.getOrDefault(player, Collections.emptyMap()));
    }

    public static LocationData getLocation(Level level, UUID player, UUID location) {
        return getLocations(level, player).get(location);
    }

    public static TempadLocationHandler read(Level level) {
        return read(level, HandlerType.create(CLIENT_ONLY, TempadLocationHandler::new), "tempad_locations");
    }

    public static boolean containsLocation(Level level, UUID player, UUID location) {
        return getLocations(level, player).containsKey(location);
    }

    @Override
    public void loadData(CompoundTag tag) {
        if (tag.contains("Favorites")) {
            CompoundTag favorites = tag.getCompound("Favorites");
            CompoundTag savedLocations = tag.getCompound("SavedLocations");

            loadLocations(savedLocations);

            for (String playerId : favorites.getAllKeys()) {
                UUID player = UUID.fromString(playerId);
                UUID favorite = favorites.getUUID(playerId);
                this.favorites.put(player, favorite);
            }
        } else {
            loadLocations(tag);
        }
    }

    public void loadLocations(CompoundTag tag) {
        for (String playerId : tag.getAllKeys()) {
            UUID player = UUID.fromString(playerId);
            for (Tag list : tag.getList(playerId, Tag.TAG_COMPOUND)) {
                if (list instanceof CompoundTag compound) {
                    addLocation(player, LocationData.fromTag(compound));
                }
            }
        }
    }

    @Override
    public void saveData(CompoundTag tag) {
        CompoundTag favorites = new CompoundTag();
        CompoundTag savedLocations = new CompoundTag();

        locations.forEach((player, locationMap) -> {
            ListTag playerTag = new ListTag();
            locationMap.forEach((uuid, location) -> playerTag.add(location.toTag()));
            savedLocations.put(player.toString(), playerTag);
        });

        this.favorites.forEach((player, location) -> favorites.putUUID(player.toString(), location));

        tag.put("Favorites", favorites);
        tag.put("SavedLocations", savedLocations);
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
