package tamit0.mappingdata.Rules;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import tamit0.mappingdata.Models.PhongBanModel;

public class PhongBanRule {
    public static String validTenPhongBan(int idPhongBan, String tenPhongBan)
    {
        if (BaseRule.validateStringEmpty(tenPhongBan, idPhongBan, PhongBanRule.class.getSimpleName(), "tenPhongBan"))
            if (BaseRule.validateLength(tenPhongBan, 2, 100, PhongBanModel.class.getSimpleName(), idPhongBan, "tenPhongBan"))
                return tenPhongBan;
        return null;
    }

    public static boolean isValid(JsonObject jsonObject)
    {
        try{
            var phongBanModel = new Gson().fromJson(jsonObject.toString(), PhongBanModel.class);
            return validTenPhongBan(phongBanModel.getIdPhongBan(), phongBanModel.getTenPhongBan()) != null;
        }catch(Exception ex)
        {
            List<String> listKeyGetValue = new ArrayList<>();
            for(var key : jsonObject.keySet())
            {
                listKeyGetValue.add(key);
            }
            int idPhongBan = Integer.parseInt(jsonObject.get(listKeyGetValue.get(0)).toString());
            String tenPhongBan = jsonObject.get(listKeyGetValue.get(1)).toString();
            return validTenPhongBan(idPhongBan, tenPhongBan) != null;
        }
    }
}
