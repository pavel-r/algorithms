package recursion;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import common.Algorithm;

public class task3 implements Algorithm {

	public static int[][] matrix;
	public static int n;
	public static int m;

	public static int[] payoff(int row) {
		if (row == n - 1)
			return matrix[row];

		int[] payoffRow = new int[m];
		int[] nextPayoffRow = payoff(row + 1);
		payoffRow[0] = matrix[row][0]
				+ (nextPayoffRow[0] < nextPayoffRow[1] ? nextPayoffRow[0]
						: nextPayoffRow[1]);
		payoffRow[m - 1] = matrix[row][m - 1]
				+ (nextPayoffRow[m - 1] < nextPayoffRow[m - 2] ? nextPayoffRow[m - 1]
						: nextPayoffRow[m - 2]);
		for (int j = 1; j < m - 1; j++) {
			int min = nextPayoffRow[j - 1];
			min = nextPayoffRow[j] < min ? nextPayoffRow[j] : min;
			min = nextPayoffRow[j + 1] < min ? nextPayoffRow[j + 1] : min;
			payoffRow[j] = matrix[row][j] + min;
		}
		return payoffRow;
	}


	@Override
	public void readParameters(Path path) throws IOException {
		try (Scanner sc = new Scanner(path)){
			n = sc.nextInt();
			m = sc.nextInt();
			System.out.printf("N=%d M=%d\n", n, m);
			matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					matrix[i][j] = sc.nextInt();
				}
			}
		}
	}

	@Override
	public List<String> run() {
		int[] payoff = payoff(0);
		int minPayoff = payoff[0];
		for (int j = 1; j < m; j++) {
			int otherPayoff = payoff[j];
			minPayoff = otherPayoff < minPayoff ? otherPayoff : minPayoff;
		}
		return Arrays.asList(minPayoff + "");
	}
}
