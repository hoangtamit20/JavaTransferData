package tamit0.mappingdata.Services;
import java.io.FileWriter;
import java.io.IOException;
public class WriteLogService {
    private static final String LOG_FILE_PATH = "log.txt";

    public static void logException(Exception e) {
        try {
            FileWriter writer = new FileWriter(LOG_FILE_PATH, true);
            writer.write(e.getMessage() + System.lineSeparator());
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}