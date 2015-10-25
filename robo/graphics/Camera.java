package robo.graphics;

public class Camera {
	private double x_off;
	private double y_off;
	private int width;
	private int height;
	
	public void setPosition(double x, double y) {
		x_off=x;
		y_off=y;	
	}
	
	public double getX() {
		return x_off;
	}

	public double getY() {
		return y_off;
	}

	public void setWindow(int x, int y) {
		width=x;
		height=y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}