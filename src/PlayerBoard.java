import java.util.ArrayList;
public class PlayerBoard {

	char [][] playerBoard = new char [10][10];
	public int k, b, c, s, d;
	private boolean shipSunk = false;
	
	public PlayerBoard(char[][] grid) {
		k = 5;
		b = 4;
		c = 3;
		s = 3;
		d = 2;
		playerBoard = grid;
	}
	
	public ArrayList<Point> getOccupiedLocations(){
		ArrayList<Point> locs = new ArrayList<Point>();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(playerBoard[i][j] != '~')
					locs.add(new Point(i, j));
			}
		}
		return locs;
	}
	
	public boolean hitOrMiss(int height, int width) {
		shipSunk = false;
		if(playerBoard[height][width] == 'K') {
			k--;
			if (k == 0)
				shipSunk = true;
			playerBoard[height][width] = 'X';
			return true;
		}else if(playerBoard[height][width] == 'B') {
			b--;
			if (b == 0)
				shipSunk = true;
			playerBoard[height][width] = 'X';
			return true;
		}else if(playerBoard[height][width] == 'C') {
			c--;
			if (c == 0)
				shipSunk = true;
			playerBoard[height][width] = 'X';
			return true;
		}else if(playerBoard[height][width] == 'S') {
			s--;
			if (s == 0)
				shipSunk = true;
			playerBoard[height][width] = 'X';
			return true;
		}else if(playerBoard[height][width] == 'D') {
			d--;
			if (d == 0)
				shipSunk = true;
			playerBoard[height][width] = 'X';
			return true;
		}else {
			playerBoard[height][width] = 'O';
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
		return playerBoard;
	}
	
	public String toString() {
		String str = "";
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				str = str + playerBoard[i][j] + " ";
			}
			str = str + "\n";
		}
		return str;
	}
	
}
