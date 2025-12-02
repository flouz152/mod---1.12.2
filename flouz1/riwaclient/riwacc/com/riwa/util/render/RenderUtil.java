package com.riwa.util.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderUtil {
    private static final int CORNER_SAMPLES = 16;

    public static final ResourceLocation ROUNDED = shader("rounded.frag");
    public static final ResourceLocation ROUNDED_OUTLINE = shader("rounded_outline.frag");
    public static final ResourceLocation ROUNDED_GRADIENT = shader("rounded_gradient.frag");
    public static final ResourceLocation ROUNDED_BLURRED = shader("rounded_blurred.frag");
    public static final ResourceLocation ROUNDED_BLURRED_GRADIENT = shader("rounded_blurred_gradient.frag");
    public static final ResourceLocation ROUNDED_TEXTURE = shader("rounded_texture.frag");
    public static final ResourceLocation BLOOM = shader("bloom.frag");
    public static final ResourceLocation BLUR = shader("blur.frag");
    public static final ResourceLocation VERTEX = shader("vertex.vert");

    private RenderUtil() {
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x + width / 2.0, y + height / 2.0, 0).color(r, g, b, a).endVertex();

        emitCorner(buffer, x + width - radius, y + radius, -Math.PI / 2.0, 0, radius, r, g, b, a);
        emitCorner(buffer, x + width - radius, y + height - radius, 0, Math.PI / 2.0, radius, r, g, b, a);
        emitCorner(buffer, x + radius, y + height - radius, Math.PI / 2.0, Math.PI, radius, r, g, b, a);
        emitCorner(buffer, x + radius, y + radius, Math.PI, 3 * Math.PI / 2.0, radius, r, g, b, a);

        Tessellator.getInstance().draw();

        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private static void emitCorner(BufferBuilder buffer, double centerX, double centerY, double startAngle, double endAngle,
                                    double radius, float r, float g, float b, float a) {
        for (int i = 0; i <= CORNER_SAMPLES; i++) {
            double progress = i / (double) CORNER_SAMPLES;
            double angle = startAngle + (endAngle - startAngle) * progress;
            double x = centerX + Math.cos(angle) * radius;
            double y = centerY + Math.sin(angle) * radius;
            buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
        }
    }

    private static ResourceLocation shader(String fileName) {
        return new ResourceLocation("rendershader", "shaders/" + fileName);
    }
}
