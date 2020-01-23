package arrays_and_string;

public class DominantIndex {
	public int dominantIndex(int[] nums) {

		// 查找第一大和第二大的数字，只要比第二大的两倍大就可以了

		if (nums.length == 1) {
			return 0;
		}

		int tp1 = -1;
		int id = -1;
		int tp2 = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] >= tp1) {
				tp2 = tp1;
				tp1 = nums[i];
				id = i;
			}else if(nums[i]>tp2){
				tp2= nums[i];
			}
		}
		System.out.println("max " + tp2 + " " + tp1);

		return tp1>=tp2*2?id:-1;
	}

	public static void main(String[] args) {
		DominantIndex m = new DominantIndex();
		System.out.println(m.dominantIndex(new int[] {0,0,3,2 }));

		System.out.println(m.dominantIndex(new int[] { 3, 6, 1, 0 }));
		System.out.println(m.dominantIndex(new int[] { 1, 2, 3, 4 }));
	}

}
