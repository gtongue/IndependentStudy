package StockData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAnalysis {
	public PrintWriter pw;
	StringBuilder sb = new StringBuilder();
	public DataAnalysis(){
		try {
			pw = new PrintWriter(new File("data.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HashMap<Integer, Pair> ChanceUp(HistoricalData data){
		// +1 = Positive      -1 = Negative
		HashMap<Integer, Pair> positive = new HashMap<>();
		HashMap<Integer, Pair> negative = new HashMap<>(); 
		boolean isPositive = false;
		boolean isPositiveChange = false;
		int counter = 1;

		ArrayList<HistoricalPoint> points = data.points;
		if(isPositive(points.get(1), points.get(0)))
			isPositive = true;
				
		HistoricalPoint currentPoint = points.get(0);
		HistoricalPoint previousPoint;

		for(int i = 1; i < points.size(); i++){
			previousPoint = currentPoint;
			currentPoint = points.get(i);
			if(isPositive(currentPoint, previousPoint))
				isPositiveChange = true;
			else
				isPositiveChange = false;
			if(isPositive){
				if(!positive.containsKey(counter)){
					positive.put(counter, new Pair());
				}
				if(isPositiveChange){
					positive.put(counter, new Pair(positive.get(counter).num1+1,positive.get(counter).num2, positive.get(counter).num3+1));
					counter++;
					continue;
				}else{
					positive.put(counter, new Pair(positive.get(counter).num1,positive.get(counter).num2+1, positive.get(counter).num3+1));
					counter = 1;
					isPositive = false;
					continue;
				}
			}else{
				if(!negative.containsKey(counter)){
					negative.put(counter, new Pair());
				}
				if(!isPositiveChange){
					negative.put(counter, new Pair(negative.get(counter).num1,negative.get(counter).num2+1, negative.get(counter).num3+1));
					counter++;
					continue;
				}else{
					negative.put(counter, new Pair(negative.get(counter).num1+1,negative.get(counter).num2, negative.get(counter).num3+1));
					counter=1;
					isPositive = true;
					continue;			
				}
			}
		}
		sb.append("Days,Positive, , , ,Negative, , , \n");
		sb.append("0,Up,Up%,Down,Down%,Up,Up%,Down,Down\n");
		int length1 = positive.size();
		int length2 = negative.size();
		int size = length1 >= length2 ? length1 : length2;
		int posNum1, posNum2;
		int negNum1, negNum2;
		double posPercent1, posPercent2;
		double negPercent1, negPercent2;
		for(int i = 1; i < size+1; i++){
			if(positive.containsKey(i)){
				Pair p = positive.get(i);
				posNum1 = p.num1;
				posNum2 = p.num2;
				posPercent1 = (double)p.num1/(p.num1+p.num2)*100;
				posPercent2 = (double)p.num2/(p.num1+p.num2)*100;
			}else{
				posNum1 = posNum2 = 0;
				posPercent1 = posPercent2 = 0;
			}
			if(negative.containsKey(i)){
				Pair p = negative.get(i);
				negNum1 = p.num1;
				negNum2 = p.num2;
				negPercent1 = (double)p.num1/(p.num1+p.num2)*100;
				negPercent2 = (double)p.num2/(p.num1+p.num2)*100;
			}else{
				negNum1 = negNum2 = 0;
				negPercent1 = negPercent2 = 0;
			}
			sb.append(i + "," + posNum1 + "," + posPercent1 + "," + posNum2 + "," + posPercent2 + "," + negNum1 + "," + negPercent1 + "," + negNum2 + "," + negPercent2 + "\n");
		}
		pw.write(sb.toString());
		sb = new StringBuilder();
		return null;
	}
	public static boolean isPositive(HistoricalPoint currentPrice, HistoricalPoint previousPrice){
		if((currentPrice.Close-previousPrice.Close)/previousPrice.Close * 100 >= 0)
			return true;
		return false;
	}
}
