package com.saaresto.conway.game;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final int x;
    private final int y;
    private boolean isAlive;

    private final List<Cell> siblings;

    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.siblings = new ArrayList<>();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void addSibling(Cell cell) {
        siblings.add(cell);
    }

    public int getAliveSiblingsCount() {
        return (int) siblings.stream().filter(Cell::isAlive).count();
    }
}
