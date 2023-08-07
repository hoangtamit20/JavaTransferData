package tamit0.mappingdata.Rules;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tamit0.mappingdata.Models.HopDongModel;
import tamit0.mappingdata.Services.WriteLogService;

public class HopDongRule {

    public static boolean validateNgayBatDau(String ngayBatDau, int idHopDong) {
        if (BaseRule.validateStringEmpty(ngayBatDau, idHopDong, HopDongRule.class.getSimpleName(), "ngayBatDau"))
            if (BaseRule.validateDate(ngayBatDau, idHopDong, HopDongRule.class.getSimpleName(), "ngayBatDau"))
                return true;
        return false;
    }

    public static boolean validateNgayKetThuc(String ngayKetThuc, int idHopDong) {
        if (BaseRule.validateStringEmpty(ngayKetThuc, idHopDong, HopDongRule.class.getSimpleName(), "ngayKetThuc"))
            if (BaseRule.validateDate(ngayKetThuc, idHopDong, HopDongRule.class.getSimpleName(), "ngayKetThuc"))
                return true;
        return false;
    }

    public static boolean validateNgayKy(String ngayKy, int idHopDong) {
        if (BaseRule.validateStringEmpty(ngayKy, idHopDong, HopDongRule.class.getSimpleName(), "ngayKy"))
            if (BaseRule.validateDate(ngayKy, idHopDong, HopDongRule.class.getSimpleName(), "ngayKy"))
                return true;
        return false;
    }

    public static boolean validateNoiDung(String noiDung, int idHopDong) {
        if (BaseRule.validateStringEmpty(noiDung, idHopDong, HopDongRule.class.getSimpleName(), "noiDung"))
            if (BaseRule.validateLength(noiDung, 10, 500, HopDongRule.class.getSimpleName(), idHopDong, "noiDung"))
                return true;
        return false;
    }

    public static boolean validateLanKy(String lanKy, int idHopDong) {
        if (BaseRule.validateStringEmpty(lanKy, idHopDong, HopDongRule.class.getSimpleName(), "lanKy"))
            if (BaseRule.validateStringIsNumber(lanKy, idHopDong, HopDongRule.class.getSimpleName(), "lanKy"))
                return true;
        return false;
    }

    public static boolean validateThoiHan(String thoiHan, int idHopDong) {
        if (BaseRule.validateStringEmpty(thoiHan, idHopDong, HopDongRule.class.getSimpleName(), "thoiHan"))
            if (BaseRule.validateStringIsNumber(thoiHan, idHopDong, HopDongRule.class.getSimpleName(), "thoiHan"))
                return true;
        return false;
    }

    public static boolean validateHeSoLuong(String heSoLuong, int idHopDong) {
        if (BaseRule.validateStringEmpty(heSoLuong, idHopDong, HopDongRule.class.getSimpleName(), "heSoLuong"))
            if (BaseRule.validateStringIsNumber(heSoLuong, idHopDong, HopDongRule.class.getSimpleName(), "heSoLuong"))
                return true;
        return false;
    }

    public static boolean validHopDong(HopDongModel hopDongModel) {
        boolean checkNgayBatDau = validateNgayBatDau(hopDongModel.getNgayBatDau().toString(),
                hopDongModel.getIdHopDong());
        boolean checkNgayKetThuc = validateNgayKetThuc(hopDongModel.getNgayKetThuc().toString(),
                hopDongModel.getIdHopDong());
        boolean checkNgayKy = validateNgayKy(hopDongModel.getNgayKy().toString(), hopDongModel.getIdHopDong());
        boolean checkNoiDung = validateNoiDung(hopDongModel.getNoiDung(), hopDongModel.getIdHopDong());
        boolean checkLanKy = validateLanKy(Integer.toString(hopDongModel.getLanKy()), hopDongModel.getIdHopDong());
        boolean checkThoiHan = validateThoiHan(Integer.toString(hopDongModel.getThoiHan()),
                hopDongModel.getIdHopDong());
        boolean checkHeSoLuong = validateHeSoLuong(Integer.toString(hopDongModel.getHeSoLuong()),
                hopDongModel.getIdHopDong());
        boolean checkIdNhanVien = BaseRule.validIdForeignKey(hopDongModel.getIdNhanVien(), hopDongModel.getIdHopDong(),
                BaoHiemRule.class.getSimpleName(), "idNhanVien");
        return checkNgayBatDau && checkNgayKetThuc && checkNgayKy && checkNoiDung && checkLanKy && checkThoiHan
                && checkHeSoLuong && checkIdNhanVien;
    }

    public static boolean isValid(JsonObject jsonObject) {
        try {
            var hopDongModel = new Gson().fromJson(jsonObject.toString(), HopDongModel.class);
            return validHopDong(hopDongModel);
        } catch (Exception ex) {
            return isValidCatchJsonObject(jsonObject);
        }
    }

    public static boolean isValidCatchJsonObject(JsonObject jsonObject) {
        List<String> listKeyGetValue = new ArrayList<>();
        for (var key : jsonObject.keySet())
            listKeyGetValue.add(key);
        try {

            int idHopDong = jsonObject.get(listKeyGetValue.get(0)).getAsInt();
            String ngayBatDau = jsonObject.get(listKeyGetValue.get(1)).getAsString();
            String ngayKetThuc = jsonObject.get(listKeyGetValue.get(2)).getAsString();
            String ngayKy = jsonObject.get(listKeyGetValue.get(3)).getAsString();
            String noiDung = jsonObject.get(listKeyGetValue.get(4)).getAsString();
            String lanKy = jsonObject.get(listKeyGetValue.get(5)).getAsString();
            String thoiHan = jsonObject.get(listKeyGetValue.get(6)).getAsString();
            String heSoLuong = jsonObject.get(listKeyGetValue.get(7)).getAsString();
            int idNhanVien = jsonObject.get(listKeyGetValue.get(8)).getAsInt();

            boolean checkNgayBatDau = validateNgayBatDau(ngayBatDau, idHopDong);
            boolean checkNgayKetThuc = validateNgayKetThuc(ngayKetThuc, idHopDong);
            boolean checkNgayKy = validateNgayKy(ngayKy, idHopDong);
            boolean checkNoiDung = validateNoiDung(noiDung, idHopDong);
            boolean checkLanKy = validateLanKy(lanKy, idHopDong);
            boolean checkThoiHan = validateThoiHan(thoiHan, idHopDong);
            boolean checkHeSoLuong = validateHeSoLuong(heSoLuong, idHopDong);
            boolean checkIdNhanVien = BaseRule.validIdForeignKey(idNhanVien,
                    idHopDong, BaoHiemRule.class.getSimpleName(), "idNhanVien");
            return checkNgayBatDau && checkNgayKetThuc && checkNgayKy && checkNoiDung && checkLanKy && checkThoiHan
                    && checkHeSoLuong && checkIdNhanVien;
        } catch (Exception ex) {
            return false;
        }
    }

}
