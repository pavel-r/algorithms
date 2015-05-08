package recursion;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class tesk4 {

	public static void main(String[] args) {
		readParameters();
	}

	
	public static int n;
	public static int[][] XY;
	
	public static double distance(int[] A, int[] B){
		int dx = Math.abs(A[0] - B[0]);
		int dy = Math.abs(A[1] - B[1]);
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public static void readParameters() {
		try (Scanner sc = new Scanner(new File("C:/projects/algorithms/Ðåêêóðñèÿ/t0792-4. ¦âTÀ¦¬¦-¦-¦¦TÃ¦¬TÏTÆ¦¬TÏ TÂTÀ¦¦TÃ¦¦¦-¦¬TÌ¦-¦¬¦¦¦-/tests/input01.txt"))){
			n = sc.nextInt();
			XY = new int[n][2];
			System.out.printf("N=%d\n", n);
			for (int i = 0; i < n; i++) {
				XY[i][0] = sc.nextInt();
				XY[i][1] = sc.nextInt();
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
		}
	}
}
