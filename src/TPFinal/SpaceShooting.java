package TPFinal;

import TPFinal.GameStates.Game;
import TPFinal.GameStates.MainMenu;
import TPFinal.GivenClasses.IProcessingApp;
import TPFinal.GivenClasses.SubPlot;
import TPFinal.Sounds.MediaPlayer;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.Arrays;

public class SpaceShooting implements IProcessingApp {
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private MainMenu mainMenu;
    private Game game;

    private enum gameStates {MainMenu, Game}
    private gameStates gameState;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        mainMenu = new MainMenu(p);
        game = new Game(p, plt);
        gameState = gameStates.MainMenu;

        MediaPlayer mp = new MediaPlayer("music.wav");
        mp.play();
    }

    @Override
    public void draw(PApplet p, float dt) {
        switch (gameState) {
            case MainMenu:
                mainMenu.draw(p);
                break;
            case Game:
                game.draw(p, dt);
                if (game.gameEnded()){
                    mainMenu.setLastScore(game.getScore());
                    p.delay(2000);
                    game = new Game(p, plt);
                    gameState = gameStates.MainMenu;
                }
                break;
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        switch (gameState) {
            case MainMenu:
                if (mainMenu.checkButtonPressed(p) == "play")
                    gameState = gameStates.Game;
                break;
            case Game:
                game.checkMousePressed(p);
                break;
        }
    }

    @Override
    public void keyPressed(PApplet p) {
        switch (gameState) {
            case MainMenu:
                mainMenu.checkKeyPressed(p);
                break;
            case Game:
                game.checkKeyPressed(p);
                break;
        }
    }

    @Override
    public void keyReleased(PApplet p){
        switch (gameState) {
            case MainMenu:
                break;
            case Game:
                game.checkKeyReleased(p);
                break;
        }
    }
}
