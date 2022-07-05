package com.saaresto.conway;

import com.saaresto.conway.game.Game;
import com.saaresto.conway.game.GameConfig;
import com.saaresto.conway.printer.ConsoleUniversePrinter;

public class Main {

    public static void main(String[] args) {
        GameConfig config = new GameConfig(25, 25, new ConsoleUniversePrinter());
        config.setInitialAliveCellsCoordinates(
                12, 12,
                13, 13,
                11, 14,
                12, 14,
                13, 14
        );

        Game game = new Game(config);
        game.run();
    }

}
