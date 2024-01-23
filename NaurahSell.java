import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NaurahSell extends JFrame {
  private JTextArea displayArea;
  private JComboBox<String> categoryComboBox;
  private JComboBox<String> productComboBox;
  private JTextField quantityField;
  private JButton buyButton;
  private JLabel saldoLabel;
  private JLabel totalLabel; // Tambahkan label untuk menampilkan total

  // +-------------------------------+
  // | STRUKTUR DATASET PRODUK       |
  // +-------------------------------+
  // | Nama        | Harga    | Stok |
  // +-------------+----------+------+
  // | Mie Ayam    | 10000    | 12   |
  // | Ayam Goreng | 15000    | 20   |
  // | Nasi Goreng | 18000    | 10   |
  // +-------------+----------+------+

  // Data Makanan
  private Object[][] makananData = {
      { "Mie Ayam", 10000, 12 },
      { "Nasi Goreng", 15000, 20 },
      { "Ayam Goreng", 15000, 20 }
  };

  // Data Snack
  private Object[][] snackData = {
      { "Keripik Singkong", 1500, 13 },
      { "Makaroni", 1000, 20 },
      { "Wafer", 2000, 20 }
  };

  // Data Minuman
  private Object[][] minumanData = {
      { "Air Putih", 4000, 13 },
      { "Es Teh", 2000, 20 },
      { "Kopi", 3000, 20 }
  };

  private int saldo = 100000;
  private int total = 0; // Tambahkan variabel total

  public NaurahSell() {
    initComponents();
  }

  private void initComponents() {
    setTitle("Program Kantin");
    setSize(400, 350); // Tambahkan sedikit tinggi untuk menampung totalLabel
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    add(panel);
    placeComponents(panel);

    displayArea.setEditable(false);
  }

  private void placeComponents(JPanel panel) {
    panel.setLayout(null);

    saldoLabel = new JLabel("Saldo: Rp " + saldo);
    saldoLabel.setBounds(10, 20, 200, 20);
    panel.add(saldoLabel);

    displayArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(displayArea);
    scrollPane.setBounds(10, 50, 360, 100);
    panel.add(scrollPane);

    JLabel categoryLabel = new JLabel("Kategori:");
    categoryLabel.setBounds(10, 170, 80, 20);
    panel.add(categoryLabel);

    String[] categories = { "Makanan", "Snack", "Minuman" };
    categoryComboBox = new JComboBox<>(categories);
    categoryComboBox.setBounds(100, 170, 120, 20);
    panel.add(categoryComboBox);

    JLabel productLabel = new JLabel("Produk:");
    productLabel.setBounds(10, 200, 80, 20);
    panel.add(productLabel);

    productComboBox = new JComboBox<>();
    productComboBox.setBounds(100, 200, 120, 20);
    panel.add(productComboBox);

    JLabel quantityLabel = new JLabel("Jumlah:");
    quantityLabel.setBounds(10, 230, 80, 20);
    panel.add(quantityLabel);

    quantityField = new JTextField();
    quantityField.setBounds(100, 230, 50, 20);
    panel.add(quantityField);

    // Label untuk menampilkan total
    totalLabel = new JLabel("Total: Rp 0");
    totalLabel.setBounds(10, 260, 200, 20);
    panel.add(totalLabel);

    buyButton = new JButton("Beli");
    buyButton.setBounds(160, 230, 80, 20);
    panel.add(buyButton);

    // Menambahkan listener untuk JComboBox categoryComboBox
    categoryComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateProductComboBox();
      }
    });

    // Menambahkan listener untuk JComboBox productComboBox
    productComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        displayProductInfo();
      }
    });

    // Menambahkan listener untuk JTextField quantityField
    quantityField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateTotal();
      }
    });

    // Menambahkan listener untuk JButton buyButton
    buyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        buyProduct();
      }
    });

    // Menampilkan informasi harga dan stok produk yang dipilih awal
    updateProductComboBox();
  }

  private void updateProductComboBox() {
    int categoryIndex = categoryComboBox.getSelectedIndex();

    if (categoryIndex == 0) {
      fillProductComboBox(makananData);
    } else if (categoryIndex == 1) {
      fillProductComboBox(snackData);
    } else if (categoryIndex == 2) {
      fillProductComboBox(minumanData);
    }

    // Tampilkan informasi harga dan stok produk yang dipilih
    displayProductInfo();
  }

  private void fillProductComboBox(Object[][] data) {
    productComboBox.removeAllItems();

    for (int i = 0; i < data.length; i++) {
      productComboBox.addItem(data[i][0].toString());
    }
  }

  private void displayProductInfo() {
    int categoryIndex = categoryComboBox.getSelectedIndex();
    int productIndex = productComboBox.getSelectedIndex();
    Object[][] data = null;

    if (categoryIndex == 0) {
      data = makananData;
    } else if (categoryIndex == 1) {
      data = snackData;
    } else if (categoryIndex == 2) {
      data = minumanData;
    }

    if (data != null && productIndex >= 0 && productIndex < data.length) {
      int harga = (int) data[productIndex][1];
      int stok = (int) data[productIndex][2];

      displayArea.setText("Harga: Rp " + harga + "\nStok Tersedia: " + stok);

      // Update totalLabel saat memilih produk
      updateTotal();
    }
  }

  private void updateTotal() {
    try {
      int quantity = Integer.parseInt(quantityField.getText());
      int productIndex = productComboBox.getSelectedIndex();
      Object[][] data = null;

      int categoryIndex = categoryComboBox.getSelectedIndex();
      if (categoryIndex == 0) {
        data = makananData;
      } else if (categoryIndex == 1) {
        data = snackData;
      } else if (categoryIndex == 2) {
        data = minumanData;
      }

      if (data != null && productIndex >= 0 && productIndex < data.length) {
        int harga = (int) data[productIndex][1];

        total = harga * quantity;
        totalLabel.setText("Total: Rp " + total);
      } else {
        totalLabel.setText("Total: Rp 0");
      }
    } catch (NumberFormatException e) {
      // Handle jika input tidak valid
      totalLabel.setText("Total: Rp 0");
    }
  }

  private void buyProduct() {
    int categoryIndex = categoryComboBox.getSelectedIndex();
    int productIndex = productComboBox.getSelectedIndex();

    if (categoryIndex == 0) {
      processTransaction(makananData, productIndex);
    } else if (categoryIndex == 1) {
      processTransaction(snackData, productIndex);
    } else if (categoryIndex == 2) {
      processTransaction(minumanData, productIndex);
    }

    // Update saldoLabel setelah transaksi
    updateSaldoLabel();

    // Reset totalLabel setelah pembelian
    total = 0;
    totalLabel.setText("Total: Rp 0");
  }

  private void processTransaction(Object[][] data, int productIndex) {
    int quantity;

    try {
      quantity = Integer.parseInt(quantityField.getText());
    } catch (NumberFormatException e) {
      displayArea.setText("Error: Jumlah tidak valid");
      return;
    }

    if (quantity <= 0) {
      displayArea.setText("Error: Jumlah harus lebih dari 0");
      return;
    }

    if (productIndex < 0 || productIndex >= data.length) {
      displayArea.setText("Error: Produk tidak valid");
      return;
    }

    int harga = (int) data[productIndex][1];
    int stok = (int) data[productIndex][2];

    if (stok >= quantity && saldo >= (harga * quantity)) {
      stok -= quantity;
      saldo -= (harga * quantity);
      data[productIndex][2] = stok;

      displayArea.setText("Pembelian sukses!\n" +
          "Produk: " + data[productIndex][0] + "\n" +
          "Harga: Rp " + harga + "\n" +
          "Jumlah: " + quantity + "\n" +
          "Total: Rp " + (harga * quantity) + "\n" +
          "Sisa Saldo: Rp " + saldo);

    } else if (stok < quantity) {
      displayArea.setText("Error: Stok tidak mencukupi");
    } else {
      displayArea.setText("Error: Saldo tidak mencukupi");
    }

    // Update saldoLabel setelah transaksi
    updateSaldoLabel();
  }

  private void updateSaldoLabel() {
    saldoLabel.setText("Saldo: Rp " + saldo);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      KantinProgram kantinProgram = new KantinProgram();
      kantinProgram.setVisible(true);
    });
  }
}
