package com.riwa.util.icon;

import com.riwa.RiwaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class IconRenderer {
    private IconRenderer() {
    }

    public static void drawIcon(ResourceLocation icon, float x, float y, float width, float height, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GlStateManager.color(r, g, b, a);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        Minecraft.getMinecraft().getTextureManager().bindTexture(icon);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
        buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        buffer.pos(x + width, y, 0).tex(1, 0).endVertex();
        buffer.pos(x, y, 0).tex(0, 0).endVertex();
        Tessellator.getInstance().draw();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
    }

    public static ResourceLocation icon(String path) {
        return new ResourceLocation(RiwaMod.MODID, path);
    }
}
