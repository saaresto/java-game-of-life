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

        for (int y = 0; y < this.config.getRowCount(); y++) {
            for (int x = 0; x < this.config.getColumnCount(); x++) {
                Cell cellToAdd = new Cell(x, y, false);
                universe.getGrid()[x][y] = cellToAdd;

                // build relationships with previously created cells
                if (x > 0) {
                    final Cell leftSibling = universe.getGrid()[x - 1][y];
                    leftSibling.addSibling(cellToAdd);
                    cellToAdd.addSibling(leftSibling);
                    if (y > 0) {
                        final Cell topLeftSibling = universe.getGrid()[x - 1][y - 1];
                        topLeftSibling.addSibling(cellToAdd);
                        cellToAdd.addSibling(topLeftSibling);
                    }
                }
                if (y > 0) {
                    final Cell topSibling = universe.getGrid()[x][y - 1];
                    topSibling.addSibling(cellToAdd);
                    cellToAdd.addSibling(topSibling);
                    if (x < this.config.getColumnCount() - 1) {
                        final Cell topRightSibling = universe.getGrid()[x + 1][y - 1];
                        topRightSibling.addSibling(cellToAdd);
                        cellToAdd.addSibling(topRightSibling);
                    }
                }
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
        if (universe == null) {
            init();
        }

        while (universe.isChanged()) {
            config.getPrinter().print(this.universe);
            universe.setChanged(tick());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean tick() {
        List<Cell> toKill = new ArrayList<>();
        List<Cell> toReanimate = new ArrayList<>();

        for (int y = 0; y < this.config.getRowCount(); y++) {
            for (int x = 0; x < this.config.getColumnCount(); x++) {
                final Cell cell = this.universe.getGrid()[y][x];
                int siblingsCount = cell.getAliveSiblingsCount();
                if (siblingsCount < 2 && cell.isAlive()) {
                    toKill.add(cell);
                }
                if (siblingsCount == 3 && !cell.isAlive()) {
                    toReanimate.add(cell);
                }
                if (siblingsCount > 3 && cell.isAlive()) {
                    toKill.add(cell);
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

}
