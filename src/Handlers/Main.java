package Handlers;

import Players.HumanPlayer;

public class Main {

    static Game game = new Game();

    public static void main(String[] args) {
        Player p1 = new HumanPlayer(true);
        Player p2 = new HumanPlayer(false);
        game.initialize(p1, p2);
    }

}
