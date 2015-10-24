package map;

public class SimpleLayerMap extends Map {

	/*public static void main(String[] args) {
		Map m = new SimpleLayerMap(200, 30);
		System.out.println(m);
	}*/

	public SimpleLayerMap(int w, int h) {
		super(w, h);
	}

	@Override
	protected void fillTiles() {
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
		for (int x = 1; x < midX; x++) {
			for (int y = 1; y < tiles[0].length-1; y++) {
				int dist = Math.abs(midX-x)^2+Math.abs(midY-y)^2;
				int threshold = (int) (Math.random() * Math.max(midX, midY) * 4); // still testing
				if (dist < threshold) {
					tiles[x][y] = TileType.Floor;
					tiles[tiles.length-1-x][tiles[0].length-1-y] = TileType.Floor;
				} else {
					tiles[x][y] = TileType.Wall;
					tiles[tiles.length-1-x][tiles[0].length-1-y] = TileType.Wall;
				}
			}
		}
	}
}