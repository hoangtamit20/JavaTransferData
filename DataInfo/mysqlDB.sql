CREATE TABLE BaoHiem (idBaoHiem int(11) NOT NULL, soBaoHiem varchar(11) NOT NULL UNIQUE, ngayCap date NOT NULL, noiCap varchar(150) NOT NULL, noiKhamBenh varchar(150) NOT NULL, idNhanVien int(11) NOT NULL, PRIMARY KEY (idBaoHiem));
CREATE TABLE ChucVu (idChucVu int(11) NOT NULL, tenChucVu varchar(100) NOT NULL, PRIMARY KEY (idChucVu));
CREATE TABLE HopDong (idHopDong int(11) NOT NULL, ngayBatDau date NOT NULL, ngayKetThuc date NOT NULL, ngayKy date NOT NULL, noiDung varchar(500) NOT NULL, lanKy int(11) NOT NULL, thoiHan int(11) NOT NULL, heSoLuong int(11) NOT NULL, idNhanVien int(11) NOT NULL, PRIMARY KEY (idHopDong));
CREATE TABLE NhanVien (idNhanVien int(11) NOT NULL, hoTen varchar(50) NOT NULL, gioiTinh int(11) NOT NULL, dienThoai varchar(11), CCCD varchar(255) NOT NULL UNIQUE, hinhAnh varchar(500), trinhDoHocVan varchar(100), idChucVu int(11) NOT NULL, idPhongBan int(11) NOT NULL, PRIMARY KEY (idNhanVien));
CREATE TABLE PhongBan (idPhongBan int(11) NOT NULL, tenPhongBan varchar(100) NOT NULL, PRIMARY KEY (idPhongBan));
ALTER TABLE HopDong ADD CONSTRAINT FKHopDong268453 FOREIGN KEY (idNhanVien) REFERENCES NhanVien (idNhanVien);
ALTER TABLE BaoHiem ADD CONSTRAINT FKBaoHiem406278 FOREIGN KEY (idNhanVien) REFERENCES NhanVien (idNhanVien);
ALTER TABLE NhanVien ADD CONSTRAINT FKNhanVien500671 FOREIGN KEY (idPhongBan) REFERENCES PhongBan (idPhongBan);
ALTER TABLE NhanVien ADD CONSTRAINT FKNhanVien923947 FOREIGN KEY (idChucVu) REFERENCES ChucVu (idChucVu);
