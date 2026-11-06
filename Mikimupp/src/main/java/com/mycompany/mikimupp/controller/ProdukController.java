package com.mycompany.mikimupp.controller;

import com.mycompany.mikimupp.database.DatabaseConnection;
import com.mycompany.mikimupp.model.Produk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukController {

    public List<Produk> getAllProduk() {
        List<Produk> daftarProduk = new ArrayList<>();

        String sql = "SELECT * FROM produk";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produk produk = new Produk(
                        rs.getInt("id_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );

                daftarProduk.add(produk);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data produk: " + e.getMessage());
        }

        return daftarProduk;
    }

    public boolean updateProduk(Produk produk) {
        String sql = "UPDATE produk SET nama_produk = ?, harga = ?, stok = ? WHERE id_produk = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produk.getNamaProduk());
            stmt.setDouble(2, produk.getHarga());
            stmt.setInt(3, produk.getStok());
            stmt.setInt(4, produk.getIdProduk());

            int barisDiubah = stmt.executeUpdate();

            if (barisDiubah > 0) {
                System.out.println("Produk berhasil diperbarui!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal memperbarui produk: " + e.getMessage());
        }

        return false;
    }

    public boolean tambahProduk(Produk produk) {
        String sql = "INSERT INTO produk (nama_produk, harga, stok) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produk.getNamaProduk());
            stmt.setDouble(2, produk.getHarga());
            stmt.setInt(3, produk.getStok());

            stmt.executeUpdate();

            System.out.println("Produk berhasil ditambahkan!");
            return true;

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan produk: " + e.getMessage());
            return false;

        }
    }

    public boolean hapusProduk(int idProduk) {
        String sql = "DELETE FROM produk WHERE id_produk = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduk);

            int barisDihapus = stmt.executeUpdate();

            if (barisDihapus > 0) {
                System.out.println("Produk berhasil dihapus!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: " + e.getMessage());
        }

        return false;
    }
}
