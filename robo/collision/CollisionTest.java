package robo.collision;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point2d;

import javafx.util.Pair;
import robo.RoboTowers;
import robo.graphics.Entity;
import robo.graphics.Projectile;
import robo.graphics.Unit;
import robo.map.Map;
import robo.map.TileType;

public class CollisionTest {
	private static List<Unit> units = new LinkedList<>();
	private static List<Projectile> bullets = new LinkedList<>();
	private static Map map;

	public static void setMap(Map m) {
		map = m;
	}

	public static void addUnit(Unit u) {
		units.add(u);
	}

	public static void deleteUnit(Unit u) {
		units.remove(u);
	}

	public static void addBullet(Projectile b) {
		bullets.add(b);
	}

	public static void deleteBullet(Projectile b) {
		bullets.remove(b);
	}

	public static void checkColisions() {
		List<Entity> toDeleate = new LinkedList<>();
		if (map != null) {
			// Bullets vs. Walls
			for (Projectile b : bullets) {
				Pair<Integer, Integer> p = pointToTile(b.getPosition());
				if (map.get(p.getKey(), p.getValue()) == TileType.Wall) {
					toDeleate.add(b);
				}
			}
			// Walls vs. Units
			for (Unit u : units) {
				double leftX = u.getPosition().x + 22*Math.cos(u.getRotation()) + 11*Math.sin(u.getRotation());
				double leftY = u.getPosition().y + 22*Math.sin(u.getRotation()) - 11*Math.cos(u.getRotation());
				double rightX = u.getPosition().x + 22*Math.cos(u.getRotation()) - 11*Math.sin(u.getRotation());
				double rightY = u.getPosition().y + 22*Math.sin(u.getRotation()) + 11*Math.cos(u.getRotation());
				Pair<Integer, Integer> leftP = pointToTile(new Point2d(leftX, leftY));
				Pair<Integer, Integer> rightP = pointToTile(new Point2d(rightX, rightY));
				if (map.get(leftP.getKey(), leftP.getValue()) == TileType.Wall ||
					map.get(rightP.getKey(), rightP.getValue()) == TileType.Wall) {
						u.instantStop();
				}
			}
		}
		// Bullets vs. Units
		for (Projectile b : bullets) {
			for (Unit u : units) {
				if (Math.sqrt(Math.pow(b.getPosition().x-u.getPosition().x, 2) + Math.pow(b.getPosition().y-u.getPosition().y, 2)) < 9) {
					u.loseHealth(b.getPower());
					toDeleate.add(b);
				}
			}
		}

		// Actually deleate things
		while (!toDeleate.isEmpty()) {
			toDeleate.get(0).delete();
			toDeleate.remove(0);
		}
	}

	public static Pair<Integer, Integer> pointToTile(Point2d p) {
		return new Pair<Integer, Integer>((int) p.x/RoboTowers.TILE_SIZE, (int) p.y/RoboTowers.TILE_SIZE);
	}
}