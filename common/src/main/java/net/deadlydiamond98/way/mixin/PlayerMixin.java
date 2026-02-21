package net.deadlydiamond98.way.mixin;

import net.deadlydiamond98.way.Way;
import net.deadlydiamond98.way.common.command.WayServerCommands;
import net.deadlydiamond98.way.platform.Service;
import net.deadlydiamond98.way.util.mixin.IWayPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Player.class)
public class PlayerMixin implements IWayPlayer {

    @Unique private boolean way$showPlayer = true;
    @Unique private boolean way$bypassOpt = false;
    @Unique private int way$color = 0xFFFFFF;
    @Unique private boolean way$isClear = true;
    @Unique private List<Component> way$players = new ArrayList<>();
    @Unique private Integer way$focusedColor = null;

    @Unique private boolean way$toggle = true;
    @Unique private boolean way$hideIfVisible = false;

    @Unique private boolean way$seeNames = true;
    @Unique private boolean way$seeDist = true;
    @Unique private boolean way$seeColors = true;
    @Unique private boolean way$seeOutlines = false;
    @Unique private boolean way$seeHead = true;
    @Unique private boolean way$seeHeadOutline = true;
    @Unique private boolean way$seeSelf = false;

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void way$attack(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (entity instanceof IWayPlayer wayPlayer && wayPlayer.way$getColor() == this.way$getColor()) {
            if (player instanceof ServerPlayer serverPlayer && WayServerCommands.NO_FRIENDLY_FIRE.getValue(serverPlayer)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void way$readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.way$showPlayer = nbt.getBoolean("showPlayerWAY");
        this.way$bypassOpt = nbt.getBoolean("bypassShowPlayerWAY");
        this.way$color = nbt.getInt("colorWAY");
        this.way$isClear = nbt.getBoolean("clearWay");
        this.way$toggle = nbt.getBoolean("toggleWay");
        this.way$hideIfVisible = nbt.getBoolean("hideIfVisWay");

        this.way$seeNames = nbt.getBoolean("seeNameWay");
        this.way$seeDist = nbt.getBoolean("seeDistWay");
        this.way$seeColors = nbt.getBoolean("seeColorWay");
        this.way$seeOutlines = nbt.getBoolean("seeOutlineWay");
        this.way$seeSelf = nbt.getBoolean("seeSelfWay");
        this.way$seeHead = nbt.getBoolean("seeHeadWay");
        this.way$seeHeadOutline = nbt.getBoolean("seeHeadOutlineWay");
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void way$addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putBoolean("showPlayerWAY", way$showPlayer);
        nbt.putBoolean("bypassShowPlayerWAY", way$bypassOpt);
        nbt.putInt("colorWAY", way$color);
        nbt.putBoolean("clearWay", way$isClear);
        nbt.putBoolean("toggleWay", way$toggle);
        nbt.putBoolean("hideIfVisWay", way$hideIfVisible);

        nbt.putBoolean("seeNameWay", way$seeNames);
        nbt.putBoolean("seeDistWay", way$seeDist);
        nbt.putBoolean("seeColorWay", way$seeColors);
        nbt.putBoolean("seeOutlineWay", way$seeOutlines);
        nbt.putBoolean("seeSelfWay", way$seeSelf);
        nbt.putBoolean("seeHeadWay", way$seeHead);
        nbt.putBoolean("seeHeadOutlineWay", way$seeHeadOutline);
    }

    @Override
    public void way$setToggle(boolean bool) {
        this.way$toggle = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$getToggle() {
        return this.way$toggle;
    }

    @Override
    public void way$setHideIfVisible(boolean bool) {
        this.way$hideIfVisible = bool;
    }

    @Override
    public boolean way$hideIfVisible() {
        return this.way$hideIfVisible;
    }

    @Override
    public void way$setShowing(boolean show) {
        this.way$showPlayer = show;
    }

    @Override
    public boolean way$showPlayer() {
        return this.way$showPlayer;
    }

    @Override
    public void way$setBypassOpt(boolean bool) {
        this.way$bypassOpt = bool;
    }

    @Override
    public boolean way$bypassOpt() {
        return this.way$bypassOpt;
    }

    @Override
    public void way$setColor(int hex) {
        this.way$color = hex | 0xFF000000;
    }

    @Override
    public int way$getColor() {
        return this.way$color | 0xFF000000;
    }

    @Override
    public void way$setClear(boolean bl) {
        this.way$isClear = bl;
    }

    @Override
    public boolean way$isClear() {
        return this.way$isClear;
    }

    // FOCUS COMMAND METHODS ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void way$setFocusedPlayerNames(List<Component> players) {
        this.way$players = players;
    }

    @Override
    public List<Component> way$getFocusedPlayerNames() {
        return this.way$players;
    }

    @Override
    public void way$setFocusedColor(@Nullable Integer color) {
        if (color != null) {
            color = color | 0xFF000000;
        }
        this.way$focusedColor = color;
    }

    @Override
    public @Nullable Integer way$getFocusedColor() {
        if (this.way$focusedColor != null) {
            return this.way$focusedColor | 0xFF000000;
        }
        return null;
    }


    // SEE COMMAND METHODS /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void way$updateRenderPreferences() {
        Player player = (Player) (Object) this;
        if (player instanceof ServerPlayer sender) {
            Service.PLATFORM.sendS2CRenderingPacket(
                    sender,
                    this.way$toggle,
                    this.way$seeNames,
                    this.way$seeDist,
                    this.way$seeColors,
                    this.way$seeOutlines,
                    this.way$seeHead,
                    this.way$seeHeadOutline,
                    WayServerCommands.COLOR_DISTANCE.getValue(sender),
                    WayServerCommands.NAME_PAIN_FLASH.getValue(sender),
                    WayServerCommands.NAME_PAIN_REDDER.getValue(sender),
                    WayServerCommands.MIN_DIST.getValue(sender),
                    WayServerCommands.MAX_DIST.getValue(sender),
                    this.way$bypassOpt
            );
        }
    }

    @Override
    public void way$setSeeName(boolean bool) {
        this.way$seeNames = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeName() {
        return this.way$seeNames;
    }

    @Override
    public void way$setSeeDist(boolean bool) {
        this.way$seeDist = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeDist() {
        return this.way$seeDist;
    }

    @Override
    public void way$setSeeColor(boolean bool) {
        this.way$seeColors = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeColor() {
        return this.way$seeColors;
    }

    @Override
    public void way$setSeeOutline(boolean bool) {
        this.way$seeOutlines = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeOutline() {
        return this.way$seeOutlines;
    }

    @Override
    public void way$setSeeHead(boolean bool) {
        this.way$seeHead = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeHead() {
        return this.way$seeHead;
    }

    @Override
    public void way$setSeeHeadOutline(boolean bool) {
        this.way$seeHeadOutline = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeHeadOutline() {
        return this.way$seeHeadOutline;
    }

    @Override
    public void way$setSeeSelf(boolean bool) {
        this.way$seeSelf = bool;
        way$updateRenderPreferences();
    }

    @Override
    public boolean way$canSeeSelf() {
        return this.way$seeSelf;
    }
}
