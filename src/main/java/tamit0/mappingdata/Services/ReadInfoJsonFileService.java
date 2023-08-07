package tamit0.mappingdata.Services;

import tamit0.mappingdata.Configuration.FieldConfig;
import tamit0.mappingdata.Configuration.TableConfig;

public class ReadInfoJsonFileService {
    public void read()
    {
        System.out.println("Tables:");
        for (String table : ConnectionService.getJsonMapper().getTables().keySet()) {
            System.out.println("  " + table);
            TableConfig tableConfig = ConnectionService.getJsonMapper().getTables().get(table);
            System.out.println("    SQL Server table: " + tableConfig.getSqlServerTableName());
            System.out.println("     MySQL table: " + tableConfig.getMySqlTableName());
            System.out.println("    Fields:");
            for (String field : tableConfig.getFields().keySet()) {
                FieldConfig fieldConfig = tableConfig.getFields().get(field);
                System.out.println("      " + field);
                System.out.println("        SQL Server field: " + fieldConfig.getSqlServerFields().get("id"));
                System.out.println("        MySQL field: " + fieldConfig.getMySqlFields().get("id"));
            }
        }
    }

    public void read1()
    {

        for (var tableName : ConnectionService.getJsonMapper().getTables().values()) {
            System.out.println("Table name: " + tableName.getSqlServerTableName());
            TableConfig tableConfig = ConnectionService.getJsonMapper().getTables().get(tableName.getSqlServerTableName());
            
            System.out.println("    Fields:");
            for (String fieldName : tableConfig.getFields().keySet()) {
                System.out.println("        " + fieldName);
                FieldConfig fieldConfig = tableConfig.getFields().get(fieldName);
                System.out.println("            Fields of SqlServer : ");
                for (String name : fieldConfig.getSqlServerFields().values())
                {
                    System.out.println("            " + name);
                }

                System.out.println("            Fields of Mysql : ");

                for (String name1 : fieldConfig.getMySqlFields().values())
                {
                    System.out.println("            " + name1);

                }
                // System.out.println("  " + fieldName + ": " + tableConfig.getFields().get(fieldName));
            }
        }
    }
}
