package other.test.scanner;

import java.io.File;
import java.util.Collection;

public interface FileScanner {
    Collection<File> scan(File base);

}
