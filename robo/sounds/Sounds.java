package robo.sounds;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

import java.util.HashMap;

public class Sounds {
	private HashMap<String, Media> sounds=new HashMap<String, Media>();
	
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
	
}
