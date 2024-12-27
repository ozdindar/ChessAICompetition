package chss;


import base.SimpleGame;
import base.SimplePanel;

import base.Board;
import chss.internal.CachedChessBoard;
import chss.internal.ChessBoardController;
import chss.internal.ChessBoardViewer;
import player.SimpleHumanPlayer;
import gui.BoardController;
import gui.BoardGame;
import gui.BoardGameSideBar;
import gui.BoardViewer;
import org.newdawn.slick.Game;
import org.newdawn.slick.util.Bootstrap;

public class ProjectDemo {


    private static final String MyBot = "chss.bots.myBot.MyAIProject";


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

            playAMatch(MyBot,MyBot);

    }

    public static void playAMatch(String project1Name, String project2Name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Board board = new CachedChessBoard();
        BoardViewer boardViewer = new ChessBoardViewer();
        BoardController boardController = new ChessBoardController();

        BoardGame chess = new BoardGame(board,boardViewer,boardController);
        BoardGameSideBar sideBar = new BoardGameSideBar(chess);
        Class<?> clazz = Class.forName(project1Name);
        AIProject project1 = (AIProject) clazz.newInstance();

        Class<?> clazz2 = Class.forName(project2Name);
        AIProject project2 = (AIProject) clazz2.newInstance();

        chess.addPlayer(new ProjectAIPlayer(project1));

        chess.addPlayer(new SimpleHumanPlayer("Ali"));

        SimplePanel chssPanel = new SimplePanel(chess,sideBar);


        Game tttGame = new SimpleGame("Chess 1.0", chssPanel);

        Bootstrap.runAsApplication(tttGame,800,600,false);
    }
}
