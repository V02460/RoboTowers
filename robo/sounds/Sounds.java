package robo.sounds;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

import java.util.HashMap;

public class Sounds {
	private HashMap<String, Media> sounds=new HashMap<String, Media>();
	private MediaPlayer bgm; // backgroundmusic
	private MediaPlayer motor; // backgroundmusic
	
	public Sounds(){
		// It works. Don't ask how, I don't know either. 
		new JFXPanel();		
	}
	
	public void addSound(String s) {
		Media sound = new Media(Paths.get("robo/res/sound/" + s).toUri().toString()); // 

		if(!sounds.containsKey(s)) {
			sounds.put(s, sound);
		}
	}
	
	public Media getSound(String s) {
		if(!sounds.containsKey(s)) {
			addSound(s);
		}
		return sounds.get(s);
	}
	
	public void playSound(String s) {
		if(!sounds.containsKey(s)) {
			addSound(s);
		}
		new MediaPlayer(getSound(s)).play();
	
	}

	public void playBGM(String s) {
		if(!sounds.containsKey(s)) {
			addSound(s);
		}
		bgm=new MediaPlayer(getSound(s));
		bgm.setCycleCount(100000);
		bgm.setVolume(0.2);
		bgm.play();
	}

	public void playMotor(double volume) {
		//NO
		if(!sounds.containsKey("motor.mp3")) {
			addSound("motor.mp3");
		}
		motor=new MediaPlayer(getSound("motor.mp3"));
		motor.setCycleCount(100000);
		if(volume>1.0) volume=1.0;
		motor.setVolume(volume);
		motor.play();		
	}
	public void stopBGM(String s) {
		if(!sounds.containsKey(s)) {
			addSound(s);
		}
		bgm.stop();
	}

	
}
