package robo.map;

public abstract class Map {
	protected TileType[][] tiles;

	public Map(int width, int height) {
		tiles = new TileType[width][height];
		fillTiles();
	}

	protected abstract void fillTiles();

	public TileType get (int x, int y) {
		return tiles[x][y];
	}

	public int getWidth () {
		return tiles.length;
	}

	public int getHeight () {
		return tiles[0].length;    // tiles[0] fuer die leere Map abfangen
	}

	public abstract int getPlayer1X();
	public abstract int getPlayer1Y();
	public abstract int getPlayer2X();
	public abstract int getPlayer2Y();

	@Override
	public String toString() {
		String out = "";
		for (int y = 0; y < tiles[0].length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				out += tiles[x][y];
			}
			out += '\n';
		}
		return out;
	}
}