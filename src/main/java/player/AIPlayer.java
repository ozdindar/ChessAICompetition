package player;

import base.Board;

public interface AIPlayer extends Player {
    void calculateMove(Board b);
}
