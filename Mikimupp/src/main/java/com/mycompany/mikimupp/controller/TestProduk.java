package com.mycompany.mikimupp.controller;

import com.mycompany.mikimupp.model.Produk;

public class TestProduk {

    public static void main(String[] args) {
        ProdukController controller = new ProdukController();

        Produk produk = new Produk(
                0,
                "Beras 5kg",
                75000,
                10
        );

        boolean berhasil = controller.tambahProduk(produk);

        if (berhasil) {
            System.out.println("Pengujian INSERT berhasil!");
        }
    }
}
