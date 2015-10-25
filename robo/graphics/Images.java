package robo.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public class Images {
	private HashMap<String, Image> images=new HashMap<String, Image>();
	
	public void addImage(String s) throws SlickException{
		Image image = new Image(s);
		if(!images.containsKey(s)) {
			images.put(s, image);
		}
	}
	
	public Image getImage(String s) throws SlickException{
		if(!images.containsKey(s)) {
			addImage(s);
		}
		return images.get(s);
	}
	
}
