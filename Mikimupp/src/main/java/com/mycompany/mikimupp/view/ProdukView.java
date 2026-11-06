package com.mycompany.mikimupp.view;

import com.mycompany.mikimupp.controller.ProdukController;
import com.mycompany.mikimupp.model.Produk;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProdukView extends JFrame {

    // ====== Palet warna ======
    private static final Color GREEN = new Color(0x2E7D32);
    private static final Color GREEN_LIGHT = new Color(0xE8F5E9);
    private static final Color BLUE = new Color(0x1976D2);
    private static final Color RED = new Color(0xD32F2F);
    private static final Color GRAY_BTN = new Color(0x78909C);
    private static final Color BG = new Color(0xF4F6F8);
    private static final Color CARD = Color.WHITE;
    private static final Color BORDER = new Color(0xDDE2E7);
    private static final Color TEXT_DARK = new Color(0x1F2937);
    private static final Color TEXT_MUTED = new Color(0x6B7280);
    private static final Color ROW_STRIPE = new Color(0xF9FBF9);

    private static final Font FONT_BASE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_SUB = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_CARD = new Font("Segoe UI", Font.BOLD, 16);

    private final ProdukController controller = new ProdukController();

    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtStok;
    private RoundedButton btnSimpan;
    private RoundedButton btnUbah;
    private RoundedButton btnHapus;
    private RoundedButton btnReset;
    private JTable tabel;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;

    private int selectedId = -1;

    private final NumberFormat rupiah = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

    public ProdukView() {
        rupiah.setMaximumFractionDigits(2);
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("Mikimupp - Kelola Produk");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(760, 560));
        setSize(980, 680);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 24, 24));
        body.add(buildFormPanel(), BorderLayout.NORTH);
        body.add(buildTablePanel(), BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayoutY(header));
        header.setBackground(GREEN);
        header.setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel title = new JLabel("Kelola Produk");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Kelola data produk Mikimupp");
        subtitle.setFont(FONT_SUB);
        subtitle.setForeground(new Color(0xC8E6C9));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        return header;
    }

    private JPanel buildFormPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(CARD);
        card.setBorder(cardBorder());

        JLabel cardTitle = new JLabel("Data Produk");
        cardTitle.setFont(FONT_CARD);
        cardTitle.setForeground(TEXT_DARK);
        card.add(cardTitle, BorderLayout.NORTH);

        txtNama = createTextField();
        txtHarga = createTextField();
        txtStok = createTextField();

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        addField(fields, gc, 0, "Nama Produk", txtNama, 2.0);
        addField(fields, gc, 1, "Harga (Rp)", txtHarga, 1.0);
        addField(fields, gc, 2, "Stok", txtStok, 1.0);

        btnSimpan = new RoundedButton("Simpan", GREEN);
        btnUbah = new RoundedButton("Ubah", BLUE);
        btnHapus = new RoundedButton("Hapus", RED);
        btnReset = new RoundedButton("Reset", GRAY_BTN);

        btnSimpan.addActionListener(e -> simpanProduk());
        btnUbah.addActionListener(e -> ubahProduk());
        btnHapus.addActionListener(e -> hapusProduk());
        btnReset.addActionListener(e -> resetForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(btnSimpan);
        buttons.add(btnUbah);
        buttons.add(btnHapus);
        buttons.add(btnReset);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(fields, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);

        card.add(center, BorderLayout.CENTER);
        return card;
    }

    private void addField(JPanel panel, GridBagConstraints gc, int col,
            String labelText, JTextField field, double weight) {
        JLabel label = new JLabel(labelText);
        label.setFont(FONT_BOLD);
        label.setForeground(TEXT_DARK);

        int right = (col < 2) ? 14 : 0;

        gc.gridx = col;
        gc.gridy = 0;
        gc.weightx = weight;
        gc.insets = new Insets(0, 0, 6, right);
        panel.add(label, gc);

        gc.gridy = 1;
        gc.insets = new Insets(0, 0, 0, right);
        panel.add(field, gc);
    }

    private JPanel buildTablePanel() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD);
        card.setBorder(cardBorder());

        JLabel title = new JLabel("Daftar Produk");
        title.setFont(FONT_CARD);
        title.setForeground(TEXT_DARK);

        lblTotal = new JLabel("Total: 0 produk");
        lblTotal.setFont(FONT_BASE);
        lblTotal.setForeground(TEXT_MUTED);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title, BorderLayout.WEST);
        top.add(lblTotal, BorderLayout.EAST);
        card.add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID Produk", "Nama Produk", "Harga", "Stok"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabel = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer,
                    int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(GREEN_LIGHT);
                    c.setForeground(TEXT_DARK);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ROW_STRIPE);
                    c.setForeground(TEXT_DARK);
                }
                return c;
            }
        };
        styleTable();

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    private void styleTable() {
        tabel.setFont(FONT_BASE);
        tabel.setRowHeight(34);
        tabel.setShowVerticalLines(false);
        tabel.setShowHorizontalLines(true);
        tabel.setGridColor(new Color(0xECEFF1));
        tabel.setIntercellSpacing(new Dimension(0, 1));
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.setFillsViewportHeight(true);
        tabel.setAutoCreateRowSorter(true);

        // Header tabel
        JTableHeader th = tabel.getTableHeader();
        th.setReorderingAllowed(false);
        th.setPreferredSize(new Dimension(0, 38));
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, false, false, row, column);
                setBackground(GREEN);
                setForeground(Color.WHITE);
                setFont(FONT_BOLD);
                setHorizontalAlignment(column == 1 ? SwingConstants.LEFT : SwingConstants.CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return this;
            }
        };
        th.setDefaultRenderer(headerRenderer);

        // Renderer sel
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        DefaultTableCellRenderer hargaRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText("Rp " + rupiah.format(((Number) value).doubleValue()));
                } else {
                    super.setValue(value);
                }
            }
        };
        hargaRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        hargaRenderer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 16));

        TableColumnModel cm = tabel.getColumnModel();
        cm.getColumn(0).setCellRenderer(center);
        cm.getColumn(1).setCellRenderer(left);
        cm.getColumn(2).setCellRenderer(hargaRenderer);
        cm.getColumn(3).setCellRenderer(center);

        cm.getColumn(0).setPreferredWidth(90);
        cm.getColumn(1).setPreferredWidth(350);
        cm.getColumn(2).setPreferredWidth(160);
        cm.getColumn(3).setPreferredWidth(80);
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BASE);
        tf.setForeground(TEXT_DARK);
        tf.setPreferredSize(new Dimension(100, 40));
        tf.setBorder(fieldBorder(BORDER));
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tf.setBorder(fieldBorder(GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                tf.setBorder(fieldBorder(BORDER));
            }
        });
        return tf;
    }

    private Border fieldBorder(Color color) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10));
    }

    private Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(16, 20, 18, 20));
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Produk> daftar = controller.getAllProduk();
            if (daftar != null) {
                for (Produk p : daftar) {
                    tableModel.addRow(new Object[]{
                        p.getIdProduk(),
                        p.getNamaProduk(),
                        p.getHarga(),
                        p.getStok()
                    });
                }
            }
            lblTotal.setText("Total: " + tableModel.getRowCount() + " produk");
        } catch (Exception ex) {
            lblTotal.setText("Total: 0 produk");
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data produk:\n" + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void isiFormDariTabel() {
        int viewRow = tabel.getSelectedRow();
        if (viewRow < 0) {
            return;
        }
        int row = tabel.convertRowIndexToModel(viewRow);

        selectedId = ((Number) tableModel.getValueAt(row, 0)).intValue();
        txtNama.setText(String.valueOf(tableModel.getValueAt(row, 1)));

        double harga = ((Number) tableModel.getValueAt(row, 2)).doubleValue();
        if (harga == Math.rint(harga)) {
            txtHarga.setText(String.valueOf((long) harga));
        } else {
            txtHarga.setText(String.valueOf(harga));
        }
        txtStok.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }

    private void simpanProduk() {
        // ID diberi 0 karena diasumsikan di-generate otomatis oleh database.
        Produk produk = bacaInput(0);
        if (produk == null) {
            return;
        }
        try {
            if (controller.tambahProduk(produk)) {
                JOptionPane.showMessageDialog(this, "Produk berhasil disimpan.",
                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Produk gagal disimpan.",
                        "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            tampilkanError("menyimpan", ex);
        }
    }

    private void ubahProduk() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih produk pada tabel terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Produk produk = bacaInput(selectedId);
        if (produk == null) {
            return;
        }
        try {
            if (controller.updateProduk(produk)) {
                JOptionPane.showMessageDialog(this, "Produk berhasil diperbarui.",
                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Produk gagal diperbarui.",
                        "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            tampilkanError("memperbarui", ex);
        }
    }

    private void hapusProduk() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih produk yang akan dihapus terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus produk \"" + txtNama.getText().trim() + "\"?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (controller.hapusProduk(selectedId)) {
                JOptionPane.showMessageDialog(this, "Produk berhasil dihapus.",
                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Produk gagal dihapus.",
                        "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            tampilkanError("menghapus", ex);
        }
    }

    private void resetForm() {
        selectedId = -1;
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        tabel.clearSelection();
        txtNama.requestFocusInWindow();
    }

    private Produk bacaInput(int id) {
        String nama = txtNama.getText().trim();
        String hargaText = txtHarga.getText().trim();
        String stokText = txtStok.getText().trim();

        if (nama.isEmpty()) {
            peringatan("Nama produk tidak boleh kosong.", txtNama);
            return null;
        }

        double harga;
        try {
            harga = Double.parseDouble(hargaText);
        } catch (NumberFormatException ex) {
            peringatan("Harga harus berupa angka (contoh: 15000).", txtHarga);
            return null;
        }
        if (Double.isNaN(harga) || Double.isInfinite(harga) || harga <= 0) {
            peringatan("Harga harus berupa angka dan lebih besar dari 0.", txtHarga);
            return null;
        }

        int stok;
        try {
            stok = Integer.parseInt(stokText);
        } catch (NumberFormatException ex) {
            peringatan("Stok harus berupa bilangan bulat (contoh: 10).", txtStok);
            return null;
        }
        if (stok < 0) {
            peringatan("Stok tidak boleh negatif.", txtStok);
            return null;
        }

        return new Produk(id, nama, harga, stok);
    }

    private void peringatan(String pesan, JTextField fokus) {
        JOptionPane.showMessageDialog(this, pesan, "Validasi", JOptionPane.WARNING_MESSAGE);
        fokus.requestFocusInWindow();
    }

    private void tampilkanError(String aksi, Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Terjadi kesalahan saat " + aksi + " produk:\n" + ex.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    private static class BoxLayoutY extends javax.swing.BoxLayout {

        BoxLayoutY(java.awt.Container target) {
            super(target, javax.swing.BoxLayout.Y_AXIS);
        }
    }

    private static class RoundedButton extends JButton {

        private final Color base;
        private boolean hovering = false;

        RoundedButton(String text, Color base) {
            super(text);
            this.base = base;
            setFont(FONT_BOLD);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(112, 40));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovering = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fill = base;
            if (getModel().isPressed()) {
                fill = base.darker().darker();
            } else if (hovering) {
                fill = base.darker();
            }
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProdukView().setVisible(true));
    }
}
