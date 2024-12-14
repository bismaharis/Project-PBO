import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class MusicPlayer implements Runnable {
    private String musicPath;
    private boolean loop;
    private Clip clip;

    public MusicPlayer(String musicPath, boolean loop) {
        this.musicPath = musicPath;
        this.loop = loop;
    }

    @Override
    public void run() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(musicPath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Putar berulang
            } else {
                clip.start(); // Putar sekali
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
