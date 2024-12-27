package chss.bots.myBot;

import ai.*;

import chss.AIProject;
import chss.evaluator.MateEvaluator;
import chss.evaluator.MaterialEvaluator;
import chss.evaluator.MobilityEvaluator;
import chss.evaluator.SimpleEvaluator;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class MyAIProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new BoardAIWithTT(new AlphaBetaPruning(4));
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        SimpleEvaluator evaluator = new SimpleEvaluator();
        evaluator.addEvaluator(new MateEvaluator());
        evaluator.addEvaluator(new MaterialEvaluator());
        evaluator.addEvaluator(new MobilityEvaluator());
        return new BoardEvaluatorWithTT(evaluator);
    }

    @Override
    public Image createProjectLogo() {
        try {
            return new Image("./res/chess/black_queen.png");
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "A-Bot";
    }
}
