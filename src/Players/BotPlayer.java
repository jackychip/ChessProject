package Players;

import Handlers.Player;

public class BotPlayer extends Player {

    public BotPlayer(boolean whiteSide) {
        this.whiteSide = whiteSide;
        this.human = false;
    }
    
}