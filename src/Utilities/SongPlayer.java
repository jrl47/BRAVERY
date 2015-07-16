package Utilities;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SongPlayer {
	
	private Clip worldSong;
	public SongPlayer(){
		worldSong = null;
		AudioInputStream songStream;
		try {
			songStream = AudioSystem.getAudioInputStream(
					DeciduousTileManager.class.getResource("/BotMap.wav"));
			worldSong = AudioSystem.getClip();
	        worldSong.open(songStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void playWorldTheme() {
        worldSong.loop(100);
	}
}
