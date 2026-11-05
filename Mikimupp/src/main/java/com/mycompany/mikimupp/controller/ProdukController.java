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
}
