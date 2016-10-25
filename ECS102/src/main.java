import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		double bl = 10.0;
		double ml = 0.0;
		ml = convert('B', bl);
		System.out.println(ml);
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();
		int numE = 0;
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == 'E')
			{
				numE++;
			}
		}
		System.out.println(numE);
		int[] g = new int[4];
	}
	public static int FindLargest(double[] d){
		int largest = 0;
		for(int i = 0; i < d.length; i++){
			if(d[i] > d[largest]){
				largest = i;
			}
		}
		return largest;
	}
	public static double convert(char conv_type, double orig_length){
		double return_length = 0;
		conv_type = Character.toLowerCase(conv_type);
		switch(conv_type){
		case 'b':
			return_length = orig_length*2.54;
			break;
		case 'm':
			return_length = orig_length*.39;
			break;
		default:
			return -1;
		}
		return return_length;
	}

}
