import java.util.ArrayList;
public class AIBoard {

	char [][] AIBoard = new char [10][10];
	public int k, b, c, s, d;
	private boolean shipSunk = false;
	
	public AIBoard() {
		k = 5;
		b = 4;
		c = 3;
		s = 3;
		d = 2;
		createBoard();
	}
	
	public void createBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				AIBoard[i][j] = '~';
			}
		}
		createShip(5, 'K');
		createShip(4, 'B');
		createShip(3, 'C');
		createShip(3, 'S');
		createShip(2, 'D');	
	}
	
	public void createShip(int size, char ship) {
		int orientation = (int)(Math.random() * 2);
		int heightLimit = 10;
		int widthLimit = 10;
		if(orientation == 0) {
			heightLimit = 10 - size;
		}else {
			widthLimit = 10 - size;
		}
		
		int height = (int)(Math.random() * heightLimit);
		int width = (int)(Math.random() * widthLimit);
		boolean isPlaceable = false;
		ArrayList<Point> occupied = getOccupiedLocations();
		while (!isPlaceable) {
			isPlaceable = true;
			if (orientation == 0) {
				for (int i = 0; i < size; i++) {
					if (occupied.contains(new Point(height + i, width))) {
						isPlaceable = false;
						height = (int)(Math.random() * heightLimit);
						width = (int)(Math.random() * widthLimit);
						break;
					}
				}
			}else {
				for (int i = 0; i < size; i++) {
					if (occupied.contains(new Point(height, width + i))) {
						isPlaceable = false;
						height = (int)(Math.random() * heightLimit);
						width = (int)(Math.random() * widthLimit);
						break;
					}
				}
			}
		}
		if (orientation == 0) {
			for (int i = 0; i < size; i++) {
				AIBoard[height + i][width] = ship;
			}
		}else {
			for (int i = 0; i < size; i++) {
				AIBoard[height][width + i] = ship;
			}
		}
	}
	
	public ArrayList<Point> getOccupiedLocations(){
		ArrayList<Point> locs = new ArrayList<Point>();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(AIBoard[i][j] != '~')
					locs.add(new Point(i, j));
			}
		}
		return locs;
	}
	
	public boolean hitOrMiss(int height, int width) {
		shipSunk = false;
		if(AIBoard[height][width] == 'K') {
			k--;
			if (k == 0)
				shipSunk = true;
			AIBoard[height][width] = 'X';
			return true;
		}else if(AIBoard[height][width] == 'B') {
			b--;
			if (b == 0)
				shipSunk = true;
			AIBoard[height][width] = 'X';
			return true;
		}else if(AIBoard[height][width] == 'C') {
			c--;
			if (c == 0)
				shipSunk = true;
			AIBoard[height][width] = 'X';
			return true;
		}else if(AIBoard[height][width] == 'S') {
			s--;
			if (s == 0)
				shipSunk = true;
			AIBoard[height][width] = 'X';
			return true;
		}else if(AIBoard[height][width] == 'D') {
			d--;
			if (d == 0)
				shipSunk = true;
			AIBoard[height][width] = 'X';
			return true;
		}else {
			AIBoard[height][width] = 'O';
			return false;
		}
	}
	
	public boolean aShipSunk() {
		return shipSunk;
	}
	
	public boolean checkIfLost() {
		if(k == 0 && b == 0 && c == 0 && s == 0 && d == 0) {
			return true;
		}
		return false;
	}
	
	public char[][] getBoard(){
		return AIBoard;
	}
	
	public String toString() {
		String str = "";
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				str = str + AIBoard[i][j] + " ";
			}
			str = str + "\n";
		}
		return str;
	}
	
	public static void main(String [] args) {
		AIBoard board = new AIBoard();
		System.out.println(board);
	}
	
}
