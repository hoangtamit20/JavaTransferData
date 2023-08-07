package tamit0.mappingdata.Configuration;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

public class AppConfig {
    private DatabaseConfig sqlServer;
    private DatabaseConfig mysql;
    @SerializedName("tables")
    private Map<String, TableConfig> tableConfigs;

    public DatabaseConfig getSqlServer() {
        return sqlServer;
    }

    public DatabaseConfig getMysql() {
        return mysql;
    }

    public Map<String, TableConfig> getTables() {
        return tableConfigs;
    }
}