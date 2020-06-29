
public class Board {

	private GridSpace[][] board;
	private GridSpace[][] flags;
	private int height;
	private int width;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new GridSpace[width][height];
		this.flags = new GridSpace[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				this.board[i][j] = new GridSpace();
			}
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				this.flags[i][j] = new GridSpace();;
			}
		}
	}
	
	public GridSpace[][] getBoard() {
		return this.board;
	}
	
	public GridSpace[][] getFlag() {
		return this.flags;
	}
	
	public void printBoard() {
		System.out.print("\n");
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				System.out.print(this.board[i][j].getNum() + "  ");
			}
			System.out.print("\n");
		}
	}
	
	public void printExploredBoard() {
		System.out.print("\n");
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				System.out.print(this.board[i][j].getExplored() + "  ");
			}
			System.out.print("\n");
		}
	}
	
	public void placeBombs(int num) {
		int counter = 0;
		loop:
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				this.board[i][j].setBomb();
				counter++;
				if(counter > num)
					break loop;
			}
		}
		shuffle();
		setNums();
	}
	
	public void shuffle() {
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				int randW = (int) (Math.random() * this.board.length);
				int randH =  (int) (Math.random() * this.board[i].length);
				GridSpace temp = this.board[i][j];
				this.board[i][j] = this.board[randW][randH];
				this.board[randW][randH] = temp;
			}
		}
	}
	
	public void setNums() {
		for(int i = 1; i < this.board.length-1; i++) {
			for(int j = 1; j < this.board[i].length-1; j++) {
				if(this.board[i][j].getBomb() == false) {
					int num = 0;
					for(int x = -1; x < 2; x++) {
						for(int y = -1; y < 2; y++) {
							if(board[i+x][j+y].getBomb() == true)
								num++;
						}
					}
					board[i][j].setNum(num);
				}
			}
		}
		
		completeBorders();
		
	}
	
	public void completeBorders() {
		setNorthBorderNums();
		setSouthBorderNums();
		setEastBorderNums(); 
		setWestBorderNums();
		setNorthWestCorner();
		setNorthEastCorner();
		setSouthWestCorner();
		setSouthEastCorner();
	}
	
	public void setNorthBorderNums() {
		for(int i = 1; i < this.board.length-1; i++) {
			if(this.board[0][i].getBomb() == false) {
				int nums = 0;
				for(int x = 0; x < 2; x++) {
					for(int y = -1; y < 2; y++) {
						if(this.board[0+x][i+y].getBomb() == true)
							nums++;
					}
				}
				this.board[0][i].setNum(nums);
			}
		}
	}
	
	public void setSouthBorderNums() {
		for(int i = 1; i < this.board.length-1; i++) {
			if(this.board[this.board.length-1][i].getBomb() == false) {
				int nums = 0;
				for(int x = -1; x < 1; x++) {
					for(int y = -1; y < 2; y++) {
						if(this.board[(this.board.length-1)+x][i+y].getBomb() == true)
							nums++;
					}
				}
				this.board[this.board.length-1][i].setNum(nums);
			}
		}
	}
	
	public void setEastBorderNums() {
		for(int i = 1; i < this.board.length-1; i++) {
			if(this.board[i][0].getBomb() == false) {
				int nums = 0;
				for(int x = -1; x < 2; x++) {
					for(int y = 0; y < 2; y++) {
						if(this.board[i+x][0+y].getBomb() == true)
							nums++;
					}
				}
				this.board[i][0].setNum(nums);
			}
		}
	}
	
	public void setWestBorderNums() {
		for(int i = 1; i < this.board.length-1; i++) {
			if(this.board[i][this.board.length-1].getBomb() == false) {
				int nums = 0;
				for(int x = -1; x < 2; x++) {
					for(int y = -1; y < 1; y++) {
						if(this.board[i+x][(this.board.length-1)+y].getBomb() == true)
							nums++;
					}
				}
				this.board[i][this.board.length-1].setNum(nums);
			}
		}
	}
	
	public void setNorthWestCorner() {
		if(this.board[0][0].getBomb() == false) {
			int num = 0;
			if(this.board[0][1].getBomb() == true){
				num++;
			}
			if(this.board[1][0].getBomb() == true){
				num++;
			}
			if(this.board[1][1].getBomb() == true){
				num++;
			}
			
			this.board[0][0].setNum(num);
		}
	}
	
	public void setNorthEastCorner() {
		if(this.board[0][this.board.length-1].getBomb() == false) {
			int num = 0;
			if(this.board[0][this.board.length-2].getBomb() == true){
				num++;
			}
			if(this.board[1][this.board.length-2].getBomb() == true){
				num++;
			}
			if(this.board[1][this.board.length-1].getBomb() == true){
				num++;
			}
			
			this.board[0][this.board.length-1].setNum(num);
		}
	}
	
	public void setSouthWestCorner() {
		if(this.board[this.board.length-1][0].getBomb() == false) {
			int num = 0;
			
			if(this.board[this.board.length-2][0].getBomb() == true){
				num++;
			}
			if(this.board[this.board.length-2][1].getBomb() == true){
				num++;
			}
			if(this.board[this.board.length-1][1].getBomb() == true){
				num++;
			}
			
			this.board[this.board.length-1][0].setNum(num);
		}
	}
	
	public void setSouthEastCorner() {
		if(this.board[this.board.length-1][this.board[0].length-1].getBomb() == false) {
			int num = 0;
			if(this.board[this.board.length-2][this.board[0].length-2].getBomb() == true){
				num++;
			}
			if(this.board[this.board.length-2][this.board[0].length-1].getBomb() == true){
				num++;
			}
			if(this.board[this.board.length-1][this.board[0].length-2].getBomb() == true){
				num++;
			}
			
			this.board[this.board.length-1][this.board[0].length-1].setNum(num);
		}
	}
}
