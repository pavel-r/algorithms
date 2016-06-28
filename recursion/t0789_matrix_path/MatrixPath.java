package recursion.t0789_matrix_path;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MatrixPath {

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

	public List<String> run() {
		int[] payoff = payoff(0);
		int minPayoff = payoff[0];
		for (int j = 1; j < m; j++) {
			int otherPayoff = payoff[j];
			minPayoff = otherPayoff < minPayoff ? otherPayoff : minPayoff;
		}
		return Arrays.asList(minPayoff + "");
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0]; //absolute input file name
		MatrixPath alg = new MatrixPath();
		alg.readParameters(Paths.get(fileName));
		List<String> result = alg.run();
		System.out.println("Result:");
		result.stream().forEach(System.out::println);
	}
}
