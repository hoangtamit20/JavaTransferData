package tamit0.mappingdata.Controller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tamit0.mappingdata.Configuration.TableConfig;
import tamit0.mappingdata.Services.ConnectionService;

@RestController
public class MappingController {
    @GetMapping("/getAllData")
    public String getAllData() throws Exception {
        var listJsonObject = new ArrayList<>();
        // Duyệt qua tất cả các table được cấu hình trong file mappings.json
        for (TableConfig tableConfig : ConnectionService.getJsonMapper().getTables().values()) {
            var listObject = new ArrayList<>();
            listObject.clear();
            listJsonObject.addAll(getResultSet(tableConfig.getSqlServerTableName(), listObject));
        }
        return (String) (new Gson().toJson(listJsonObject));
    }

    public List<Object> getResultSet(String tableName, List<Object> jsonObjectList) {
        String sql = getStrSqlSelect(tableName);
        try (PreparedStatement statement = ConnectionService.getConnection("sqlserver").prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            // Iterate over the result set and add the rows to the list of JSON objects.
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    var columnName = rs.getMetaData().getColumnType(i);
                    if (columnName == Types.INTEGER)
                    {
                        jsonObject.addProperty(rs.getMetaData().getColumnName(i), rs.getObject(i) != null ? rs.getObject(i).toString() : "-1");
                    }
                    else
                    {
                        jsonObject.addProperty(rs.getMetaData().getColumnName(i), rs.getObject(i) != null ? rs.getObject(i).toString() : null);
                    }
                    
                }
                jsonObjectList.add(jsonObject);
            }
            return jsonObjectList;
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStrSqlSelect(String tableName)
    {
        String sql = "SELECT ";
        int count = 0;
        for (var strField : getFieldOfTable(tableName))
        {
            if (++count == getFieldOfTable(tableName).size())
            {
                sql += strField + "";
            }
            else
            {
                sql += strField + ", ";
            }
        }
        sql += " FROM "  + tableName;
        return sql;
    }

    public static List<Object> getFieldOfTable(String tableName)
    {
        List<Object> listFieldOfTable = new ArrayList<>();
        TableConfig tableConfig = ConnectionService.getJsonMapper().getTables().get(tableName);
        if (tableConfig != null)
        {
            for (String fieldName : tableConfig.getFields().keySet())
            {
                for (String fieldMySqlTable : tableConfig.getFields().get(fieldName).getMySqlFields().values())
                {
                    listFieldOfTable.add(fieldMySqlTable);
                }
            }
        }
        return listFieldOfTable;
    }
}