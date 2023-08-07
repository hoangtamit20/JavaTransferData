package tamit0.mappingdata.Rules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tamit0.mappingdata.Models.ChucVuModel;
import tamit0.mappingdata.Services.WriteLogService;

public class ChucVuRule {
    public static final String dateTimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    public static final int errorNumberResult = -1;
    public static String validateTenChucVu(int idChucVu, String tenChucVu)
    {
        return BaseRule.validateLength(tenChucVu, 5, 100, ChucVuRule.class.getSimpleName(), idChucVu, "tenChuVu") ? tenChucVu : null;
    }

    public static boolean validateChucVuModel(ChucVuModel chucVuModel)
    {
        return (validateTenChucVu(chucVuModel.getIdChucVu(), chucVuModel.getTenChucVu()) != null);
    }

    public int validateChucVu(String idChucVu, String tenChucVu)
    {
        int result = 0;
        try{
            var a = Integer.parseInt(idChucVu);
            result += 1;
            if (tenChucVu.isEmpty() || tenChucVu == null)
            {
                WriteLogService.logException(new Exception(""));
                result = errorNumberResult;
            }
        }catch(NumberFormatException ex)
        {
            WriteLogService.logException(new Exception(""));
            result = errorNumberResult;
        }
        return result;
    }

    // public boolean isValid(JsonObject jsonObject)
    // {
    //     try {
    //         // Parse the JSON string and create a JavaModel object.
    //         ChucVuModel chucVuModel = new Gson().fromJson(jsonObject.toString(), ChucVuModel.class);
    //         return ChucVuRule.validateChucVuModel(chucVuModel);
    //     } catch (Exception e) {
    //         // The JSON string is not valid.
    //         List<Object> listField = new ArrayList<>();
    //         for (var obj : jsonObject.keySet())
    //         {
    //             listField.add(jsonObject.get(obj));
    //         }
    //         if (validateChucVu(listField.get(0).toString(), listField.get(1).toString()) != -1)
    //         {
    //             return true;
    //         }
    //         System.err.println("The JSON string is not valid: " + e.getMessage());
    //     }
    //     return false;
    // }

    public static String validTenChucVu(int idChucVu, String tenChucVu)
    {
        boolean checkLength = BaseRule.validateLength(tenChucVu, 5, 100, "ChucVuRule", idChucVu, "tenChucVu");
        return checkLength ? tenChucVu : null;
    }

    public static boolean isValid(JsonObject jsonObject)
    {
        try{
            var chucVuModel = new Gson().fromJson(jsonObject.toString(), ChucVuModel.class);
            return validTenChucVu(chucVuModel.getIdChucVu(), chucVuModel.getTenChucVu()) != null;
        }catch(Exception ex)
        {
            List<String> listKeyGetValue = new ArrayList<>();
            for(var key : jsonObject.keySet())
            {
                listKeyGetValue.add(key);
            }
            int idChucVu = Integer.parseInt((jsonObject.get(listKeyGetValue.get(0))).toString());
            String tenChucVu = jsonObject.get(listKeyGetValue.get(1)).toString();
            return validTenChucVu(idChucVu, tenChucVu) != null;
        }
    }


}
