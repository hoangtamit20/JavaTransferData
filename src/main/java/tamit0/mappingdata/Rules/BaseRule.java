package tamit0.mappingdata.Rules;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import tamit0.mappingdata.Services.WriteLogService;

public class BaseRule {
    public static final String dateTimeNow = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    public static final int DO_DAI_CMND = 9;
    public static final int DO_DAI_CCCD = 12;
    public static final int DO_DAI_SO_DIEN_THOAI_BAN = 11;
    public static final int DO_DAI_SO_DIEN_THOAI_CAM_TAY = 10;
    public static final int DO_DAI_SO_BAO_HIEM = 11;

    public static boolean validateLength(String str, int minLength, int maxLength, String ruleName, int idObject,
            String variableName) {
        if (!validateStringEmpty(str, idObject, ruleName, variableName))
            return false;
        else if (str.length() < minLength || str.length() > maxLength) {
            WriteLogService.logException(
                    new Exception(dateTimeNow + " : " + ruleName + " - validateLength - " + idObject + " - "
                            + variableName + " : phải có độ dài từ " + minLength + " đến " + maxLength + " ký tự."));
            return false;
        }
        return true;
    }

    public static boolean validateStringEmpty(String str, int idObject, String ruleName, String variableName) {
        if (str == null) {
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateStringEmpty - "
                    + idObject + " - " + variableName + " : rỗng."));
            return false;
        }
        return true;
    }

    public static boolean validateDate(String date, int idObject, String ruleName, String variableName) {
        var count = 0;
        if (!validateStringEmpty(date, idObject, ruleName, variableName))
            return false;
        String[] formats = { "yyyy-MM-dd", "yyyyMMdd", "dd-MM-yyyy", "MM-dd-yyyy", "EEEE, MMMM dd, yyyy",
                "MMMM d, yyyy", "d MMMM, yyyy", "dd/MM/yyyy", "MM/dd/yyyy" };
        for (String format : formats) {
            try {

                SimpleDateFormat sdf = new SimpleDateFormat(format);
                Date dateFormat = new java.sql.Date(sdf.parse(date).getTime());
                if (dateFormat != null && dateFormat.toString().equals(date)) {
                    count++;
                }

            } catch (ParseException e) {
                // System.out.println("Invalid date format");
            }
        }
        if (count == 0) {
            WriteLogService.logException(new Exception(
                    dateTimeNow + " : " + ruleName + " - validateDate - "
                            + idObject + " - " + variableName + " : định dạng không hợp lệ."));
            return false;
        }
        return true;
    }

    public static boolean validateSoDienThoai(String strSdt, int idObject, String ruleName, String variableName) {
        var count = 0;
        if (strSdt.length() != DO_DAI_SO_DIEN_THOAI_CAM_TAY && strSdt.length() != DO_DAI_SO_DIEN_THOAI_BAN) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateSoDienThoai - "
                    + idObject + " - " + variableName + " : có độ dài không hợp lệ"));
        }
        if (!Pattern.compile("^[0-9]+$").matcher(strSdt).matches()) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateSoDienThoai - "
                    + idObject + " - " + variableName + " : định dạng không hợp lệ!"));
        }
        return count == 0;
    }

    public static boolean validateCCCD(String strCCCD, int idObject, String ruleName, String variableName,
            List<Object> listCCCD) {
        var count = 0;
        if (strCCCD.length() != DO_DAI_CMND && strCCCD.length() != DO_DAI_CCCD) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateCCCD - "
                    + idObject + " - " + variableName + " : độ dài không hợp lệ.(" + DO_DAI_CMND + "hoặc "+ DO_DAI_CCCD +" số)!"));
        }
        if (listCCCD.contains(strCCCD)) {
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateCCCD - "
                    + idObject + " - " + variableName + " : Đã tồn tại!"));
        }
        if (!Pattern.compile("^[0-9]+$").matcher(strCCCD).matches()) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateCCCD - "
                    + idObject + " - " + variableName + " : định dạng không hợp lệ!"));
        }
        return count == 0;
    }

    public static boolean validateStringIsCharacter(String str, int idObject, String ruleName, String variableName) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                WriteLogService
                        .logException(new Exception(dateTimeNow + " : " + ruleName + " - validateStringIsCharacter - "
                                + idObject + " - " + variableName + " : định dạng không hợp lệ!"));
                return false;
            }
        }
        return true;
    }

    public static boolean validateStringIsNumber(String str, int idObject, String ruleName, String variableName) {
        if (!Pattern.compile("^[0-9]+$").matcher(str).matches()) {
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validateStringIsNumber - "
                    + idObject + " - " + variableName + " : định dạng không hợp lệ!"));
            return false;
        }
        return true;
    }

    public static boolean validIdForeignKey(int idForeignKey, int idObject, String ruleName, String variableName) {
        if (idForeignKey < 1) {
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validIdForeignKey - "
                    + idObject + " - " + variableName + " : null"));
            return false;
        }
        return true;
    }

    public static boolean validSoBaoHiem(String strSoBaoHiem, int idObject, String ruleName, String variableName,
            List<Object> listSoBaoHiem) {
        var count = 0;
        if (strSoBaoHiem.length() != DO_DAI_SO_BAO_HIEM) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validSoBaoHiem - "
                    + idObject + " - " + variableName + " : độ dài không hợp lệ.(11 số)!"));
        }
        if (listSoBaoHiem.contains(strSoBaoHiem)) {
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validSoBaoHiem - "
                    + idObject + " - " + variableName + " : Đã tồn tại!"));
        }
        if (!Pattern.compile("^[0-9]+$").matcher(strSoBaoHiem).matches()) {
            count++;
            WriteLogService.logException(new Exception(dateTimeNow + " : " + ruleName + " - validSoBaoHiem - "
                    + idObject + " - " + variableName + " : định dạng không hợp lệ!"));
        }
        return count == 0;
    }

    public static Date stringToDate(String strDate)
    {
        String[] formats = { "yyyy-MM-dd", "yyyyMMdd", "dd-MM-yyyy", "MM-dd-yyyy", "EEEE, MMMM dd, yyyy",
                "MMMM d, yyyy", "d MMMM, yyyy", "dd/MM/yyyy", "MM/dd/yyyy" };
        for (String format : formats) {
            try {

                SimpleDateFormat sdf = new SimpleDateFormat(format);
                Date dateFormat = new java.sql.Date(sdf.parse(strDate).getTime());
                if (dateFormat != null && dateFormat.toString().equals(strDate)) {
                    return dateFormat;
                }

            } catch (ParseException e) {                
            }
        }
        return null;
    }
}