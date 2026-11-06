package com.mycompany.mikimupp;

import com.mycompany.mikimupp.view.ProdukView;
import javax.swing.SwingUtilities;

public class Mikimupp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProdukView view = new ProdukView();
            view.setVisible(true);
        });
    }
}
