
public class GridSpace {

	private int number;
	private boolean hasBomb;
	private boolean hasExplored;
	private boolean hasFlag;
	
	public GridSpace() {
		this.number = 0;
		this.hasBomb = false;
		this.hasExplored = false;
		this.hasFlag = false;
	}
	
	public void setBomb() {
		this.hasBomb = true;
		this.number = 9;
	}
	
	public boolean getBomb() {
		return this.hasBomb;
	}
	
	public void setFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}
	
	public boolean getFlag() {
		return this.hasFlag;
	}
	
	public int getNum() {
		return this.number;
	}
	
	public void setNum(int num) {
		this.number = num;
	}
	
	public boolean getExplored() {
		return this.hasExplored;
	}
	
	public void setExplored(boolean hasExplored) {
		this.hasExplored = hasExplored;
	}
	
	public String numToString() {
		String str = "";
		switch(this.number) {
		case 1:
			str = "one";
			break;
		case 2:
			str = "two";
			break;
		case 3:
			str = "three";
			break;
		case 4:
			str = "four";
			break;
		case 5:
			str = "five";
			break;
		case 6:
			str = "six";
			break;
		case 7:
			str = "seven";
			break;
		case 8:
			str = "eight";
			break;
		}
		
		return str;
	}
}
