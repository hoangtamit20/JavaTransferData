package tamit0.mappingdata.Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import tamit0.mappingdata.Rules.BaseRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Services.WriteLogService;

public class NhanVienModel {
    private int idNhanVien;
    private String hoTen;
    private int gioiTinh;
    private String dienThoai;
    private String CCCD;
    private String hinhAnh;
    private String trinhDoHocVan;
    private int idChucVu;
    private int idPhongBan;

    public NhanVienModel() {
    }

    public NhanVienModel(int idNhanVien, String hoTen, int gioiTinh, String dienThoai, String CCCD, String hinhAnh,
            String trinhDoHocVan, int idChucVu, int idPhongBan) {
        this.idNhanVien = idNhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.CCCD = CCCD;
        this.hinhAnh = hinhAnh;
        this.trinhDoHocVan = trinhDoHocVan;
        this.idChucVu = idChucVu;
        this.idPhongBan = idPhongBan;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrinhDoHocVan() {
        return trinhDoHocVan;
    }

    public void setTrinhDoHocVan(String trinhDoHocVan) {
        this.trinhDoHocVan = trinhDoHocVan;
    }

    public int getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(int idChucVu) {
        this.idChucVu = idChucVu;
    }

    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    public static int insertNhanVien(NhanVienModel nhanVienModel, PreparedStatement preparedStatement)
    {
        try {
            preparedStatement.setInt(1, nhanVienModel.getIdNhanVien());
            preparedStatement.setString(2, nhanVienModel.getHoTen());
            preparedStatement.setInt(3, nhanVienModel.getGioiTinh());
            preparedStatement.setString(4, nhanVienModel.getDienThoai());
            preparedStatement.setString(5, nhanVienModel.getCCCD());
            preparedStatement.setString(6, nhanVienModel.getHinhAnh());
            preparedStatement.setString(7, nhanVienModel.getTrinhDoHocVan());
            preparedStatement.setInt(8, nhanVienModel.getIdChucVu());
            preparedStatement.setInt(9, nhanVienModel.getIdPhongBan());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            WriteLogService.logException(new Exception(BaseRule.dateTimeNow + " : " + NhanVienRule.class.getSimpleName() + " - " + nhanVienModel.getIdNhanVien() + " - Lỗi : " + ex.getMessage()));
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
