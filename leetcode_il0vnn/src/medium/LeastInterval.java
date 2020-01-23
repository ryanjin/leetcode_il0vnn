package medium;

public class LeastInterval {

	public int leastInterval(char[] tasks, int n) {

		int[][] chars = new int[26][1];
		for(char c : tasks){
			chars[c-'A'][0]++;
		}
		
		int rst = 0;
		
		int max = max(chars);
		
		int remain = remain(chars)-1;
		int remainsize = remainsize(chars,max);
		int gap_size = (chars[max][0]==1?n:(chars[max][0]-1)*n);
		if(remain<=n || remainsize<gap_size){
			
			rst += chars[max][0]+gap_size;
			if(chars[max][0]==1&&gap_size>remainsize){
				rst-=gap_size-remainsize;
			}
			
			
			
			release(chars,(chars[max][0]==1?1:(chars[max][0]-1)));
			chars[max][0]--;
			return rst+remain(chars);
		}else{
			return tasks.length;
		}
		
	}

	private int remainsize(int[][] chars, int max) {
		int r = 0;
		for(int i=0;i<chars.length;i++){
			if(i!=max){
				r+=chars[i][0];
			}
		}
		return r;
	}

	private void release(int[][] chars, int k) {
		for(int i=0;i<chars.length;i++){
			chars[i][0]-=k;
		}
	}

	private int remain(int[][] chars) {
		int r = 0;
		int m = 0;
		for(int i=0;i<chars.length;i++){
			if(chars[i][0]>0){
				if((m & (1<<i)) == 0){
					m|=1<<i;
					r++;
				}
			}
		}
		return r;
	}

	private int max(int[][] chars){
		int r = -1;
		int m = -1;
		for(int i=0;i<chars.length;i++){
			if(chars[i][0]>m && chars[i][0]>0){
				m = chars[i][0];
				r=i;
			}
		}
		return r;
	}
	
	public static void main(String[] args) {
		LeastInterval m = new LeastInterval();
		
		System.out.println(m.leastInterval(new char[]{'A','B'}, 2)); //3

		
		System.out.println(m.leastInterval(new char[]{'A','A','A','B','B','B'}, 2)); //8

		System.out.println(m.leastInterval(new char[]{'A','A','A','A','A','A','B','C','D','E','F','G'}, 4)); //26
		
		System.out.println(m.leastInterval(new char[]{'A','B','C'}, 2)); //3

	}
}
