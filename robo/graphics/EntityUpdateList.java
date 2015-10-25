package robo.graphics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import javax.vecmath.Point2d;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import robo.map.TileType;
import robo.network.NetworkEntity;


/**
 * Created by Matthias on 25.10.2015.
 */
public class EntityUpdateList {

    private static Map<Integer, List<NetworkEntity>> entities = new HashMap<>();
    private static Images images = new Images();


    public static void addEntity(NetworkEntity e) {
        if (entities.containsKey(e.getLayer())) {
            entities.get(e.getLayer()).add(e);
        } else {
            List<NetworkEntity> l = new LinkedList<NetworkEntity>();
            l.add(e);
            entities.put(e.getLayer(), l);
        }
    }

    public static void deleteEntity(Entity e) {
        if(entities.containsKey(e.getLayer())) {
            entities.get(e.getLayer()).remove(e);
        }
    }

    public static void updateEntities() {
        for (List<NetworkEntity> l : entities.values()) {
            for (NetworkEntity e : l) {
                try {
                    e.update();
                }
                catch (SlickException exc) {
                    System.out.println(exc.toString());
                }
            }
        }
    }
}
