package common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Algorithm {

	void readParameters(Path path) throws IOException;
	
	List<String> run();
}
