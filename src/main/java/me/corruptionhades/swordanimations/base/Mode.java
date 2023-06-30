package me.corruptionhades.swordanimations.base;

import java.util.List;

public class Mode<Mode> {

    private final List<Mode> modes;
    private Mode defaultMode;
    private int index;

    public Mode(Mode defaultMode, Mode[] modes) {
        this.modes = List.of(modes);
        this.defaultMode = defaultMode;
        index = this.modes.indexOf(defaultMode);
    }

    public List<Mode> getModes() {
        return modes;
    }

    public Mode getMode() {
        return modes.get(index);
    }

    public void setMode(String mode) {
        this.index = this.modes.indexOf(getFromString(mode));
    }

    public Mode getFromString(String mode) {
        return modes.stream().filter(m -> m.toString().equalsIgnoreCase(mode)).findFirst().orElse(defaultMode);
    }

    public String getModeAsString() {
        return ((Enum<?>) getMode()).name();
    }

    public boolean is(String mode){
        return index == this.modes.indexOf(getFromString(mode));
    }

    public void cycle(boolean forwards) {
        if (forwards) {
            if (this.index < this.modes.size() - 1) {
                this.index++;
            } else {
                this.index = 0;
            }
        }
        if (!forwards) {
            if (this.index > 0) {
                this.index--;
            } else {
                this.index = this.modes.size() - 1;
            }
        }
    }
}
