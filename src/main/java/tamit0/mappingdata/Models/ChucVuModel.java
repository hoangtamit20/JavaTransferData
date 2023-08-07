package tamit0.mappingdata.Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import tamit0.mappingdata.Rules.BaseRule;
import tamit0.mappingdata.Rules.ChucVuRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Services.WriteLogService;

public class ChucVuModel {
    private int idChucVu;
    private String tenChucVu;

    public ChucVuModel() {
    }

    public ChucVuModel(int idChucVu, String tenChucVu) {
        this.idChucVu = idChucVu;
        this.tenChucVu = tenChucVu;
    }

    public int getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(int idChucVu) {
        this.idChucVu = idChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public static int insertChucVu(ChucVuModel chucVuModel, PreparedStatement preparedStatement)
    {
        try {
            preparedStatement.setInt(1, chucVuModel.getIdChucVu());
            preparedStatement.setString(2, chucVuModel.getTenChucVu());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            WriteLogService.logException(new Exception(BaseRule.dateTimeNow + " : " + ChucVuRule.class.getSimpleName() + " - " + chucVuModel.getIdChucVu() + " - Lỗi : " + ex.getMessage()));
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
