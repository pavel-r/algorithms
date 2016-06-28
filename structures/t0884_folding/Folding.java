package structures.t0884_folding;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Folding {

	private int n;
	private int k;

	public void readParameters(Path path) throws IOException {
		try(Scanner scanner = new Scanner(path)){
			k = scanner.nextInt();
			n = 1 << k;
			//System.out.println(n);
		}
	}

	public List<String> run() {
		int[][] matrix = new int[n][1];
		for(int i=0; i<n;i++){
			matrix[i][0] = n - i;
		}
		
		for(int i = 0; i<k;i++){
			matrix = unbow(matrix);
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < matrix[0].length; i++){
			sb.append(matrix[0][i]+" ");
		}
		return Arrays.asList(sb.toString());
	};

	private int[][] unbow(int[][] matrix){
		int nk = matrix.length;
		int mk = matrix[0].length;
		if(nk == 1) return matrix;
		int[][] newMatrix = new int[nk/2][mk*2];
		for(int i = 0; i< nk/2; i++){
			newMatrix[i] = Arrays.copyOf(matrix[nk/2+i], mk*2);
		}
		for(int i=0; i<nk/2; i++){
			for(int j=0; j<mk; j++){
				newMatrix[i][mk + j] = matrix[nk/2 - 1 - i][mk-1-j];
			}
		}
		return newMatrix;
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0]; //absolute input file name
		Folding alg = new Folding();
		alg.readParameters(Paths.get(fileName));
		List<String> result = alg.run();
		System.out.println("Result:");
		result.stream().forEach(System.out::println);
	}
}
