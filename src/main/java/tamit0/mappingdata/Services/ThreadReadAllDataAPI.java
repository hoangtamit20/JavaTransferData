package tamit0.mappingdata.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tamit0.mappingdata.Configuration.TableConfig;
import tamit0.mappingdata.Models.BaoHiemModel;
import tamit0.mappingdata.Models.ChucVuModel;
import tamit0.mappingdata.Models.HopDongModel;
import tamit0.mappingdata.Models.NhanVienModel;
import tamit0.mappingdata.Models.PhongBanModel;
import tamit0.mappingdata.Rules.BaoHiemRule;
import tamit0.mappingdata.Rules.ChucVuRule;
import tamit0.mappingdata.Rules.HopDongRule;
import tamit0.mappingdata.Rules.NhanVienRule;
import tamit0.mappingdata.Rules.PhongBanRule;

public class ThreadReadAllDataAPI extends Thread {
    private static final int ID_IS_NULL = 0;

    public static JsonArray getDataFromApi(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create a new HttpGet object for the API URL.
        HttpGet httpGet = new HttpGet(url);

        try (// Execute the request and get the response.
                CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // Check if the response is successful.
            if (response.getStatusLine().getStatusCode() == 200) {
                // Get the response body as a JSON array.
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                String responseBody = bufferedReader.readLine();

                // Convert the response body to a JsonArray.
                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(responseBody, JsonArray.class);

                return jsonArray;
            }
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<JsonObject> getListJsonObject(JsonArray jsonArray) {
        List<JsonObject> listJONSObject = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObjectMap = jsonArray.get(i).getAsJsonObject();
                // get JsonObject đưa về dạng object theo model
                JsonObject jsonObject = jsonObjectMap.has("map") ? jsonObjectMap.getAsJsonObject("map") : jsonObjectMap;
                listJONSObject.add(jsonObject);
            }
        } catch (JSONException e) {
            System.out.println("Error getting JSONObject from JSONArray: " + e.getMessage());
        }
        return listJONSObject;
    }

    public static List<Object> getFieldOfTable(String tableName) {
        List<Object> listFieldOfTable = new ArrayList<>();
        TableConfig tableConfig = ConnectionService.getJsonMapper().getTables().get(tableName);
        if (tableConfig != null) {
            for (String fieldName : tableConfig.getFields().keySet()) {
                for (String fieldMySqlTable : tableConfig.getFields().get(fieldName).getMySqlFields().values()) {
                    listFieldOfTable.add(fieldMySqlTable);
                }
            }
        }
        return listFieldOfTable;
    }

    public static String getStrSql(String tableName, JsonObject jsonObject) {
        String sql = "INSERT INTO " + tableName + "(";
        int count = 0;
        for (var field : getFieldOfTable(tableName)) {
            if (++count == getFieldOfTable(tableName).size()) {
                sql += field + ")";
                count = 0;
            } else {
                sql += field + ", ";
            }
        }
        sql += " VALUES(";
        for (var field : getFieldOfTable(tableName)) {
            if (++count == getFieldOfTable(tableName).size()) {
                sql += "?" + ")";
                count = 0;
            } else {
                sql += "?" + ", ";
            }
        }
        return sql;
    }

