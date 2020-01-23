package contest.sf_2020;

import java.util.Arrays;

public class Otaku {
	public boolean otaku(int[] nums) {
		Arrays.sort(nums);
		int zeros = 0;
		int idx = 0;
		for(int i=0;i<nums.length;i++){
			if(nums[i]==0){
				idx++;
				zeros++;
			}else{
				break;
			}
		}
		
		for(int i=idx;i<nums.length-1;i++){
			while(zeros>0 && nums[i]+1 < nums[i+1]){
				zeros--;
				nums[i]++;
			}
			if(nums[i]+1!=nums[i+1]){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Otaku m = new Otaku();
		System.out.println(m.otaku(new int[]{2,3,1,4,5}));
		System.out.println(m.otaku(new int[]{2,3,0,4,5}));
		System.out.println(m.otaku(new int[]{2,3,1,0,5}));
		System.out.println(m.otaku(new int[]{2,3,1,0,6}));
	}
}
