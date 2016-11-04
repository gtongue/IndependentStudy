package StockData.YahooCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.sound.sampled.DataLine;

import StockData.HistoricalPoint;
import StockData.ETFPair;
import StockData.HistoricalData;
import StockData.Price;

public class CSV {
	public CSV() {

	}

	public static void parseETF(String ETFName, HashMap<String, ArrayList<ETFPair>> map, String rawHTML) {
		int temp = 0;
		String SYM = "";
		double percent = 0;
		while (temp != -1) {
			temp = rawHTML.indexOf("/stock/");
			rawHTML = rawHTML.substring(temp);
			SYM = rawHTML.substring(0, rawHTML.indexOf("</a>"));
			if (SYM.length() >= 20) {
				if (SYM.substring(0, 20).contains("/stock/highstock.js")) {
					break;
				}
			}
			SYM = SYM.substring(SYM.indexOf("\">") + 2);
			rawHTML = rawHTML.substring(rawHTML.indexOf("\n") + 2);
			rawHTML = rawHTML.substring(rawHTML.indexOf("\n") + 2);
			temp = rawHTML.indexOf("right\">");
			rawHTML = rawHTML.substring(temp + 7);
			percent = Double.parseDouble(rawHTML.substring(0, rawHTML.indexOf("%</td>")));
			if (map.containsKey(SYM)) {
				map.get(SYM).add(new ETFPair(ETFName, percent));
			} else {
				map.put(SYM, new ArrayList<ETFPair>());
				map.get(SYM).add(new ETFPair(ETFName, percent));
			}
		}
	}

	public static void buildETFData(HashMap<String, ArrayList<ETFPair>> map) {
		StringBuilder sb = new StringBuilder();
		try {
			PrintWriter pw;
			pw = new PrintWriter(new File("ETF.csv"));

			Set<String> keys = map.keySet();
			for (String key : keys) {
				ArrayList<ETFPair> pairs = map.get(key);
				sb.append(key + ",");
				for (ETFPair p : pairs) {
					sb.append(p.name + "," + p.percent + ",");
				}
				sb.append("\n");
			}
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap<String, String> LoadCSVToHashmap(String CSV) {
		HashMap<String, String> stocks = new HashMap<String, String>();
		String SYM = "";
		String data = "";
		int temp = 0;
		while (!(CSV.equals(""))) {
			temp = CSV.indexOf("\n");
			if (temp == -1) {
				CSV = "";
				continue;
			}
			SYM = CSV.substring(0, temp);
			CSV = CSV.substring(temp + 1);
			temp = CSV.indexOf("\n\n");
			if (temp == -1) {
				CSV = "";
				continue;
			}
			data = CSV.substring(0, temp);
			CSV = CSV.substring(temp + 3);
			stocks.put(SYM, data);
		}
		return stocks;
	}

	public static String ReadFile(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
			reader.close();
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static HistoricalData ParseHistoricalCSV(String csvData) {
		csvData = FetchCSVData(csvData);
		if (csvData.equals("ERROR"))
			return null;
		HistoricalData data = new HistoricalData();
		String[] rows = csvData.split("\n");
		rows[0] = "";
		for (String row : rows) {
			if (row == null || row.equals(""))
				continue;
			String[] cols = row.split(",");
			HistoricalPoint p = new HistoricalPoint();
			p.Date = cols[0];
			p.Open = Float.parseFloat(cols[1]);
			p.High = Float.parseFloat(cols[2]);
			p.Low = Float.parseFloat(cols[3]);
			p.Close = Float.parseFloat(cols[4]);
			p.Volume = Float.parseFloat(cols[5]);
			p.AdjClose = Float.parseFloat(cols[6]);
			data.points.add(p);
		}
		return data;
	}

	public static ArrayList<Price> ParseCSV(String csvData) {
		csvData = FetchCSVData("");
		if (csvData.equals("ERROR"))
			return null;
		ArrayList<Price> prices = new ArrayList<>();
		String[] rows = csvData.split("\n");
		for (String row : rows) {
			if (row == null || row.equals(""))
				continue;
			String[] cols = row.split(",");
			Price p = new Price();
			p.Symbol = cols[0];
			p.Name = cols[1];
			p.Bid = Double.parseDouble(cols[2]);
			p.Ask = Double.parseDouble(cols[3]);
			p.PreviousClose = Double.parseDouble(cols[4]);
			p.Last = Double.parseDouble(cols[5]);

			prices.add(p);
		}
		return prices;
	}

	public static String FetchCSVData(String urlString) {
		// urlString =
		// "http://finance.yahoo.com/d/quotes.csv?s=GSPC+GOOG+MSFT&f=snbaopl1";
		// urlString =
		// "http://chart.finance.yahoo.com/table.csv?s=^GSPC&a=7&b=21&c=1500&d=10&e=11&f=2016&g=d&ignore=.csv";
		// urlString =
		// "http://chart.finance.yahoo.com/table.csv?s=AAPL&a=1&b=1&c=1500&d=8&e=21&f=2016&g=d&ignore=.csv";

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			reader.close();
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
	}
}
