package earth.terrarium.tempad.common.network.c2s

import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs
import com.teamresourceful.resourcefullib.common.network.Packet
import com.teamresourceful.resourcefullib.common.network.base.NetworkHandle
import com.teamresourceful.resourcefullib.common.network.base.PacketType
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType
import earth.terrarium.tempad.tempadId
import earth.terrarium.tempad.common.data.FavoriteLocationAttachment
import earth.terrarium.tempad.common.registries.pinnedPosition
import net.minecraft.resources.ResourceLocation
import java.util.*
import kotlin.jvm.optionals.getOrNull

data class SetFavoritePacket(val favorite: FavoriteLocationAttachment?) : Packet<SetFavoritePacket> {
    constructor(providerId: ResourceLocation, locationId: UUID) : this(
        FavoriteLocationAttachment(
            providerId,
            locationId
        )
    )

    constructor(favorite: Optional<FavoriteLocationAttachment>) : this(favorite.getOrNull())

    companion object {
        val type = CodecPacketType.Server.create(
            "set_favorite".tempadId,
            ObjectByteCodec.create(
                ExtraByteCodecs.RESOURCE_LOCATION.fieldOf { it.providerId },
                ByteCodec.UUID.fieldOf { it.locationId },
                ::FavoriteLocationAttachment
            ).optionalOf().map(::SetFavoritePacket) { Optional.ofNullable(it.favorite) },
            NetworkHandle.handle { message, player ->
                player.pinnedPosition = message.favorite
            }
        )
    }

    override fun type(): PacketType<SetFavoritePacket> = type
}