package Utilities;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SongPlayer {
	
	private Clip worldSong;
	private Clip menuSong;
	public SongPlayer(){
		worldSong = null;
		menuSong = null;
		
		AudioInputStream worldStream;
		AudioInputStream menuStream;
		try {
			worldStream = AudioSystem.getAudioInputStream(
					SongPlayer.class.getResource("/BotMap.wav"));
			worldSong = AudioSystem.getClip();
	        worldSong.open(worldStream);
	        
			menuStream = AudioSystem.getAudioInputStream(
					SongPlayer.class.getResource("/BRAVERY.wav"));
			menuSong = AudioSystem.getClip();
	        menuSong.open(menuStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void playTheme(String state) {
		if(state.equals("game") || state.equals("map")){
			menuSong.stop();
			worldSong.loop(100);
		}
		if(state.equals("main")){
			menuSong.loop(100);
		}
	}
	public void stop() {
		menuSong.stop();
		worldSong.stop();
	}
}
