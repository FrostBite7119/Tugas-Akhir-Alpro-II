
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class LihatData extends javax.swing.JFrame {
    public Connection conn;
    public ResultSet rs;
    public Statement stm;
    String sql;
    String nrp;
    /**
     * Creates new form LihatData
     */
    public LihatData() {
        initComponents();
        koneksi DB = new koneksi();
        DB.config();
        conn = DB.con;
        stm = DB.stm;
        updatetabeljadwalmhs();
        updateTabelTranskrip();
    }
    
    public void setData(String nrp){
        this.nrp = nrp;
        if(!"".equals(this.nrp)){
            set();
        }else{
            JOptionPane.showMessageDialog(null, "NRP kosong");
        }
    }
    
    private void set(){
        setInformasiMahasiswa();
        setJadwal();
        setTranskrip();
    }
    
    private void setInformasiMahasiswa(){
        try{
            rs = stm.executeQuery("SELECT * FROM mahasiswa WHERE NRP = '"+nrp+"'");
            rs.next();
            tfNrp.setText(rs.getString("NRP"));
            tfNama.setText(rs.getString("NAMA_MAHASISWA"));
            tfProdi.setText(rs.getString("PRODI"));
            tfStatus.setText(rs.getString("STATUS_MASUK"));
            tfJk.setText(rs.getString("JENIS_KELAMIN"));
            tfAgama.setText(rs.getString("AGAMA"));
            tfAlamat.setText(rs.getString("ALAMAT"));
            tfEmail.setText(rs.getString("EMAIL"));
            tfNoHp.setText(rs.getString("NO_HP"));
            tfNamaAyah.setText(rs.getString("NAMA_AYAH"));
            tfNoKtpAyah.setText(rs.getString("NOMOR_KTP_AYAH"));
            tfIbu.setText(rs.getString("NAMA_IBU"));
            tfOrtu.setText(rs.getString("TELEPON_ORANG_TUA"));
            tfAlamatOrtu.setText(rs.getString("ALAMAT_ORANG_TUA"));
            BufferedImage img = ImageIO.read(new File(rs.getString("link_foto")));
            Image resizedImage = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            lbl_image.setText("");
            lbl_image.setIcon(icon);
        }catch(SQLException | IOException e){
            JOptionPane.showMessageDialog(null, e);
        }    
    }
    
    private void setJadwal(){
        try {
            rs = stm.executeQuery("SELECT * FROM mahasiswa WHERE nrp = '"+nrp+"'");
            rs.next();
            tfNrpNama.setText(rs.getString("NRP")+" - "+rs.getString("NAMA_MAHASISWA"));
            rs = stm.executeQuery("SELECT matakuliah.KODE_MATA_KULIAH, matakuliah.NAMA_MATA_KULIAH, "
                    + "kelas.KELAS, kelas.WAKTU, dosen.NAMA_DOSEN FROM mengambil INNER JOIN mahasiswa "
                    + "ON mengambil.NRP = mahasiswa.NRP INNER JOIN matakuliah ON "
                    + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH INNER JOIN dosen ON "
                    + "matakuliah.NIP_DOSEN = dosen.NIP_DOSEN INNER JOIN kelas ON "
                    + "matakuliah.ID_KELAS = kelas.ID_KELAS WHERE mengambil.NRP = '"+nrp+"'");
            
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kode Matakuliah");
            model.addColumn("Nama Mata Kuliah");
            model.addColumn("Kelas");
            model.addColumn("Waktu");
            model.addColumn("Pengajar");
            tabeljadwal.setModel(model);
            
            while(rs.next()){
                Object[] data = new Object[5];
                data[0] = rs.getString("KODE_MATA_KULIAH");
                data[1] = rs.getString("NAMA_MATA_KULIAH");
                data[2] = rs.getString("KELAS");
                data[3] = rs.getString("WAKTU");
                data[4] = rs.getString("NAMA_DOSEN");
                model.addRow(data);
                tabeljadwal.setModel(model);
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setTranskrip(){
        try {
            rs = stm.executeQuery("SELECT * FROM mahasiswa WHERE nrp = '"+nrp+"'");
            rs.next();
            lbJenjang.setText(rs.getString("jenjang"));
            lbProdiTranskrip.setText(rs.getString("PRODI"));
            lbNamaTranskrip.setText(rs.getString("NAMA_MAHASISWA"));
            lbNomorInduk.setText(rs.getString("NRP"));

            rs = stm.executeQuery("SELECT matakuliah.KODE_MATA_KULIAH, matakuliah.NAMA_MATA_KULIAH, "
                    + "matakuliah.sks, transkrip.nilai FROM mengambil INNER JOIN transkrip ON "
                    + "mengambil.id_ambil_matkul = transkrip.id_ambil_matkul INNER JOIN matakuliah ON "
                    + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH INNER JOIN kelas ON "
                    + "matakuliah.ID_KELAS = kelas.ID_KELAS INNER JOIN dosen ON matakuliah.NIP_DOSEN = "
                    + "dosen.NIP_DOSEN WHERE mengambil.NRP = '"+nrp+"';");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No");
            model.addColumn("Kode Mata Kuliah");
            model.addColumn("Nama Mata Kuliah");
            model.addColumn("SKS");
            model.addColumn("Nilai");
            model.addColumn("Index");
            model.addColumn("NxK");
            tabelTranskrip.setModel(model);

            int no = 1;
            while(rs.next()){
                Object[] data = new Object[7];
                data[0] = no;
                data[1] = rs.getString("KODE_MATA_KULIAH");
                data[2] = rs.getString("NAMA_MATA_KULIAH");
                data[3] = rs.getString("sks");
                if(Double.parseDouble(rs.getString("nilai")) == 4){
                        data[4] = "A";
                    }else if(Double.parseDouble(rs.getString("nilai")) >= 3.5){
                        data[4] = "B+";
                    }else if(Double.parseDouble(rs.getString("nilai")) >= 3){
                        data[4] = "B";
                    }else if(Double.parseDouble(rs.getString("nilai")) >= 2){
                        data[4] = "C";
                    }else if(Double.parseDouble(rs.getString("nilai")) >= 1){
                        data[4] = "D";
                    }else{
                        data[4] = "E";
                    }
                    data[5] = rs.getString("nilai");
                    double nxk = Double.parseDouble(rs.getString("nilai")) * Double.parseDouble(rs.getString("sks"));
                    data[6] = String.format(".%1f", nxk);
                    model.addRow(data);
                    tabeljadwal.setModel(model);
                    no++;
                }
                
                //Total Ambil SKS
                rs = stm.executeQuery("SELECT SUM(matakuliah.sks) FROM mengambil INNER JOIN matakuliah ON "
                        + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH WHERE "
                        + "mengambil.NRP='"+nrp+"'");
                rs.next();
                int totalAmbilSks = rs.getInt(1);
                lbTotalSks.setText(Integer.toString(totalAmbilSks));
                
                //Total SKS Lulus
                Statement stat = conn.createStatement();
                rs = stat.executeQuery("SELECT SUM(matakuliah.sks) FROM mengambil INNER JOIN matakuliah ON "
                        + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH INNER JOIN transkrip "
                        + "ON mengambil.id_ambil_matkul = transkrip.id_ambil_matkul "
                        + "WHERE mengambil.NRP = '"+nrp+"' AND transkrip.nilai >= 2");
                
                int totalLulus = 0;
                rs.next();
                totalLulus =  rs.getInt(1);
                lbLulusSks.setText(Integer.toString(totalLulus));
                
                //Total Nilai
                Statement stat2 = conn.createStatement();
                rs = stat2.executeQuery("SELECT matakuliah.sks, transkrip.nilai FROM mengambil INNER JOIN "
                        + "matakuliah ON mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH "
                        + "INNER JOIN transkrip ON mengambil.id_ambil_matkul = transkrip.id_ambil_matkul "
                        + "WHERE mengambil.NRP = '"+nrp+"'");
                double totalNilai = 0;
                while(rs.next()){
                    totalNilai += Double.parseDouble(rs.getString("sks")) * Double.parseDouble(rs.getString("nilai"));
                }
                lbTotalNilai.setText(String.valueOf(totalNilai));
                
                // IPK
                Statement stat3 = conn.createStatement();
                rs = stat3.executeQuery("SELECT * FROM mahasiswa WHERE NRP = '"+nrp+"'");
                rs.next();
                double ipk = Double.parseDouble(rs.getString("ipk"));
                lbIpk.setText(String.format("%.2f", ipk));
                
                //Total SKS D
                Statement stat4 = conn.createStatement();
                rs = stat4.executeQuery("SELECT SUM(matakuliah.sks) FROM mengambil INNER JOIN matakuliah ON "
                        + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH INNER JOIN transkrip "
                        + "ON mengambil.id_ambil_matkul = transkrip.id_ambil_matkul "
                        + "WHERE mengambil.NRP = '"+nrp+"' AND transkrip.nilai < 2 AND transkrip.nilai >= 1");
                rs.next();
                int totalAmbilSksD = rs.getInt(1);
                lbSksD.setText(Integer.toString(totalAmbilSksD));
                
                //Total SKS E
                Statement stat5 = conn.createStatement();
                rs = stat5.executeQuery("SELECT SUM(matakuliah.sks) FROM mengambil INNER JOIN matakuliah ON "
                        + "mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH INNER JOIN transkrip "
                        + "ON mengambil.id_ambil_matkul = transkrip.id_ambil_matkul "
                        + "WHERE mengambil.NRP = '"+nrp+"' AND transkrip.nilai < 1");
                rs.next();
                int totalAmbilSksE = rs.getInt(1);
                lbSksE.setText(Integer.toString(totalAmbilSksE));
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }    
    }
    
    private void updatetabeljadwalmhs(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Kelas");
        model.addColumn("Nama Mata Kuliah");
        model.addColumn("Kelas");
        model.addColumn("Waktu");
        model.addColumn("Pengajar");
        tabeljadwal.setModel(model);
    }
    
    private void updateTabelTranskrip(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Kode Mata Kuliah");
        model.addColumn("Nama Mata Kuliah");
        model.addColumn("SKS");
        model.addColumn("Nilai");
        model.addColumn("Index");
        model.addColumn("NxK");
        tabelTranskrip.setModel(model);
    }

    private void clearPassword(){
        tfPasswordLama.setText("");
        tfPasswordBaru.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentPanel = new javax.swing.JPanel();
        panelMahasiswa = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tfNrp = new javax.swing.JLabel();
        tfNama = new javax.swing.JLabel();
        tfProdi = new javax.swing.JLabel();
        tfStatus = new javax.swing.JLabel();
        tfJk = new javax.swing.JLabel();
        tfAgama = new javax.swing.JLabel();
        tfAlamat = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JLabel();
        tfNoHp = new javax.swing.JLabel();
        tfNamaAyah = new javax.swing.JLabel();
        tfNoKtpAyah = new javax.swing.JLabel();
        tfIbu = new javax.swing.JLabel();
        tfOrtu = new javax.swing.JLabel();
        tfAlamatOrtu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfPasswordBaru = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tfPasswordLama = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        panelJadwal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfNrpNama = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeljadwal = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        panelTranskrip = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbJenjang = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbProdiTranskrip = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbNamaTranskrip = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbNomorInduk = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTranskrip = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        lbTotalSks = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lbLulusSks = new javax.swing.JLabel();
        lbTotalNilai = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lbIpk = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lbSksD = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lbSksE = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuJadwal = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parentPanel.setLayout(new java.awt.CardLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Informasi Mahasiswa");

        jLabel7.setText("NRP");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_image.setText("Foto Diri");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jLabel8.setText("Nama");

        jLabel9.setText("Prodi");

        jLabel10.setText("Status Masuk");

        jLabel11.setText("Jenis Kelamin");

        jLabel12.setText("Agama");

        jLabel13.setText("Alamat");

        tfNrp.setText("Data");

        tfNama.setText("Data");

        tfProdi.setText("Data");

        tfStatus.setText("Data");

        tfJk.setText("Data");

        tfAgama.setText("Data");

        tfAlamat.setText("Data");

        jLabel21.setText("Email");

        jLabel22.setText("No HP");

        jLabel23.setText("Nama Ayah");

        jLabel24.setText("No KTP Ayah");

        jLabel25.setText("Nama Ibu");

        jLabel26.setText("Telepon Orang Tua");

        jLabel27.setText("Alamat Orang Tua");

        tfEmail.setText("Data");

        tfNoHp.setText("Data");

        tfNamaAyah.setText("Data");

        tfNoKtpAyah.setText("Data");

        tfIbu.setText("Data");

        tfOrtu.setText("Data");

        tfAlamatOrtu.setText("Data");

        jLabel2.setText("Ubah Password");

        jLabel5.setText("Password Baru");

        jButton1.setText("Perbarui");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Password Lama");

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMahasiswaLayout = new javax.swing.GroupLayout(panelMahasiswa);
        panelMahasiswa.setLayout(panelMahasiswaLayout);
        panelMahasiswaLayout.setHorizontalGroup(
            panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(75, 75, 75)
                                        .addComponent(tfNrp))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(68, 68, 68)
                                        .addComponent(tfNama))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(71, 71, 71)
                                        .addComponent(tfProdi))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(31, 31, 31)
                                        .addComponent(tfStatus))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(32, 32, 32)
                                        .addComponent(tfJk))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(62, 62, 62)
                                        .addComponent(tfAgama))
                                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(62, 62, 62)
                                        .addComponent(tfAlamat)))
                                .addGap(29, 29, 29)
                                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27))
                                .addGap(28, 28, 28)
                                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfAlamatOrtu)
                                    .addComponent(tfOrtu)
                                    .addComponent(tfIbu)
                                    .addComponent(tfNoKtpAyah)
                                    .addComponent(tfNamaAyah)
                                    .addComponent(tfNoHp)
                                    .addComponent(tfEmail)))
                            .addComponent(jLabel5)
                            .addGroup(panelMahasiswaLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(149, 149, 149)
                                .addComponent(jButton2))
                            .addComponent(jLabel15)
                            .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfPasswordLama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addComponent(tfPasswordBaru, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        panelMahasiswaLayout.setVerticalGroup(
            panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(62, 62, 62)
                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMahasiswaLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tfNrp)
                            .addComponent(jLabel21)
                            .addComponent(tfEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tfNama)
                            .addComponent(jLabel22)
                            .addComponent(tfNoHp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tfProdi)
                            .addComponent(jLabel23)
                            .addComponent(tfNamaAyah))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tfStatus)
                            .addComponent(jLabel24)
                            .addComponent(tfNoKtpAyah))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tfJk)
                            .addComponent(jLabel25)
                            .addComponent(tfIbu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tfAgama)
                            .addComponent(jLabel26)
                            .addComponent(tfOrtu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(tfAlamat)
                            .addComponent(jLabel27)
                            .addComponent(tfAlamatOrtu))))
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfPasswordLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfPasswordBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        parentPanel.add(panelMahasiswa, "panelMahasiswa");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Jadwal Mahasiswa");

        jLabel3.setText("Menampilkan Jadwal:");

        tabeljadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabeljadwal);

        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelJadwalLayout = new javax.swing.GroupLayout(panelJadwal);
        panelJadwal.setLayout(panelJadwalLayout);
        panelJadwalLayout.setHorizontalGroup(
            panelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJadwalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(panelJadwalLayout.createSequentialGroup()
                        .addGroup(panelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(panelJadwalLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfNrpNama)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(23, 23, 23)))
                .addContainerGap())
        );
        panelJadwalLayout.setVerticalGroup(
            panelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJadwalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(73, 73, 73)
                .addGroup(panelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfNrpNama)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        parentPanel.add(panelJadwal, "panelJadwal");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Transkrip Akademik");

        jLabel14.setText("Jenjang Pendidikan");

        jLabel16.setText("Program Studi");

        jLabel18.setText("Nama");

        jLabel20.setText("Nomor Induk");

        tabelTranskrip.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelTranskrip);

        jLabel29.setText("Total Ambil SKS");

        jLabel31.setText("Total SKS Lulus");

        jLabel34.setText("Total Nilai");

        jLabel35.setText("IPK");

        jLabel37.setText("Total SKS D");

        jLabel39.setText("Total SKS E");

        javax.swing.GroupLayout panelTranskripLayout = new javax.swing.GroupLayout(panelTranskrip);
        panelTranskrip.setLayout(panelTranskripLayout);
        panelTranskripLayout.setHorizontalGroup(
            panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranskripLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelTranskripLayout.createSequentialGroup()
                        .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20))
                        .addGap(38, 38, 38)
                        .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbProdiTranskrip)
                            .addComponent(lbJenjang)
                            .addComponent(lbNamaTranskrip)
                            .addComponent(lbNomorInduk))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranskripLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbTotalSks))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranskripLayout.createSequentialGroup()
                        .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTotalNilai, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbLulusSks, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbIpk, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbSksD, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbSksE, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(panelTranskripLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTranskripLayout.setVerticalGroup(
            panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranskripLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(26, 26, 26)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbJenjang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbProdiTranskrip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbNamaTranskrip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lbNomorInduk))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(lbTotalSks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(lbLulusSks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTotalNilai)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(lbIpk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(lbSksD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTranskripLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(lbSksE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        parentPanel.add(panelTranskrip, "panelTranskrip");

        jMenu1.setText("Menu");

        jMenuItem1.setText("Mahasiswa");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        menuJadwal.setText("Jadwal");
        menuJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJadwalActionPerformed(evt);
            }
        });
        jMenu1.add(menuJadwal);

        jMenuItem2.setText("Transkrip Nilai");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        menuKeluar.setText("Logout");
        menuKeluar.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                menuKeluarMenuSelected(evt);
            }
        });
        jMenuBar1.add(menuKeluar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuKeluarMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuKeluarMenuSelected
        // TODO add your handling code here:
        FormLoginMhs logout = new FormLoginMhs();
        logout.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuKeluarMenuSelected

    private void menuJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJadwalActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "panelJadwal");
    }//GEN-LAST:event_menuJadwalActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(!"".equals(nrp)){
            try{
                rs = stm.executeQuery("SELECT * FROM mengambil WHERE nrp = '"+nrp+"'");
                if(rs.next()){
                    String jrxmlFile = "src/jadwal.jrxml";
                    HashMap param = new HashMap();
                    param.put("nrp", nrp);
                    JasperReport jspR = JasperCompileManager.compileReport(jrxmlFile);
                    JasperPrint JPrint = JasperFillManager.fillReport(jspR, param, conn);
                    JasperViewer.viewReport(JPrint, false);
                }else{
                    JOptionPane.showMessageDialog(null, "Data yang ingin dicetak tidak tersedia!");
                }
            }catch(SQLException | JRException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "NRP Kosong!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "panelMahasiswa");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "panelTranskrip");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            rs = stm.executeQuery("SELECT * FROM mahasiswa WHERE NRP = '"+nrp+"' AND password = '"+tfPasswordLama.getText()+"'");
            if(rs.next()){
                if(!"".equals(tfPasswordBaru.getText())){
                    stm.executeUpdate("UPDATE mahasiswa SET password = '"+tfPasswordBaru.getText()+"' WHERE NRP = '"+nrp+"'");
                    JOptionPane.showMessageDialog(null, "Password berhasil diubah");
                    clearPassword();
                }else{
                    JOptionPane.showMessageDialog(null, "Kolom password baru tidak boleh kosong!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Password lama yang Anda masukan salah!");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearPassword();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LihatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LihatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LihatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LihatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LihatData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbIpk;
    private javax.swing.JLabel lbJenjang;
    private javax.swing.JLabel lbLulusSks;
    private javax.swing.JLabel lbNamaTranskrip;
    private javax.swing.JLabel lbNomorInduk;
    private javax.swing.JLabel lbProdiTranskrip;
    private javax.swing.JLabel lbSksD;
    private javax.swing.JLabel lbSksE;
    private javax.swing.JLabel lbTotalNilai;
    private javax.swing.JLabel lbTotalSks;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JMenuItem menuJadwal;
    private javax.swing.JMenu menuKeluar;
    private javax.swing.JPanel panelJadwal;
    private javax.swing.JPanel panelMahasiswa;
    private javax.swing.JPanel panelTranskrip;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JTable tabelTranskrip;
    private javax.swing.JTable tabeljadwal;
    private javax.swing.JLabel tfAgama;
    private javax.swing.JLabel tfAlamat;
    private javax.swing.JLabel tfAlamatOrtu;
    private javax.swing.JLabel tfEmail;
    private javax.swing.JLabel tfIbu;
    private javax.swing.JLabel tfJk;
    private javax.swing.JLabel tfNama;
    private javax.swing.JLabel tfNamaAyah;
    private javax.swing.JLabel tfNoHp;
    private javax.swing.JLabel tfNoKtpAyah;
    private javax.swing.JLabel tfNrp;
    private javax.swing.JLabel tfNrpNama;
    private javax.swing.JLabel tfOrtu;
    private javax.swing.JPasswordField tfPasswordBaru;
    private javax.swing.JPasswordField tfPasswordLama;
    private javax.swing.JLabel tfProdi;
    private javax.swing.JLabel tfStatus;
    // End of variables declaration//GEN-END:variables
}
