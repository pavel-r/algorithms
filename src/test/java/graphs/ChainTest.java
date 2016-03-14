package graphs;

import graphs.Chain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import common.Algorithm;
import common.GeneralAlgorithmTest;

@RunWith(Parameterized.class)
public class ChainTest extends GeneralAlgorithmTest {

	@Parameters
	public static Iterable<Object[]> data() throws IOException,
			URISyntaxException {
		return GeneralAlgorithmTest.readFiles("/graphs/t0741_chain");
	}

	public ChainTest(Path inputPath, Path outputPath) {
		super(inputPath, outputPath);
	}

	@Override
	public Algorithm doGetAlgorithmInstance() {
		return new Chain();
	}

}