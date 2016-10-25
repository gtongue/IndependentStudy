package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import StockData.DataAnalysis;
import StockData.HistoricalData;
import StockData.Price;
import StockData.YahooCSV.CSV;

public class main {
	public static void main(String [] args){
		//ArrayList<Price> prices = CSV.ParseCSV("");
		//System.out.println(prices);
		//getData();
		String data = CSV.ReadFile("data.csv");
	//	System.out.println(data);
		HashMap<String, String> stocks = CSV.LoadCSVToHashmap(data);
		String input = "";
		Scanner scan = new Scanner(System.in);
		while(!input.equals("end")){
			input = scan.nextLine();
			if(stocks.containsKey(input)){
				System.out.println(stocks.get(input));
			}else{
				System.out.println("That stock is not in the S&P 500");
			}
		}
	}
	public static void getData(){
		DataAnalysis data = new DataAnalysis();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/S&P.csv"));
			String line = reader.readLine();
			line = reader.readLine();
			String SYMBOL = "";
			data.pw.write("S&P \n");
			HistoricalData SP = CSV.ParseHistoricalCSV("http://chart.finance.yahoo.com/table.csv?s=^GSPC&a=10&b=11&c=2013&d=10&e=11&f=2016&g=d&ignore=.csv");
			data.ChanceUp(SP);
			while(line != null){
				SYMBOL = line.substring(0, line.indexOf(','));
				System.out.println(SYMBOL);
				SP = CSV.ParseHistoricalCSV("http://chart.finance.yahoo.com/table.csv?s=" + SYMBOL +"&a=10&b=11&c=2013&d=10&e=11&f=2016&g=d&ignore=.csv");
				data.pw.write("\n\n" + SYMBOL + "\n");
				data.ChanceUp(SP);
				line = reader.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.pw.close();
	}
}
