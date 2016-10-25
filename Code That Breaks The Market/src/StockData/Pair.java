package StockData;

public class Pair {
	public int num1, num2, num3;
	public Pair(int x, int y, int z){
		num1 = x;
		num2 = y;
		num3 = z;
	}
	public Pair(){
		num1 = num2 = num3 =0;
	}
	public String toString(){
		int total = num1+num2;
		double percentUp = (double)num1/total * 100;
		double percentDown = (double)num2/total * 100;
		return percentUp + "% chance of up. " + percentDown + "% chance of being down. |" + num3 + "| ";
	}
}
