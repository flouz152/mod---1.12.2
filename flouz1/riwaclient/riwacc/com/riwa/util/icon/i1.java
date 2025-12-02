package com.riwa.util.icon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class i1 {
    private static final Map<String, ResourceLocation> a = new HashMap<>();

    public static void a() {
        a.clear();
    }

    public static void a(String key, ResourceLocation icon) {
        a.put(key, icon);
    }

    public static void a(String key, float x, float y, float w, float h) {
        ResourceLocation icon = a.get(key);
        if (icon == null) {
            return;
        }
        b(icon, x, y, w, h);
    }

    public static void b(ResourceLocation icon, float x, float y, float w, float h) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getTextureManager().bindTexture(icon);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y + h, 0).tex(0, 1).color(255, 255, 255, 255).endVertex();
        buffer.pos(x + w, y + h, 0).tex(1, 1).color(255, 255, 255, 255).endVertex();
        buffer.pos(x + w, y, 0).tex(1, 0).color(255, 255, 255, 255).endVertex();
        buffer.pos(x, y, 0).tex(0, 0).color(255, 255, 255, 255).endVertex();
        tessellator.draw();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
