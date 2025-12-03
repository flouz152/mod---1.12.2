package com.riwa.util.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class f1 {
    private static final Map<String, Font> a = new HashMap<>();

    public static void a() {
        a.clear();
        b("comfortaa", "fontrender/font/Comfortaa.ttf");
        b("nunito", "fontrender/font/Nunito-Medium.ttf");
        b("greycliff", "fontrender/font/Greycliff.ttf");
        b("montserrat", "fontrender/font/Montserrat Medium.ttf");
        b("icons", "fontrender/font/Icons.ttf");
    }

    private static void b(String key, String path) {
        try {
            IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
            ResourceLocation location = new ResourceLocation("fontrender", path.substring("fontrender/".length()));
            IResource resource = manager.getResource(location);
            try (InputStream stream = resource.getInputStream()) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.PLAIN, 20f);
                a.put(key.toLowerCase(Locale.ROOT), font);
            }
        } catch (Exception e) {
            // Ignore missing font, fallback will handle
        }
    }

    public static int a(String key, String text, float x, float y, int color, float scale) {
        Font font = a.get(key.toLowerCase(Locale.ROOT));
        if (font == null || text == null || text.isEmpty()) {
            return Minecraft.getMinecraft().fontRenderer.drawString(text == null ? "" : text, x, y, color, true);
        }

        Font derived = font.deriveFont(Font.PLAIN, 20f * scale);
        BufferedImage img = c(derived, text, color);
        int width = img.getWidth();
        int height = img.getHeight();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.translate(x, y, 0);

        net.minecraft.client.renderer.texture.DynamicTexture texture = new net.minecraft.client.renderer.texture.DynamicTexture(img);
        ResourceLocation loc = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("riwa/" + key, texture);
        texture.updateDynamicTexture();
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0, height, 0).tex(0, 1).color(255, 255, 255, 255).endVertex();
        buffer.pos(width, height, 0).tex(1, 1).color(255, 255, 255, 255).endVertex();
        buffer.pos(width, 0, 0).tex(1, 0).color(255, 255, 255, 255).endVertex();
        buffer.pos(0, 0, 0).tex(0, 0).color(255, 255, 255, 255).endVertex();
        tessellator.draw();

        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        return width;
    }

    private static BufferedImage c(Font font, String text, int color) {
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tmp.createGraphics();
        g2d.setFont(font);
        int width = g2d.getFontMetrics().stringWidth(text);
        int height = g2d.getFontMetrics().getHeight();
        g2d.dispose();

        BufferedImage img = new BufferedImage(Math.max(1, width), Math.max(1, height), BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(new Color(color, true));
        g2d.drawString(text, 0, g2d.getFontMetrics().getAscent());
        g2d.dispose();
        return img;
    }
}
