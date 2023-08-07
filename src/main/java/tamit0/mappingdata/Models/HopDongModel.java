package tamit0.mappingdata.Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import tamit0.mappingdata.Rules.BaseRule;
import tamit0.mappingdata.Rules.HopDongRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Services.WriteLogService;

import java.sql.Date;

public class HopDongModel {
    private int idHopDong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayKy;
    private String noiDung;
    private int lanKy;
    private int thoiHan;
    private int heSoLuong;
    private int idNhanVien;

    public HopDongModel() {
    }

    public HopDongModel(int idHopDong, Date ngayBatDau, Date ngayKetThuc, Date ngayKy, String noiDung, int lanKy,
            int thoiHan, int heSoLuong, int idNhanVien) {
        this.idHopDong = idHopDong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayKy = ngayKy;
        this.noiDung = noiDung;
        this.lanKy = lanKy;
        this.thoiHan = thoiHan;
        this.heSoLuong = heSoLuong;
        this.idNhanVien = idNhanVien;
    }

    public int getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(int idHopDong) {
        this.idHopDong = idHopDong;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getNgayKy() {
        return ngayKy;
    }

    public void setNgayKy(Date ngayKy) {
        this.ngayKy = ngayKy;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getLanKy() {
        return lanKy;
    }

    public void setLanKy(int lanKy) {
        this.lanKy = lanKy;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public int getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(int heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public static int insertHopDong(HopDongModel hopDongModel, PreparedStatement preparedStatement)
    {
        try {
            preparedStatement.setInt(1, hopDongModel.getIdHopDong());
            preparedStatement.setDate(2, hopDongModel.getNgayBatDau());
            preparedStatement.setDate(3, hopDongModel.getNgayKetThuc());
            preparedStatement.setDate(4, hopDongModel.getNgayKy());
            preparedStatement.setNString(5, hopDongModel.getNoiDung());
            preparedStatement.setInt(6, hopDongModel.getLanKy());
            preparedStatement.setInt(7, hopDongModel.getThoiHan());
            preparedStatement.setInt(8, hopDongModel.getHeSoLuong());
            preparedStatement.setInt(9, hopDongModel.getIdNhanVien());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            WriteLogService.logException(new Exception(BaseRule.dateTimeNow + " : " + HopDongRule.class.getSimpleName() + " - " + hopDongModel.getIdHopDong() + "Lỗi : " + ex.getMessage()));
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
