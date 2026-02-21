package net.deadlydiamond98.way.networking;

import net.deadlydiamond98.way.Way;
import net.deadlydiamond98.way.common.command.WayServerCommands;
import net.deadlydiamond98.way.common.events.WayTickingEvent;
import net.deadlydiamond98.way.util.PlayerLocation;
import net.deadlydiamond98.way.util.mixin.IWayPlayer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class WayFabricNetworking {
    public static final ResourceLocation UPDATE_PLAYER_PACKET = new ResourceLocation(Way.MOD_ID, "update_players");
    public static final ResourceLocation CLEAR_PLAYERS_PACKET = new ResourceLocation(Way.MOD_ID, "clear_players");
    public static final ResourceLocation UPDATE_NAMEPLATE_RENDER_PACKET = new ResourceLocation(Way.MOD_ID, "update_nameplate_render");

    public static class Client {
        public static void registerS2CPackets() {
            ClientPlayNetworking.registerGlobalReceiver(UPDATE_PLAYER_PACKET, WayFabricNetworking.Client::recievePlayerList);
            ClientPlayNetworking.registerGlobalReceiver(CLEAR_PLAYERS_PACKET, WayFabricNetworking.Client::recieveClearPlayers);
            ClientPlayNetworking.registerGlobalReceiver(UPDATE_NAMEPLATE_RENDER_PACKET, WayFabricNetworking.Client::recieveNamePlateRender);
        }

        private static void recieveClearPlayers(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buf, PacketSender packetSender) {
            WayTickingEvent.PLAYER_POS.clear();
        }

        private static void recievePlayerList(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buf, PacketSender packetSender) {
            WayTickingEvent.PLAYER_POS.add(
                    new PlayerLocation(
                            buf.readComponent(), buf.readFloat(),
                            buf.readDouble(), buf.readDouble(), buf.readDouble(),
                            buf.readDouble(),
                            buf.readUUID(),
                            buf.readBoolean(), buf.readInt(),
                            buf.readInt(), buf.readFloat(), buf.readFloat(),
                            buf.readBoolean()
            ));
        }

        private static void recieveNamePlateRender(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buf, PacketSender packetSender) {
            if (minecraft.player != null) {
                IWayPlayer player = ((IWayPlayer) minecraft.player);

                player.way$setToggle(buf.readBoolean());
                player.way$setSeeName(buf.readBoolean());
                player.way$setSeeDist(buf.readBoolean());
                player.way$setSeeColor(buf.readBoolean());
                player.way$setSeeOutline(buf.readBoolean());
                player.way$setSeeHead(buf.readBoolean());
                player.way$setSeeHeadOutline(buf.readBoolean());

                Way.colorDistance = buf.readBoolean();
                Way.namePainFlash = buf.readBoolean();
                Way.namePainGetRedder = buf.readBoolean();

                Way.minRender = buf.readInt();
                Way.maxRender = buf.readInt();

                player.way$setBypassOpt(buf.readBoolean());
            }
        }
    }

    public static class Server {

        public static void sendClearPlayers(ServerPlayer sender) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            ServerPlayNetworking.send(sender, CLEAR_PLAYERS_PACKET, buf);
        }

        public static void sendPlayerList(ServerPlayer sender, Player player) {
            FriendlyByteBuf buf = PacketByteBufs.create();

            buf.writeComponent(player.getName());
            buf.writeFloat(player.getNameTagOffsetY());
            buf.writeDouble(player.getX());
            buf.writeDouble(player.getY());
            buf.writeDouble(player.getZ());
            buf.writeDouble(player.getEyeHeight());

            buf.writeUUID(player.getUUID());

            buf.writeBoolean(((IWayPlayer) player).way$showPlayer());
            buf.writeInt(((IWayPlayer) player).way$getColor());

            buf.writeInt(player.hurtTime);
            buf.writeFloat(player.getHealth());
            buf.writeFloat(player.getMaxHealth());

            buf.writeBoolean(WayServerCommands.FORCE_OPT.getValue(sender) || ((IWayPlayer) player).way$showPlayer());

            ServerPlayNetworking.send(sender, UPDATE_PLAYER_PACKET, buf);
        }

        public static void sendRenderValues(ServerPlayer sender, boolean toggle, boolean names, boolean distance, boolean colors, boolean outlines, boolean head, boolean headOutline,
                                            boolean colordistance, boolean namePainFlash, boolean namePainGetRedder, int minRender, int maxRender, boolean bypassOpt) {
            FriendlyByteBuf buf = PacketByteBufs.create();

            buf.writeBoolean(toggle);

            buf.writeBoolean(names);
            buf.writeBoolean(distance);
            buf.writeBoolean(colors);
            buf.writeBoolean(outlines);

            buf.writeBoolean(head);
            buf.writeBoolean(headOutline);

            buf.writeBoolean(colordistance);
            buf.writeBoolean(namePainFlash);
            buf.writeBoolean(namePainGetRedder);

            buf.writeInt(minRender);
            buf.writeInt(maxRender);

            buf.writeBoolean(bypassOpt);

            ServerPlayNetworking.send(sender, UPDATE_NAMEPLATE_RENDER_PACKET, buf);
        }
    }
}
