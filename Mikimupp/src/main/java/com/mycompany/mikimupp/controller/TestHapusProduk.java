package com.mycompany.mikimupp.controller;

public class TestHapusProduk {

    public static void main(String[] args) {

        ProdukController controller = new ProdukController();

        int idProduk = 5;

        boolean berhasil = controller.hapusProduk(idProduk);

        if (berhasil) {
            System.out.println("Pengujian DELETE berhasil!");
        } else {
            System.out.println("Pengujian DELETE gagal!");
        }
    }
}
