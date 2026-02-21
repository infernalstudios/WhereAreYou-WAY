package net.deadlydiamond98.way.common.events;

import net.deadlydiamond98.way.util.mixin.IWayPlayer;
import net.minecraft.world.entity.player.Player;

public class WayRespawnEvent {
    public static void respawn(Player oldPlayer, Player newPlayer, boolean alive) {
        IWayPlayer oldWay = (IWayPlayer) oldPlayer;
        IWayPlayer newWay = (IWayPlayer) newPlayer;

        newWay.way$setShowing(oldWay.way$showPlayer());
        newWay.way$setBypassOpt(oldWay.way$bypassOpt());
        newWay.way$setColor(oldWay.way$getColor());
        newWay.way$setToggle(oldWay.way$getToggle());

        newWay.way$setSeeName(oldWay.way$canSeeName());
        newWay.way$setSeeDist(oldWay.way$canSeeDist());
        newWay.way$setSeeColor(oldWay.way$canSeeColor());
        newWay.way$setClear(oldWay.way$isClear());
        newWay.way$setSeeOutline(oldWay.way$canSeeOutline());
        newWay.way$setSeeSelf(oldWay.way$canSeeSelf());
        newWay.way$setSeeHead(oldWay.way$canSeeHead());
        newWay.way$setSeeHeadOutline(oldWay.way$canSeeHeadOutline());

        newWay.way$setFocusedColor(oldWay.way$getFocusedColor());
        newWay.way$setFocusedPlayerNames(oldWay.way$getFocusedPlayerNames());

        WayTickingEvent.resyncPlayer = true;
    }
}
