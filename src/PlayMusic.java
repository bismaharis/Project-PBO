import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayMusic {
  String judul;
  Clip clip;
  
  public PlayMusic(String judul){
    this.judul = judul;
  }

  private void playAudio(){
    try{
      AudioInputStream audio = AudioSystem.getAudioInputStream(new File(judul));
      clip = AudioSystem.getClip();
      clip.open(audio);
      clip.start();
    } catch(UnsupportedAudioFileException uae){
      System.out.println(uae);
    } catch (LineUnavailableException lua){
      System.out.println(lua);
    }
  }

  public void PlayMusicMenu(){
    playAudio();
  }

  public void PlayMusicCLick(){
    playAudio();
  }

  public void PlayMusicPlay(){
    playAudio();
  }

  public void PlayMusicGameOver(){
    playAudio();
  }

  public void stopSound(){
    clip.stop();
  }

  
  
  
}
