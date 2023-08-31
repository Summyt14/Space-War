package TPFinal.GameStates;

import TPFinal.Buttons.PlayButton;
import TPFinal.Buttons.QuitButton;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class MainMenu {
    private PImage background;
    private PlayButton playButton;
    private QuitButton quitButton;
    private int lastScore;
    private PFont font;

    public MainMenu(PApplet p) {
        p.pushStyle();
        background = p.loadImage("space.jpg");
        background.resize(p.width, p.height);
        int buttonWidth = 300;
        int buttonHeight = 100;
        int buttonX = (int) (p.width / 2 - (buttonWidth * 0.77f));
        int playButtonY = p.height / 2 + buttonHeight;
        int quitButtonY = p.height / 2 + (buttonHeight * 2 + 20);
        playButton = new PlayButton(buttonX, playButtonY, buttonWidth, buttonHeight);
        quitButton = new QuitButton(buttonX, quitButtonY, buttonWidth, buttonHeight);
        p.popStyle();
        lastScore = 0;
    }

    public void displayText(PApplet p) {
        p.pushStyle();
        p.fill(165, 0, 0);
        font = p.createFont("Copperplate Gothic Bold", 100);
        p.textFont(font);
        p.textAlign(p.CENTER, p.CENTER);
        p.text("Space War", p.width / 2, p.height / 3);
        p.popStyle();
    }

    public void displayLastScore(PApplet p){
        p.pushMatrix();
        p.pushStyle();
        p.fill(69, 172, 0);
        p.textSize(28);
        p.textAlign(p.RIGHT, p.CENTER);
        p.text("Last Score: " + lastScore, p.width * 0.95f, p.height * 0.94f);
        p.popStyle();
        p.popMatrix();
    }

    public void draw(PApplet p){
        p.pushStyle();
        p.background(background);
        displayText(p);
        displayLastScore(p);

        playButton.update(p);
        playButton.display(p);
        quitButton.update(p);
        quitButton.display(p);
        p.popStyle();
    }

    public void setLastScore(int lastScore){
        this.lastScore = lastScore;
    }

    public String checkButtonPressed(PApplet p){
        if (playButton.mouseIsOver){
            return "play";
        }
        else if (quitButton.mouseIsOver){
            quitButton.clickAction(p);
            return "quit";
        }
        return "";
    }

    public void checkKeyPressed(PApplet p) {

    }
}
