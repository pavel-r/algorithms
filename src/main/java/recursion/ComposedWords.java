package recursion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import common.Algorithm;

public class ComposedWords implements Algorithm {

	private List<String> words;

	private final Set<String> seedWords = new HashSet<>();
	
	@Override
	public void readParameters(Path path) throws IOException {
		words = Files.lines(path).collect(Collectors.toList());
	}

	public boolean isComposable(final String s){
		return IntStream.rangeClosed(1, s.length()).anyMatch(i -> {
			final String first = s.substring(0, i);
			final String second = s.substring(i, s.length());
			//System.out.println("Checking " + first + " and " + second);
			return seedWords.contains(first) && (second.isEmpty() || isComposable(second));
		});
	}
	
	@Override
	public List<String> run() {
		words.sort((a, b) -> a.length() == b.length() ? a.compareTo(b) : a.length() - b.length());
		//words.stream().forEach(System.out::println);

		seedWords.add(words.get(0));
		String longestComposable = words.stream().skip(1).map(w -> {
			if(isComposable(w)){
				return w;
			} else {
				seedWords.add(w);
				return "";
			}
		}).reduce(words.get(0), (longest, w) -> w.length() > longest.length() ? w : longest);
		
		System.out.println("Longest: " + longestComposable);
		return Arrays.asList(longestComposable);
	}

}
