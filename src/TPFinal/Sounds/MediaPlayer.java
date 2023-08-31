package TPFinal.Sounds;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MediaPlayer {

    InputStream in;
    AudioStream as;

    public MediaPlayer(String sound) {

        try {
            in = new FileInputStream("./src/TPFinal/Sounds/" + sound);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            as = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void play(){
        AudioPlayer.player.start(as);
    }

    public void stop(){
        AudioPlayer.player.stop(as);
    }
}
