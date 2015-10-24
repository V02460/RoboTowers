package map;

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