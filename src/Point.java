public class Point {
	private int row, col;
	
	public Point() {
		row = 0;
		col = 0;
	}
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public boolean equals(Object o) {
		if(!(o instanceof Point))
			return false;
		Point p = (Point)o;
		if(row == p.row && col == p.col)
			return true;
		return false;
	}
	
	public int getHeight() {
		return row;
	}
	
	public int getWidth() {
		return col;
	}
	
	public int getQuadrant() {
		if (row < 5 && col < 5) {
			return 0;
		}else if(row < 5 && col >= 5) {
			return 1;
		}else if(row >= 5 && col < 5) {
			return 2;
		}else {
			return 3;
		}
	}
	
	public boolean notValid() {
		if(row > 9 || row < 0 || col > 9 || col < 0) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String str = "(" + row + ", " + col + ")";
		return str;
	}
	
}
