package tamit0.mappingdata.Services;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import tamit0.mappingdata.Configuration.AppConfig;

public class ConnectionService {
    public static AppConfig getJsonMapper() {
        String jsonFilePath = "E:\\JavaCS445\\mappingdata\\src\\main\\resources\\test.json";
        // Tạo một đối tượng Gson
        Gson gson = new Gson();
        // Đọc JSON từ file và chuyển thành đối tượng AppConfig
        try {
            return gson.fromJson(new FileReader(jsonFilePath), AppConfig.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(String databaseType) throws SQLException {
        if (databaseType.equals("sqlserver")) {
            return DriverManager.getConnection(getJsonMapper().getSqlServer().getUrl(), getJsonMapper().getSqlServer().getUsername(), getJsonMapper().getSqlServer().getPassword());
        } else if (databaseType.equals("mysql")) {
            return DriverManager.getConnection(getJsonMapper().getMysql().getUrl(), getJsonMapper().getMysql().getUsername(), getJsonMapper().getMysql().getPassword());
        } else {
            throw new SQLException("Invalid database type: " + databaseType);
        }
    }
}
