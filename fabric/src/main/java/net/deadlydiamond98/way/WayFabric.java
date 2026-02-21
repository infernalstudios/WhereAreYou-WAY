package net.deadlydiamond98.way;

import net.deadlydiamond98.way.common.command.WayServerCommands;
import net.deadlydiamond98.way.common.events.WayRespawnEvent;
import net.deadlydiamond98.way.common.events.WayTickingEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class WayFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Way.init();
        ServerTickEvents.END_SERVER_TICK.register(WayTickingEvent::tick);
        CommandRegistrationCallback.EVENT.register(WayServerCommands::register);
        ServerPlayerEvents.AFTER_RESPAWN.register(WayRespawnEvent::respawn);

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> WayTickingEvent.resyncPlayer = true);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            WayTickingEvent.resyncPlayer = true;
            WayTickingEvent.resyncDelay = 6;
        });
    }
}
