package arrays_and_string;

import utils.LtUtils;

public class FindDiagonalOrder {


	public int[] findDiagonalOrder(int[][] matrix) {

		if (matrix.length == 0)
			return new int[] {};
		int[] r = new int[matrix.length * matrix[0].length];

		int id = 0;
		;

		int i = 0;
		boolean up = true;
		find(id, i, r, up, matrix);
		return r;
	}

	private void find(int id, int i, int[] r, boolean up, int[][] matrix) {

		if (id == (matrix.length + matrix[0].length - 1))
			return;

		int j;
		if (up)
			j = id > matrix.length - 1 ?  matrix.length -1 : id;
		else
			j = id > matrix[0].length - 1 ? matrix[0].length - 1 : id;
		for (; j >=0; j--) {
			int k = id - j;
			if ((up && j < matrix.length && k < matrix[0].length)
					|| (!up && k< matrix.length && j < matrix[0].length)) {
				r[i++] = up ? matrix[j][k] : matrix[k][j];
			} else {
				break;
			}

		}
		find(++id, i, r, !up, matrix);
	}

	public static void main(String[] args) {
		FindDiagonalOrder m = new FindDiagonalOrder();
		LtUtils.print(m.findDiagonalOrder(
				new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 7, 8, 9 } }));

		LtUtils.print(m.findDiagonalOrder(new int[][] {}));
		
		String ss = "13651715158";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ss.length();i++) {
			sb.append((char)(ss.charAt(i)-'1'+'A'));
			
		}
		System.out.println(sb.toString());
	}
}
