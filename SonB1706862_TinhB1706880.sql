
		drop database doan;
		create database Doan;
		use  Doan;

		-- bang mon an
		-- drop table monan;
		create table MonAn(
			idMA varchar(10) primary key,
			tenMA varchar(30) not null,
			gia int,
			trangthaiMA boolean

		);

		-- bang ban
		-- drop table ban;
		create table ban(
			idBan varchar(10) primary key,
			tenBan varchar(30),
			ngayvao date,
			tgvao time,
			trangthaiBan boolean

		);

-- them du lieu vao bang "Ban"
		insert into  ban(idBan,tenBan,trangthaiBan) value("A01","khu vuc A/ban so 01",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("A02","khu vuc A/ban so 02",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("A03","khu vuc A/ban so 03",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("A04","khu vuc A/ban so 04",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("A05","khu vuc A/ban so 05",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("B01","khu vuc B/ban so 01",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("B02","khu vuc B/ban so 02",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("B03","khu vuc B/ban so 03",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("B04","khu vuc B/ban so 04",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("B05","khu vuc B/ban so 05",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("C01","khu vuc C/ban so 01",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("C02","khu vuc C/ban so 02",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("C03","khu vuc C/ban so 03",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("C04","khu vuc C/ban so 04",false);
		insert into  ban(idBan,tenBan,trangthaiBan) value("C05","khu vuc C/ban so 05",false);


		create table BangChucVu(
			macv varchar(10) primary key,
			ChucVu varchar(20),
			mucluong int,
			donvi varchar(10)
		);

		insert into BangChucVu value ("NVQL","Nhan Vien Quan Ly","250000","8h");
		insert into BangChucVu value ("NVPV","Nhan Vien Phuc Vu","180000","8h");
		insert into BangChucVu value ("CTV","Cong Tac Vien","120000","8h");
		insert into BangChucVu value ("NVB","Nhan Vien Bep","200000","8h");
		insert into BangChucVu value ("NVPB","Nhan Vien Phu Bep","150000","8h");
		insert into BangChucVu value ("NVBV","Nhan Vien Bao Ve","140000","8h");

		-- bang nhan vien
		-- drop table nhanvien;
		create table NhanVien(
		idNV varchar(10) primary key,
		tenNV varchar(40),
		GTinh 	varchar(5),
		DChi 	varchar(100),
		SDT varchar(12),
		ngayBD date,
		soNgayLV int,
		macv 	varchar(10),
		foreign key(macv) references BangChucVu(macv),
		trangthaiNV boolean
		);

	-- them du lieu vao bang "Nhan Vien"
			insert into nhanvien value ("QL","Nguyen Van Lom","nam","Soc Trang","0381234567","2019/11/12","30","NVQL",true);
			insert into nhanvien value ("QL1","Tran Cao A Nam","nu","Dong Thap","0798578314","2019/11/12","30","NVQL",false);	

			insert into nhanvien value ("NV1","Cao Quoc Hung","nam","An Giang","0909767534","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV2","Tran Thi Thao My","nu","Can Tho","0988762412","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV4","Nguyen Hong Nga","nu","Tien Giang","0986862451","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV3","Tran Le Minh Hien","nam","Can Tho","0985486662","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV5","Pham Kieu Nhung","nu","Can Tho","0897446622","2019/11/12","30","NVPV",false);
			insert into nhanvien value ("NV6","Tran Thi Minh Ha","nam","Tra Vinh","0927211316","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV7","Nguyen Thi Hong","nu","An Giang","0988721316","2019/11/12","30","NVPV",true);
			insert into nhanvien value ("NV8","Pham Binh Minh","nam","Tra Vinh","0927288965","2019/11/12","30","NVPV",false);

			insert into nhanvien value ("NV9","Le Thi My Duyen","nu","Can Tho","0927097463","2019/11/12","30","CTV",false);
			insert into nhanvien value ("NV10","Hoang Nhat Phi","nam","Soc Trang","0925356205","2019/11/12","30","CTV",true);
			insert into nhanvien value ("NV11","Thai Hoang Nam","nam","Long An","0808738965","2019/11/12","30","CTV",true);
			insert into nhanvien value ("NV12","Cao Quoc Trung","nam","Tien Giang","0908738453","2019/11/12","30","CTV",true);

			insert into nhanvien value ("NV13","Tran Nhu Quynh","nu","Long An","0808738965","2019/11/12","30","NVB",true);
			insert into nhanvien value ("NV14","Thai Hoang Nam","nam","Dong Nai","0907778965","2019/11/12","30","NVB",true);
			insert into nhanvien value ("NV15","Thai Anh Quoc","nam","Long An","0808738965","2019/11/12","30","NVB",true);

			insert into nhanvien value ("NV16","Thai Hoang Nam","nam","Long An","0808738965","2019/11/12","30","NVPB",true);
			insert into nhanvien value ("NV17","Thai Hoang Nam","nam","Long An","0808738965","2019/11/12","30","NVPB",true);

			insert into nhanvien value ("NV18","Thai Hoang Nam","nam","Long An","0808738965","2019/11/12","30","NVBV",true);
			insert into nhanvien value ("NV19","Thai Hoang Nam","nam","Long An","0808738965","2019/11/12","30","NVBV",true);

		create table Ca(
			maca varchar(5) primary key,
			tenca varchar(10),
			thoigian varchar(20),
			hesoluong double
		);
		
		insert into Ca value ("c1","Ca 1","5h30'-13h30'","1,1");
		insert into Ca value ("c2","Ca 2","13h30'-21h30'","1,4");
		insert into Ca value ("c3","Ca 3","15h30'-23h30'","1,6");
		

		create table BangDiemDanh(
			idNV varchar(10),
			maca varchar(5),
			ngayDD date,
			soNgayLam int,
			foreign key(idNV) references NhanVien(idNV),
			foreign key(maca) references Ca(maca)

		);
		

		create table BangLuong(
			idNV varchar(10),
			soNgayLam int,
			tienLuong int,
			trangthaiLuong boolean,
			foreign key(idNV) references NhanVien(idNV)
		);


	-- bang goi mon an
	-- drop table GoiMon;
		create table GoiMon(
			STT int,
			idNV varchar(10),
			idBan varchar(10),
			idMA varchar(10) ,
			soluongmon int,
			ngayvao date,
			tgvao time,
			tgra datetime,
			trangthaiOrder boolean,

			foreign key(idBan) references ban(idBan),

			foreign key(idMA) references monan(idMA)
				ON DELETE CASCADE 
				ON UPDATE  CASCADE,
			foreign key(idNV) references nhanvien(idNV)
				ON DELETE CASCADE 
				ON UPDATE  CASCADE

		);


	-- het--------------------------------------

		-- them du lieu vao bang "Mon An"
			insert into monan value("ncf1","ca phe da","15000",true);
			insert into monan value("ncf2","ca phe sua","18000",true);
			insert into monan value("ncf3","ca phe nong","25000",true);

			insert into monan value("nn1","Sting","12000",true);
			insert into monan value("nn2","coca cola","12000",true);
			insert into monan value("nn3","pessi","12000",true);

			insert into monan value("ntra1","tra duong","12000",true);
			insert into monan value("ntra2","tra sua socola","20000",true);
			insert into monan value("ntra3","tra sua matcha","20000",true);
			insert into monan value("ntra4","tra dao","20000",true);
			insert into monan value("ntra5","tra lipton","20000",true);
			insert into monan value("ntra6","tra thai","20000",true);

			insert into monan value("amy1","my cay","45000",true);
			insert into monan value("amy2","my y","30000",true);
			insert into monan value("amy3","my xao","25000",true);
			insert into monan value("amy4","my tron","40000",true);
			insert into monan value("ah1","hu tieu","20000",true);
			insert into monan value("ah2","banh canh","20000",true);

			insert into monan value("ab1","bun thit xao","20000",true);
			insert into monan value("ab2","bun tra gio","20000",true);
			insert into monan value("ab3","bun mam","20000",true);
			insert into monan value("ab4","bun rieu","20000",true);

			insert into monan value("ac1","com suon","20000",true);
			insert into monan value("ac2","com ga","27000",true);
			insert into monan value("ac3","com ech","25000",true);
			insert into monan value("ac4","com suon tra","24000",true);
			insert into monan value("ac5","com thit","20000",true);
			insert into monan value("ac6","com ca","20000",true);

		
	

		SELECT * FROM monan;
		select * from ban;
		select * from nhanvien as a, BangChucVu as b	where a.macv=b.macv;
		select * from GoiMon;





