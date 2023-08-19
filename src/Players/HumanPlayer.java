package Players;

import Handlers.Player;

public class HumanPlayer extends Player {

    public HumanPlayer(boolean whiteSide) {
        this.whiteSide = whiteSide;
        this.human = true;
    }
}
