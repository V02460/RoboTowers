package map;

public class FixedMap extends Map {

	/*public static void main(String[] args) {
		Map m = new FixedMap(0, 0);
		System.out.println(m);
	}*/

	public FixedMap(int w, int h) {
		super(10, 10);
	}

	@Override
	protected void fillTiles() {
		for (int x = 1; x < 9; x++) {
			for (int y = 1; y < 9; y++) {
				tiles[x][y] = TileType.Floor;
			}
		}
		for (int i = 0; i < 10; i++) {
			tiles[0][i] = TileType.Wall;
			tiles[9][i] = TileType.Wall;
			tiles[i][0] = TileType.Wall;
			tiles[i][9] = TileType.Wall;
		}
		tiles[1][1] = TileType.PlayerSpawn;
		tiles[1][8] = TileType.PowerUpSpawn;
		tiles[8][1] = TileType.PowerUpSpawn;
		tiles[8][8] = TileType.PowerUpSpawn;
		tiles[2][2] = TileType.TowerSlot;
		tiles[2][7] = TileType.TowerSlot;
		tiles[7][2] = TileType.TowerSlot;
		tiles[7][7] = TileType.TowerSlot;
	}
}