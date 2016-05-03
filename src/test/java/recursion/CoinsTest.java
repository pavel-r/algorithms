package recursion;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import common.Algorithm;
import common.GeneralAlgorithmTest;

@RunWith(Parameterized.class)
public class CoinsTest  extends GeneralAlgorithmTest {

    @Parameters
    public static Iterable<Object[]> data() throws IOException,
            URISyntaxException {
        return GeneralAlgorithmTest
                .readFiles("/recursion/t0788_coins");
    }

    public CoinsTest(Path inputPath, Path outputPath) {
        super(inputPath, outputPath);
    }

    @Override
    public Algorithm doGetAlgorithmInstance() {
        return new Coins();
    }
}
