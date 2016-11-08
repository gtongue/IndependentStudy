package StockData;

public class HistoricalPointQuandl {
	public String Symbol;
	//public Date date;
	public String Date;
	public float Open;
	public float High;
	public float Low;
	public float Close;
	public float Volume;
	public float AdjClose;
	public String toString(){
		return "Date: " + Date + " Open: " + Open + " Close: " + Close + " Daily Percent Change: " + (Open-Close)/Open * 100;
	}
	public boolean isPositive(){
		if((Open-Close)/Open * 100 >= 0)
			return true;
		return false;
	}
}
