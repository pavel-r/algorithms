/***
 * The problem descriptions is located here https://github.com/NodePrime/quiz
 */
package recursion.composed_words;

import static java.util.function.BinaryOperator.maxBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ComposedWordsWithTrie {

	static class Trie {
		boolean endOfWord;
		Trie[] children = new Trie[256];

		public static Trie find(String word, Trie trie){
			int i = 0;
			for(; i < word.length(); i++){
				if(trie.children[word.charAt(i)] != null){
					trie = trie.children[word.charAt(i)];
				} else {
					return null;
				}
			}
			return trie;
		}
		
		public static void insert(String word, Trie trie){
			int i = 0;
			
			for(; i < word.length(); i++){
				if(trie.children[word.charAt(i)] != null){
					trie = trie.children[word.charAt(i)];
				} else {
					break;
				}
			}
			
			for(; i < word.length(); i++){
				trie.children[word.charAt(i)] = new Trie();
				trie = trie.children[word.charAt(i)];
			}

			trie.endOfWord = true;
		}
	}
	
	private Trie root = new Trie();

	private List<String> words;
	
	public void readParameters(Path path) throws IOException {
		words = Files.lines(path).collect(Collectors.toList());
	}
	
	public boolean isComposable(final String s){
		Trie trie = root;
		for(int i=0; i < s.length(); i++){
			if(trie.children[s.charAt(i)] != null){
				trie = trie.children[s.charAt(i)];
			} else {
				return false;
			}
			if(trie.endOfWord){
				if(isComposable(s.substring(i + 1, s.length()))){
					return true;
				}
			}
		}
		return trie.endOfWord;
	}

	public List<String> run() {
		//sort by word length
		Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());
		words.sort(byLength);

		Trie.insert(words.get(0), root);

		Optional<String> longestComposed = words.stream().skip(1).map(w -> {
			if(isComposable(w)){
				return w;
			} else {
				Trie.insert(w, root);
				return null;
			}
		}).filter(w -> w != null).reduce(maxBy(byLength));

		return longestComposed.map(Arrays::asList).orElse(Arrays.asList());
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0]; //absolute input file name
		ComposedWordsWithTrie alg = new ComposedWordsWithTrie();
		alg.readParameters(Paths.get(fileName));
		List<String> result = alg.run();
		System.out.println("Result:");
		result.stream().forEach(System.out::println);
	}
}
