package medium;

public class GetSum {
	public int getSum(int a, int b) { 

		int carry = b;

		while (carry > 0) {
			carry = (a & b) << 1;
			a = (a ^b);
			b= carry;
			
		}
		return a;
	}
	
	public static void main(String[] args) {
		GetSum s = new GetSum();
		int b = s.getSum(11, 2);
		System.out.println(b);
	}
}
