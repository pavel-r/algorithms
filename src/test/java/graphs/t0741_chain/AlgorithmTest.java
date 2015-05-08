package graphs.t0741_chain;

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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AlgorithmTest {
	public static String INPUT = "in";
	public static String OUTPUT = "out";

	public static String getTestFolder(){
		return "/graphs/t0741_chain";
	};

	@Parameters
	public static Iterable<Object[]> data() throws IOException, URISyntaxException{
		URL resourceUrl = AlgorithmTest.class.getResource(getTestFolder());
		Path folderPath = Paths.get(resourceUrl.toURI());
		return Files.find(folderPath, 1, (path, args) -> path.getFileName().toString().startsWith(INPUT)).map(path -> {
			Path outputPath = path.resolveSibling(path.getFileName().toString().replace(INPUT, OUTPUT));
			return new Object[]{path, outputPath};
		}).collect(toList());
	}

	public Path inputPath;
	public Path outputPath;
	public AlgorithmTest(Path inputPath, Path outputPath){
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}
	
	@Test
	public void test() throws IOException{
		Chain sut = new Chain();
		sut.readParameters(inputPath);
		List<String> result = sut.run();
		assertThat(result, is(outputFileAsString()));
	}

	private List<String> outputFileAsString() throws IOException{
		return Files.lines(outputPath).collect(toList());
	}
}
