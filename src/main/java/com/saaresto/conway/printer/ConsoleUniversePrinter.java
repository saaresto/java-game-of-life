package com.saaresto.conway.printer;

import com.saaresto.conway.game.Universe;

public class ConsoleUniversePrinter implements UniversePrinter {

    @Override
    public void print(Universe universe) {
        clearConsole();
        for (int y = 0; y < universe.getGrid().length; y++) {
            for (int x = 0; x < universe.getGrid()[y].length; x++) {
                System.out.print(universe.getGrid()[x][y].isAlive() ? "■" : "□");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name");

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
