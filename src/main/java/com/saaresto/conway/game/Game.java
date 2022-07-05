package com.saaresto.conway.game;

public class Game {

    private GameConfig config;

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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean tick() {
        config.getPrinter().print(this.universe);
        return false;
    }
}
