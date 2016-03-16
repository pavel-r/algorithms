package recursion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
			return seedWords.contains(first) && (second.isEmpty() || isComposable(second));
		});
	}

	@Override
	public List<String> run() {
		//sort by word length
		words.sort((a, b) -> a.length() - b.length());

		seedWords.add(words.get(0));
		Optional<String> longestComposed = words.stream().skip(1).map(w -> {
			if(isComposable(w)){
				return w;
			} else {
				seedWords.add(w);
				return null;
			}
		}).filter(w -> w != null).reduce((longest, w) -> w.length() > longest.length() ? w : longest);

		return longestComposed.map(Arrays::asList).orElse(Arrays.asList());
	}

}
