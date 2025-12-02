package com.riwa.util.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class FontRendererUtil {
    private static final Map<String, Font> LOADED_FONTS = new HashMap<>();

    private FontRendererUtil() {
    }

    public static Font getFont(String fileName, float size) {
        Font baseFont = LOADED_FONTS.computeIfAbsent(fileName, FontRendererUtil::loadFont);
        return baseFont.deriveFont(Font.PLAIN, size);
    }

    public static ResourceLocation getIconFont() {
        return new ResourceLocation("fontrender", "font/Icons.ttf");
    }

    private static Font loadFont(String fileName) {
        ResourceLocation fontLocation = new ResourceLocation("fontrender", "font/" + fileName);
        try (InputStream stream = getFontStream(fontLocation)) {
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load font: " + fontLocation, e);
        }
    }

    private static InputStream getFontStream(ResourceLocation fontLocation) throws IOException {
        IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(fontLocation);
        return resource.getInputStream();
    }
}
