package gui;

import base.Board;
import player.HumanPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface BoardController {
    public void init(BoardViewer viewer);
    public void update(Board board, GameContainer gameContainer, StateBasedGame stateBasedGame, float time, HumanPlayer currentPlayer);
}
