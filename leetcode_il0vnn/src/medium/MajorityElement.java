package medium;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

	public int majorityElement(int[] nums) {

		int cmp = nums.length/2;
		Map<Integer, Integer> m = new HashMap<>();
		for(int n : nums){
			if(!m.containsKey(n)){
				m.put(n, 0);
			}
			m.put(n, m.get(n)+1);
			if(m.get(n)>cmp){
				return n;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		MajorityElement m = new MajorityElement();
		System.out.println(m.majorityElement(new int[] {3,2,3}));
		System.out.println(m.majorityElement(new int[] {2,2,1,1,1,2,2}));
	}
}
