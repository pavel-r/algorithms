package recursion;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import common.Algorithm;

public class Coins implements Algorithm{

	private int[] buyCoins;
	private int[] sellCoins;
	private byte[] temp;
	private int S;

	@Override
	public List<String> run(){
		return calculate() ? Arrays.asList("Yes") : Arrays.asList("No");
	}

	private boolean calculate() {
		for(int i=0; i < buyCoins.length; i++){
			for(int j=temp.length -1; j>=0; j--){
				if(temp[j] == 1){
					temp[j+buyCoins[i]] = 1;
				}
			}
			//System.out.println(Arrays.toString(temp));
			if(temp[S] == 1){
				return true;
			}
		}
		for(int i=0; i < sellCoins.length; i++){
			for(int j=0; j < temp.length; j++){
				if(temp[j] == 1 && j-sellCoins[i] > 0){
					temp[j-sellCoins[i]] = 1;
				}
			}
			if(temp[S] == 1){
				return true;
			}
		}
		return false;
	}

	@Override
	public void readParameters(Path path) throws IOException {
		try (Scanner scanner = new Scanner(path)){
			buyCoins = new int[scanner.nextInt()];
			sellCoins = new int[scanner.nextInt()];
			S = scanner.nextInt();
			temp = new byte[S+101];
			temp[0] = 1;
			for(int i=0; i<buyCoins.length; i++){
				buyCoins[i] = scanner.nextInt();
			}
			for(int i=0; i<sellCoins.length; i++){
				sellCoins[i] = scanner.nextInt();
			}
		}
	}
}
