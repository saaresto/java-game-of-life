package com.saaresto.conway.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final GameConfig config;

    private Universe universe;

    public Game(GameConfig config) {
        this.config = config;
    }

    private void init() {
        this.universe = new Universe();
        universe.setChanged(true);
        universe.setGrid(new Cell[this.config.getRowCount()][this.config.getColumnCount()]);

        for (int x = 0; x < this.config.getColumnCount(); x++) {
            for (int y = 0; y < this.config.getRowCount(); y++) {
                universe.getGrid()[x][y] = new Cell(x, y, false);
            }
        }

        for (int i = 0; i < this.config.getInitialAliveCellsCoordinates().length - 1; i = i + 2) {
            universe.getGrid()
                    [this.config.getInitialAliveCellsCoordinates()[i]]
                    [this.config.getInitialAliveCellsCoordinates()[i + 1]]
                    .setAlive(true);
        }
    }

    public void run() {
        System.out.println("Running the conway");

        if (universe == null) {
            init();
        }

        while (universe.isChanged()) {
            universe.setChanged(tick());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean tick() {
        config.getPrinter().print(this.universe);

        List<Cell> toKill = new ArrayList<>();
        List<Cell> toReanimate = new ArrayList<>();

        for (int y = 0; y < this.config.getRowCount(); y++) {
            for (int x = 0; x < this.config.getColumnCount(); x++) {
                int siblingsCount = calculateSiblingsCount(x, y);
                if (siblingsCount < 2 && universe.getGrid()[y][x].isAlive()) {
                    toKill.add(universe.getGrid()[y][x]);
                }
                if (siblingsCount == 3 && !universe.getGrid()[y][x].isAlive()) {
                    toReanimate.add(universe.getGrid()[y][x]);
                }
                if (siblingsCount > 3 && universe.getGrid()[y][x].isAlive()) {
                    toKill.add(universe.getGrid()[y][x]);
                }
            }
        }

        for (Cell cell : toKill) {
            cell.setAlive(false);
        }
        for (Cell cell : toReanimate) {
            cell.setAlive(true);
        }

        return toKill.size() > 0 && toReanimate.size() > 0;
    }

    private int calculateSiblingsCount(int x, int y) {
        int count = 0;

        // top left
        if (x > 0 && y > 0) {
            if (universe.getGrid()[y - 1][x - 1].isAlive()) count++;
        }

        // top
        if (y > 0) {
            if (universe.getGrid()[y - 1][x].isAlive()) count++;
        }

        // top right
        if (y > 0 && x < universe.getGrid()[y - 1].length - 1) {
            if (universe.getGrid()[y - 1][x + 1].isAlive()) count++;
        }

        // left
        if (x > 0) {
            if (universe.getGrid()[y][x - 1].isAlive()) count++;
        }

        // right
        if (x < universe.getGrid()[y].length - 1) {
            if (universe.getGrid()[y][x + 1].isAlive()) count++;
        }

        // bottom left
        if (x > 0 && y < universe.getGrid().length - 1) {
            if (universe.getGrid()[y + 1][x - 1].isAlive()) count++;
        }

        // bottom
        if (y < universe.getGrid().length - 1) {
            if (universe.getGrid()[y + 1][x].isAlive()) count++;
        }

        // bottom right
        if (y < universe.getGrid().length - 1 && x < universe.getGrid()[y + 1].length - 1) {
            if (universe.getGrid()[y + 1][x + 1].isAlive()) count++;
        }

        return count;
    }
}
