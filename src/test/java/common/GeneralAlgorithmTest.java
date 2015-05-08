package common;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public abstract class GeneralAlgorithmTest {
	public static String INPUT = "in";
	public static String OUTPUT = "out";

	public static Iterable<Object[]> readFiles(String testFolderPath) throws IOException, URISyntaxException{
		URL resourceUrl = GeneralAlgorithmTest.class.getResource(testFolderPath);
		Path folderPath = Paths.get(resourceUrl.toURI());
		return Files.find(folderPath, 1, (path, args) -> path.getFileName().toString().startsWith(INPUT)).map(path -> {
			Path outputPath = path.resolveSibling(path.getFileName().toString().replace(INPUT, OUTPUT));
			return new Object[]{path, outputPath};
		}).collect(toList());
	}

	public Path inputPath;
	public Path outputPath;
	public GeneralAlgorithmTest(Path inputPath, Path outputPath){
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	public abstract Algorithm doGetAlgorithmInstance();

	@Test
	public void test() throws IOException{
		Algorithm algorithm = doGetAlgorithmInstance();
		algorithm.readParameters(inputPath);
		List<String> result = algorithm.run();
		assertThat(result, is(outputFileAsString()));
	}

	
	private List<String> outputFileAsString() throws IOException{
		return Files.lines(outputPath).collect(toList());
	}
}
