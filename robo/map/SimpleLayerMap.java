package robo.map;

public class SimpleLayerMap extends Map {

	private int playerX;
	private int playerY;

	/*public static void main(String[] args) {
		Map m = new SimpleLayerMap(50, 50);
		System.out.println(m);
	}*/

	public SimpleLayerMap(int w, int h) {
		super(w, h);
	}

	@Override
	protected void fillTiles() {
		// setup basic box
		int midX = tiles.length/2;
		int midY = tiles[0].length/2;
		for (int i = 1; i < tiles.length-1; i++) {
			tiles[i][0] = TileType.Wall;
			tiles[i][tiles[0].length-1] = TileType.Wall;
		}
		for (int i = 1; i < tiles[0].length-1; i++) {
			tiles[0][i] = TileType.Wall;
			tiles[tiles.length-1][i] = TileType.Wall;
		}
		tiles[0][0] = TileType.Nothing;
		tiles[tiles.length-1][0] = TileType.Nothing;
		tiles[0][tiles[0].length-1] = TileType.Nothing;
		tiles[tiles.length-1][tiles[0].length-1] = TileType.Nothing;
		
		// setup random walls
		double maxDist = Math.sqrt(Math.pow(midX, 2) + Math.pow(midY, 2)); // sqrt(midX^2 + midY^2) / maxDist
		for (int x = 1; x < midX; x++) {
			for (int y = 1; y < tiles[0].length-1; y++) {
				double relativeDist = Math.sqrt(Math.pow(Math.abs(midX-x), 2) + Math.pow(Math.abs(midY-y), 2)) / maxDist; // sqrt(|midX-x|^2 + |midY-y|^2) / maxDist
				int wallCount = 0;
				if (tiles[x-1][y] == TileType.Wall) {
					wallCount++;
				}
				if (tiles[x][y-1] == TileType.Wall) {
					wallCount++;
				}
				double threshold = Math.random();
				if (Math.pow(relativeDist, 2)*0.5 < threshold - 0.075*wallCount) {
					tiles[x][y] = TileType.Floor;
					tiles[tiles.length-1-x][tiles[0].length-1-y] = TileType.Floor;
				} else {
					tiles[x][y] = TileType.Wall;
					tiles[tiles.length-1-x][tiles[0].length-1-y] = TileType.Wall;
				}
			}
		}

		// setup random player spawn
		playerX = (int) ((1 + Math.random())*0.125*tiles.length);
		playerY = (int) ((1 + Math.random())*0.125*tiles[0].length);
		tiles[playerX][playerY] = TileType.PlayerSpawn;
		tiles[tiles.length-1-playerX][tiles[0].length-1-playerY] = TileType.PlayerSpawn;
		
		// TODO:
		// nothing Tiles
		// tower slots
		// powerups
	}

	@Override
	public int getPlayer1X() {
		return playerX;
	}

	@Override
	public int getPlayer1Y() {
		return playerY;
	}

	@Override
	public int getPlayer2X() {
		return tiles.length-1-playerX;
	}

	@Override
	public int getPlayer2Y() {
		return tiles[0].length-1-playerY;
	}
}