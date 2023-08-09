package tamit0.mappingdata.Rules;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tamit0.mappingdata.Models.BaoHiemModel;

public class BaoHiemRule {

    public static boolean validateSoBaoHiem(String soBaoHiem, int idBaoHiem, List<Object> listSoBaoHiem)
    {
        if (BaseRule.validateStringEmpty(soBaoHiem, idBaoHiem, BaoHiemRule.class.getSimpleName(), "soBaoHiem"))
        {
            if (BaseRule.validSoBaoHiem(soBaoHiem, idBaoHiem, BaoHiemRule.class.getSimpleName(), "soBaoHiem", listSoBaoHiem))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean validateNgayCap(String ngayCap, int idBaoHiem)
    {
        if (BaseRule.validateStringEmpty(ngayCap, idBaoHiem, BaoHiemRule.class.getSimpleName(), "ngayCap"))
        {
            if (BaseRule.validateDate(ngayCap, idBaoHiem, BaoHiemRule.class.getSimpleName(), "ngayCap"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean validateNoiCap(String noiCap, int idBaoHiem)
    {
        if (BaseRule.validateStringEmpty(noiCap, idBaoHiem, BaoHiemRule.class.getSimpleName(), "noiCap"))
        {
            if (BaseRule.validateLength(noiCap, 5, 150, BaoHiemRule.class.getSimpleName(), idBaoHiem, "noiCap"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean validateNoiKhamBenh(String noiKhamBenh, int idBaoHiem)
    {
        if (BaseRule.validateStringEmpty(noiKhamBenh, idBaoHiem, BaoHiemRule.class.getSimpleName(), "noiCap"))
        {
            if (BaseRule.validateLength(noiKhamBenh, 5, 150, BaoHiemRule.class.getSimpleName(), idBaoHiem, "noiCap"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean validBaoHiem(BaoHiemModel baoHiemModel, List<BaoHiemModel> listBaoHiem)
    {
        List<Object> listBH = new ArrayList<>();
        for(var bh : listBaoHiem)
            listBH.add(bh.getSoBaoHiem());
        boolean checkSoBaoHiem = validateSoBaoHiem(baoHiemModel.getSoBaoHiem(), baoHiemModel.getIdBaoHiem(), listBH);
        boolean checkNgayCap = validateNgayCap(baoHiemModel.getNgayCap().toString(), baoHiemModel.getIdBaoHiem());
        boolean checkNoiCap = validateNoiCap(baoHiemModel.getNoiCap(), baoHiemModel.getIdBaoHiem());
        boolean checkNoiKhamBenh = validateNoiKhamBenh(baoHiemModel.getNoiKhamBenh(), baoHiemModel.getIdBaoHiem());
        boolean checkIdNhanVien = BaseRule.validIdForeignKey(baoHiemModel.getIdNhanVien(), baoHiemModel.getIdBaoHiem(), BaoHiemRule.class.getSimpleName(), "idNhanVien");
        return checkIdNhanVien && checkNgayCap && checkNoiKhamBenh && checkSoBaoHiem && checkNoiCap;
    }


    public static boolean isValid(JsonObject jsonObject, List<BaoHiemModel> listBaoHiem) {
        try{
            var baoHiemModel = new Gson().fromJson(jsonObject.toString(), BaoHiemModel.class);
            return validBaoHiem(baoHiemModel, listBaoHiem);
        }catch(Exception ex)
        {
            return isValidCatchJsonObject(jsonObject, listBaoHiem);
        }
    }

    public static boolean isValidCatchJsonObject(JsonObject jsonObject, List<BaoHiemModel> listBaoHiem)
    {
        return jsonObjectToBaoHiemModel(jsonObject, listBaoHiem) != null;
    }

    public static BaoHiemModel jsonObjectToBaoHiemModel(JsonObject jsonObject, List<BaoHiemModel> listBaoHiem)
    {
        List<Object> listBH = new ArrayList<>();
        for(var bh : listBaoHiem)
            listBH.add(bh.getSoBaoHiem());
        List<String> listKeyGetValue = new ArrayList<>();
        for(var key : jsonObject.keySet())
            listKeyGetValue.add(key);
        int idBaoHiem = jsonObject.get(listKeyGetValue.get(0)).getAsInt();
        String soBaoHiem = jsonObject.get(listKeyGetValue.get(1)).getAsString();
        String ngayCap = jsonObject.get(listKeyGetValue.get(2)).getAsString();
        String noiCap = jsonObject.get(listKeyGetValue.get(3)).getAsString();
        String noiKhamBenh = jsonObject.get(listKeyGetValue.get(4)).getAsString();
        int idNhanVien = jsonObject.get(listKeyGetValue.get(5)).getAsInt();

        try{
            boolean checkSoBaoHiem = validateSoBaoHiem(soBaoHiem, idBaoHiem, listBH);
            boolean checkNgayCap = validateNgayCap(ngayCap, idBaoHiem);
            boolean checkNoiCap = validateNoiCap(noiCap, idBaoHiem);
            boolean checkNoiKhamBenh = validateNoiKhamBenh(noiKhamBenh, idBaoHiem);
            boolean checkIdNhanVien = BaseRule.validIdForeignKey(idNhanVien, idBaoHiem, BaoHiemRule.class.getSimpleName(), "idNhanVien");
            boolean check =  checkIdNhanVien && checkNgayCap && checkNoiKhamBenh && checkSoBaoHiem && checkNoiCap;
            if (check)
            {
                return new BaoHiemModel(idBaoHiem, soBaoHiem, BaseRule.stringToDate(ngayCap), noiCap, noiKhamBenh, idNhanVien);
            }
            return null;

        }catch(Exception ex)
        {
            return null;
        }
    }
}
