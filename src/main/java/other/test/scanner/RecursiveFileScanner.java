package other.test.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecursiveFileScanner implements FileScanner {

    @Override
    public Collection<File> scan(File base) {
        List<File> result = new ArrayList<>();

        recursiveScan(base, result);

        return result;
    }

    private void recursiveScan(File directory, Collection<File> storage){
        //1. Беру файл directory, и прохожусь по всем файлам внутри - child
        //2. Если же child - директория, то я вызываю recursiveScan(child, storage)
        //3. Если child - не директория (просто файл) - добавляю его в storage

        for (File child: directory.listFiles()) {
            if (child.isDirectory()){
                recursiveScan(child, storage);
            }else {
                storage.add(child);
            }

        }
    }
}
