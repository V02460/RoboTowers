package robo.graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

/**
 * Created by Matthias on 25.10.2015.
 */
public class Overlay {
    private UnicodeFont ourFont;

    public Overlay() throws SlickException{
        ourFont = new UnicodeFont("robo/res/mytype.ttf", 18, false, false);
    }

    public void update() {
        ourFont.drawString(20, 20, "IT WORKS!!!");
    }
}
