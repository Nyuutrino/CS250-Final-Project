package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	//store the audio file
	Clip clip;
	URL soundURL[]= new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/Dungeon.wav");
		soundURL[1] = getClass().getResource("/sound/fanfare.wav");
		soundURL[2] = getClass().getResource("/sound/levelup.wav");
		soundURL[3] = getClass().getResource("/sound/powerup.wav");
		soundURL[4] = getClass().getResource("/sound/dooropen.wav");
	}
	//open the audio file we want from the URL array
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	
	//start playing the clip
	public void play() {
		clip.start();
		
	}
	//loop the clip
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
		
	}
	//stop the loop
	public void stop() {
		clip.stop();
	}
	
	
}
