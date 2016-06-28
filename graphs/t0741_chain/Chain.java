package graphs.t0741_chain;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Chain {

	int n;
	int m;
	int[][] matrix;

	public void readParameters(Path path) throws IOException {
		try (Scanner scanner = new Scanner(path)) {
			n = scanner.nextInt();
			m = scanner.nextInt();
			matrix = new int[n][n];
			for (int i = 0; i < m; i++) {
				int v = scanner.nextInt() - 1;
				int w = scanner.nextInt() - 1;
				matrix[v][w] = 1;
			}
		}
	}

	private List<Integer> chain;
	private List<Integer> bestChain = new ArrayList<>();

	public List<String> run() {
		for (int i = 0; i < n; i++) {
			chain = new ArrayList<>();
			chain.add(i);
			find();
		}
		bestChain = bestChain.stream().map(e -> e + 1)
				.collect(Collectors.toList());
		System.out.println(Arrays.toString(bestChain.toArray()));
		List<String> result = new ArrayList<>(chainToString(bestChain));
		result.add(0, "" + (bestChain.size() - 1));
		return result;
	}

	private void find() {
		int i = chain.get(chain.size() - 1);
		for (int j = 0; j < n; j++) {
			if (matrix[i][j] == 1) {
				chain.add(j);
				matrix[i][j] = -1;
				find();
				matrix[i][j] = 1;
				chain.remove(chain.size() - 1);
			}
		}
		if (chain.size() > bestChain.size()
				|| (chain.size() == bestChain.size() && Arrays.toString(
						chain.toArray()).compareTo(
						Arrays.toString(bestChain.toArray())) < 0)) {
			bestChain = new ArrayList<>(chain);
		}
	}

	private List<String> chainToString(List<Integer> chain) {
		List<String> pairs = new ArrayList<>();
		for (int i = 0; i < chain.size() - 1; i++) {
			pairs.add(chain.get(i) + " " + chain.get(i + 1));
		}
		return pairs;
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0]; //absolute input file name
		Chain alg = new Chain();
		alg.readParameters(Paths.get(fileName));
        List<String> result = alg.run();
        System.out.println("Result:");
        result.stream().forEach(System.out::println);
    }
}
