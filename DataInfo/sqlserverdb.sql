CREATE TABLE BaoHiem (idBaoHiem int NOT NULL, soBaoHiem nvarchar(500) NULL, ngayCap nvarchar(500) NULL, noiCap nvarchar(500) NULL, noiKhamBenh nvarchar(500) NULL, idNhanVien int NULL, PRIMARY KEY (idBaoHiem));
CREATE TABLE ChucVu (idChucVu int NOT NULL, tenChucVu nvarchar(500) NULL, PRIMARY KEY (idChucVu));
CREATE TABLE HopDong (idHopDong int NOT NULL, ngayBatDau nvarchar(500) NULL, ngayKetThuc nvarchar(500) NULL, ngayKy nvarchar(500) NULL, noiDung nvarchar(500) NULL, lanKy nvarchar(500) NULL, thoiHan nvarchar(500) NULL, heSoLuong nvarchar(500) NULL, idNhanVien int NULL, PRIMARY KEY (idHopDong));
CREATE TABLE NhanVien (idNhanVien int NOT NULL, hoTen nvarchar(500) NULL, gioiTinh nvarchar(500) NULL, dienThoai nvarchar(500) NULL, CCCD varchar(500) NULL, hinhAnh nvarchar(500) NULL, trinhDoHocVan nvarchar(500) NULL, idChucVu int NULL, idPhongBan int NULL, PRIMARY KEY (idNhanVien));
CREATE TABLE PhongBan (idPhongBan int NOT NULL, tenPhongBan nvarchar(500) NULL, PRIMARY KEY (idPhongBan));
ALTER TABLE HopDong ADD CONSTRAINT FKHopDong268453 FOREIGN KEY (idNhanVien) REFERENCES NhanVien (idNhanVien);
ALTER TABLE BaoHiem ADD CONSTRAINT FKBaoHiem406278 FOREIGN KEY (idNhanVien) REFERENCES NhanVien (idNhanVien);
ALTER TABLE NhanVien ADD CONSTRAINT FKNhanVien500671 FOREIGN KEY (idPhongBan) REFERENCES PhongBan (idPhongBan);
ALTER TABLE NhanVien ADD CONSTRAINT FKNhanVien923947 FOREIGN KEY (idChucVu) REFERENCES ChucVu (idChucVu);
