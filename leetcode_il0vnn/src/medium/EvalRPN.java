package medium;

import java.util.Stack;

public class EvalRPN {
	public int evalRPN(String[] tokens) {

		Stack<Integer> stack = new Stack<>();
		for( int i=0;i<tokens.length;i++){
			if(isOp(tokens[i])){
				//操作
				int b = stack.pop();
				int a = stack.pop();
				int rst = operation(a,b, tokens[i]);
				stack.add(rst);
			}else{
				stack.add(Integer.parseInt(tokens[i]));
			}
		}
		return stack.pop();
	}
	
	public boolean isOp(String s){
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") ; 
	}
	
	public int operation(int a,int b,String op){
		if("+".equals(op)){
			return a+b;
		}else if ("/".equals(op)){
			return a/b;
		}else if("-".equals(op)){
			return a-b;
		}else{
			return a*b;
		}
		
	}

	public static void main(String[] args) {
		EvalRPN m = new EvalRPN();

		System.out.println(m.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
		System.out.println(m.evalRPN(new String[]{"4", "13", "5", "/", "+"}));
		System.out.println(m.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
	}
}
