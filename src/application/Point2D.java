package application;

/**
 * @author Mateusz
 * Klasa reprezentujaca wektor 2d
 */
public class Point2D {
	private int x;
	private int y;
	
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int lengthSquared() {
		return this.x*this.x + this.y*this.y;
	}
	
	/** 
	 * Zwraca dlugosc wektora
	 * @return dlugosc wektora
	 */
	public int lenght() {
		return (int)Math.sqrt(this.lengthSquared());
	}
	
	public Point2D subtract(Point2D vec) {
		return new Point2D(this.x - vec.x,this.y - vec.y);
	}
	
	
	
}
