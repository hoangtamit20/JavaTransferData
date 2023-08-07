package tamit0.mappingdata.Models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tamit0.mappingdata.Rules.BaoHiemRule;
import tamit0.mappingdata.Rules.BaseRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Services.WriteLogService;

public class BaoHiemModel {
    private int idBaoHiem;
    private String soBaoHiem;
    private Date ngayCap;
    private String noiCap;
    private String noiKhamBenh;
    private int idNhanVien;

    public BaoHiemModel() {
    }

    public BaoHiemModel(int idBaoHiem, String soBaoHiem, Date ngayCap, String noiCap, String noiKhamBenh,
            int idNhanVien) {
        this.idBaoHiem = idBaoHiem;
        this.soBaoHiem = soBaoHiem;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.noiKhamBenh = noiKhamBenh;
        this.idNhanVien = idNhanVien;
    }

    public int getIdBaoHiem() {
        return idBaoHiem;
    }

    public void setIdBaoHiem(int idBaoHiem) {
        this.idBaoHiem = idBaoHiem;
    }

    public String getSoBaoHiem() {
        return soBaoHiem;
    }

    public void setSoBaoHiem(String soBaoHiem) {
        this.soBaoHiem = soBaoHiem;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getNoiKhamBenh() {
        return noiKhamBenh;
    }

    public void setNoiKhamBenh(String noiKhamBenh) {
        this.noiKhamBenh = noiKhamBenh;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public static int insertBaoHiem(BaoHiemModel baoHiemModel, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setInt(1, baoHiemModel.getIdBaoHiem());
            preparedStatement.setString(2, baoHiemModel.getSoBaoHiem());
            preparedStatement.setDate(3, baoHiemModel.getNgayCap());
            preparedStatement.setString(4, baoHiemModel.getNoiCap());
            preparedStatement.setString(5, baoHiemModel.getNoiKhamBenh());
            preparedStatement.setInt(6, baoHiemModel.getIdNhanVien());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            WriteLogService.logException(new Exception(BaseRule.dateTimeNow + " : " + BaoHiemRule.class.getSimpleName() + " - " + baoHiemModel.getIdBaoHiem() + " - Lỗi : " + ex.getMessage()));
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
