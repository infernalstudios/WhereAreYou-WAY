package net.deadlydiamond98.way.util.mixin;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IWayPlayer {

    void way$setToggle(boolean bool);
    boolean way$getToggle();

    void way$setHideIfVisible(boolean bool);
    boolean way$hideIfVisible();

    void way$setShowing(boolean show);
    boolean way$showPlayer();

    void way$setBypassOpt(boolean bool);
    boolean way$bypassOpt();

    void way$setColor(int hex);
    int way$getColor();

    void way$setClear(boolean bl);
    boolean way$isClear();

    void way$setFocusedPlayerNames(List<Component> players);
    List<Component> way$getFocusedPlayerNames();

    void way$setFocusedColor(@Nullable Integer color);
    @Nullable Integer way$getFocusedColor();

    void way$updateRenderPreferences();

    void way$setSeeName(boolean bool);
    boolean way$canSeeName();

    void way$setSeeDist(boolean bool);
    boolean way$canSeeDist();

    void way$setSeeColor(boolean bool);
    boolean way$canSeeColor();

    void way$setSeeOutline(boolean bool);
    boolean way$canSeeOutline();

    void way$setSeeHead(boolean bool);
    boolean way$canSeeHead();

    void way$setSeeHeadOutline(boolean bool);
    boolean way$canSeeHeadOutline();

    void way$setSeeSelf(boolean bool);
    boolean way$canSeeSelf();
}
