package com.riwa.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class r1 {
    private static final Map<String, Integer> a = new HashMap<>();
    private static String b;

    public static void a() {
        b = c(new ResourceLocation("rendershader", "shaders/vertex.vert"));
        d("rounded", "rendershader/shaders/rounded.frag");
        d("rounded_outline", "rendershader/shaders/rounded_outline.frag");
        d("rounded_blur", "rendershader/shaders/rounded_blurred.frag");
        d("rounded_gradient", "rendershader/shaders/rounded_gradient.frag");
    }

    public static void a(float x, float y, float w, float h, float round, int color) {
        Integer program = a.get("rounded");
        if (program == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();

        GL20.glUseProgram(program);
        int colorLoc = GL20.glGetUniformLocation(program, "color");
        int sizeLoc = GL20.glGetUniformLocation(program, "size");
        int roundLoc = GL20.glGetUniformLocation(program, "round");

        float aColor = (color >> 24 & 0xFF) / 255.0F;
        float rColor = (color >> 16 & 0xFF) / 255.0F;
        float gColor = (color >> 8 & 0xFF) / 255.0F;
        float bColor = (color & 0xFF) / 255.0F;
        GL20.glUniform4f(colorLoc, rColor, gColor, bColor, aColor);
        GL20.glUniform2f(sizeLoc, w, h);
        GL20.glUniform1f(roundLoc, round);

        GlStateManager.translate(x, y, 0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, h, 0).tex(0, 1).endVertex();
        buffer.pos(w, h, 0).tex(1, 1).endVertex();
        buffer.pos(w, 0, 0).tex(1, 0).endVertex();
        buffer.pos(0, 0, 0).tex(0, 0).endVertex();
        tessellator.draw();

        GL20.glUseProgram(0);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private static void d(String key, String path) {
        String frag = c(new ResourceLocation("rendershader", path.substring("rendershader/".length())));
        if (frag == null || b == null) {
            return;
        }
        int program = GL20.glCreateProgram();
        int vertId = e(b, GL20.GL_VERTEX_SHADER);
        int fragId = e(frag, GL20.GL_FRAGMENT_SHADER);
        GL20.glAttachShader(program, vertId);
        GL20.glAttachShader(program, fragId);
        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);
        a.put(key, program);
    }

    private static int e(String shader, int type) {
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shader);
        GL20.glCompileShader(shaderId);
        return shaderId;
    }

    private static String c(ResourceLocation location) {
        try (InputStream input = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
