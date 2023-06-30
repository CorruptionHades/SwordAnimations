package me.corruptionhades.swordanimations.screen;

import static me.corruptionhades.swordanimations.utils.SFUtils.*;

import me.corruptionhades.swordanimations.base.Renderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;

import java.awt.*;

public class ConfigScreen extends Screen {

    private static final ConfigScreen instance = new ConfigScreen();

    private float x, y, width, height;
    private float dragX, dragY;
    private boolean dragging;

    protected ConfigScreen() {
        super(Text.of("Sword Animations Config Screen"));
    }

    @Override
    protected void init() {
        super.init();

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        x = (width / 2f) - 50;
        y = (height / 2f) - 50;
        this.width = 1250;
        this.height = 600;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (dragging) {
            float sf = getGuiScale();
            float xDiff = (mouseX - dragX);
            float yDiff = (mouseY - dragY);
            x = xDiff * sf;
            y = yDiff * sf;
        }

        drawRoundedRect(matrices, x, y, x + width, y + height, 20, 20, Color.darkGray.darker().darker());

        drawString(matrices, "Sword Animations Config", x + 10, y + 10, Color.white);
        drawString(matrices, x + width - 10, y + height - 5 - (client.textRenderer.fontHeight * getGuiScale()));

        drawString(matrices, "Mode", x + 20, y + 50, Color.white);
        drawRect(matrices, x + 20, y + 65, x + 20 + client.textRenderer.getWidth("Mode") * getGuiScale(), y + 69, Color.gray.getRGB());

        int offset = 0;
        for (Object mode : Renderer.mode.getModes()) {
            drawString(matrices, mode.toString(), x + 30, y + 85 + (offset * 30), Color.white);

            if(mode == Renderer.mode.getMode()) {
                drawRect(matrices, x + 30,
                        y + 85 + (client.textRenderer.fontHeight * getGuiScale()) + (offset * 30),
                        x + 30 + client.textRenderer.getWidth(mode.toString()) * getGuiScale(),
                        y + 89 + (client.textRenderer.fontHeight * getGuiScale()) + (offset * 30), Color.white.getRGB());
            }

            offset++;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (isHovered(x, y, x + width, y + 20, mouseX, mouseY) && button == 0) {
            dragging = true;
            float sf = getGuiScale();
            dragX = (int) ((mouseX - x / sf));
            dragY = (int) ((mouseY - y / sf));
        }

        int offset = 0;
        for (Object mode : Renderer.mode.getModes()) {
            if (isHovered(x + 30, y + 85 + (offset * 30), x + 30 + client.textRenderer.getWidth(mode.toString()) * getGuiScale(), y + 89 + (client.textRenderer.fontHeight * getGuiScale()) + (offset * 30), mouseX, mouseY)) {
                Renderer.mode.setMode(mode.toString());
            }

            offset++;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        dragging = false;

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {



        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {



        return super.charTyped(chr, modifiers);
    }

    public boolean isHovered(float x, float y, float x2, float y2, double mouseX, double mouseY) {
        double sf = client.getWindow().getScaleFactor();
        x /= sf;
        y = (float) (y / sf);
        x2 = (float) (x2 / sf);
        y2 = (float) (y2 / sf);

        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public static ConfigScreen getInstance() {
        return instance;
    }
}
