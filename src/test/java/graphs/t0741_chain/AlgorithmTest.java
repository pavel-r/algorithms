package graphs.t0741_chain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import common.Algorithm;
import common.GeneralAlgorithmTest;

@RunWith(Parameterized.class)
public class AlgorithmTest extends GeneralAlgorithmTest{

	@Parameters
	public static Iterable<Object[]> data() throws IOException, URISyntaxException{
		return GeneralAlgorithmTest.readFiles("/graphs/t0741_chain");
	}

	public AlgorithmTest(Path inputPath, Path outputPath){
		super(inputPath, outputPath);
	}
	
	@Override
	public Algorithm doGetAlgorithmInstance() {
		return new Chain();
	}
}
