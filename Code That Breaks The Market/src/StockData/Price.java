package StockData;

public class Price {
	public String Symbol;
	public String Name;
	public double Bid;
	public double Ask;
	public double Open;
	public double PreviousClose;
	public double Last;
	
	public String toString(){
		return "Symbol: " + Symbol + " Name: " + Name + " Bid: " + Bid + " Ask: " + Ask + " Open: " + Open + " Previous Close: " + PreviousClose + " Last: " + Last;
	}
}
