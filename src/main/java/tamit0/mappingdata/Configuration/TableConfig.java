package tamit0.mappingdata.Configuration;
import java.util.Map;
import com.google.gson.annotations.SerializedName;
public class TableConfig {
    @SerializedName("sqlServerTable")
    private String sqlServerTableName;
    @SerializedName("mySqlTable")
    private String mySqlTableName;
    @SerializedName("fields")
    private Map<String, FieldConfig> fields;
    @SerializedName("model")
    private String model;
    @SerializedName("rule")
    private String rule;

    public String getSqlServerTableName() {
        return sqlServerTableName;
    }
    public void setSqlServerTableName(String sqlServerTableName) {
        this.sqlServerTableName = sqlServerTableName;
    }
    public String getMySqlTableName() {
        return mySqlTableName;
    }
    public void setMySqlTableName(String mySqlTableName) {
        this.mySqlTableName = mySqlTableName;
    }
    public Map<String, FieldConfig> getFields() {
        return fields;
    }
    public void setFields(Map<String, FieldConfig> fields) {
        this.fields = fields;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getRule() {
        return rule;
    }
    public void setRule(String rule) {
        this.rule = rule;
    }
}
