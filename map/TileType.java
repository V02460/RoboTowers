package map;

public enum TileType {
	Nothing,
	Floor,
	Wall,
	TowerSlot,
	PlayerSpawn,
	PowerUpSpawn;
	
	@Override
	public String toString() {
		switch (this) {
		case Nothing: return " ";
		case Floor: return ".";
		case Wall: return "W";
		case TowerSlot: return "T";
		case PlayerSpawn: return "S";
		case PowerUpSpawn: return "P";
		default: return "?";
		}
	}
}