package mysql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaCRUD {

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
   static final String USER = "root";
   static final String PASS = "";

   static Connection conn;
   static Statement stmt;
   static ResultSet rs;
   static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
   static BufferedReader input = new BufferedReader(inputStreamReader);

   public static void main(String[] args) {

       try {
           // register driver
           Class.forName(JDBC_DRIVER);

           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           stmt = conn.createStatement();

           while (!conn.isClosed()) {
               showMenu();
           }

           stmt.close();
           conn.close();

       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }

   }

   static void showMenu() {
       System.out.println("\n=========== MENU UTAMA ===========");
       System.out.println("1. Insert Data");
       System.out.println("2. Show Data");
       System.out.println("3. Edit Data");
       System.out.println("4. Delete Data");
       System.out.println("0. Keluar");
       System.out.println("");
       System.out.print("PILIHAN: ");

       try {
           int pilihan = Integer.parseInt(input.readLine());

           switch (pilihan) {
               case 0:
                   System.exit(0);
                   break;
               case 1:
                   insertBuku();
                   break;
               case 2:
                   showData();
                   break;
               case 3:
                   updateBuku();
                   break;
               case 4:
                   deleteBuku();
                   break;
               default:
                   System.out.println("Pilihan salah!");

           }
       } catch (IOException | NumberFormatException e) {
           e.printStackTrace();
       }
   }

   static void showData() {
       String sql = "SELECT * FROM buku";

       try {
           rs = stmt.executeQuery(sql);
           
           System.out.println("+--------------------------------+");
           System.out.println("|    DATA BUKU DI PERPUSTAKAAN   |");
           System.out.println("+--------------------------------+");

           while (rs.next()) {
               int idBuku = rs.getInt("id_buku");
               String judul = rs.getString("judul");
               String pengarang = rs.getString("pengarang");

               
               System.out.println(String.format("%d. %s -- (%s)", idBuku, judul, pengarang));
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }

   }

   static void insertBuku() {
       try {
           // ambil input dari user
           System.out.print("Judul: ");
           String judul = input.readLine().trim();
           System.out.print("Pengarang: ");
           String pengarang = input.readLine().trim();
           System.out.print("Penerbit: ");
           String penerbit = input.readLine().trim();
           System.out.print("Tahun Terbit: ");
           String tahun_terbit = input.readLine().trim();
           System.out.print("kategori: ");
           String kategori = input.readLine().trim();
           System.out.print("Harga: ");
           String harga = input.readLine().trim();
           System.out.print("Stok: ");
           String stok = input.readLine().trim();
           // query simpan
           String sql = "INSERT INTO buku (judul, pengarang, penerbit, tahun_terbit, kategori, harga, stok) VALUES('%s', '%s', '%s', %d, '%s', %.2f, %d)";
           sql = String.format(sql, judul, pengarang, penerbit, Integer.parseInt(tahun_terbit), kategori, Double.parseDouble(harga), Integer.parseInt(stok));


           // simpan buku
           stmt.execute(sql);
           
       } catch (IOException | SQLException e) {
           e.printStackTrace();
       }

   }

   static void updateBuku() {
       try {
           
           // ambil input dari user
           System.out.print("ID yang mau diedit: ");
           int idBuku = Integer.parseInt(input.readLine());
           System.out.print("Judul: ");
           String judul = input.readLine().trim();
           System.out.print("Pengarang: ");
           String pengarang = input.readLine().trim();

           // query update
           String sql = "UPDATE buku SET judul='%s', pengarang='%s' WHERE id_buku=%d";
           sql = String.format(sql, judul, pengarang, idBuku);

           // update data buku
           stmt.execute(sql);
           
       } catch (IOException | NumberFormatException | SQLException e) {
           e.printStackTrace();
       }
   }

   static void deleteBuku() {
       try {
           
           // ambil input dari user
           System.out.print("ID yang mau dihapus: ");
           int idBuku = Integer.parseInt(input.readLine());
           
           // buat query hapus
           String sql = String.format("DELETE FROM buku WHERE id_buku=%d", idBuku);

           // hapus data
           stmt.execute(sql);
           
           System.out.println("Data telah terhapus...");
       } catch (IOException | NumberFormatException | SQLException e) {
           e.printStackTrace();
       }
   }
}
