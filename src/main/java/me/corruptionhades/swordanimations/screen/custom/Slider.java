package me.corruptionhades.swordanimations.screen.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static me.corruptionhades.swordanimations.utils.SFUtils.*;

public class Slider {

    private NumberSetting numSet;
    private boolean sliding = false;
    private float x, y, pH = 50, pW = 500;
    MinecraftClient mc = MinecraftClient.getInstance();

    public Slider(NumberSetting numSet, float x, float y) {
        this.x = x;
        this.y = y;
        this.numSet = numSet;
    }

    public void drawScreen(MatrixStack matrices, double mouseX, double mouseY) {

        double diff = Math.min(pW - 31, Math.max(0, mouseX * getGuiScale() - (x + 30)));
        int renderWidth = (int) ((pW - 61) * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));


        drawRect(matrices, x + 30, y + pH + y,x + 30 + renderWidth, y + y + pH + 45, Color.green.darker().darker().darker().getRGB());

        if (sliding) {
            if (diff == 0 ) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue(roundToPlace(((diff / (pW - 31)) * (numSet.getMax() - numSet.getMin()) + numSet.getMin()), 2));
            }
        }

        int textOffset = (int) ((pH / 2) - mc.textRenderer.fontHeight /2);
        drawString(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2),x + 30 + textOffset, y + pH + y + 10, Color.white);

        // Reset
        drawRect(matrices, x + 30 + pW, y + pH + y, x + 75 + pW, y + pH + y + 45, Color.red.getRGB());

        // Up
        drawRect(matrices, x - 45, y + pH + y, x - 5, y + pH + y + 45, Color.red.getRGB());
        drawString(matrices, "^", x - 30, y + pH + y + 15, Color.white);

        // Down
        drawRect(matrices, x - 85, y + pH + y, x - 50, y + pH + y + 45, Color.red.getRGB());
        drawString(matrices,  "\\" +"/", x - 80, y + pH + y + 15, Color.white);

        // drawRect(matrices, x + 30, y + pH + y,x + pW - 30, y + y + pH + 50, Color.red.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isInside(mouseX, mouseY, x + 30, y + pH + y,x + pW - 31, y + y + pH + 50)) {
            sliding = true;
        }
        if(isInside(mouseX, mouseY, x + 30 + pW, y + pH + y, x + 75 + pW, y + pH + y + 45)) {
            numSet.reset();
        }
        if(isInside(mouseX, mouseY, x - 45, y + pH + y, x - 5, y + pH + y + 45)) {
            numSet.setValue(numSet.getValue() + numSet.getIncrement());
        }
        if(isInside(mouseX, mouseY, x - 85, y + pH + y, x - 50, y + pH + y + 45)) {
            numSet.setValue(numSet.getValue() - numSet.getIncrement());
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
    }

    boolean isInside(double mouseX, double mouseY, double x, double y, double x2, double y2) {
        double sf = MinecraftClient.getInstance().getWindow().getScaleFactor();
        x /= sf;
        y = y / sf;
        x2 = x2 / sf;
        y2 = y2 / sf;

        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
