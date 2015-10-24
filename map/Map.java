package map;

public abstract class Map {
	protected int width;
	protected int height;

	protected TileType[][] tiles;

	public Map(int w, int h) {
		width = w;
		height = h;
		tiles = new TileType[width][height];
		fillTiles();
	}

	protected abstract void fillTiles();

	public TileType get (int x, int y) {
		return tiles[x][y];
	}

	public int get_width () {
		return width;
	}
	public int get_height () {
		return height;
	}

	
	
	@Override
	public String toString() {
		String out = "";
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				out += tiles[x][y];
			}
			out += '\n';
		}
		return out;
	}
}