    public static String getTableNameOfJsonObject(JsonObject jsonObject) {
        var count = 0;
        for (var j : jsonObject.keySet()) {
            if (count == 0) {
                ++count;
                for (var tblConfig : ConnectionService.getJsonMapper().getTables().values()) {
                    for (var fieldConfig : tblConfig.getFields().values()) {
                        if (fieldConfig.getSqlServerFields().get("id").equals(j)) {
                            return tblConfig.getSqlServerTableName();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        var url = "http://localhost:8080/getAllData";
        var jsonArray = getDataFromApi(url);
        var jsonObjects = getListJsonObject(jsonArray);
        List<ChucVuModel> chucVuModels = new ArrayList<>();
        List<PhongBanModel> phongBanModels = new ArrayList<>();
        List<NhanVienModel> nhanVienModels = new ArrayList<>();
        List<HopDongModel> hopDongModels = new ArrayList<>();
        List<BaoHiemModel> baoHiemModels = new ArrayList<>();

        for (var j : jsonObjects) {
            var chucVuModel = new ChucVuModel();
            var phongBanModel = new PhongBanModel();
            var nhanVienModel = new NhanVienModel();
            var hopDongModel = new HopDongModel();
            var baoHiemModel = new BaoHiemModel();
            var sql = getStrSql(getTableNameOfJsonObject(j), j);
            try (PreparedStatement preparedStatement = ConnectionService.getConnection("mysql").prepareStatement(sql)) {
                try {
                    nhanVienModel = new Gson().fromJson(j.toString(), NhanVienModel.class);
                    if (nhanVienModel != null && nhanVienModel.getIdNhanVien() > ID_IS_NULL
                            && nhanVienModel.getIdChucVu() != ID_IS_NULL
                            && nhanVienModel.getIdPhongBan() != ID_IS_NULL
                            && nhanVienModel.getClass().getDeclaredFields().length >= j.entrySet().size()) {
                        if (NhanVienRule.isValid(j, nhanVienModels)) {
                            if (NhanVienModel.insertNhanVien(nhanVienModel, preparedStatement) > 0)
                                nhanVienModels.add(nhanVienModel);
                        }
                        continue;
                    }
                } catch (Exception ex) {
                    NhanVienRule.isValidCatchJsonObject(j, nhanVienModels);
                    continue;
                }

                try {
                    hopDongModel = new Gson().fromJson(j.toString(), HopDongModel.class);
                    if (hopDongModel != null && hopDongModel.getIdHopDong() > ID_IS_NULL
                            && hopDongModel.getIdNhanVien() != ID_IS_NULL
                            && hopDongModel.getClass().getDeclaredFields().length >= j.entrySet().size()) {
                        if (HopDongRule.isValid(j)) {
                            if (HopDongModel.insertHopDong(hopDongModel, preparedStatement) > 0)
                                hopDongModels.add(hopDongModel);
                        }
                        continue;
                    }
                } catch (Exception ex) {
                    HopDongRule.isValidCatchJsonObject(j);
                }

                try {
                    baoHiemModel = new Gson().fromJson(j.toString(), BaoHiemModel.class);
                    if (baoHiemModel != null && baoHiemModel.getIdBaoHiem() > ID_IS_NULL
                            && baoHiemModel.getIdNhanVien() != ID_IS_NULL
                            && baoHiemModel.getClass().getDeclaredFields().length >= j.entrySet().size()) {
                        if (BaoHiemRule.isValid(j, baoHiemModels)) {
                            if (BaoHiemModel.insertBaoHiem(baoHiemModel, preparedStatement) > 0)
                                baoHiemModels.add(baoHiemModel);
                        }
                        continue;
                    }
                } catch (Exception ex) {
                    var bh = BaoHiemRule.jsonObjectToBaoHiemModel(j, baoHiemModels);
                    if (bh != null)
                        if (BaoHiemModel.insertBaoHiem(bh, preparedStatement) > 0)
                            baoHiemModels.add(bh);
                    continue;
                }
                try {
                    chucVuModel = new Gson().fromJson(j.toString(), ChucVuModel.class);
                    if (chucVuModel != null && chucVuModel.getIdChucVu() > 0
                            && chucVuModel.getClass().getDeclaredFields().length >= j.entrySet().size()) {
                        if (ChucVuRule.isValid(j)) {
                            if (ChucVuModel.insertChucVu(chucVuModel, preparedStatement) > 0)
                                chucVuModels.add(chucVuModel);
                        }
                        continue;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                try {
                    phongBanModel = new Gson().fromJson(j.toString(), PhongBanModel.class);
                    if (phongBanModel != null && phongBanModel.getIdPhongBan() > 0
                            && phongBanModel.getClass().getDeclaredFields().length >= j.entrySet().size()) {
                        if (PhongBanRule.isValid(j)) {
                            if (PhongBanModel.insertPhongBan(phongBanModel, preparedStatement) > 0)
                                phongBanModels.add(phongBanModel);
                        }
                        continue;
                    }
                } catch (Exception ex) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        printThongKe(jsonArray.size(), chucVuModels.size() + phongBanModels.size() + nhanVienModels.size()
                + hopDongModels.size() + baoHiemModels.size());
    }

    public static void printThongKe(int lengOfJsonArray, int sizeOfAllList) {
        System.out.println("\n\n\n\n\n---------- THONG KE CHUYEN DU LIEU ----------");
        System.out.println("\u001B[36m" + "Total : " + lengOfJsonArray + "\u001B[0m");
        System.out.println("\u001B[32m" + "Success : " + sizeOfAllList + "\u001B[0m");
        System.out.println("\u001B[31m" + "Failed : " + (lengOfJsonArray - sizeOfAllList) + "\u001B[0m");
        System.out.println("\u001b[38;5;11m"+"Log Path : "+ "E:\\JavaCS445\\mappingdata\\log.txt" +"\u001b[0m");
    }
}