package com.riwa;

import com.riwa.util.font.f1;
import com.riwa.util.render.r1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class k1 {
    private boolean a;
    private boolean b;

    @SubscribeEvent
    public void a(TickEvent.ClientTickEvent event) {
        boolean key = Keyboard.isKeyDown(Keyboard.KEY_INSERT);
        if (key && !b) {
            a = !a;
        }
        b = key;
    }

    @SubscribeEvent
    public void a(RenderGameOverlayEvent.Post event) {
        if (!a || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        float centerX = sr.getScaledWidth() / 2f;
        float centerY = sr.getScaledHeight() / 2f;

        String en = "riwa hack";
        String ru = "рива хак";

        int enWidth = f1.a("comfortaa", en, centerX - 40, centerY - 10, 0xFFFFFFFF, 1.0f);
        f1.a("greycliff", ru, centerX - 40, centerY + 6, 0xFFFFFFFF, 1.0f);

        float boxX = centerX + Math.max(enWidth, 80) * 0.5f + 10f;
        float boxY = centerY - 14f;
        r1.a(boxX, boxY, 90f, 30f, 6f, 0xAA4A90E2);
    }
}
