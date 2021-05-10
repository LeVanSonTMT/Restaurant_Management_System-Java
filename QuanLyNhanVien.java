package goi1;
import java.util.Date;
import java.sql.*;
import java.util.*;

public class QuanLyNhanVien {

    Connection conn = null;

    // tao ket noi toi csdl
    public void ketNoi() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Doan?" + "user=root");
            //System.out.println("noi ket thanh cong!");

        } catch (Exception ex) {
            System.out.println("noi ket den database that bai!");
//            ex.printStackTrace();
        }

    }

    // hien thi danh sach nhan vien
    public void hienThi() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        int i = 0;
        System.out.println("    1 - Hien Thi Danh Sach Nhan Vien    ");
        System.out.println("");
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT idNV,tenNV,Gtinh,Dchi,SDT,ngayBD,soNgayLV,b.ChucVu,trangthaiNV FROM NhanVien as a, BangChucVu as b WHERE a.macv=b.macv and trangthaiNV =true ");
            System.out.println("|---|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
            System.out.println("|STT|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |Ngay Bat Dau|So Ngay Lam|     Chuc vu     |  DiaChi  |");
            System.out.println("|---|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
            while (rs.next()) {
                i++;
                System.out.printf("|%3d|", i);
                System.out.printf("%-10s|", rs.getString("idNV"));
                System.out.printf("     %-20s|", rs.getString("tenNV"));
                System.out.printf("    %-7s|", rs.getString("GTinh"));
                System.out.printf("%12s|", rs.getString("SDT"));
                System.out.printf(" %-11s|", rs.getString("ngayBD"));
                System.out.printf("%11s|", rs.getString("soNgayLV"));
                System.out.printf("%-17s|", rs.getString("ChucVu"));
                System.out.printf("%-10s|", rs.getString("Dchi"));
                System.out.printf("\n");
            }
            System.out.println("|---|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
        } catch (SQLException e) {
            System.out.println("Loi!");
        }
        this.menuNhanVien();
    }

    // them nhan vien moi vao danh sach nhan vien
    public void themNV() throws SQLException {
        String kt = "y";
        Scanner sc = new Scanner(System.in);
        String ten, gt, id, dc, cv = null, sdt;
        int songay=0;
        Date d = new Date();
        java.sql.Date ngayBD = new java.sql.Date(d.getTime());
        PreparedStatement pstsm = null;
        ResultSet rs = null;
        do {
            System.out.println("    3 - Them Nhan Vien           ");
            System.out.println("");
            int t = 0;
            do {
                System.out.println("Nhap ID nhan vien moi(nhap '0' de thoat): ");
                id = sc.nextLine();
                if (id.equals("0")) {
                    menuNhanVien();
                    //break;
                    return;
                }
                if (!id.equals("")) {
                    t++;
                }
            } while (t == 0);

            int i = 0;
            do {
                System.out.println("Nhap ho vs ten(nhap '0' de thoat): ");
                ten = sc.nextLine();
                if (ten.equals("0")) {
                    menuNhanVien();
                    //break;
                    return;
                }
                if (!ten.equals("")) {
                    i++;
                }
            } while (i == 0);

            int j = 0;
            do {
                System.out.println("Chon gioi tinh(Nam/Nu)(nhap '0' de thoat):");
                gt = sc.nextLine();
                if (gt.equals("0")) {
                    menuNhanVien();
//                    break;
                    return;
                }
                if (gt.equals("nam") || gt.equals("nu") || gt.equals("Nam") || gt.equals("Nu")) {
                    j++;
                }
            } while (j == 0);

            int g = 0;
            do {
                System.out.println("Nhap dia chi(nhap '0' de thoat): ");
                dc = sc.nextLine();
                if (dc.equals("0")) {
                    menuNhanVien();
//                    break;
                    return;
                }
                if (!dc.equals("")) {
                    g++;
                }
            } while (g == 0);

            int f = 0;
            do {
                String macvtem[];
                macvtem = new String[7];
                System.out.println("Chuc vu(nhap '0' de thoat): ");
                pstsm = conn.prepareStatement("SELECT macv,ChucVu from BangChucVu ORDER BY macv DESC ");
                rs = pstsm.executeQuery();
                int so = 0;
                macvtem[0] ="0";
                System.out.println("|---|-----|------------------|");
                System.out.println("|STT|Ma CV|    Ten Chuc Vu   |");
                System.out.println("|---|-----|------------------|");
                while (rs.next()) {            
                    so++;
                    macvtem[so] = rs.getString("macv");
                    System.out.printf("| %d |", so+1);
                    System.out.printf("|%-5s|", rs.getString("macv"));
                    System.out.printf("%-17s|\n", rs.getString("ChucVu"));
                }
                System.out.println("|---|-----|------------------|");
                int nhap;
                do {
                    System.out.println("Moi chon: ");
                    nhap = sc.nextInt();
                    sc.nextLine();
                    if (nhap < 0 || nhap > 6) {
                        System.out.println("nhap sai,nhap lai ");
                    }
                } while (nhap < 0 || nhap > 6);
                    cv=macvtem[nhap];                
                if (cv.equals("0")) {
                    menuNhanVien();
//                    break;
                    return;
                }
                if (!cv.equals("")) {
                    f++;
                }
            } while (f == 0);

            int k = 0;
            do {
                System.out.println("So Dien thoai(nhap '0' de thoat): ");
                sdt = sc.nextLine();
                if (sdt.equals("0")) {
                    menuNhanVien();
                    return;
//                    break;
                }
                if (!sdt.equals("")) {
                    k++;
                }
            } while (k == 0);
            
            try {
                pstsm = conn.prepareStatement("INSERT INTO NhanVien VALUE(?,?,?,?,?,?,?,?,true)");
                pstsm.setString(1, id);
                pstsm.setString(2, ten);
                pstsm.setString(3, gt);
                pstsm.setString(4, dc);
                pstsm.setString(5, sdt);
                pstsm.setDate(6, ngayBD);
                pstsm.setInt(7, songay);
                pstsm.setString(8, cv);
                int rrr = pstsm.executeUpdate();
                if (rrr == 1) {
                    System.out.println("Them thanh cong!");
                    //in kq 
                    pstsm = conn.prepareStatement("SELECT idNV,tenNV,Gtinh,Dchi,SDT,ngayBD,soNgayLV,b.ChucVu,trangthaiNV FROM NhanVien as a, BangChucVu as b WHERE a.macv=b.macv and idNV=? and trangthaiNV =true ");
                    pstsm.setString(1, id);
                    rs = pstsm.executeQuery();
                    System.out.println("|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
                    System.out.println("|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |Ngay Bat Dau|So Ngay Lam|     Chuc vu     |  DiaChi  |");
                    System.out.println("|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
                    while (rs.next()) {
                        System.out.printf("|%-10s|", rs.getString("idNV"));
                        System.out.printf("     %-20s|", rs.getString("tenNV"));
                        System.out.printf("    %-7s|", rs.getString("GTinh"));
                        System.out.printf("%12s|", rs.getString("SDT"));
                        System.out.printf(" %-11s|", rs.getString("ngayBD"));
                        System.out.printf("%11s|", rs.getString("soNgayLV"));
                        System.out.printf("%-17s|", rs.getString("ChucVu"));
                        System.out.printf("%-10s|", rs.getString("Dchi"));
                        System.out.printf("\n");
                    }
                    System.out.println("|----------|-------------------------|-----------|------------|------------|-----------|-----------------|----------|");
       
                } else {
                    System.out.println("Loi! Khong the them!");
                }
            } catch (SQLException e) {
                System.out.println("Khong the them! ID Da Ton Tai");
                e.printStackTrace();
            }

            System.out.println("--> Ban co muon them tiep nua hong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));

        this.menuNhanVien();
    }

    //tim kiem nhan vien theo IDNV
    public void timKiemID() throws SQLException {
        System.out.println("   (1) - Tim theo ID        ");
        System.out.println("");
        String kt = "y";
        Scanner sc = new Scanner(System.in);
        String id = "";
        do {
            do {
                System.out.println("Nhap 'IDNV' can tim(nhap '0' de thoat): ");
                id = sc.nextLine();
                if (id.equals("0")) {
                    timKiem();
                    //break;
                    return;
                }
            } while (id.equals(""));

            PreparedStatement pstsm = null;
            ResultSet rs = null;
            pstsm = conn.prepareStatement("SELECT idNV,tenNV,Gtinh,Dchi,SDT,ngayBD,soNgayLV,b.ChucVu,trangthaiNV FROM NhanVien as a, BangChucVu as b WHERE a.macv=b.macv and trangthaiNV =true and idNV=?");
            pstsm.setString(1, id);
            rs = pstsm.executeQuery();
            System.out.println("Ket qua: ");
            int i = 0;
            try {
                System.out.println("|---|----------|-------------------------|-----------|------------|-----------------|----------|----------|");
                System.out.println("|STT|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |     Chuc vu     |  DiaChi  |Trang Thai|");
                System.out.println("|---|----------|-------------------------|-----------|------------|-----------------|----------|----------|");
                while (rs.next()) {
                    i++;
                    System.out.printf("|%-3d|", i);
                    System.out.printf("%-10s|", rs.getString("idNV"));
                    System.out.printf("%-25s|", rs.getString("tenNV"));
                    System.out.printf("%-11s|", rs.getString("GTinh"));
                    System.out.printf("%-12s|", rs.getString("SDT"));
                    System.out.printf("%-13s|", rs.getString("ChucVu"));
                    System.out.printf("%-10s|", rs.getString("Dchi"));
                    if (rs.getBoolean("trangthaiNV") == true) {
                        System.out.print("Dang Lam  |");
                    } else {
                        System.out.print("   Nghi   |");
                    }
                    System.out.printf("\n");
                }
                System.out.println("|---|----------|-------------------------|-----------|------------|-------------|----------|----------|");
                if (i == 0) {
                    System.out.println("IDNV khong ton tai!");
                }
            } catch (SQLException e) {
                System.out.println("Loi! Khong tim thay");
                //e.printStackTrace();
            }
            System.out.println("--> Ban co muon tim kiem IDNV tiep hong?(nhan 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));
        this.timKiem();
    }

    //tim kiem nhan vien theo ten
    public void timKiemTen() throws SQLException {
        System.out.println("   (2) - Tim theo ho va ten        ");
        System.out.println("");
        String kt = "y";
        Scanner sc = new Scanner(System.in);
        String ten;
        do {
            do {
                System.out.println("Nhap ho va ten can tim(nhap '0' de thoat): ");
                ten = sc.nextLine();
                if (ten.equals("0")) {
                    timKiem();
                    //break;
                    return;
                }
            } while (ten.equals(""));
            PreparedStatement pstsm = null;
            ResultSet rs = null;

            pstsm = conn.prepareStatement("SELECT idNV,tenNV,Gtinh,Dchi,SDT,ngayBD,soNgayLV,b.ChucVu,trangthaiNV FROM NhanVien as a, BangChucVu as b WHERE a.macv=b.macv and trangthaiNV =true and tenNV=?");
            pstsm.setString(1, ten);
            rs = pstsm.executeQuery();
            System.out.println("Ket qua: ");
            try {
                int i = 0;
                System.out.println("|---|----------|-------------------------|-----------|------------|-------------|----------|----------|");
                System.out.println("|STT|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |   Chuc vu   |  DiaChi  |Trang Thai|");
                System.out.println("|---|----------|-------------------------|-----------|------------|-------------|----------|----------|");
                while (rs.next()) {
                    i++;
                    System.out.printf("|%-3d|", i);
                    System.out.printf("%-10s|", rs.getString("idNV"));
                    System.out.printf("%-25s|", rs.getString("tenNV"));
                    System.out.printf("%-11s|", rs.getString("GTinh"));
                    System.out.printf("%-12s|", rs.getString("SDT"));
                    System.out.printf("%-13s|", rs.getString("ChucVu"));
                    System.out.printf("%-10s|", rs.getString("Dchi"));
                    if (rs.getBoolean("trangthaiNV") == true) {
                        System.out.print("Dang Lam|");
                    } else {
                        System.out.print("Nghi|");
                    }
                    System.out.printf("\n");
                }
                System.out.println("|---|----------|-------------------------|-----------|------------|-------------|----------|----------|");

                if (i == 0) {
                    System.out.println("Ten nhan vien khong ton tai!");
                }
            } catch (SQLException e) {
                System.out.println("Loi! Khong tim thay");
            }
            System.out.println("Ban co muon tiep tuc tim kiem?(nhan 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));
        this.timKiem();
    }

    //bang lua chon tim kiem nhan vien
    public void timKiem() throws SQLException {
        System.out.println("    2 - Tim Kiem Nhan Vien       ");
        System.out.println("|----------------------------|");
        System.out.println("|   (1) - Tim theo ID        |");
        System.out.println("|   (2) - Tim theo ho va ten |");
        System.out.println("|   (0) - Tro lai            |");
        System.out.println("|----------------------------|");
        Scanner sc = new Scanner(System.in);
        int a;

        do {
            System.out.println("Moi chon: ");
            a = sc.nextInt();
            if (a < 0 || a > 2) {
                System.out.println("\tNhap sai,nhap lai!");
            }
        } while (a < 0 || a > 2);
        switch (a) {
            case 1: {
                this.timKiemID();
                break;
            }
            case 2: {
                this.timKiemTen();
                break;
            }
            case 0: {
                this.menuNhanVien();
                break;
            }
        }
    }

    String idmoi;

    // sua ID cho nhan vien
    public void suaID(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        do {
            System.out.println("Nhap ID moi(ID phai la duy nhat): ");
            idmoi = sc.nextLine();
            if(idmoi.equals("0")) {
                suaTT();
                return;
            }

        } while (idmoi.equals("") );

        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set idNV = ?"
                    + " where idNV =?");
            pstsm.setString(1, idmoi);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r != 1) {
                System.out.println("Sua khong thanh cong!");
                // in ra kq
            } else {
                System.out.println("Sua thanh cong!");
            }
        } catch (SQLException e) {
            System.out.println("Loi! IDNV da ton tai");
        }
        suaTT();
    }

    //sua ten cua nhan vien
    public void suaTen(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        String k;
        do {
            System.out.println("Nhap ten moi: ");
            k = sc.nextLine();
            if(k.equals("0")){
                suaTT();
                return;
            }
        } while (k.equals(""));
        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set tenNV=?"
                    + " where idNV=?");
            pstsm.setString(1, k);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua!");
        }
        suaTT();
    }

    //sua dia chi cua nhan vien
    public void suaDchi(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        String k;
        do {
            System.out.println("Nhap dia chi moi: ");
            k = sc.nextLine();
            if(k.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k.equals(""));
        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set Dchi = ?"
                    + " where idNV =?");
            pstsm.setString(1, k);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua!");
        }
        suaTT();
    }

    //sua gioi tinh cua nhan vien
    public void suaGT(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        String k;
        do {
            System.out.println("Nhap gioi tinh moi: ");
            k = sc.nextLine();
            if(k.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k.equals(""));
        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set GTinh = ?"
                    + " where idNV =?");
            pstsm.setString(1, k);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua!");
        }
        suaTT();
    }

    //sua SDT cua nhan vien
    public void suaSDT(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        String k;
        do {
            System.out.println("Nhap SDT moi: ");
            k = sc.nextLine();
            if(k.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k.equals(""));
        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set SDT = ?"
                    + " where idNV =?");
            pstsm.setString(1, k);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua!");
        }
        suaTT();
    }

    //sua chuc vu cua nhan vien
    public void suaCV(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        ResultSet rs=null;
        String k;
        do {
            System.out.println("Nhap CV moi: ");
            
                String macvtem[];
                macvtem = new String[6];
                pstsm = conn.prepareStatement("SELECT macv,ChucVu from BangChucVu ORDER BY macv DESC ");
                rs = pstsm.executeQuery();
                int so = 0;
                System.out.println("|---|-----|------------------|");
                System.out.println("|STT|Ma CV|    Ten Chuc Vu   |");
                System.out.println("|---|-----|------------------|");
                while (rs.next()) {            
                    
                    macvtem[so] = rs.getString("macv");
                    System.out.printf("| %d |", so+1);
                    System.out.printf("|%-5s|", rs.getString("macv"));
                    System.out.printf("%-17s|\n", rs.getString("ChucVu"));
                    so++;
                }
                System.out.println("|---|-----|------------------|");
                int nhap;
                do {
                    System.out.println("Moi chon: ");
                    nhap = sc.nextInt();
                    sc.nextLine();
                    if (nhap < 0 || nhap > 6) {
                        System.out.println("nhap sai,nhap lai ");
                    }
                } while (nhap < 0 || nhap > 6);
            
            k = macvtem[nhap-1];
            
        } while (k.equals(""));
        try {
            pstsm = conn.prepareStatement("update NhanVien"
                    + " set macv = ?"
                    + " where idNV =?");
            pstsm.setString(1, k);
            pstsm.setString(2, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua!");
        }
        suaTT();
    }

    //sua tat ca thong tin nhan vien
    public void suaAll(String a) throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        ResultSet rs=null;
        System.out.println("NHAP THONG TIN MOI CHO NHAN VIEN: ");
        String k1, k2, k3, k4, k5;
        do {
            System.out.println("ID: ");
            idmoi = sc.nextLine();
            if(idmoi.equals("0"))
            {
                suaTT();
                return;
            }
        } while (idmoi.equals(""));

        do {
            System.out.println("Ten: ");
            k1 = sc.nextLine();
            if(k1.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k1.equals(""));

        do {
            System.out.println("Gioi tinh: ");
            k4 = sc.nextLine();
            if(k4.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k4.equals(""));

        do {
            System.out.println("SDT: ");
            k3 = sc.nextLine();
            if(k3.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k3.equals(""));

        do {
            System.out.println("Dia chi: ");
            k2 = sc.nextLine();
            if(k2.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k2.equals(""));

        do {
            System.out.println("Chuc vu: ");
            String macvtem[];
                macvtem = new String[6];
                pstsm = conn.prepareStatement("SELECT macv,ChucVu from BangChucVu ORDER BY macv DESC ");
                rs = pstsm.executeQuery();
                int so = 0;
                System.out.println("|---|-----|------------------|");
                System.out.println("|STT|Ma CV|    Ten Chuc Vu   |");
                System.out.println("|---|-----|------------------|");
                while (rs.next()) {            
                    
                    macvtem[so] = rs.getString("macv");
                    System.out.printf("| %d |", so+1);
                    System.out.printf("|%-5s|", rs.getString("macv"));
                    System.out.printf("%-17s|\n", rs.getString("ChucVu"));
                    so++;
                }
                System.out.println("|---|-----|------------------|");
                int nhap;
                do {
                    System.out.println("Moi chon: ");
                    nhap = sc.nextInt();
                    sc.nextLine();
                    if (nhap < 0 || nhap > 6) {
                        System.out.println("nhap sai,nhap lai ");
                    }
                } while (nhap < 0 || nhap > 6);
            
            k5 =macvtem[nhap];
            if(k5.equals("0"))
            {
                suaTT();
                return;
            }
        } while (k5.equals(""));

        try {
            pstsm = conn.prepareStatement("UPDATE NhanVien "
                    + "set idNV=?,tenNV=?,DChi=?,SDT=?,GTinh=?,ChucVu=?"
                    + " where idNV=? ");
            pstsm.setString(1, idmoi);
            pstsm.setString(2, k1);
            pstsm.setString(3, k2);
            pstsm.setString(4, k3);
            pstsm.setString(5, k4);
            pstsm.setString(6, k5);
            pstsm.setString(7, a);
            int r = pstsm.executeUpdate();
            if (r == 1) {
                System.out.println("Sua thanh cong!");
                //in ra kq

            } else {
                System.out.println("Khong the sua!");
            }
        } catch (SQLException e) {
            System.out.println("Khong The Sua! IDNV Da Ton Tai");
        }
        suaTT();
    }

    //bang danh sach lua chon muc sua thong tin nhan vien
    public void suaTT() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        ResultSet rs = null;
        boolean t;
        String id;
        
            System.out.println("    4 - Sua Thong Tin Nhan Vien  ");
            System.out.println("");
            do {
                System.out.println("Nhap IDNV cua nhan vien can sua vao(nhap '0' de thoat):");
                id = sc.nextLine();
                if (id.equals("0")) {
                    menuNhanVien();
                    return;
                }
            } while (id.equals(""));

            pstsm = conn.prepareStatement("SELECT * FROM NhanVien WHERE idNV=? and trangthaiNV=true");
            pstsm.setString(1, id);
            rs = pstsm.executeQuery();
            t = rs.next();
            if (t == true) {
                pstsm = conn.prepareStatement("SELECT idNV,tenNV,Gtinh,Dchi,SDT,ngayBD,soNgayLV,b.ChucVu,trangthaiNV FROM NhanVien as a, BangChucVu as b WHERE a.macv=b.macv and trangthaiNV =true and idNV=?");
                pstsm.setString(1, id);
                rs = pstsm.executeQuery();
                System.out.println("Ket qua: ");
                try {
                    System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                    System.out.println("|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |   Chuc vu   |  DiaChi  |");
                    System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                    while (rs.next()) {
                        System.out.printf("|%-10s|", rs.getString("idNV"));
                        System.out.printf("%-25s|", rs.getString("tenNV"));
                        System.out.printf("%-11s|", rs.getString("GTinh"));
                        System.out.printf("%-12s|", rs.getString("SDT"));
                        System.out.printf("%-13s|", rs.getString("ChucVu"));
                        System.out.printf("%-10s|", rs.getString("Dchi"));
                        System.out.printf("\n");
                    }
                    System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                } catch (SQLException e) {
                    System.out.println("Loi!Khong tim thay");
                }
                System.out.println("Ban chon cach sua cho nhan vien ");
                System.out.println("\t|--------------------------|");
                System.out.println("\t|  (1) - Sua IDNV          |");
                System.out.println("\t|  (2) - Sua Ten NV        |");
                System.out.println("\t|  (3) - Sua Dia Chi       |");
                System.out.println("\t|  (4) - Sua Gioi Tinh     |");
                System.out.println("\t|  (5) - Sua SDT           |");
                System.out.println("\t|  (6) - Sua Chuc Vu       |");
                System.out.println("\t|  (7) - Sua Tat Ca        |");
                System.out.println("\t|  (0) - Tro ve            |");
                System.out.println("\t|--------------------------|");
                int a;
                do {
                    System.out.println("Moi chon: ");
                    a = sc.nextInt();
                    if (a < 0 || a > 7) {
                        System.out.println("Nhap sai,nhap lai");
                    }
                } while (a < 0 || a > 7);
                switch (a) {
                    case 0: {
                        menuNhanVien();
                        break;
                    }
                    case 1: {
                        suaID(id);
                        break;
                    }
                    case 2: {
                        suaTen(id);
                        break;
                    }
                    case 3: {
                        suaDchi(id);
                        break;
                    }
                    case 4: {
                        suaGT(id);
                        break;
                    }
                    case 5: {
                        suaSDT(id);
                        break;
                    }
                    case 6: {
                        suaCV(id);
                        break;
                    }
                    case 7: {
                        suaAll(id);
                        break;
                    }

                }
                id = idmoi;

            } else {
                System.out.println("IDNV khong ton tai");
            }
    }

    //xoa nhan vien
    public void xoaNV() throws SQLException {
        System.out.println("    5 - Xoa Nhan Vien            ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstsm = null;
        ResultSet rs = null;
        String kt = "y", id = "";
        do {
            do {
                System.out.println("Nhap ID nhan vien can xoa(nhap '0' de thoat)");
                id = sc.nextLine();
                if (id.equals("0")) {
                    this.menuNhanVien();
                    return;
                }
            } while (id.equals(""));
            pstsm = conn.prepareStatement("SELECT * FROM NhanVien WHERE idNV=? and trangthaiNV =true");
            pstsm.setString(1, id);
            rs = pstsm.executeQuery();
            int i = 0;
            System.out.println("Ket qua:");
            try {
                System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                System.out.println("|   IDNV   |          TenNV          | Gioi Tinh |    SDT     |   Chuc vu   |  DiaChi  |");
                System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                while (rs.next()) {
                    i++;

                    System.out.printf("|%-10s|", rs.getString("idNV"));
                    System.out.printf("%-25s|", rs.getString("tenNV"));
                    System.out.printf("%-11s|", rs.getString("GTinh"));
                    System.out.printf("%-12s|", rs.getString("SDT"));
                    System.out.printf("%-13s|", rs.getString("ChucVu"));
                    System.out.printf("%-10s|", rs.getString("Dchi"));
                    System.out.printf("\n");
                }
                System.out.println("|----------|-------------------------|-----------|------------|-------------|----------|");
                if (i == 0) {
                    System.out.println("IDNV khong ton tai!");
                } else {
                    System.out.println("\nBan co chac chan muon xoa khong?(nhan phim 'y' de xoa)");
                    String x = sc.nextLine();
                    if (x.equals("y")) {
                        try {
                            pstsm = conn.prepareStatement("update NhanVien "
                                    + " set trangthaiNV=false"
                                    + " where idNV=?");
                            pstsm.setString(1, id);
                            int h = pstsm.executeUpdate();
                            if (h == 1) {
                                System.out.println("Xoa Thanh Cong");
                            } else {
                                System.out.println("Khong The Xoa");
                            }

                        } catch (SQLException e) {
                            System.out.println("Loi! Khong The Xoa");
                        }
                    } else {
                        this.xoaNV();
                    }

                }
            } catch (SQLException e) {
                System.out.println("Loi!Khong tim thay");
            }
            System.out.println("--> Ban co muon tiep tuc nua khong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));
        this.menuNhanVien();
    }

    public void thoat() throws SQLException {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {

        }
        System.out.println("-------------------------------------------------");
        return;
    }

    public void trolai() throws SQLException {
        QuanLyQuanAn a = new QuanLyQuanAn();
        thoat();
        a.menuChucNang();
        return;
    }

    public void menuNhanVien() throws SQLException {
        System.out.println("\n\t\t|---------------------------------|");
        System.out.println("\t\t|        QUAN LY NHAN VIEN        |");
        System.out.println("\t\t|---------------------------------|");
        System.out.println("\t\t|    0 - Tro lai                  |");
        System.out.println("\t\t|    1 - Hien Thi DS Nhan Vien    |");
        System.out.println("\t\t|    2 - Tim Kiem Nhan Vien       |");
        System.out.println("\t\t|    3 - Them Nhan Vien           |");
        System.out.println("\t\t|    4 - Sua Thong Tin Nhan Vien  |");
        System.out.println("\t\t|    5 - Xoa Nhan Vien            |");
        System.out.println("\t\t|    6 - Thoat                    |");
        System.out.println("\t\t|---------------------------------|");
        Scanner sc = new Scanner(System.in);
        int a;
        do {
            System.out.println("Moi chon: ");
            a = sc.nextInt();
            sc.nextLine();
            if (a < 0 || a > 6) {
                System.out.println("nhap sai,nhap lai ");
            }
        } while (a < 0 || a > 6);

        switch (a) {
            case 0: {
                this.trolai();
                break;
            }
            case 1: {
                this.hienThi();
                break;
            }
            case 2: {
                this.timKiem();
                break;
            }
            case 3: {
                this.themNV();
                break;
            }
            case 4: {
                this.suaTT();
                break;
            }
            case 5: {
                this.xoaNV();
                break;
            }
            case 6: {
                thoat();
                break;
            }

        }
        return;
    }

}
