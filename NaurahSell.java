import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NaurahSell {
    private JTable makananTable;
    private JTable snackTable;
    private JTable minumanTable;
    private JLabel saldoLabel;
    private JButton beliButton;
    private JFrame frame;

    private int saldo = 100000;

    private Object[][] makananData = {{"Mie Ayam", 10000, 12}, {"Nasi Goreng", 15000, 20}, {"Ayam Goreng", 15000, 20}};
    private Object[][] snackData = {{"Keripik Singkong", 1500, 13}, {"Makaroni", 1000, 20}, {"Wafer", 2000, 20}};
    private Object[][] minumanData = {{"Air Putih", 4000, 13}, {"Es Teh", 2000, 20}, {"Kopi", 3000, 20}};

    public static void main(String[] args) {
        NaurahSell app = new NaurahSell();
        app.createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Kantin App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 1));

        // Makanan Panel
        JPanel makananPanel = new JPanel();
        makananPanel.setLayout(new BorderLayout());

        JLabel makananLabel = new JLabel("Makanan");
        makananPanel.add(makananLabel, BorderLayout.NORTH);

        String[] makananColumnNames = {"Nama", "Harga", "Stok"};
        DefaultTableModel makananModel = new DefaultTableModel(makananData, makananColumnNames);
        makananTable = new JTable(makananModel);

        JScrollPane makananScrollPane = new JScrollPane(makananTable);
        makananPanel.add(makananScrollPane, BorderLayout.CENTER);

        container.add(makananPanel);

        // Snack Panel
        JPanel snackPanel = new JPanel();
        snackPanel.setLayout(new BorderLayout());

        JLabel snackLabel = new JLabel("Snack");
        snackPanel.add(snackLabel, BorderLayout.NORTH);

        String[] snackColumnNames = {"Nama", "Harga", "Stok"};
        DefaultTableModel snackModel = new DefaultTableModel(snackData, snackColumnNames);
        snackTable = new JTable(snackModel);

        JScrollPane snackScrollPane = new JScrollPane(snackTable);
        snackPanel.add(snackScrollPane, BorderLayout.CENTER);

        container.add(snackPanel);

        // Minuman Panel
        JPanel minumanPanel = new JPanel();
        minumanPanel.setLayout(new BorderLayout());

        JLabel minumanLabel = new JLabel("Minuman");
        minumanPanel.add(minumanLabel, BorderLayout.NORTH);

        String[] minumanColumnNames = {"Nama", "Harga", "Stok"};
        DefaultTableModel minumanModel = new DefaultTableModel(minumanData, minumanColumnNames);
        minumanTable = new JTable(minumanModel);

        JScrollPane minumanScrollPane = new JScrollPane(minumanTable);
        minumanPanel.add(minumanScrollPane, BorderLayout.CENTER);

        container.add(minumanPanel);

        // Saldo dan Beli Panel
        JPanel saldoBeliPanel = new JPanel();
        saldoBeliPanel.setLayout(new GridLayout(2, 1));

        saldoLabel = new JLabel("Saldo: Rp" + saldo);
        saldoBeliPanel.add(saldoLabel);

        beliButton = new JButton("Beli");
        beliButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowMakanan = makananTable.getSelectedRow();
            int selectedRowSnack = snackTable.getSelectedRow();
            int selectedRowMinuman = minumanTable.getSelectedRow();
    
            if (selectedRowMakanan == -1 && selectedRowSnack == -1 && selectedRowMinuman == -1) {
                JOptionPane.showMessageDialog(frame, "Harap pilih makanan, snack, atau minuman yang akan dibeli!");
                return;
            }
    
            int makananHarga = 0;
            int snackHarga = 0;
            int minumanHarga = 0;
    
            if (selectedRowMakanan != -1) {
                makananHarga = (int) makananData[selectedRowMakanan][1];
            }
            if (selectedRowSnack != -1) {
                snackHarga = (int) snackData[selectedRowSnack][1];
            }
            if (selectedRowMinuman != -1) {
                minumanHarga = (int) minumanData[selectedRowMinuman][1];
            }
    
            int totalHarga = makananHarga + snackHarga + minumanHarga;
    
            if (totalHarga > saldo) {
                JOptionPane.showMessageDialog(frame, "Saldo tidak cukup untuk melakukan pembelian ini!");
                return;
            }
    
            saldo -= totalHarga;
            saldoLabel.setText("Saldo: Rp" + saldo);
    
            // Mengurangi stok dan memberi notifikasi pembelian berhasil
            updateStok(selectedRowMakanan, selectedRowSnack, selectedRowMinuman);
            
            // Menambahkan parameter yang diperlukan untuk showPembelianNotifikasi
            showPembelianNotifikasi(makananHarga, snackHarga, minumanHarga, totalHarga,
                                    selectedRowMakanan, selectedRowSnack, selectedRowMinuman);
    
            // Mengupdate tampilan tabel setelah pembelian
            makananModel.fireTableDataChanged();
            snackModel.fireTableDataChanged();
            minumanModel.fireTableDataChanged();
        }            
        });

        saldoBeliPanel.add(beliButton);
        container.add(saldoBeliPanel);

        frame.setVisible(true);
    }

    private void updateStok(int selectedRowMakanan, int selectedRowSnack, int selectedRowMinuman) {
        if (selectedRowMakanan != -1) {
            updateStokItem(makananData, selectedRowMakanan);
        }
        if (selectedRowSnack != -1) {
            updateStokItem(snackData, selectedRowSnack);
        }
        if (selectedRowMinuman != -1) {
            updateStokItem(minumanData, selectedRowMinuman);
        }
    }

    private void updateStokItem(Object[][] data, int selectedRow) {
        int stok = (int) data[selectedRow][2];
        data[selectedRow][2] = stok - 1;
    }

    private void showPembelianNotifikasi(int makananHarga, int snackHarga, int minumanHarga, int totalHarga,
                                        int selectedRowMakanan, int selectedRowSnack, int selectedRowMinuman) {

        String makananName = "";
        String snackName = "";
        String minumanName = "";

        if (makananHarga > 0 && selectedRowMakanan != -1) {
            makananName = makananData[selectedRowMakanan][0].toString();
        }
        if (snackHarga > 0 && selectedRowSnack != -1) {
            snackName = snackData[selectedRowSnack][0].toString();
        }
        if (minumanHarga > 0 && selectedRowMinuman != -1) {
            minumanName = minumanData[selectedRowMinuman][0].toString();
        }

        int makananStok = 0;
        int snackStok = 0;
        int minumanStok = 0;

        if (selectedRowMakanan != -1) {
            makananStok = (int) makananData[selectedRowMakanan][2];
        }
        if (selectedRowSnack != -1) {
            snackStok = (int) snackData[selectedRowSnack][2];
        }
        if (selectedRowMinuman != -1) {
            minumanStok = (int) minumanData[selectedRowMinuman][2];
        }

        JOptionPane.showMessageDialog(frame, "Pembelian berhasil!\n" +
                "Makanan: " + makananName + ", Harga: Rp" + makananHarga + ", Stok: " + (makananStok - 1) + "\n" +
                "Snack: " + snackName + ", Harga: Rp" + snackHarga + ", Stok: " + (snackStok - 1) + "\n" +
                "Minuman: " + minumanName + ", Harga: Rp" + minumanHarga + ", Stok: " + (minumanStok - 1) + "\n" +
                "Total Harga: Rp" + totalHarga);
    }


    
}
