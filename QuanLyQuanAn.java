package goi1;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class QuanLyQuanAn {

    QuanLyNhanVien nv = new QuanLyNhanVien();
    monAn ma = new monAn();
    bangOrder o = new bangOrder();

    public void menuChucNang() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("|\t\t\t\t     HAPPY NEW YEAR                          \t\t|");
        System.out.print("\n|\t\t\t\t     2020-CANH TY                          \t\t|");
        System.out.print("\n|----------------------------------------------");
        System.out.print("-----------------------------------------|\n");
        
        System.out.print("|\t\t\t\t     QUAN AN GIA DINH S-T                    \t\t|");
        System.out.print("\n|----------------------------------------------");
        System.out.print("-----------------------------------------|\n");
        System.out.print("|\t\t\t\t     MENU CHUC NANG CHINH                    \t\t|");
        System.out.print("\n|------------------------|-----------------------");
        System.out.print("|-----------------------|--------------|\n");
        System.out.print("|  1 - Quan Ly Nhan Vien |");
        System.out.print("  2 - Quan Ly Mon An   |");
        System.out.print("  3 - Quan ly Phuc Vu  |");
        System.out.print("  0 - Thoat   |");
        System.out.print("\n|------------------------|-----------------------");
        System.out.print("|-----------------------|--------------|\n");
        System.out.println("Nhap lua chon cua ban:");
        int c;
        do {
            c = sc.nextInt();
            if (c < 0 || c > 3) {
                System.out.println("Nhap sai,nhap lai!");
            }
        } while (c < 0 || c > 3);

        switch (c) {
            case 0:

//            nv.thoat();
//            ma.thoat();
                o.thoat();
                break;
            case 1: {
                nv.ketNoi();
                nv.menuNhanVien();
                break;
            }
            case 2: {

                ma.ketNoi();
                ma.menuMonAn();
                break;
            }

            case 3: {

                o.ketNoiSQL();
                o.menuQuanLyPhuVu();
                break;
            }

        }
        return;
    }

    public static void main(String[] args) throws SQLException {
        QuanLyQuanAn a = new QuanLyQuanAn();
        a.menuChucNang();

    }
}
