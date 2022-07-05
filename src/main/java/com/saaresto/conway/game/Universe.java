package com.saaresto.conway.game;

public class Universe {

    private Cell[][] grid;

    private boolean isChanged;

    public Universe() {

    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
