package goi1;

import java.sql.*;
import java.util.*;

public class monAn {

    Connection conn = null;

    public void ketNoi() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/doan?" + "user=root");
            //System.out.println("noi ket thanh cong!");

        } catch (Exception ex) {
            System.out.println("noi ket that bai!");
            //ex.printStackTrace();
        }
    }

    //hien thi danh sach cac mon an cua quan
    public void hienThiMonAn() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        System.out.println("\t 1 - Hien Thi DS Mon An                    ");
        try {
            int i = 0;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM monan Where trangthaiMA=true");
            System.out.println("|---|----------|-------------------------|---------|");
            System.out.println("|STT|   IDMA   |          TenMA          |   Gia   |");
            System.out.println("|---|----------|-------------------------|---------|");
            while (rs.next()) {
                i++;
                System.out.printf("|%-3d|", i);
                System.out.printf("%-10s|", rs.getString("idMA"));
                System.out.printf("%-25s|", rs.getString("tenMA"));
                System.out.printf("%,39s|\n", rs.getString("gia"));

            }
        } catch (SQLException e) {
            System.out.println("khong lam duoc!");
        }
        System.out.println("|---|----------|-------------------------|---------|");
        // dao ve trang chuc nang;
        menuMonAn();
        return;
    }

    public void themMonAn() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        String kt = "";
        String id = "", ten = "";
        int gia;
        do {
            System.out.println("\t 3 - Them Mon An Vao DS                    ");

            do {
                System.out.println("Nhap ID cho mon an(nhap '0' de thoat): ");
                id = sc.nextLine();
                if (id.equals("0")) {
                    menuMonAn();
                    return;
                    //break;
                }
            } while (id.equals(""));
            do {
                System.out.println("Nhap ten cho mon an(nhap '0' de thoat): ");
                ten = sc.nextLine();
                if (ten.equals("0")) {
                    menuMonAn();
                    return;
                    //break;
                }
            } while (ten.equals(""));
            do {
                System.out.println("Nhap gia cho mon an: ");
                gia = sc.nextInt();
            } while (gia <= 1000);

            try {
                pstmt = conn.prepareStatement("insert into  monan value(?, ?, ? , true)");
                pstmt.setString(1, id);
                pstmt.setString(2, ten);
                pstmt.setInt(3, gia);
                // pstmt.setBoolean(5,true);

                int r = pstmt.executeUpdate();
                if (r == 1) {
                    System.out.println("Them thanh cong!");
                    //in ra kq
                    pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMA=true");
                    pstmt.setString(1, id);
                    rs = pstmt.executeQuery();
                    System.out.println("Ket qua");
                    System.out.println("|----------|-------------------------|---------|");
                    System.out.println("|   IDMA   |          TenMA          |   Gia   |");
                    System.out.println("|----------|-------------------------|---------|");
                    while (rs.next()) {
                        System.out.printf("|%-10s|", rs.getString("idMA"));
                        System.out.printf("%-25s|", rs.getString("tenMA"));
                        System.out.printf("%9s|\n", rs.getString("gia"));

                    }
                    System.out.println("|----------|-------------------------|---------|");

                } else {
                    System.out.println("Khong them duoc!");
                }

            } catch (SQLException e) {
                System.out.println("Loi! khong them duoc!");
                //e.printStackTrace();
            }
            sc.nextLine();
            System.out.println("--> Ban co muon tiep tuc them nua hong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();

        } while (kt.equals("y"));
        // dao ve trang chuc nang;
        this.menuMonAn();
        return;
    }

    public void timKiemID() throws SQLException {
        System.out.println("\t (1).Tim kiem theo ID mon an             ");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        String a;
        String kt;
        do {
            do {
                System.out.println("Nhap ID mon an can tim(nhap '0' de thoat): ");
                a = sc.nextLine();
                if (a.equals("0")) {
                    timKiem();
                    return;
                }
            } while (a.equals(""));

            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMA=true");
            pstmt.setString(1, a);
            rs = pstmt.executeQuery();

            System.out.println("Ket qua");
            try {
                int i = 0;
                System.out.println("|---|----------|-------------------------|---------|");
                System.out.println("|STT|   IDMA   |          TenMA          |   Gia   |");
                System.out.println("|---|----------|-------------------------|---------|");
                while (rs.next()) {
                    i++;
                    System.out.printf("|%-3d|", i);
                    System.out.printf("%-10s|", rs.getString("idMA"));
                    System.out.printf("%-25s|", rs.getString("tenMA"));
                    System.out.printf("%9s|\n", rs.getString("gia"));

                }
                System.out.println("|---|----------|-------------------------|---------|");
                if (i == 0) {
                    System.out.println("tim khong thay!");
                }
            } catch (SQLException e) {
                System.out.println("Loi! Tìm không thấy!");
                // e.printStackTrace();
            }

            System.out.println("Ban co muon tiep tuc nua hong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();

        } while (kt.equals("y"));
        timKiem();
        return;
    }

    public void timKiemTen() throws SQLException {
        System.out.println("\t (2).Tim kiem theo ten mon an            ");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        String a;
        String kt = "";
        do {
            do {
                System.out.println("Nhap ten mon an can tim(nhap '0' de thoat): ");
                a = sc.nextLine();
                if (a.equals("0")) {
                    timKiem();
                    return;

                }
            } while (a.equals(""));
            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE INSTR(tenMA,?) > 0 and trangthaiMA=true");
            pstmt.setString(1, a);
            rs = pstmt.executeQuery();
            System.out.println("Ket qua");
            try {
                int i = 0;
                System.out.println("|---|----------|-------------------------|---------|");
                System.out.println("|STT|   IDMA   |          TenMA          |   Gia   |");
                System.out.println("|---|----------|-------------------------|---------|");
                while (rs.next()) {
                    i++;
                    System.out.printf("|%-3d|", i);
                    System.out.printf("%-10s|", rs.getString("idMA"));
                    System.out.printf("%-25s|", rs.getString("tenMA"));
                    System.out.printf("%9s|\n", rs.getString("gia"));

                }
                System.out.println("|---|----------|-------------------------|---------|");
                if (i == 0) {
                    System.out.println("tim khong thay!");
                }
            } catch (SQLException e) {
                System.out.println("Loi!tim khong thay!");
                // e.printStackTrace();
            }

            System.out.println("Ban co muon tiep tuc nua hong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));

        timKiem();
        return;
    }

    public void timKiemGia() throws SQLException {
        System.out.println("\t (3).Tim kiem theo gia mon an            ");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        int a;
        String kt;
        do {
            do {
                System.out.println("Nhap gia can tim kiem(nhap '0' de thoat): ");
                a = sc.nextInt();
                if (a == 0) {
                    timKiem();
                    return;

                }
            } while (a < 0);

            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE Gia=? and trangthaiMA=true");
            pstmt.setInt(1, a);
            rs = pstmt.executeQuery();
            System.out.println("\tKet qua:");
            try {
                int i = 0;
                System.out.println("|---|----------|-------------------------|---------|");
                System.out.println("|STT|   IDMA   |          TenMA          |   Gia   |");
                System.out.println("|---|----------|-------------------------|---------|");
                while (rs.next()) {
                    i++;
                    System.out.printf("|%-3d|", i);
                    System.out.printf("%-10s|", rs.getString("idMA"));
                    System.out.printf("%-25s|", rs.getString("tenMA"));
                    System.out.printf("%9s|\n", rs.getString("gia"));

                }
                System.out.println("|---|----------|-------------------------|---------|");
                if (i == 0) {
                    System.out.println("\ttim khong thay!");
                }
            } catch (SQLException e) {
                System.out.println("\t Loi!tim khong thay!");
                //e.printStackTrace();
            }

            sc.nextLine();
            System.out.println("Ban co muon tiep tuc nua hong?(nhan phim 'y' de tiep tuc)");
            kt = sc.nextLine();
        } while (kt.equals("y"));
        timKiem();
        return;
    }

    public void timKiem() throws SQLException {
        Scanner sc = new Scanner(System.in);

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        System.out.println("\t 2 - Tim Kiem Mon An Trong DS              ");
        System.out.println("\t|-----------------------------------------|");
        System.out.println("\t| (0).Tro lai                             |");
        System.out.println("\t| (1).Tim kiem theo ID mon an             |");
        System.out.println("\t| (2).Tim kiem theo ten mon an            |");
        System.out.println("\t| (3).Tim kiem theo gia mon an            |");
        System.out.println("\t|-----------------------------------------|");
        int c;
        do {
            System.out.println("\tMoi ban chon: ");
            c = sc.nextInt();

            if (c > 3 || c < 0) {
                System.out.println("\tNhap sai,nhap lai ");
            }
        } while ((c > 3 || c < 0));
        switch (c) {
            case 0:
                menuMonAn();
                break;
            case 1:
                timKiemID();
                break;
            case 2:
                timKiemTen();
                break;
            case 3:
                timKiemGia();
                break;
        }
return;
    }

    String idtemp;

    public void suaIDMA(String id) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("\t (1).Sua ID_MA        ");

        do {
            System.out.println("Nhap ID moi cho mon an(nhap '0' de thoat): ");
            idtemp = sc.nextLine();
            if (idtemp.equals("0")) {
                suaMonAn();
                return;
            }
        } while (idtemp.equals(""));

        try {
            pstmt = conn.prepareStatement("UPDATE monan set idMA=? where idMA=?");
            pstmt.setString(1, idtemp);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            System.out.println("Sua thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi!khong sua duoc.");
//            e.printStackTrace();
        }
        suaMonAn();
        return;
    }

    public void suaten(String id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("\t (2).Sua ten mon an   ");
        String ten1;
        do {
            System.out.println("Nhap ten moi cho mon an(nhap '0' de thoat): ");
            ten1 = sc.nextLine();
            if (ten1.equals("0")) {
                suaMonAn();
                return;
                //break;
            }
        } while (id.equals(""));
        try {
            pstmt = conn.prepareStatement("UPDATE monan  set tenMA=? where idMA=?");
            pstmt.setString(1, ten1);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            System.out.println("Sua thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi!khong sua duoc.");
            //e.printStackTrace();
        }
        suaMonAn();
        return;
    }

    public void suagia(String id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("\t (3).Sua gia mon an   ");
        int gia1;
        do {
            System.out.println("Nhap gia moi cho mon an(nhap '0' de thoat): ");
            gia1 = sc.nextInt();
            if (gia1 == 0) {
                suaMonAn();
                return;//break;
            }
        } while (gia1 <= 1000);
        sc.nextLine();
        try {
            pstmt = conn.prepareStatement("UPDATE monan set gia=? where idMA=?");
            pstmt.setInt(1, gia1);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            System.out.println("Sua thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi!khong sua duoc.");
            //e.printStackTrace();
        }
        suaMonAn();
        return;
    }

    public void suaall(String id) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        String ten;
        int gia;
        System.out.println("\t (4).Sua tat ca       ");
        do {
            System.out.println("Nhap ID moi cho mon an(nhap '0' de thoat): ");
            idtemp = sc.nextLine();
            if (idtemp.equals("0")) {
                suaMonAn();
                return;//break;
            }
        } while (idtemp.equals(""));
        do {
            System.out.println("Nhap ten moi cho mon an: ");
            ten = sc.nextLine();

        } while (ten.equals(""));
        do {
            System.out.println("Nhap gia moi cho mon an: ");
            gia = sc.nextInt();
        } while (gia <= 1000);

        try {
            pstmt = conn.prepareStatement("UPDATE monan "
                    + "set idMA=?, tenMA=?,gia=?"
                    + " where idMA=?");
            pstmt.setString(1, idtemp);
            pstmt.setString(2, ten);
            pstmt.setInt(3, gia);
            pstmt.setString(4, id);
            pstmt.executeUpdate();

            System.out.println("Sua thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi!khong sua duoc.");
//            e.printStackTrace();
        }
        suaMonAn();
        return;
    }

    public void suaMonAn() throws SQLException {
        System.out.println();
        System.out.println("\t 4 - Sua Thong Tin Mon An                  ");
        Scanner sc = new Scanner(System.in);
        String id;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        do {
            System.out.println("Nhap ID mon an can sua(nhap '0' de thoat): ");
            id = sc.nextLine();
            if (id.equals("0")) {
                menuMonAn();
                return;
            }
        } while (id.equals(""));

        pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMA=true");
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        boolean i = rs.next();

        if (i == true) {

            pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMA=true");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            System.out.println("Ket qua");
            System.out.println("|----------|-------------------------|---------|");
            System.out.println("|   IDMA   |          TenMA          |   Gia   |");
            System.out.println("|----------|-------------------------|---------|");
            while (rs.next()) {
                System.out.printf("|%-10s|", rs.getString("idMA"));
                System.out.printf("%-25s|", rs.getString("tenMA"));
                System.out.printf("%9s|\n", rs.getString("gia"));

            }
            System.out.println("|----------|-------------------------|---------|");

            int c;
            System.out.println("\n\t|----------------------|");
            System.out.println("\t| (0).Tro lai          |");
            System.out.println("\t| (1).Sua ID_MA        |");
            System.out.println("\t| (2).Sua ten mon an   |");
            System.out.println("\t| (3).Sua gia mon an   |");
            System.out.println("\t| (4).Sua tat ca       |");
            System.out.println("\t|----------------------|");

            do {

                System.out.println("Moi ban chon: ");
                c = sc.nextInt();

                if (c > 4 || c < 0) {
                    System.out.println("Nhap sai,nhap lai ");
                }
            } while ((c > 4 || c < 0));
            switch (c) {
                case 0:
                    menuMonAn();
                    break;
                case 1: {
                    suaIDMA(id);
                    break;
                }
                case 2: {
                    suaten(id);
                    break;
                }
                case 3: {
                    suagia(id);
                    break;
                }

                case 4: {
                    suaall(id);
                    break;
                }
            }
            id = idtemp;
        } else {
            System.out.println("ID mon an khong ton tai!");
        }
        return;
    }

    public void xoaMonAnTheoSTT() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String id;
        System.out.println("\t 5 - Xoa Mon An Khoi DS                    ");
        do {
            System.out.println("Nhap ID mon an can xoa(nhap '0' de thoat): ");
            id = sc.nextLine();
            if (id.equals("0")) {
                menuMonAn();
                return;//break;
            }
        } while (id.equals(""));

        pstmt = conn.prepareStatement("SELECT * FROM monan WHERE idMA=? and trangthaiMA=true");
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        System.out.println("\tKet qua:");
        int jj = 0;
        System.out.println("|----------|-------------------------|---------|");
        System.out.println("|   IDMA   |          TenMA          |   Gia   |");
        System.out.println("|----------|-------------------------|---------|");
        while (rs.next()) {
            jj++;
            System.out.printf("|%-10s|", rs.getString("idMA"));
            System.out.printf("%-25s|", rs.getString("tenMA"));
            System.out.printf("%9s|\n", rs.getString("gia"));
        }
        System.out.println("|----------|-------------------------|---------|");
        try {
            pstmt = conn.prepareStatement("UPDATE monan set trangthaiMA=false where idMA=?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Xoa thanh cong!");
        } catch (SQLException e) {
            System.out.println("Loi!khong xoa duoc");
        }
        if (jj == 0) {
            System.out.println("\ttim khong thay!");
        }

        menuMonAn();
        return;
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
            e.printStackTrace();

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

    public void menuMonAn() throws SQLException {
        System.out.println("\n\t\t|-------------------------------------------|");
        System.out.println("\t\t|             QUAN LY MON AN                |");
        System.out.println("\t\t|-------------------------------------------|");
        System.out.println("\t\t| 0 - Tro Lai                               |");
        System.out.println("\t\t| 1 - Hien Thi DS Mon An                    |");
        System.out.println("\t\t| 2 - Tim Kiem Mon An Trong DS              |");
        System.out.println("\t\t| 3 - Them Mon An Vao DS                    |");
        System.out.println("\t\t| 4 - Sua Thong Tin Mon An                  |");
        System.out.println("\t\t| 5 - Xoa Mon An Khoi DS                    |");
        System.out.println("\t\t| 6 - Thoat                                 |");
        System.out.println("\t\t|-------------------------------------------|");

        Scanner sc = new Scanner(System.in);
        int c;
        do {
            System.out.println("Nhap vao lua chon cua ban: ");
            c = sc.nextInt();
//            sc.nextLine();
            if (c > 6 || c < 0) {
                System.out.println("Nhap sai,nhap lai");
            }
        } while (c > 6 || c < 0);

        switch (c) {
            case 0:
                trolai();
                break;
            case 1:
                hienThiMonAn();
                break;
            case 2:
                timKiem();
                break;
            case 3:
                themMonAn();
                break;
            case 4:
                suaMonAn();
                break;
            case 5:
                xoaMonAnTheoSTT();
                break;
            case 6:
                thoat();
                break;
        }
        return;
    }

}
