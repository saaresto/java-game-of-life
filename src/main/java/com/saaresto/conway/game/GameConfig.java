package com.saaresto.conway.game;

import com.saaresto.conway.printer.UniversePrinter;

public class GameConfig {

    private int columnCount;

    private int rowCount;

    /**
     * Stream of coordinates stored as "x1, y1, x2, y2, ... xN, yN"
     */
    private int[] initialAliveCellsCoordinates;

    private final UniversePrinter printer;

    public GameConfig(int columnCount, int rowCount, UniversePrinter printer) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.printer = printer;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int[] getInitialAliveCellsCoordinates() {
        return initialAliveCellsCoordinates;
    }

    public void setInitialAliveCellsCoordinates(int... initialAliveCellsCoordinates) {
        if (initialAliveCellsCoordinates.length % 2 > 0) {
            throw new RuntimeException("Invalid initial pattern coordinates count");
        }
        this.initialAliveCellsCoordinates = initialAliveCellsCoordinates;
    }

    public UniversePrinter getPrinter() {
        return printer;
    }
}
