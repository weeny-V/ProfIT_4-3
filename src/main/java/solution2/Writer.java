package solution2;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public void writeFile(String filename, String str) {
        try {
            FileWriter file = new FileWriter(filename);

            file.write(str);
            file.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
