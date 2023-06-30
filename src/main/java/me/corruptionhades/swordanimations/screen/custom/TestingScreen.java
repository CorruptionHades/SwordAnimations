package me.corruptionhades.swordanimations.screen.custom;

import me.corruptionhades.swordanimations.base.Renderer;
import me.corruptionhades.swordanimations.utils.SFUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestingScreen extends Screen {

    List<Slider> sliders = new ArrayList<>();

    public TestingScreen() {
        super(Text.of("xdvh7uigjfm"));

        List<NumberSetting> settings = new ArrayList<>();
        settings.add(Renderer.tXs);
        settings.add(Renderer.tYs);
        settings.add(Renderer.tZs);
        settings.add(Renderer.tX);
        settings.add(Renderer.tY);
        settings.add(Renderer.tZ);
        settings.add(Renderer.rotX);
        settings.add(Renderer.rotY);
        settings.add(Renderer.rotZ);
        settings.add(Renderer.scaleX);
        settings.add(Renderer.scaleY);
        settings.add(Renderer.scaleZ);

        int i = 0;
        for (NumberSetting setting : settings) {
            sliders.add(new Slider(setting, 100, 50 + i));
            i += 30;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

       /* DrawableHelper.fill(matrices, 60, 10, 70, 110, Color.red.getRGB());

        matrices.push();
        // Translation to the rotation center
        matrices.translate(105, 50, 0);

        // Render the rotation center point
        int markerSize = 4; // Adjust the size of the marker as needed
        DrawableHelper.fill(matrices, -markerSize/2, -markerSize/2, markerSize/2, markerSize/2, 0xFF0000); // Use a different color or shape for the marker

        // Apply rotation
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((System.currentTimeMillis() % 5000) / 5000f * 360f));

        // Reverse the translation to the rotation center
        matrices.translate(-105, -50, 0);

        // Render the square or any other objects
        DrawableHelper.fill(matrices, 60, 10, 150, 110, -1);

        matrices.pop();*/

        for (Slider slider : sliders) {
            slider.drawScreen(matrices, mouseX, mouseY);
        }

        float fh = client.textRenderer.fontHeight * SFUtils.getGuiScale();

        SFUtils.drawString(matrices, "matrices.translate(" + Renderer.tXs.getFloatValue() + ", " + Renderer.tYs.getFloatValue() + ", " + Renderer.tZs.getFloatValue() + ");", 740, 100, Color.white);
        SFUtils.drawString(matrices, "matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(" + Renderer.rotY.getFloatValue() + ");", 740, 100 + fh, Color.white);
        SFUtils.drawString(matrices, "matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(" + Renderer.rotZ.getFloatValue() + ");", 740, 100 + (fh * 2), Color.white);
        SFUtils.drawString(matrices, "matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(" + Renderer.rotX.getFloatValue() + ");", 740, 100 + (fh * 3), Color.white);
        SFUtils.drawString(matrices, "matrices.translate(" + Renderer.tX.getFloatValue() + ", " + Renderer.tY.getFloatValue() + ", " + Renderer.tZ.getFloatValue() + ");", 740, 100 + (fh * 4), Color.white);

        SFUtils.drawRect(matrices, 0, client.getWindow().getScaledHeight() * SFUtils.getGuiScale() - 50, 50, client.getWindow().getScaledHeight() * SFUtils.getGuiScale(), Renderer.override ? Color.green.getRGB() : Color.red.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Slider slider : sliders) {
            slider.mouseClicked(mouseX, mouseY, button);
        }

        if(isInside(mouseX, mouseY, 0, client.getWindow().getScaledHeight() * SFUtils.getGuiScale() - 50, 50, client.getWindow().getScaledHeight() * SFUtils.getGuiScale())) {
            Renderer.override = !Renderer.override;
            Renderer.init();
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    boolean isInside(double mouseX, double mouseY, double x, double y, double x2, double y2) {
        double sf = MinecraftClient.getInstance().getWindow().getScaleFactor();
        x /= sf;
        y = y / sf;
        x2 = x2 / sf;
        y2 = y2 / sf;

        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Slider slider : sliders) {
            slider.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
