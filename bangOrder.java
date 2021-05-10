package goi1;

import com.sun.javafx.image.impl.IntArgb;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class bangOrder {

    Connection conn = null;

    public void ketNoiSQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/doan?" + "user=root");
            // System.out.println("noi ket thanh cong!");

        } catch (Exception ex) {
//            System.out.println("noi ket that bai!");
//            ex.printStackTrace();
        }

    }

    public void themBangGoi() throws SQLException {
        System.out.println("\t    1 - Them bang goi mon                  ");

        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        //lay thoi gian hien hanh.
        Date d = new Date();
        java.sql.Date date = new java.sql.Date(d.getTime());
        java.sql.Time time = new java.sql.Time(d.getTime());

        int stt = 0;
        pstmt = conn.prepareStatement("select STT From GoiMon"
                + " WHERE STT=(SELECT max(STT) FROM GoiMon) ");
        rs = pstmt.executeQuery();
        while (rs.next()) {
            if (rs.getInt("STT") != 0) {
                stt = rs.getInt("STT");
            }
        }
        stt = stt + 1;

        // kiem tra ID nhan vien dung hong
        String idNV = "";
        int z = 0;
        do {
            String idnv;
            do {
                System.out.println("Nhap ID nhan vien(nhap '0' de thoat):");
                idnv = sc.nextLine();
                if (idnv.equals("0")) {
                    menuQuanLyPhuVu();
                    //break;
                    return;
                }
            } while (idnv.equals(""));

            pstmt = conn.prepareStatement("SELECT * FROM NhanVien"
                    + " WHERE idNV=? and trangthaiNV=true");
            pstmt.setString(1, idnv);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                z++;
                if (z != 0) {
                    idNV = rs.getString("idnv");
                }
            }
            if (z == 0) {
                System.out.println("Nhap ID nhan vien chua hop ly");
            }
        } while (z == 0);

        //kiem tra ban nhap vo dung hong va co trong hong.
        String tem = "";
        int i = 0;
        do {
            String idban;
            do {
                System.out.println("Nhap ID ban can goi mon(nhap '0' de thoat):");
                idban = sc.nextLine();
                if (idban.equals("0")) {
                    menuQuanLyPhuVu();
                    return;
                    //break;
                }
            } while (idban.equals(""));
            pstmt = conn.prepareStatement("SELECT * FROM ban "
                    + "WHERE idBan=? and trangthaiBan=false");
            pstmt.setString(1, idban);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                i++;
                if (i != 0) {
                    tem = idban;

                    //them mon vao erder
                    String mon;
                    int slmon;
                    String c;
                    do {
                        int j = 0;
                        do {
                            String idma;
                            do {
                                System.out.println("Nhap ID mon an(nhap '0' de thoat): ");
                                idma = sc.nextLine();
                                if (idma.equals("0")) {
                                    menuQuanLyPhuVu();
                                    return;
                                    //break;
                                }
                            } while (idma.equals(""));
                            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMa=true ");
                            pstmt.setString(1, idma);
                            rs = pstmt.executeQuery();
                            while (rs.next()) {
                                j++;
                                if (j != 0) {
                                    mon = rs.getString("idma");
                                    do {
                                        System.out.println("Nhap so luong " + mon);
                                        slmon = sc.nextInt();
                                    } while (slmon == 0);
                                    try {
                                        pstmt = conn.prepareStatement("insert into  GoiMon value(?,?,?,?,?,?,?,true)");
                                        pstmt.setInt(1, stt);
                                        pstmt.setString(2, idNV);
                                        pstmt.setString(3, tem);
                                        pstmt.setString(4, mon);
                                        pstmt.setInt(5, slmon);
                                        pstmt.setDate(6, date);
                                        pstmt.setTime(7, time);
                                        int kq = pstmt.executeUpdate();
                                        if (kq == 1) {
                                            System.out.println("Them thanh cong!");

                                        } else {
                                            System.out.println("Khong them duoc!");
                                        }
                                    } catch (SQLException e) {
                                        System.out.println("Loi!");
                                        //e.printStackTrace();
                                    }

                                }
                            }
                            if (j == 0) {
                                System.out.println("\t Nhap ID mon an sai");
                            }
                        } while (j == 0);
                        sc.nextLine();
                        System.out.println("Them mon nua hong(nhan phim 'y' de them tiep)");
                        c = sc.nextLine();
                    } while (c.equals("y"));

                }
            }
            if (i == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }
        } while (i == 0);
        try {
            pstmt = conn.prepareStatement("UPDATE ban set trangthaiBan=true where idBan=?");
            pstmt.setString(1, tem);
            int kt = pstmt.executeUpdate();
            if (kt == 1) {
                System.out.println("Hoan tat!");
            } else {
                System.out.println("Khong hoan tat!");
            }
        } catch (SQLException e) {
            System.out.println("Loi!");
        }
        menuQuanLyPhuVu();
    }

    public void themMonVaoBangGoi() throws SQLException {
        System.out.println("\t   2 - Them mon vao bang goi mon          ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        //lay thoi gian hien hanh.
        Date d = new Date();
        java.sql.Date date = new java.sql.Date(d.getTime());
        java.sql.Time time = new java.sql.Time(d.getTime());

        String tem = "";
        int i = 0;
        do {
            String idban;
            do {
                System.out.println("Nhap ID ban can goi mon(nhap '0' de thoat):");
                idban = sc.nextLine();
                if (idban.equals("0")) {
                    menuQuanLyPhuVu();
                    return;
                    //break;
                }
            } while (idban.equals(""));
            pstmt = conn.prepareStatement("SELECT * FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
            pstmt.setString(1, idban);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int stt = rs.getInt("STT");
                String idnv = rs.getString("idNV");
                i++;
                if (i != 0) {
                    tem = idban;
                    //them mon vao erder
                    String mon, c;
                    int slmon;

                    do {
                        int j = 0;
                        do {
                            String idma;
                            do {
                                System.out.println("Nhap ID mon an(nhap '0' de thoat): ");
                                idma = sc.nextLine();
                                if (idma.equals("0")) {
                                    menuQuanLyPhuVu();
                                    return;
                                    //break;
                                }
                            } while (idma.equals(""));
                            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMa=true ");
                            pstmt.setString(1, idma);
                            rs = pstmt.executeQuery();
                            while (rs.next()) {
                                j++;
                                if (j != 0) {
                                    mon = rs.getString("idma");
                                    do {
                                        System.out.println("Nhap so luong " + mon);
                                        slmon = sc.nextInt();
                                    } while (slmon == 0);
                                    try {
                                        pstmt = conn.prepareStatement("insert into  GoiMon value(?,?,?,?,?,?,?,true)");
                                        pstmt.setInt(1, stt);
                                        pstmt.setString(2, idnv);
                                        pstmt.setString(3, tem);
                                        pstmt.setString(4, mon);
                                        pstmt.setInt(5, slmon);
                                        pstmt.setDate(6, date);
                                        pstmt.setTime(7, time);
                                        int kq = pstmt.executeUpdate();
                                        if (kq == 1) {
                                            System.out.println("Them thanh cong!");
                                        } else {
                                            System.out.println("Khong them duoc!");
                                        }
                                    } catch (SQLException e) {
                                        System.out.println("Loi!");
                                        //e.printStackTrace();
                                    }

                                }
                            }
                            if (j == 0) {
                                System.out.println("\t Nhap ID mon an sai!");
                            }
                        } while (j == 0);
                        sc.nextLine();
                        System.out.println("Them mon nua hong!(nhan phim 'y' de them tiep)");
                        c = sc.nextLine();
                    } while (c.equals("y"));
                }
            }
            if (i == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }
        } while (i == 0);
        if (i != 0) {
            System.out.println("\t Hoan tat");
        }
        menuQuanLyPhuVu();
    }

    public void suaMontrongBangGoi() throws SQLException {
        System.out.println("\t    3 - Sua mon trong bang goi mon         ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        String idban;
        int i = 0;
        do {
            do {
                System.out.println("Nhap ID ban can sua mon(nhap '0' de thoat):");
                idban = sc.nextLine();
                if (idban.equals("0")) {
                    menuQuanLyPhuVu();
                    //break;
                    return;
                }
            } while (idban.equals(""));
            pstmt = conn.prepareStatement("SELECT idBan FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
            pstmt.setString(1, idban);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                i++;
            }
            if (i == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }
        } while (i == 0);

        String c;
        do {
            String idma;
            int j = 0;
            do {
                do {
                    System.out.println("Nhap ID mon an can sua lai(nhap '0' de thoat): ");
                    idma = sc.nextLine();
                    if (idma.equals("0")) {
                        menuQuanLyPhuVu();
                        //break;
                        return;
                    }
                } while (idma.equals(""));
                pstmt = conn.prepareStatement("SELECT idMA FROM GoiMon WHERE idMA=? and trangthaiOrder=true ");
                pstmt.setString(1, idma);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    j++;
                }
                if (j == 0) {
                    System.out.println("\t Nhap ID mon an sai!");
                }
            } while (j == 0);

            int z = 0;
            String monmoi = "";
            do {

                do {
                    System.out.println("Nhap ID mon moi lai(nhap '0' de thoat): ");
                    monmoi = sc.nextLine();
                    if (monmoi.equals("0")) {
                        menuQuanLyPhuVu();
                        //break;
                        return;
                    }
                } while (monmoi.equals(""));
                pstmt = conn.prepareStatement("SELECT idMA FROM monan WHERE idMA=? and trangthaiMA=true ");
                pstmt.setString(1, monmoi);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    z++;
                }
                if (z == 0) {
                    System.out.println("\t Nhap ID mon an sai!");
                }

            } while (z == 0);
            int slmon;
            do {
                System.out.println("Nhập so luong " + monmoi + " :");
                slmon = sc.nextInt();
            } while (slmon == 0);
            try {
                pstmt = conn.prepareStatement("UPDATE GoiMon set idMA=?,soluongmon=? WHERE idBan=? and idMA=? and trangthaiOrder=true");
                pstmt.setString(1, monmoi);
                pstmt.setInt(2, slmon);
                pstmt.setString(3, idban);
                pstmt.setString(4, idma);
                int kq = pstmt.executeUpdate();
                if (kq == 0) {
                    System.out.println("Khong sua duoc!");

                } else {
                    System.out.println("Sua thanh cong!");
                }
            } catch (SQLException e) {
                System.out.println("khong sua duoc! Loi!");
                // e.printStackTrace();
            }

            System.out.println("--> Ban co muon sua tiep nua hong?(nhan phim 'y' de tiep tuc)");
            sc.nextLine();
            c = sc.nextLine();
        } while (c.equals("y"));

        menuQuanLyPhuVu();
    }

    public void xoaMonTrongBangGoi() throws SQLException {
        System.out.println("\t    4 - Xoa mon trong bang goi mon         ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        String tem = "";
        int i = 0;
        do {
            String idban;
            do {
                System.out.println("Nhap ID ban can xoa mon(nhap '0' de thoat):");
                idban = sc.nextLine();
                if (idban.equals("0")) {
                    menuQuanLyPhuVu();
                    return;
                    //break;
                }
            } while (idban.equals(""));
            pstmt = conn.prepareStatement("SELECT * FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
            pstmt.setString(1, idban);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                i++;
                if (i != 0) {
                    tem = idban;
                    String mon = "", c;

                    do {
                        int j = 0;
                        do {

                            System.out.println("Nhap ID man an can xoa: ");
                            String idma = sc.nextLine();

                            pstmt = conn.prepareStatement("SELECT idMA FROM monan WHERE idMA=? and trangthaiMA=true ");
                            pstmt.setString(1, idma);
                            rs = pstmt.executeQuery();
                            while (rs.next()) {
                                j++;
                                if (j != 0) {
                                    mon = rs.getString("idMA");
                                    try {
                                        pstmt = conn.prepareStatement("delete from  GoiMon where idMA=? and trangthaiOrder=true");
                                        pstmt.setString(1, mon);
                                        int kq = pstmt.executeUpdate();
                                        if (kq != 1) {
                                            System.out.println("da rong!");
                                        }
                                    } catch (SQLException e) {
                                        System.out.println("khong xoa duoc! Loi!");
//                                        e.printStackTrace();
                                    }

                                }
                            }
                            if (j == 0) {
                                System.out.println("\t Nhap ID mon an sai!");
                            }
                        } while (j == 0);
                        System.out.println("Them mon can xoa nua hong?(phim 'y' de tiep tuc)");
                        c = sc.nextLine();
                    } while (c.equals("y"));

                }
            }
            if (i == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }
        } while (i == 0);

        // 
        if (i != 0) {
            System.out.print("\t xoa xong");

            pstmt = conn.prepareStatement("SELECT * FROM GoiMon "
                    + "WHERE idBan=? and trangthaiOrder=true");
            pstmt.setString(1, tem);
            rs = pstmt.executeQuery();
            int v = 0;
            while (rs.next()) {
                v++;
            }
            if (v == 0) {
                try {
                    pstmt = conn.prepareStatement("UPDATE ban set trangthaiBan=false"
                            + " where idBan=?");
                    pstmt.setString(1, tem);
                    int kt = pstmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Loi!");
                }
            } else {
                System.out.print("!");
                System.out.println("");
            }
        }
        menuQuanLyPhuVu();
    }

    public void chuyenBan() throws SQLException {
        System.out.println("\t    5 - Chuyen ban                         ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        String idban = "";
        int i = 0;
        do {
            do {
                System.out.println("Nhap ID ban can chuyen(nhap '0' de thoat): ");
                idban = sc.nextLine();
                if (idban.equals("0")) {
                    menuQuanLyPhuVu();
                    return;
                    //break;
                }
            } while (idban.equals(""));

            pstmt = conn.prepareStatement("SELECT idBan FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
            pstmt.setString(1, idban);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                i++;
            }
            if (i == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }

        } while (i == 0);

        String idbanchuyen = "";
        int j = 0;
        do {
            do {
                System.out.println("Nhap ID ban chuyen den(nhap '0' de thoat):");
                idbanchuyen = sc.nextLine();
                if (idbanchuyen.equals("0")) {
                    menuQuanLyPhuVu();
                    //break;
                    return;
                }
            } while (idbanchuyen.equals(""));

            pstmt = conn.prepareStatement("SELECT idBan FROM ban WHERE idBan=? and trangthaiBan=false");
            pstmt.setString(1, idbanchuyen);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                j++;
            }

            if (j == 0) {
                System.out.println("\t Nhap ID ban chua hop ly ");
            }

        } while (j == 0);
// cap nhat tren bang Goimon
        if (j != 0) {
            try {
                pstmt = conn.prepareStatement("UPDATE GoiMon set idBan=? WHERE idBan=? and trangthaiOrder=true");
                pstmt.setString(1, idbanchuyen);
                pstmt.setString(2, idban);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("khong chuyen duoc! Loi!");
//                e.printStackTrace();
            }
        }
// cap nhat tren ban ban.
        try {
            pstmt = conn.prepareStatement("UPDATE ban set trangthaiBan=false where idBan=?");
            pstmt.setString(1, idban);
            int kt = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi!");
        }
// cap nhat tren ban an
        try {
            pstmt = conn.prepareStatement("UPDATE ban set trangthaiBan=true where idBan=?");
            pstmt.setString(1, idbanchuyen);
            int kt = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi!");
        }
        System.out.println("Chuyen thanh cong!");
        menuQuanLyPhuVu();
    }

    public void xemHoaDon() throws SQLException {
        System.out.println("\t    7 - Xem hoa don                        ");

        System.out.println("\tDS Ban Chua Thanh Toan        ");
        Scanner sc = new Scanner(System.in);
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            int iii = 0;
            pstmt = conn.prepareStatement("select distinct idBan from GoiMon where trangthaiOrder=true");
            rs = pstmt.executeQuery();
            System.out.println("|---|--------|");
            System.out.println("|STT| ID_Ban |");
            System.out.println("|---|--------|");
            while (rs.next()) {
                iii++;
                System.out.printf("|%-3d|", iii);
                System.out.printf("%-8s|\n", rs.getString("idBan"));

            }
            System.out.println("|---|--------|");
            if (iii == 0) {
                System.out.println("\tkhong co!");
            }
            if (iii != 0) {
                Date d = new Date();
                java.sql.Date ddd = new java.sql.Date(0);
                java.sql.Time ttg = new java.sql.Time(0);

                long gia[] = null, tt[] = null, tong = 0;
                int j, i = 0, stt = 0, sl[] = null, k = 100;
                String tennv = "", tenban = "", tenMA[] = null;
                tenMA = new String[k];
                sl = new int[k];
                gia = new long[k];
                tt = new long[k];
                System.out.println("");
                String tem = "";
                int z = 0;
                do {
                    String idban;
                    do {
                        System.out.println("Nhap ID ban can xem hoa don(nhap '0' de thoat): ");
                        idban = sc.nextLine();
                        if (idban.equals("0")) {
                            this.menuQuanLyPhuVu();
                            return;
                            //break;
                        }
                    } while (idban.equals(""));
                    pstmt = conn.prepareStatement("SELECT idBan FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
                    pstmt.setString(1, idban);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        z++;
                        if (z != 0) {
                            tem = idban;
                            try {
                                pstmt = conn.prepareStatement("select  a.STT,a.ngay,a.tg,d.tenNV , b.idBan,b.tenBan,c.tenMA,a.soluongmon,c.gia "
                                        + "from GoiMon as a, ban as b,monan as c, nhanvien as d\n"
                                        + "where a.idBan=b.idBan and a.idMA=c.idMA and d.idNV=a.idNV and a.idBan=? and a.trangthaiOrder=true");
                                pstmt.setString(1, idban);
                                rs = pstmt.executeQuery();
                                while (rs.next()) {
                                    stt = rs.getInt("STT");
                                    ddd = rs.getDate("ngay");
                                    ttg = rs.getTime("tg");
                                    tennv = rs.getString("tenNV");
                                    tenban = rs.getString("tenBan");
                                    tenMA[i] = rs.getString("tenMA");
                                    sl[i] = rs.getInt("soluongmon");
                                    gia[i] = rs.getInt("gia");

                                    i++;
                                }
                            } catch (SQLException e) {
                                System.out.println("khong in duoc!Loi");
                                //e.printStackTrace();
                            }
                        }
                    }
                    if (z == 0) {
                        System.out.println("Nhap ID ban chua dung!nhap lai");
                    }
                } while (z == 0);
                if (z != 0) {
                    System.out.println("\n---------------------------------------------------------");
                    System.out.print("\t         QUAN AN GIA DINH S-T    ");
                    System.out.println("\n\n\t\t  PHIEU THANH TOAN ");
                    System.out.println("STT: " + stt);
                    System.out.println("Ten nhan vien: " + tennv);
                    System.out.println("Ten ban: " + tenban);
                    System.out.println("Thoi gian vao: " + ddd + " " + ttg);
                    System.out.println(" STT       Ten mon         So Luong    Gia    Thanh Tien ");

                    for (j = 0; j < i; j++) {

                        tt[j] = (sl[j]) * (gia[j]);
                        tong = tong + tt[j];
                        System.out.printf("  %-3d", (j + 1));
                        System.out.printf("%-20s", tenMA[j]);
                        System.out.printf("%8d", sl[j]);
                        System.out.printf("%11d", gia[j]);
                        System.out.printf("%12d\n", tt[j]);
                    }

                    System.out.println("\t\t Tong tien: " + tong);
                    System.out.println("\t\t (chua thanh toan)");
                    System.out.println("---------------------------------------------------------");
                }

            }

        } catch (SQLException e) {
            System.out.println("khong in duoc!");
//                        e.printStackTrace();
        }
        menuQuanLyPhuVu();
    }

    public void xuatHoaDon() throws SQLException {
        System.out.println("\t    8 - Xuat hoa don                       ");

        System.out.println("\tDS Ban Chua Thanh Toan        ");
        Scanner sc = new Scanner(System.in);
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            int iii = 0;

            pstmt = conn.prepareStatement("select distinct idBan from GoiMon where trangthaiOrder=true");
            rs = pstmt.executeQuery();
            System.out.println("|---|--------|");
            System.out.println("|STT| ID_Ban |");
            System.out.println("|---|--------|");
            while (rs.next()) {
                iii++;
                System.out.printf("|%-3d|", iii);
                System.out.printf("%-8s|\n", rs.getString("idBan"));

            }
            System.out.println("|---|--------|");
            if (iii == 0) {
                System.out.println("\tkhong co!");
            }
            if (iii != 0) {

                Date d = new Date();
                java.sql.Date ddd = new java.sql.Date(0);
                java.sql.Time ttg = new java.sql.Time(0);

                long gia[] = null, tt[] = null, tong = 0;
                int j, i = 0, stt = 0, sl[] = null, k = 100;
                String tennv = "", tenban = "", tenMA[] = null;
                tenMA = new String[k];
                sl = new int[k];
                gia = new long[k];
                tt = new long[k];

                String tem = "";
                int z = 0;
                do {
                    String idban;
                    do {
                        System.out.println("Nhap ID ban can thanh toan(nhap '0' de thoat):");
                        idban = sc.nextLine();
                        if (idban.equals("0")) {
                            menuQuanLyPhuVu();
                            return;
                            //break;
                        }
                    } while (idban.equals(""));
                    pstmt = conn.prepareStatement("SELECT idBan FROM GoiMon WHERE idBan=? and trangthaiOrder=true");
                    pstmt.setString(1, idban);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        z++;
                        if (z != 0) {
                            tem = idban;
                            try {

                                pstmt = conn.prepareStatement("select  a.STT,a.ngay,a.tg,d.tenNV , b.idBan,b.tenBan,c.tenMA,a.soluongmon,c.gia "
                                        + "from GoiMon as a, ban as b,monan as c, nhanvien as d\n"
                                        + "where a.idBan=b.idBan and a.idMA=c.idMA and d.idNV=a.idNV and a.idBan=? and a.trangthaiOrder=true");
                                pstmt.setString(1, idban);
                                rs = pstmt.executeQuery();
                                while (rs.next()) {
                                    stt = rs.getInt("STT");
                                    ddd = rs.getDate("ngay");
                                    ttg = rs.getTime("tg");
                                    tennv = rs.getString("tenNV");
                                    tenban = rs.getString("tenBan");
                                    tenMA[i] = rs.getString("tenMA");
                                    sl[i] = rs.getInt("soluongmon");
                                    gia[i] = rs.getInt("gia");

                                    i++;
                                }
                            } catch (SQLException e) {
                                System.out.println("khong in duoc!");
                                //                        e.printStackTrace();
                            }
                        }
                    }
                    if (z == 0) {
                        System.out.println("Nhap ID ban chua dung!nhap lai");
                    }
                } while (z == 0);

                try {
                    pstmt = conn.prepareStatement("UPDATE ban set trangthaiBan=false where idBan=?");
                    pstmt.setString(1, tem);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Loi!");
                }
                try {
                    pstmt = conn.prepareStatement("UPDATE GoiMon set trangthaiOrder=false where STT=?");
                    pstmt.setInt(1, stt);
                    pstmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Loi!");
                }
                long thu;

                do {
                    System.out.println("\nNhap tien thu vao cua khach la: ");
                    thu = sc.nextLong();
                } while (thu < tong);
                System.out.println("\n---------------------------------------------------------");
                System.out.print("\t         QUAN AN GIA DINH S-T    ");
                System.out.println("\n\n\t\t  PHIEU THANH TOAN ");
                System.out.println("STT: " + stt);
                System.out.println("Ten nhan vien: " + tennv);
                System.out.println("Ten ban: " + tenban);
                System.out.println("Thoi gian vao: " + ddd + "" + ttg);
                System.out.println("Thoi gian ra: " + d);
                System.out.println(" STT       Ten mon         So Luong    Gia    Thanh Tien ");

                for (j = 0; j < i; j++) {

                    tt[j] = (sl[j]) * (gia[j]);
                    tong = tong + tt[j];
                    System.out.printf("  %-3d", (j + 1));
                    System.out.printf("%-20s", tenMA[j]);
                    System.out.printf("%8d", sl[j]);
                    System.out.printf("%,311d%n", gia[j]);
                    System.out.printf("%12d\n", tt[j]);
                }

                System.out.println("\n\t\tTong tien: " + tong);
                System.out.println("\t\tThu vao la: " + thu);
                System.out.println("\t\tTra lai: " + (thu - tong));
                System.out.println("\tCam on quy khach, hen gap lai quy khach!");
                System.out.println("\n---------------------------------------------------------");

            }

        } catch (SQLException e) {
            System.out.println("khong xuat duoc!");
//                        e.printStackTrace();
        }

        menuQuanLyPhuVu();
    }

    public void hienThiBanTrong() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println("\t    6 - Hien thi ban con trong             ");
        System.out.println("Danh sach ban trong:");
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ban WHERE trangthaiBan=false ");

            System.out.println("|---|--------|------------------------------|");
            System.out.println("|STT| ID_Ban |           Ten_Ban            |");
            System.out.println("|---|--------|------------------------------|");
            int i = 1;
            while (rs.next()) {
                System.out.printf("|%-3d|", i);
                System.out.printf("%-8s|", rs.getString("idBan"));
                System.out.printf("%-30s|\n", rs.getString("tenBan"));
                i++;
            }
            System.out.println("|---|--------|------------------------------|");
        } catch (SQLException e) {
            System.out.println("Loi!");
            //e.printStackTrace();
        }
        menuQuanLyPhuVu();
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
        System.out.print("\n-------------------------------------------------");
        System.out.print("-------------------------------------------------\n");
        return;
    }

    public void trolai() throws SQLException {
        QuanLyQuanAn a = new QuanLyQuanAn();
        this.thoat();
        a.menuChucNang();
        return;
    }

    public void menuQuanLyPhuVu() throws SQLException {
        System.out.println("\n\t\t|---------------------------------------------|");
        System.out.println("\t\t|          CHUC NANG QUAN LY PHUC VU          |");
        System.out.println("\t\t|---------------------------------------------|");
        System.out.println("\t\t|    0 - Tro lai                              |");
        System.out.println("\t\t|    1 - Them phieu yeu cau goi mon           |");
        System.out.println("\t\t|    2 - Them mon vao phieu yeu cau goi mon   |");
        System.out.println("\t\t|    3 - Sua mon trong phieu yeu cau goi mon  |");
        System.out.println("\t\t|    4 - Xoa mon trong phieu yeu cau goi mon  |");
        System.out.println("\t\t|    5 - Chuyen ban                           |");
        System.out.println("\t\t|    6 - Hien thi ban con trong               |");
        System.out.println("\t\t|    7 - Xem hoa don                          |");
        System.out.println("\t\t|    8 - Xuat hoa don                         |");
        System.out.println("\t\t|    9 - Thoat                                |");
        System.out.println("\t\t|---------------------------------------------|");
        Scanner sc = new Scanner(System.in);
        int a;
        do {
            System.out.println("Moi chon: ");
            a = sc.nextInt();
            sc.nextLine();
            if (a < 0 || a > 9) {
                System.out.println("nhap sai,nhap lai ");
            }
        } while (a < 0 || a > 9);

        switch (a) {
            case 0:
                trolai();
                break;
            case 1:
                themBangGoi();
                break;
            case 2:
                themMonVaoBangGoi();
                break;
            case 3:
                suaMontrongBangGoi();
                break;
            case 4:
                xoaMonTrongBangGoi();
                break;
            case 5:
                chuyenBan();
                break;
            case 6:
                hienThiBanTrong();
                break;

            case 7:
                xemHoaDon();

                break;
            case 8:
                xuatHoaDon();
                break;
            case 9: {
                thoat();
                break;
            }

        }
        return;
    }

}
