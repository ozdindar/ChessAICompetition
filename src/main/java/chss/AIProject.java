package chss;



import ai.BoardAI;
import ai.BoardEvaluator;
import org.newdawn.slick.Image;

public interface AIProject {
    BoardAI createBoardAI();
    BoardEvaluator createBoardEvaluator();
    Image createProjectLogo();
    String getName();

}
