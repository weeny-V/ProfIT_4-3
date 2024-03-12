package solution1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public void writeFile(String pathToFile, String str) {
        File outputFile = new File(pathToFile);

        try {
            FileWriter out = new FileWriter(outputFile.getAbsolutePath());
            out.write(str);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
