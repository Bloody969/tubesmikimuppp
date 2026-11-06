package com.mycompany.mikimupp.controller;

import com.mycompany.mikimupp.model.Produk;

public class TestUpdateProduk {

    public static void main(String[] args) {

        ProdukController controller = new ProdukController();

        Produk produk = new Produk(
                5,
                "Kopi Susu Premium",
                15000,
                25
        );

        boolean berhasil = controller.updateProduk(produk);

        if (berhasil) {
            System.out.println("pengujian UPDATE berhasil!");
        } else {
            System.out.println("Pengujian UPDATE gagal!");
        }
    }
}
