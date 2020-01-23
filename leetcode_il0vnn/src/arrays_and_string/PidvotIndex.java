package arrays_and_string;

public class PidvotIndex {
	public int pidvotIndex(int[] nums) {

		if (nums.length == 0)
			return -1;

		if (nums.length == 1)
			return 0;

		int all = 0;
		for(int i=0;i<nums.length;i++){
			all+=nums[i]	;
		}
		
		int left =0;
		for(int i=0;i<nums.length;i++){
			int right= all-left-nums[i];
			if(right == left)
				return i;
			left+=nums[i];
			
		}

		return -1;
	}

	public static void main(String[] args) {
		PidvotIndex m = new PidvotIndex();

		
		System.out.println(m.pidvotIndex(new int[] {-1,-1,-1,0,-1,-1}));
		
		System.out.println(m.pidvotIndex(new int[] {-1,-1,-1,0,1,1}));
		
		
		System.out.println(m.pidvotIndex(new int[] {-1,-1,-1,-1,-1,0}));
		
		System.out.println(m.pidvotIndex(new int[] { 1, 7, 3, 6, 5, 6 }));

		System.out.println(m.pidvotIndex(new int[] { 1, 2, 3 }));
	}
}
