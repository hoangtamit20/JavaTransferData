package tamit0.mappingdata.Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import tamit0.mappingdata.Rules.BaseRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Rules.PhongBanRule;
import tamit0.mappingdata.Services.WriteLogService;

public class PhongBanModel {
    private int idPhongBan;
    private String tenPhongBan;

    public PhongBanModel() {
    }

    public PhongBanModel(int idPhongBan, String tenPhongBan) {
        this.idPhongBan = idPhongBan;
        this.tenPhongBan = tenPhongBan;
    }

    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public static int insertPhongBan(PhongBanModel phongBanModel, PreparedStatement preparedStatement)
    {
        try {
            preparedStatement.setInt(1, phongBanModel.getIdPhongBan());
            preparedStatement.setString(2, phongBanModel.getTenPhongBan());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            WriteLogService.logException(new Exception(BaseRule.dateTimeNow + " : " + PhongBanRule.class.getSimpleName() + " - " + phongBanModel.getIdPhongBan() + "Lỗi : " + ex.getMessage()));
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
