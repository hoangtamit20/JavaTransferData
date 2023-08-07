package tamit0.mappingdata.Rules;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tamit0.mappingdata.Models.NhanVienModel;
import tamit0.mappingdata.Services.WriteLogService;

public class NhanVienRule {

    private static final int GIOI_TINH_NU = 0;
    private static final int GIOI_TINH_NAM = 1;
    private static final int GIOI_TINH_THU_BA = 2;

    public static boolean validateHoTenNhanVien(String hoTen, int idNhanVien) {
        boolean checkLength = BaseRule.validateLength(hoTen, 3, 100, NhanVienRule.class.getSimpleName(), idNhanVien,
                "hoTen");
        if (checkLength) {
            return BaseRule.validateStringIsCharacter(hoTen, idNhanVien, NhanVienRule.class.getSimpleName(), "hoTen");
        }
        return false;
    }

    public static boolean validateGioiTinh(String gioiTinh, int idNhanVien) {
        var count = 0;
        boolean checkEmptyOrNull = BaseRule.validateStringEmpty(gioiTinh, idNhanVien,
                NhanVienRule.class.getSimpleName(), "gioiTinh");
        if (!checkEmptyOrNull)
            count++;
        boolean checkStringIsNumber = BaseRule.validateStringIsNumber(gioiTinh, idNhanVien,
                NhanVienRule.class.getSimpleName(), "gioiTinh");
        if (checkStringIsNumber) {
            var gioiTinhTest = Integer.parseInt(gioiTinh);
            if (gioiTinhTest != GIOI_TINH_NAM && gioiTinhTest != GIOI_TINH_NU && gioiTinhTest != GIOI_TINH_THU_BA) {
                count++;
                WriteLogService.logException(new Exception(
                        BaseRule.dateTimeNow + " : " + NhanVienRule.class.getSimpleName() + " - validateGioiTinh - "
                                + idNhanVien + " - " + "gioiTinh" + " : chỉ có thể là các số 0,1 hoặc 2"));
            }
        }
        else
        {
            count++;
        }
        return count == 0;
    }

    public static boolean validateDienThoai(String soDT, int idNhanVien) {
        boolean checkEmptyOrNull = BaseRule.validateSoDienThoai(soDT, idNhanVien, NhanVienRule.class.getSimpleName(),
                "soDT");
        boolean checkSoDT = BaseRule.validateSoDienThoai(soDT, idNhanVien, NhanVienRule.class.getSimpleName(), "soDT");
        return checkEmptyOrNull && checkSoDT;
    }

    public static boolean validateCCCD(String CCCD, int idNhanVien, List<Object> listCCCD) {
        boolean checkCCCD = BaseRule.validateCCCD(CCCD, idNhanVien, NhanVienRule.class.getSimpleName(), "CCCD",
                listCCCD);
        boolean checkEmptyOrNull = BaseRule.validateStringEmpty(CCCD, idNhanVien, NhanVienRule.class.getSimpleName(),
                "CCCD");
        return checkCCCD && checkEmptyOrNull;
    }

    public static boolean validateHinhAnh(String hinhAnh, int idNhanVien) {
        boolean checkLength = BaseRule.validateLength(hinhAnh, 4, 500, NhanVienRule.class.getSimpleName(), idNhanVien,
                "hinhAnh");
        return checkLength;
    }

    public static boolean validateTrinhDoHocVan(String trinhDoHocVan, int idNhanVien) {
        boolean checkLength = BaseRule.validateLength(trinhDoHocVan, 4, 100, NhanVienRule.class.getSimpleName(),
                idNhanVien, "trinhDoHocVan");
        return checkLength;
    }

