package net.deadlydiamond98.way.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.deadlydiamond98.way.Way;
import net.deadlydiamond98.way.util.mixin.IWayPlayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class WayKeybindings {
    public static final KeyMapping TOGGLE_NAMEPLATE = register("toggle", GLFW.GLFW_KEY_M);

    private static boolean renderNameOverlayToggle = true;
    private static boolean hasFired = false;

    public static void tickKeybinding(Minecraft client) {
        if (client.player != null) {
            if (TOGGLE_NAMEPLATE.isDown()) {
                if (!hasFired) {
                    hasFired = true;
                    renderNameOverlayToggle = !renderNameOverlayToggle;
                }
            } else {
                hasFired = false;
            }
        }
    }

    public static boolean renderNameOverlay() {
        Player player = Minecraft.getInstance().player;

        if (player instanceof IWayPlayer wayPlayer && !wayPlayer.way$getToggle()) {
            return TOGGLE_NAMEPLATE.isDown();
        }
        return renderNameOverlayToggle;
    }

    private static KeyMapping register(String toggle, int glfw) {
        String key = "key." + Way.MOD_ID + ".";
        return new KeyMapping(key + toggle, InputConstants.Type.KEYSYM, glfw, key + "category");
    }
}
