package recursion.t0800_binary_sequences;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BinarySequences {

	private String a;
	private String b;
	private int[][] matrix;

	public void readParameters(Path path) throws IOException {
		try(Scanner scanner = new Scanner(path)){
			scanner.nextLine();
			a = scanner.nextLine().replaceFirst("0*", "");
			b = scanner.nextLine().replaceFirst("0*", "");
		}
	}

	public List<String> run() {
		matrix = new int[a.length()+1][b.length()+1];
		
		String[] prev = new String[b.length()+1];
		Arrays.fill(prev, "");
		
		for(int i = 1; i< matrix.length;i++){
			String[] curr = new String[b.length() + 1];
			Arrays.fill(curr, "");
			for(int j = 1; j< matrix[0].length; j++){
				if(a.charAt(i-1) == b.charAt(j-1)){
					matrix[i][j] = matrix[i-1][j-1] + 1;
					curr[j] = prev[j-1] + a.charAt(i-1);
					if(matrix[i-1][j] == matrix[i][j]){
						curr[j] = curr[j].compareTo(prev[j]) > 0 ? curr[j] : prev[j];
					}
					if(matrix[i][j-1] == matrix[i][j]){
						curr[j] = curr[j].compareTo(curr[j-1]) > 0 ? curr[j] : curr[j-1];
					}
				} else {
					if(matrix[i-1][j] > matrix[i][j-1]){
						matrix[i][j] =  matrix[i-1][j];
						curr[j] = prev[j];
					} else if (matrix[i-1][j] < matrix[i][j-1]){
						matrix[i][j] =  matrix[i][j-1];
						curr[j] = curr[j-1];
					} else {
						matrix[i][j] =  matrix[i-1][j];
						curr[j] = curr[j-1].compareTo(prev[j]) > 0 ? curr[j-1] : prev[j];
					}
				}
			}
			prev = curr;
		}
		return Arrays.asList(matrix[matrix.length -1][matrix[0].length - 1] + "", prev[prev.length -1]);
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0]; //absolute input file name
		BinarySequences alg = new BinarySequences();
		alg.readParameters(Paths.get(fileName));
		List<String> result = alg.run();
		System.out.println("Result:");
		result.stream().forEach(System.out::println);
	}
}
