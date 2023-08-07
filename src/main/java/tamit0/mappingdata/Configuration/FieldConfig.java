package tamit0.mappingdata.Configuration;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class FieldConfig {
    @SerializedName("sqlServerFields")
    private Map<String, String> sqlServerFields;

    @SerializedName("mySqlFields")
    private Map<String, String> mySqlFields;

    public Map<String, String> getSqlServerFields() {
        return sqlServerFields;
    }

    public void setSqlServerFields(Map<String, String> sqlServerFields) {
        this.sqlServerFields = sqlServerFields;
    }

    public Map<String, String> getMySqlFields() {
        return mySqlFields;
    }

    public void setMySqlFields(Map<String, String> mySqlFields) {
        this.mySqlFields = mySqlFields;
    }
}
