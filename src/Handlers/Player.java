package Handlers;

public abstract class Player {
    public boolean whiteSide;
    public boolean human;

    public boolean isWhiteSide() {
        return this.whiteSide;
    }

    public boolean isHuman() {
        return this.human;
    }
}