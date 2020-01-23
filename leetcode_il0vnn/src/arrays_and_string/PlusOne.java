package arrays_and_string;


import java.util.Arrays;

import utils.LtUtils;

public class PlusOne {
	 public int[] plusOne(int[] digits) {
		 
		 int[] k = Arrays.copyOf(digits, digits.length);
		 
		 for(int i=k.length-1;i>=0;i--){
			 k[i]++;
			 if(k[i]==10){
				 k[i]=0;
			 }else{
				 break;
			 }
		 }
		 if(k[0]==0){
			 int[] rst = new int[k.length+1];
			 rst[0]=1;
			 return rst;
		 }else{
			 return k;
		 }
	 }
	 
	 public static void main(String[] args) {
		PlusOne m = new PlusOne();
		LtUtils.print(m.plusOne(new int[]{1,2,3}));
		LtUtils.print(m.plusOne(new int[]{4,3,2,1}));
		LtUtils.print(m.plusOne(new int[]{1}));
		LtUtils.print(m.plusOne(new int[]{9}));
		LtUtils.print(m.plusOne(new int[]{1,9}));
		LtUtils.print(m.plusOne(new int[]{1,1,9}));
		LtUtils.print(m.plusOne(new int[]{9,9,9}));
		
	 }
}
