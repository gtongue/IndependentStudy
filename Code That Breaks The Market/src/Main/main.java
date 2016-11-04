package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import StockData.DataAnalysis;
import StockData.ETFPair;
import StockData.HistoricalData;
import StockData.Price;
import StockData.YahooCSV.CSV;

public class main {
	public static void main(String [] args){
		//ArrayList<Price> prices = CSV.ParseCSV("");
		//System.out.println(prices);
		//getData();
		String[] ETFS = {"SPY","GDX","EEM","XLF","USO","VXX","UVXY","NUGT","EWJ","XIV","IWM","UWTI","QQQ","EWZ","FXI","GDXJ","EFA","JNUG","XOP","XLU","XLE","VWO",
				"DGAZ","XLP","HYG","JNK","DUST","XLI","GLD","SLV","SDS","XLV","UCO","XLK","TVIX","IYR","TLT","RSX","TZA","AMLP","EWT","VEA","IEMG","UNG","SQQQ",
				"XBI","IAU","SPXU","LQD","KRE","SVXY","XLY","OIH","JDST","DWTI","SPXS","XLB","VNQ","XME","DXJ","EZU","EWW","USMV","DBEF","BKLN","XRT","ERX","TNA","LABD","EWU","VGK",
				"IVV","EPI","EWG","KBE","PFF","VIXY","DIA","LABU","OIL","UGAZ","FAS","SPLV","SSO","EWH","AGG","SMH","SH","IWD","UPRO","QID","EWY","ITB","INDA","XHB","ERY"};
		HashMap<String, ArrayList<ETFPair>> map = new HashMap<String, ArrayList<ETFPair>>();
		for(String etf : ETFS){
			System.out.println(etf);
			String test = CSV.FetchCSVData("http://etfdailynews.com/etf/" + etf + "/");
			CSV.parseETF(etf, map, test);
		}
		CSV.buildETFData(map);
		System.out.println(map);
		//System.out.println(test);

	}
	public static void loadDataToHash(){
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