    public static boolean validNhanVien(NhanVienModel nhanVienModel, List<NhanVienModel> listNV) {
        List<Object> listObject = new ArrayList<>();
        for (var nv : listNV) {
            listObject.add(nv.getCCCD());
        }
        boolean checkCCCD = validateCCCD(nhanVienModel.getCCCD(), nhanVienModel.getIdNhanVien(), listObject);
        boolean checkHoTen = validateHoTenNhanVien(nhanVienModel.getHoTen(), nhanVienModel.getIdNhanVien());
        boolean checkTrinhDoHocVan = validateTrinhDoHocVan(nhanVienModel.getTrinhDoHocVan(),
                nhanVienModel.getIdNhanVien());
        boolean checkGioiTinh = validateGioiTinh(Integer.toString(nhanVienModel.getGioiTinh()), nhanVienModel.getIdNhanVien());
        boolean checkDienThoai = validateDienThoai(nhanVienModel.getDienThoai(), nhanVienModel.getIdNhanVien());
        boolean checkHinhAnh = validateHinhAnh(nhanVienModel.getHinhAnh(), nhanVienModel.getIdNhanVien());
        boolean checkIdChuVu = BaseRule.validIdForeignKey(nhanVienModel.getIdChucVu(), nhanVienModel.getIdNhanVien(),
                NhanVienRule.class.getSimpleName(), "idChucVu");
        boolean checkIdPhongBan = BaseRule.validIdForeignKey(nhanVienModel.getIdPhongBan(),
                nhanVienModel.getIdNhanVien(), NhanVienRule.class.getSimpleName(), "idPhongBan");
        return (checkCCCD && checkDienThoai && checkHoTen && checkTrinhDoHocVan && checkGioiTinh && checkHinhAnh
                && checkIdChuVu && checkIdPhongBan);
    }

    public static boolean isValid(JsonObject jsonObject, List<NhanVienModel> listNV) {
        try {
            var nhanVienModel = new Gson().fromJson(jsonObject.toString(), NhanVienModel.class);
            return validNhanVien(nhanVienModel, listNV);
        } catch (Exception ex) {
            return isValidCatchJsonObject(jsonObject, listNV);
        }
    }

    public static boolean isValidCatchJsonObject(JsonObject jsonObject, List<NhanVienModel> listNV) {
        List<Object> listObject = new ArrayList<>();
        for (var nv : listNV) {
            listObject.add(nv.getCCCD());
        }

        List<String> listKeyGetValue = new ArrayList<>();
        for (var key : jsonObject.keySet())
            listKeyGetValue.add(key);
        int idNhanVien = jsonObject.get(listKeyGetValue.get(0)).getAsInt();
        String hoTen = jsonObject.get(listKeyGetValue.get(1)).getAsString();
        String gioiTinh = jsonObject.get(listKeyGetValue.get(2)).getAsString();
        String dienThoai = jsonObject.get(listKeyGetValue.get(3)).getAsString();
        String CCCD = jsonObject.get(listKeyGetValue.get(4)).getAsString();
        String hinhAnh = jsonObject.get(listKeyGetValue.get(5)).getAsString();
        String trinhDoHocVan = jsonObject.get(listKeyGetValue.get(6)).getAsString();
        int idChucVu = jsonObject.get(listKeyGetValue.get(7)).getAsInt();
        int idPhongBan = jsonObject.get(listKeyGetValue.get(8)).getAsInt();

        try {
            boolean checkCCCD = validateCCCD(CCCD, idNhanVien, listObject);
            boolean checkHoTen = validateHoTenNhanVien(hoTen, idNhanVien);
            boolean checkTrinhDoHocVan = validateTrinhDoHocVan(trinhDoHocVan, idNhanVien);
            boolean checkGioiTinh = validateGioiTinh(gioiTinh, idNhanVien);
            boolean checkDienThoai = validateDienThoai(dienThoai, idNhanVien);
            boolean checkHinhAnh = validateHinhAnh(hinhAnh, idNhanVien);
            boolean checkIdChuVu = BaseRule.validIdForeignKey(idChucVu, idNhanVien, NhanVienRule.class.getSimpleName(),
                    "idChucVu");
            boolean checkIdPhongBan = BaseRule.validIdForeignKey(idPhongBan, idNhanVien,
                    NhanVienRule.class.getSimpleName(), "idPhongBan");
            return (checkCCCD && checkDienThoai && checkHoTen && checkTrinhDoHocVan && checkGioiTinh && checkHinhAnh
                    && checkIdChuVu && checkIdPhongBan);
        } catch (Exception ex) {
            return false;
        }
    }
}
