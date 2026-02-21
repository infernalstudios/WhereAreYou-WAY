package net.deadlydiamond98.way.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.deadlydiamond98.way.common.command.commands.*;
import net.deadlydiamond98.way.common.command.commands.admin.*;
import net.deadlydiamond98.way.common.command.commands.color.ClearColorCommand;
import net.deadlydiamond98.way.common.command.commands.color.DyeColorCommmand;
import net.deadlydiamond98.way.common.command.commands.color.HexColorCommand;
import net.deadlydiamond98.way.common.command.commands.focus.ClearFocusCommand;
import net.deadlydiamond98.way.common.command.commands.focus.FocusDyeColorCommand;
import net.deadlydiamond98.way.common.command.commands.focus.FocusHexColorCommand;
import net.deadlydiamond98.way.common.command.commands.focus.FocusPlayersCommand;
import net.deadlydiamond98.way.common.world.WaySavedData;
import net.deadlydiamond98.way.util.mixin.IWayPlayer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class WayServerCommands {

    // REGULAR COMMANDS ////////////////////////////////////////////////////////////////////////////////////////////////

    // util
    private static final AbstractWayCommand TOGGLE = new ToggleCommand(0);

    // opt
    private static final AbstractWayCommand OPT_IN = new OptPlayerCommand(0, true);
    private static final AbstractWayCommand OPT_OUT = new OptPlayerCommand(0, false);

    // color
    private static final AbstractWayCommand SET_COLOR = new DyeColorCommmand(0);
    private static final AbstractWayCommand SET_COLOR_HEX = new HexColorCommand(0);
    private static final AbstractWayCommand CLEAR_COLOR = new ClearColorCommand(0);

    // focus
    private static final AbstractWayCommand FOCUS_PLAYERS = new FocusPlayersCommand(0);
    private static final AbstractWayCommand FOCUS_COLOR = new FocusDyeColorCommand(0);
    private static final AbstractWayCommand FOCUS_COLOR_HEX = new FocusHexColorCommand(0);
    private static final AbstractWayCommand CLEAR_FOCUS = new ClearFocusCommand(0);

    // see
    private static final AbstractWayCommand SEE_NAME = new SeeCommand(0, "name",
            (player, bool) -> ((IWayPlayer) player).way$setSeeName(bool)
    );
    private static final AbstractWayCommand SEE_DIST = new SeeCommand(0, "distance",
            (player, bool) -> ((IWayPlayer) player).way$setSeeDist(bool)
    );
    private static final AbstractWayCommand SEE_COLOR = new SeeCommand(0, "color",
            (player, bool) -> ((IWayPlayer) player).way$setSeeColor(bool)
    );
    private static final AbstractWayCommand SEE_OUTLINE = new SeeCommand(0, "outline",
            (player, bool) -> ((IWayPlayer) player).way$setSeeOutline(bool)
    );
    private static final AbstractWayCommand SEE_HEAD = new SeeCommand(0, "head",
            (player, bool) -> ((IWayPlayer) player).way$setSeeHead(bool)
    );
    private static final AbstractWayCommand SEE_HEAD_OUTLINE = new SeeCommand(0, "headOutline",
            (player, bool) -> ((IWayPlayer) player).way$setSeeHeadOutline(bool)
    );
    private static final AbstractWayCommand SEE_SELF = new SeeCommand(0, "self",
            (player, bool) -> ((IWayPlayer) player).way$setSeeSelf(bool)
    );

    private static final AbstractWayCommand TOGGLE_RAYCAST = new OnlyShowBehindBlocksCommand(0);

    // ADMIN COMMANDS //////////////////////////////////////////////////////////////////////////////////////////////////

    // opt
    private static final AbstractWayCommand OPT_IN_ADMIN = new OptPlayerCommand(2, true);
    private static final AbstractWayCommand OPT_OUT_ADMIN = new OptPlayerCommand(2, false);

    // color
    private static final AbstractWayCommand SET_COLOR_ADMIN = new DyeColorCommmand(2);
    private static final AbstractWayCommand SET_COLOR_HEX_ADMIN = new HexColorCommand(2);
    private static final AbstractWayCommand CLEAR_COLOR_ADMIN = new ClearColorCommand(2);

    // focus
    private static final AbstractWayCommand FOCUS_PLAYERS_ADMIN = new FocusPlayersCommand(2);
    private static final AbstractWayCommand FOCUS_COLOR_ADMIN = new FocusDyeColorCommand(2);
    private static final AbstractWayCommand FOCUS_COLOR_HEX_ADMIN = new FocusHexColorCommand(2);
    private static final AbstractWayCommand CLEAR_FOCUS_ADMIN = new ClearFocusCommand(2);

    // see
    private static final AbstractWayCommand SEE_NAME_ADMIN = new SeeCommand(2, "name",
            (player, bool) -> ((IWayPlayer) player).way$setSeeName(bool)
    );
    private static final AbstractWayCommand SEE_DIST_ADMIN = new SeeCommand(2, "distance",
            (player, bool) -> ((IWayPlayer) player).way$setSeeDist(bool)
    );
    private static final AbstractWayCommand SEE_COLOR_ADMIN = new SeeCommand(2, "color",
            (player, bool) -> ((IWayPlayer) player).way$setSeeColor(bool)
    );
    private static final AbstractWayCommand SEE_OUTLINE_ADMIN = new SeeCommand(2, "outline",
            (player, bool) -> ((IWayPlayer) player).way$setSeeOutline(bool)
    );
    private static final AbstractWayCommand SEE_HEAD_ADMIN = new SeeCommand(2, "head",
            (player, bool) -> ((IWayPlayer) player).way$setSeeHead(bool)
    );
    private static final AbstractWayCommand SEE_HEAD_OUTLINE_ADMIN = new SeeCommand(2, "headOutline",
            (player, bool) -> ((IWayPlayer) player).way$setSeeHeadOutline(bool)
    );
    private static final AbstractWayCommand SEE_SELF_ADMIN = new SeeCommand(2, "self",
            (player, bool) -> ((IWayPlayer) player).way$setSeeSelf(bool)
    );


    // admin exclusive

    // bool
    public static final AbstractWayCommand SEE_ALL = new SeeAllCommand();

    public static final PersistantStateBooleanCommand COLOR_DISTANCE = new PersistantStateBooleanCommand("colorDistance",
            WaySavedData::colorDistance, WaySavedData::setColorDistance, true
    );
    public static final NamePainCommand NAME_PAIN_FLASH = new NamePainCommand("nameFlashesWhenHurt",
            WaySavedData::namePainFlash, WaySavedData::setNamePainFlash, true
    );
    public static final NamePainCommand NAME_PAIN_REDDER = new NamePainCommand("nameColorGetsRedder",
            WaySavedData::namePainGetRedder, WaySavedData::setNamePainGetRedder, true
    );
    public static final PersistantStateBooleanCommand FORCE_OPT = new PersistantStateBooleanCommand("forceOpt-in",
            WaySavedData::forceOptIn, WaySavedData::setForceOptIn
    );
    public static final PersistantStateBooleanCommand SEE_TEAM_ONLY = new PersistantStateBooleanCommand("seeTeamColorOnly",
            WaySavedData::seeTeamColorOnly, WaySavedData::setSeeTeamColorOnly
    );

    public static final PersistantStateBooleanCommand NO_FRIENDLY_FIRE = new PersistantStateBooleanCommand("teamColorNoFriendlyFire",
            WaySavedData::teamColourNoFriendlyFire, WaySavedData::setTeamColourNoFriendlyFire
    );

    // int
    public static final PersistantStateIntegerCommand PACKET_UPDATE_RATE = new PersistantStateIntegerCommand("packetUpdateRate",
            WaySavedData::getPacketUpdateRate, WaySavedData::setPacketUpdateRate, 1, 20, false
    );
    public static final PersistantStateIntegerCommand MIN_DIST = new PersistantStateIntegerCommand("disableIfWithin",
            WaySavedData::getMinRender, WaySavedData::setMinRender, 0, 999999, true
    );
    public static final PersistantStateIntegerCommand MAX_DIST = new PersistantStateIntegerCommand("disableIfFurtherThan",
            WaySavedData::getMaxRender, WaySavedData::setMaxRender, 0, 999999, true
    );

    public static final ShowDistCommand SHOW_MIN_DIST = new ShowDistCommand("disableIfWithin",
            WaySavedData::getMinRender, WaySavedData::setMinRender
    );
    public static final ShowDistCommand SHOW_MAX_DIST = new ShowDistCommand("disableIfFurtherThan",
            WaySavedData::getMaxRender, WaySavedData::setMaxRender
    );

    // admin exclusive lock
    public static final LockCommand LOCK_COLOR = new LockCommand("color",
            WaySavedData::colorLocked, WaySavedData::setColorLocked
    );
    public static final LockCommand LOCK_SEE = new LockCommand("see",
            WaySavedData::seeLocked, WaySavedData::setSeeLocked
    );


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        AbstractWayCommand.COMMANDS.forEach(wayCommand -> wayCommand.register(dispatcher));
    }
}
