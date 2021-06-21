
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class ManageData extends javax.swing.JFrame {

    /**
     * Creates new form ManageData
     */
    Connection conn;
    Statement stm;
    ResultSet rs;
    String sql;
    String[] dataDosen;
    String[] dataFoto;
    String[] dataKelas;
    String [] dataMatkul;
    String asalFile = "";
    public ManageData() {
        initComponents();
        koneksi DB = new koneksi();
        DB.config();
        conn = DB.con;
        stm = DB.stm;
        rbPria.setSelected(true);
        rbPriaMhs.setSelected(true);
        updateTabelDosen();
        updateTabelMahasiswa();
        updateTabelKelas();
        updateTabelMatkul();
        updateTabelAmbilMk();
    }
    
    private void clearAmbilMk(){
        tfNrpAmbilMk.setText("");
        tfMatkulAmbilMk.setText("");
        tfKodeAmbilMk.setText("");
        tblAmbilMk.clearSelection();
    }
    
    private void updateTabelAmbilMk(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Ambil MK");
        model.addColumn("NRP");
        model.addColumn("Nama Mahasiswa");
        model.addColumn("Kode Mata Kuliah");
        model.addColumn("Nama Mata Kuliah");
        tblAmbilMk.setModel(model);
        
        try{
            rs = stm.executeQuery("SELECT * FROM mengambil INNER JOIN mahasiswa ON mengambil.NRP = mahasiswa.NRP INNER JOIN matakuliah ON mengambil.KODE_MATA_KULIAH = matakuliah.KODE_MATA_KULIAH");
            while(rs.next()){
                Object[] data = new Object[5];
                data[0] = rs.getString("id_ambil_matkul");
                data[1] = rs.getString("NRP");
                data[2] = rs.getString("NAMA_MAHASISWA");
                data[3] = rs.getString("KODE_MATA_KULIAH");
                data[4] = rs.getString("NAMA_MATA_KULIAH");
                model.addRow(data);
                tblAmbilMk.setModel(model);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void refreshData(){
        updateTabelDosen();
        updateTabelMahasiswa();
        updateTabelKelas();
        updateTabelMatkul();
        updateTabelAmbilMk();
    }
    private void clearMahasiswa(){
        tfNrp.setText("");
        tfNamaMhs.setText("");
        cbProdi.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
        rbPriaMhs.setSelected(true);
        cbAgama.setSelectedIndex(0);
        tfAlamatMhs.setText("");
        tfEmailMhs.setText("");
        tfNoHpMhs.setText("");
        tfNamaAyah.setText("");
        tfKtpAyah.setText("");
        tfNamaIbu.setText("");
        tfTelpOrtu.setText("");
        tfAlamatOrtu.setText("");
        tabelMahasiswa.clearSelection();
        asalFile = "";
        lbl_image.setIcon(null);
        lbl_image.setText("Foto");
    }
    private void clearKelas(){
        tfidkelas.setText("");
        tfnamakelas.setText("");
        tfpertemuan.setText("");
        tfwaktu.setText("");
        cbruang.setSelectedIndex(0);
        tabelKelas.clearSelection();
    }
    
    private int getNumberDosen(){
        int row = 0;
        try {
            rs = stm.executeQuery("SELECT COUNT(*) FROM dosen WHERE NIP_DOSEN != 000");
            while(rs.next()){
                row =  rs.getInt(1);
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return row;
    }
    
    private int getNumberMahasiswa(){
        int row = 0;
        try {
            rs = stm.executeQuery("SELECT COUNT(*) FROM mahasiswa");
            while(rs.next()){
                row =  rs.getInt(1);
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return row;
    }
    private int getNumberKelas(){
        int row = 0;
        try {
            rs = stm.executeQuery("SELECT COUNT(*) FROM Kelas WHERE ID_KELAS != 000");
            while(rs.next()){
                row =  rs.getInt(1);
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return row;
    }
    
    private void updateTabelMahasiswa(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NRP");
        model.addColumn("Nama");
        model.addColumn("PRODI");
        model.addColumn("Status Masuk");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Agama");
        model.addColumn("Alamat");
        model.addColumn("Email");
        model.addColumn("No HP");
        model.addColumn("Nama Ayah");
        model.addColumn("Nomor KTP Ayah");
        model.addColumn("NAMA Ibu");
        model.addColumn("Telepon Orang Tua");
        model.addColumn("Alamat Orang Tua");
        tabelMahasiswa.setModel(model);
        
        try{
            rs = stm.executeQuery("SELECT * FROM mahasiswa");
            while(rs.next()){
                Object[] data = new Object[14];
                data[0] = rs.getString("NRP");
                data[1] = rs.getString("NAMA_MAHASISWA");
                data[2] = rs.getString("PRODI");
                data[3] = rs.getString("STATUS_MASUK");
                data[4] = rs.getString("JENIS_KELAMIN");
                data[5] = rs.getString("AGAMA");
                data[6] = rs.getString("ALAMAT");
                data[7] = rs.getString("EMAIL");
                data[8] = rs.getString("NO_HP");
                data[9] = rs.getString("NAMA_AYAH");
                data[10] = rs.getString("NOMOR_KTP_AYAH");
                data[11] = rs.getString("NAMA_IBU");
                data[12] = rs.getString("TELEPON_ORANG_TUA");
                data[13] = rs.getString("ALAMAT_ORANG_TUA");
                
                model.addRow(data);
                tabelMahasiswa.setModel(model);
            }
            rs.close();
            
            try{
                int row = getNumberMahasiswa();
                rs = stm.executeQuery("SELECT * FROM mahasiswa");
                dataFoto = new String[row];
                int i = 0;
                while(rs.next()){
                    dataFoto[i] = rs.getString("link_foto");
                    i++;
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void updateTabelMatkul(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Mata Kuliah");
        model.addColumn("Dosen Pengampu");
        model.addColumn("ID Kelas");
        model.addColumn("Periode");
        model.addColumn("Nama Mata Kuliah");
        tabelMatkul.setModel(model);
        
        try{
            rs = stm.executeQuery("SELECT * FROM matakuliah INNER JOIN dosen ON matakuliah.NIP_DOSEN = dosen.NIP_DOSEN INNER JOIN kelas ON matakuliah.ID_KELAS = kelas.ID_KELAS");
            while(rs.next()){
                Object[] data = new Object[5];
                data[0] = rs.getString("KODE_MATA_KULIAH");
                data[1] = rs.getString("NAMA_DOSEN");
                data[2] = rs.getString("ID_KELAS");
                data[3] = rs.getString("PERIODE");
                data[4] = rs.getString("NAMA_MATA_KULIAH");
                model.addRow(data);
                tabelMatkul.setModel(model);
            }
            rs.close();
            
            try{
                int row = getNumberDosen();
                rs = stm.executeQuery("SELECT * FROM dosen WHERE NIP_DOSEN");
                dataDosen = new String[row];
                for(int i = 0; i < row; i++){
                    rs.next();
                    dataDosen[i] = rs.getString("NIP_DOSEN");   
                }
                rs.beforeFirst();
                while(rs.next()){
                    String namaDosen = rs.getString("NAMA_DOSEN");
                    cbnipdosen.addItem(namaDosen);
                }
                rs.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
            try{
                int row = getNumberKelas();
                rs = stm.executeQuery("SELECT * FROM kelas");
                dataKelas = new String[row];
                for(int i = 0; i < row; i++){
                    rs.next();
                    dataKelas[i] = rs.getString("ID_KELAS");
                }
                rs.beforeFirst();
                while(rs.next()){
                    String idKelas = rs.getString("ID_KELAS");
                    cbidkelas.addItem(idKelas);
                }
                rs.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    private void updateTabelDosen(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NIP");
        model.addColumn("NIDN");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        model.addColumn("Email");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Tempat Tanggal Lahir");
        model.addColumn("Status Kepegawaian");
        model.addColumn("Pendidikan Tertinggi");
        model.addColumn("Jabatan Akademik");
        tabelDosen.setModel(model);
        
        sql = "SELECT * FROM dosen WHERE NIP_DOSEN != '000'";
        try{
            rs = stm.executeQuery(sql);
            while(rs.next()){
                Object[] data = new Object[11];
                data[0] = rs.getString("NIP_DOSEN");
                data[1] = rs.getString("NIDN_DOSEN");
                data[2] = rs.getString("NAMA_DOSEN");
                data[3] = rs.getString("ALAMAT");
                data[4] = rs.getString("TELEPON");
                data[5] = rs.getString("EMAIL");
                data[6] = rs.getString("JENIS_KELAMIN");
                data[7] = rs.getString("TEMPAT_TANGGAL_LAHIR");
                data[8] = rs.getString("STATUS_KEPEGAWAIAN");
                data[9] = rs.getString("PENDIDIKAN_TERTINGGI");
                data[10] = rs.getString("JABATAN_AKADEMIK");
                
                model.addRow(data);
                tabelDosen.setModel(model);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
private void updateTabelKelas(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Kelas");
        model.addColumn("Kelas");
        model.addColumn("Pertemuan");
        model.addColumn("Waktu");
        model.addColumn("Ruang");
        
        tabelKelas.setModel(model);
        
        sql = "SELECT * FROM kelas WHERE id_kelas != '000'";
        try{
            rs = stm.executeQuery(sql);
            while(rs.next()){
                Object[] data = new Object[5];
                data[0] = rs.getString("ID_KELAS");
                data[1] = rs.getString("KELAS");
                data[2] = rs.getString("PERTEMUAN");
                data[3] = rs.getString("WAKTU");
                data[4] = rs.getString("RUANG");  
                model.addRow(data);
                tabelKelas.setModel(model);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void clearDosen(){
        tfNip.setText("");
        tfNidn.setText("");
        tfNamaDosen.setText("");
        tfAlamatDosen.setText("");
        tfTeleponDosen.setText("");
        tfEmailDosen.setText("");
        rbPria.setSelected(true);
        ttlDosen.setText("");
        cbStatusKepegawaian.setSelectedIndex(0);
        cbPendidikanDosen.setSelectedIndex(0);
        tfJabatanAkademik.setText("");
        tabelDosen.clearSelection();
    }
    private void clearMatkul(){
        tfkdmatkul.setText("");
        tfperiode.setText("");
        tfnamamatkul.setText("");
        cbnipdosen.setSelectedIndex(0);
        cbidkelas.setSelectedIndex(0);
        tabelMatkul.clearSelection();
       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        parentPanel = new javax.swing.JPanel();
        manageMahasiswa = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelMahasiswa = new javax.swing.JTable();
        tfNrp = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tfNamaMhs = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cbProdi = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        rbPriaMhs = new javax.swing.JRadioButton();
        rbWanitaMhs = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        cbAgama = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        tfAlamatMhs = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tfEmailMhs = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tfNoHpMhs = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tfNamaAyah = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        tfKtpAyah = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tfNamaIbu = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        tfTelpOrtu = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tfAlamatOrtu = new javax.swing.JTextField();
        btnInputMahasiswa = new javax.swing.JButton();
        btnUpdateMhs = new javax.swing.JButton();
        btnDeleteMhs = new javax.swing.JButton();
        btnClearMhs = new javax.swing.JButton();
        btnPilihGambar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        manageDosen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfNip = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfNidn = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfNamaDosen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfAlamatDosen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfTeleponDosen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rbPria = new javax.swing.JRadioButton();
        rbWanita = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        ttlDosen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbStatusKepegawaian = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbPendidikanDosen = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        tfJabatanAkademik = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDosen = new javax.swing.JTable();
        btnInput = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tfEmailDosen = new javax.swing.JTextField();
        manageKelas = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        tfidkelas = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        tfnamakelas = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        tfpertemuan = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        tfwaktu = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        cbruang = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelKelas = new javax.swing.JTable();
        btinputkelas = new javax.swing.JButton();
        btupdatekelas = new javax.swing.JButton();
        btdeletekelas = new javax.swing.JButton();
        btclearkelas = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        manageAmbilMk = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        tfNrpAmbilMk = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        tfMatkulAmbilMk = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        tfKodeAmbilMk = new javax.swing.JTextField();
        btnInputAmbilMk = new javax.swing.JButton();
        btnDeleteAmbilMk = new javax.swing.JButton();
        btnClearAmbilMk = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblAmbilMk = new javax.swing.JTable();
        manageMatkul = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        tfkdmatkul = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        tfperiode = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        tfnamamatkul = new javax.swing.JTextField();
        cbnipdosen = new javax.swing.JComboBox<>();
        cbidkelas = new javax.swing.JComboBox<>();
        btinputmatkul = new javax.swing.JButton();
        btupdatematkul = new javax.swing.JButton();
        btdeletematkul = new javax.swing.JButton();
        btclearmatkul = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelMatkul = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemMahasiswa = new javax.swing.JMenuItem();
        menuItemDosen = new javax.swing.JMenuItem();
        MenuItemKelas = new javax.swing.JMenuItem();
        menuItemMatkul = new javax.swing.JMenuItem();
        menuAmbilMk = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parentPanel.setLayout(new java.awt.CardLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Manage Data Mahasiswa");

        jLabel14.setText("NRP");

        tabelMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelMahasiswa);

        jLabel15.setText("Nama");

        jLabel16.setText("Prodi");

        cbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teknik Informatika", "Sistem Informasi", "Manajemen Informatika", "Desain Komunikasi Visual" }));

        jLabel17.setText("Status Masuk");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masuk", "Tidak Masuk" }));

        jLabel18.setText("Jenis Kelamin");

        buttonGroup2.add(rbPriaMhs);
        rbPriaMhs.setText("Pria");

        buttonGroup2.add(rbWanitaMhs);
        rbWanitaMhs.setText("Wanita");

        jLabel19.setText("Agama");

        cbAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Islam", "Kristen", "Katolik", "Hindu", "Budha", "Kong Hu Chu" }));

        jLabel20.setText("Alamat");

        jLabel21.setText("Email");

        jLabel22.setText("No HP");

        jLabel23.setText("Nama Ayah");

        jLabel24.setText("Nomor KTP Ayah");

        jLabel25.setText("Nama Ibu");

        jLabel26.setText("Telepon Orang Tua");

        jLabel27.setText("Alamat Orang Tua");

        btnInputMahasiswa.setText("Input");
        btnInputMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputMahasiswaActionPerformed(evt);
            }
        });

        btnUpdateMhs.setText("Update");
        btnUpdateMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMhsActionPerformed(evt);
            }
        });

        btnDeleteMhs.setText("Delete");
        btnDeleteMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMhsActionPerformed(evt);
            }
        });

        btnClearMhs.setText("Clear");
        btnClearMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearMhsActionPerformed(evt);
            }
        });

        btnPilihGambar.setText("Pilih Gambar");
        btnPilihGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihGambarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_image.setText("Foto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_image, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout manageMahasiswaLayout = new javax.swing.GroupLayout(manageMahasiswa);
        manageMahasiswa.setLayout(manageMahasiswaLayout);
        manageMahasiswaLayout.setHorizontalGroup(
            manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(manageMahasiswaLayout.createSequentialGroup()
                        .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addGap(18, 18, 18)
                                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbAgama, javax.swing.GroupLayout.Alignment.LEADING, 0, 201, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, manageMahasiswaLayout.createSequentialGroup()
                                        .addComponent(rbPriaMhs)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbWanitaMhs))
                                    .addComponent(tfAlamatMhs, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfEmailMhs)))
                            .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel13)
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel16))
                                    .addGap(55, 55, 55)
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfNrp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfNamaMhs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbProdi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(28, 110, Short.MAX_VALUE)
                        .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel23))
                                    .addGap(11, 11, 11)
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfNamaAyah, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(tfNoHpMhs)))
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfKtpAyah))
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfNamaIbu, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(manageMahasiswaLayout.createSequentialGroup()
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel27))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfTelpOrtu)
                                        .addComponent(tfAlamatOrtu))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMahasiswaLayout.createSequentialGroup()
                                .addComponent(btnClearMhs)
                                .addGap(10, 10, 10)
                                .addComponent(btnDeleteMhs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdateMhs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInputMahasiswa)))))
                .addContainerGap())
            .addGroup(manageMahasiswaLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnPilihGambar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manageMahasiswaLayout.setVerticalGroup(
            manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tfNrp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(tfNoHpMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tfNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(tfNamaAyah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(tfKtpAyah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(tfNamaIbu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(rbPriaMhs)
                    .addComponent(rbWanitaMhs)
                    .addComponent(jLabel26)
                    .addComponent(tfTelpOrtu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(tfAlamatOrtu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(tfAlamatMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfEmailMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInputMahasiswa)
                    .addComponent(btnUpdateMhs)
                    .addComponent(btnDeleteMhs)
                    .addComponent(btnClearMhs))
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageMahasiswaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addComponent(btnPilihGambar)
                        .addGap(69, 69, 69))
                    .addGroup(manageMahasiswaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        parentPanel.add(manageMahasiswa, "manageMahasiswa");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Manage Data Dosen");

        jLabel9.setText("NIP Dosen");

        jLabel2.setText("NIDN Dosen");

        jLabel3.setText("Nama Dosen");

        jLabel4.setText("Alamat");

        jLabel5.setText("Telepon");

        jLabel6.setText("Jenis Kelamin");

        buttonGroup1.add(rbPria);
        rbPria.setText("Pria");

        buttonGroup1.add(rbWanita);
        rbWanita.setText("Wanita");

        jLabel7.setText("Tempat Tanggal Lahir");

        jLabel8.setText("Status Kepegawaian");

        cbStatusKepegawaian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Non Aktif" }));

        jLabel10.setText("Pendidikan Tertinggi");

        cbPendidikanDosen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SD", "SMP", "SMA", "S1", "S2", "S3" }));

        jLabel11.setText("Jabatan Akademik");

        tabelDosen.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelDosen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDosenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelDosen);

        btnInput.setText("Input");
        btnInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel12.setText("Email");

        javax.swing.GroupLayout manageDosenLayout = new javax.swing.GroupLayout(manageDosen);
        manageDosen.setLayout(manageDosenLayout);
        manageDosenLayout.setHorizontalGroup(
            manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDosenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageDosenLayout.createSequentialGroup()
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(manageDosenLayout.createSequentialGroup()
                                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfNip)
                                    .addComponent(tfNidn)
                                    .addComponent(tfNamaDosen)
                                    .addComponent(tfAlamatDosen)
                                    .addComponent(tfTeleponDosen)
                                    .addComponent(tfEmailDosen, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(manageDosenLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbStatusKepegawaian, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(manageDosenLayout.createSequentialGroup()
                                    .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                    .addGap(18, 18, 18)
                                    .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbPendidikanDosen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfJabatanAkademik)))
                                .addGroup(manageDosenLayout.createSequentialGroup()
                                    .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(manageDosenLayout.createSequentialGroup()
                                            .addComponent(rbPria)
                                            .addGap(18, 18, 18)
                                            .addComponent(rbWanita))
                                        .addComponent(ttlDosen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(manageDosenLayout.createSequentialGroup()
                                .addComponent(btnClear)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnInput)))))
                .addContainerGap())
        );
        manageDosenLayout.setVerticalGroup(
            manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDosenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(rbPria)
                    .addComponent(rbWanita))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(manageDosenLayout.createSequentialGroup()
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfNidn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfNamaDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tfAlamatDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tfTeleponDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(manageDosenLayout.createSequentialGroup()
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(ttlDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbStatusKepegawaian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbPendidikanDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(tfJabatanAkademik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageDosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfEmailDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnInput))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );

        parentPanel.add(manageDosen, "manageDosen");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Manage Data Kelas");

        jLabel30.setText("ID Kelas");

        jLabel31.setText("Kelas");

        jLabel32.setText("Pertemuan");

        tfpertemuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfpertemuanActionPerformed(evt);
            }
        });

        jLabel33.setText("Waktu");

        jLabel34.setText("Ruang");

        cbruang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A.1.1", "A.2.3", "A.3.3", "B.1.1", "B.2.2", "B.3.3", "C.1.1", "C.2.2", "C.3.3", "D.1.1", "D.2.2", "D.3.3", " " }));

        tabelKelas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKelasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelKelas);

        btinputkelas.setText("Input");
        btinputkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btinputkelasActionPerformed(evt);
            }
        });

        btupdatekelas.setText("Update");
        btupdatekelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btupdatekelasActionPerformed(evt);
            }
        });

        btdeletekelas.setText("Delete");
        btdeletekelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdeletekelasActionPerformed(evt);
            }
        });

        btclearkelas.setText("Clear");
        btclearkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btclearkelasActionPerformed(evt);
            }
        });

        jLabel41.setText("Format Penulisan Waktu = YYYY-MM-DD HH:MM:SS");

        javax.swing.GroupLayout manageKelasLayout = new javax.swing.GroupLayout(manageKelas);
        manageKelas.setLayout(manageKelasLayout);
        manageKelasLayout.setHorizontalGroup(
            manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageKelasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btclearkelas)
                .addGap(18, 18, 18)
                .addComponent(btdeletekelas)
                .addGap(18, 18, 18)
                .addComponent(btupdatekelas)
                .addGap(18, 18, 18)
                .addComponent(btinputkelas)
                .addContainerGap())
            .addGroup(manageKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageKelasLayout.createSequentialGroup()
                        .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(manageKelasLayout.createSequentialGroup()
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfidkelas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfnamakelas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(manageKelasLayout.createSequentialGroup()
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfwaktu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(tfpertemuan, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(41, 41, 41))
                    .addGroup(manageKelasLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(manageKelasLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageKelasLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(75, 75, 75)
                        .addComponent(cbruang, 0, 537, Short.MAX_VALUE)
                        .addGap(31, 31, 31))
                    .addGroup(manageKelasLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        manageKelasLayout.setVerticalGroup(
            manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(tfidkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(tfnamakelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(tfpertemuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(tfwaktu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbruang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btinputkelas)
                    .addComponent(btupdatekelas)
                    .addComponent(btdeletekelas)
                    .addComponent(btclearkelas))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );

        parentPanel.add(manageKelas, "manageKelas");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("Manage Ambil Kuliah");

        jLabel43.setText("NRP Mahasiswa");

        jLabel44.setText("Kode Matkul");

        jLabel45.setText("Kode Ambil MK");

        tfKodeAmbilMk.setEditable(false);

        btnInputAmbilMk.setText("Input");
        btnInputAmbilMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputAmbilMkActionPerformed(evt);
            }
        });

        btnDeleteAmbilMk.setText("Delete");
        btnDeleteAmbilMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAmbilMkActionPerformed(evt);
            }
        });

        btnClearAmbilMk.setText("Clear");
        btnClearAmbilMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAmbilMkActionPerformed(evt);
            }
        });

        tblAmbilMk.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAmbilMk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAmbilMkMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblAmbilMk);

        javax.swing.GroupLayout manageAmbilMkLayout = new javax.swing.GroupLayout(manageAmbilMk);
        manageAmbilMk.setLayout(manageAmbilMkLayout);
        manageAmbilMkLayout.setHorizontalGroup(
            manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageAmbilMkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageAmbilMkLayout.createSequentialGroup()
                        .addGap(0, 465, Short.MAX_VALUE)
                        .addComponent(btnClearAmbilMk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteAmbilMk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInputAmbilMk))
                    .addGroup(manageAmbilMkLayout.createSequentialGroup()
                        .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addGroup(manageAmbilMkLayout.createSequentialGroup()
                                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel45))
                                .addGap(18, 18, 18)
                                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfNrpAmbilMk)
                                    .addComponent(tfMatkulAmbilMk, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(tfKodeAmbilMk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        manageAmbilMkLayout.setVerticalGroup(
            manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageAmbilMkLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(tfNrpAmbilMk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(tfMatkulAmbilMk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(tfKodeAmbilMk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(manageAmbilMkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInputAmbilMk)
                    .addComponent(btnDeleteAmbilMk)
                    .addComponent(btnClearAmbilMk))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        parentPanel.add(manageAmbilMk, "manageAmbilMk");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Manage Mata Kuliah");

        jLabel36.setText("Kode Mata Kuliah");

        tfkdmatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfkdmatkulActionPerformed(evt);
            }
        });

        jLabel37.setText("NIP Dosen");

        jLabel38.setText("ID Kelas");

        jLabel39.setText("Periode");

        jLabel40.setText("Nama Mata Kuliah");

        tfnamamatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnamamatkulActionPerformed(evt);
            }
        });

        btinputmatkul.setText("Input");
        btinputmatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btinputmatkulActionPerformed(evt);
            }
        });

        btupdatematkul.setText("Update");
        btupdatematkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btupdatematkulActionPerformed(evt);
            }
        });

        btdeletematkul.setText("Delete");
        btdeletematkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdeletematkulActionPerformed(evt);
            }
        });

        btclearmatkul.setText("Clear");
        btclearmatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btclearmatkulActionPerformed(evt);
            }
        });

        tabelMatkul.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelMatkul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMatkulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelMatkulMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tabelMatkul);

        javax.swing.GroupLayout manageMatkulLayout = new javax.swing.GroupLayout(manageMatkul);
        manageMatkul.setLayout(manageMatkulLayout);
        manageMatkulLayout.setHorizontalGroup(
            manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMatkulLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createSequentialGroup()
                        .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(manageMatkulLayout.createSequentialGroup()
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40))
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(cbidkelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createSequentialGroup()
                                                .addComponent(btclearmatkul)
                                                .addGap(18, 18, 18)
                                                .addComponent(btdeletematkul)
                                                .addGap(18, 18, 18)
                                                .addComponent(btupdatematkul)
                                                .addGap(18, 18, 18)
                                                .addComponent(btinputmatkul))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfnamamatkul, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(tfperiode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))))))
                            .addGroup(manageMatkulLayout.createSequentialGroup()
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfkdmatkul, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(cbnipdosen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(41, 41, 41))
                    .addGroup(manageMatkulLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(manageMatkulLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        manageMatkulLayout.setVerticalGroup(
            manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMatkulLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(tfkdmatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(cbnipdosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(cbidkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(tfperiode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(tfnamamatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btinputmatkul)
                    .addComponent(btupdatematkul)
                    .addComponent(btdeletematkul)
                    .addComponent(btclearmatkul))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );

        parentPanel.add(manageMatkul, "manageMatkul");

        jMenu1.setText("Menu");

        menuItemMahasiswa.setText("Manage Mahasiswa");
        menuItemMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMahasiswaActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemMahasiswa);

        menuItemDosen.setText("Manage Dosen");
        menuItemDosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDosenActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemDosen);

        MenuItemKelas.setText("Manage Kelas");
        MenuItemKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemKelasActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemKelas);

        menuItemMatkul.setText("Manage Matkul");
        menuItemMatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMatkulActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemMatkul);

        menuAmbilMk.setText("Ambil Matakuliah");
        menuAmbilMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAmbilMkActionPerformed(evt);
            }
        });
        jMenu1.add(menuAmbilMk);

        jMenuBar1.add(jMenu1);

        menuKeluar.setText("Keluar");
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
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuKeluarMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuKeluarMenuSelected
        // TODO add your handling code here:
        MainMenu main = new MainMenu();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuKeluarMenuSelected

    private void btnInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputActionPerformed
        // TODO add your handling code here:
        String nipDosen = tfNip.getText();
        String nidnDosen = tfNidn.getText();
        String namaDosen = tfNamaDosen.getText();
        String alamatDosen = tfAlamatDosen.getText();
        String teleponDosen = tfTeleponDosen.getText();
        String emailDosen = tfEmailDosen.getText();
        String jkDosen = "Pria";
        if(rbWanita.isSelected()){
            jkDosen = rbWanita.getText();
        }
        String ttl = ttlDosen.getText();
        String statusKep = cbStatusKepegawaian.getSelectedItem().toString();
        String pendidikanDosen = cbPendidikanDosen.getSelectedItem().toString();
        String jabatanAkademik = tfJabatanAkademik.getText();
        
        if(!"".equals(nipDosen) & !"".equals(nidnDosen) & !"".equals(namaDosen) & !"".equals(alamatDosen) & !"".equals(teleponDosen) 
                & !"".equals(emailDosen) & !"".equals(jkDosen) & !"".equals(ttl) & !"".equals(statusKep) & !"".equals(pendidikanDosen) & !"".equals(jabatanAkademik)){
            sql = "INSERT INTO dosen VALUES('"+nipDosen+"', '"+nidnDosen+"', '"+namaDosen+"', "
                    + "'"+alamatDosen+"', '"+teleponDosen+"', '"+emailDosen+"','"+jkDosen+"', "
                    + "'"+ttl+"', '"+statusKep+"', '"+pendidikanDosen+"', '"+jabatanAkademik+"')";
            try{
                int status = stm.executeUpdate(sql);
                if(status == 1){
                    JOptionPane.showMessageDialog(null, "Data berhasil diinputkan");
                    clearDosen();
                    refreshData();
                }else{
                    JOptionPane.showMessageDialog(null, "Data tidak berhasil diinputkan");
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Pastikan semua data sudah terisi!");
        }
    }//GEN-LAST:event_btnInputActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearDosen();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tabelDosenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDosenMouseClicked
        // TODO add your handling code here:
        int row = tabelDosen.getSelectedRow();
        tfNip.setText(tabelDosen.getValueAt(row, 0).toString());
        tfNidn.setText(tabelDosen.getValueAt(row, 1).toString());
        tfNamaDosen.setText(tabelDosen.getValueAt(row, 2).toString());
        tfAlamatDosen.setText(tabelDosen.getValueAt(row, 3).toString());
        tfTeleponDosen.setText(tabelDosen.getValueAt(row, 4).toString());
        tfEmailDosen.setText(tabelDosen.getValueAt(row, 5).toString());
        String jk = tabelDosen.getValueAt(row, 6).toString();
        if(jk.equals("Pria")){
            rbPria.setSelected(true);
        }else if(jk.equals("Wanita")){
            rbWanita.setSelected(true);
        }
        ttlDosen.setText(tabelDosen.getValueAt(row, 7).toString());
        String statusKepegawaian = tabelDosen.getValueAt(row, 8).toString();
        if(statusKepegawaian.equals("Aktif")){
            cbStatusKepegawaian.setSelectedIndex(0);
        }else if(statusKepegawaian.equals("Non Aktif")){
            cbStatusKepegawaian.setSelectedIndex(0);
        }
        String pendidikanTertinggi = tabelDosen.getValueAt(row, 9).toString();
        if(pendidikanTertinggi.equals("SD")){
            cbPendidikanDosen.setSelectedIndex(0);
        }else if(pendidikanTertinggi.equals("SMP")){
            cbPendidikanDosen.setSelectedIndex(1);
        }else if(pendidikanTertinggi.equals("SMA")){
            cbPendidikanDosen.setSelectedIndex(2);
        }else if(pendidikanTertinggi.equals("S1")){
            cbPendidikanDosen.setSelectedIndex(3);
        }else if(pendidikanTertinggi.equals("S2")){
            cbPendidikanDosen.setSelectedIndex(4);
        }else if(pendidikanTertinggi.equals("S3")){
            cbPendidikanDosen.setSelectedIndex(5);
        }
        tfJabatanAkademik.setText(tabelDosen.getValueAt(row, 10).toString());
    }//GEN-LAST:event_tabelDosenMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String nipDosen = tfNip.getText();
        if(!"".equals(nipDosen)){
            
            try{
                stm.executeUpdate("UPDATE matkul SET NIP_DOSEN = '000' WHERE NIP_DOSEN = '"+nipDosen+"'");
                stm.executeUpdate("DELETE FROM dosen WHERE NIP_DOSEN = '"+nipDosen+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearDosen();
                refreshData();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Kolom NIP harus diisi!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        String nipDosen = tfNip.getText();
        String nidnDosen = tfNidn.getText();
        String namaDosen = tfNamaDosen.getText();
        String alamatDosen = tfAlamatDosen.getText();
        String teleponDosen = tfTeleponDosen.getText();
        String emailDosen = tfEmailDosen.getText();
        String jkDosen = "Pria";
        if(rbWanita.isSelected()){
            jkDosen = rbWanita.getText();
        }
        String ttl = ttlDosen.getText();
        String statusKep = cbStatusKepegawaian.getSelectedItem().toString();
        String pendidikanDosen = cbPendidikanDosen.getSelectedItem().toString();
        String jabatanAkademik = tfJabatanAkademik.getText();
        
        if(!"".equals(nipDosen) & !"".equals(nidnDosen) & !"".equals(namaDosen) & !"".equals(alamatDosen) & !"".equals(teleponDosen) 
                & !"".equals(emailDosen) & !"".equals(jkDosen) & !"".equals(ttl) & !"".equals(statusKep) & !"".equals(pendidikanDosen) & !"".equals(jabatanAkademik)){
            sql = "UPDATE dosen SET `NIDN_DOSEN`='"+nidnDosen+"',"
                    + "`NAMA_DOSEN`='"+namaDosen+"', ALAMAT='"+alamatDosen+"',`TELEPON`='"+teleponDosen+"',"
                    + "`EMAIL`='"+emailDosen+"',`JENIS_KELAMIN`='"+jkDosen+"',`"
                    + "TEMPAT_TANGGAL_LAHIR`='"+ttl+"',`STATUS_KEPEGAWAIAN`='"+statusKep+"',`"
                    + "PENDIDIKAN_TERTINGGI`='"+pendidikanDosen+"',`JABATAN_AKADEMIK`='"+jabatanAkademik+"' "
            + "WHERE NIP_DOSEN = '"+nipDosen+"'";
            try{
                int status = stm.executeUpdate(sql);
                if(status == 1){
                    JOptionPane.showMessageDialog(null, "Data berhasil di-update");
                    clearDosen();
                    refreshData();
                }else{
                    JOptionPane.showMessageDialog(null, "Data tidak berhasil di-update");
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Pastikan semua data sudah terisi!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void menuItemMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMahasiswaActionPerformed
        // TODO add your handling code here:
        CardLayout cl= (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageMahasiswa");
    }//GEN-LAST:event_menuItemMahasiswaActionPerformed

    private void menuItemDosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDosenActionPerformed
        // TODO add your handling code here:
        CardLayout cl= (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageDosen");
    }//GEN-LAST:event_menuItemDosenActionPerformed

    private void btnInputMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputMahasiswaActionPerformed
        // TODO add your handling code here:
        String nrp = tfNrp.getText();
        String nama = tfNamaMhs.getText();
        String prodi = cbProdi.getSelectedItem().toString();
        String statusMasuk = cbStatus.getSelectedItem().toString();
        String jenisKelamin = "Pria";
        if(rbPriaMhs.isSelected()){
            jenisKelamin = "Pria";
        }else if(rbWanitaMhs.isSelected()){
            jenisKelamin = "Wanita";
        }
        String agama = cbAgama.getSelectedItem().toString();
        String alamat = tfAlamatMhs.getText();
        String email = tfEmailMhs.getText();
        String noHpMhs = tfNoHpMhs.getText();
        String ayah = tfNamaAyah.getText();
        String ktpAyah = tfKtpAyah.getText();
        String ibu = tfNamaIbu.getText();
        String telpOrtu = tfTelpOrtu.getText();
        String alamatOrtu = tfAlamatOrtu.getText();

        if(!"".equals(nrp) & !"".equals(nama) & !"".equals(prodi) & !"".equals(jenisKelamin) & !"".equals(agama) & !"".equals(alamat) & !"".equals(email) & !"".equals(noHpMhs) & !"".equals(ayah) & !"".equals(ktpAyah) & !"".equals(ibu) & !"".equals(telpOrtu) & !"".equals(alamatOrtu) & !"".equals(asalFile)){
            try {
                int index = asalFile.lastIndexOf('.');
                String jenisFile = asalFile.substring(index + 1);
                String linkFile = "C:\\Tugas\\agfdaf\\Semester 2\\Algoritma dan Pemrograman II\\Tugas\\14_pertemuan\\TugasAkhir\\src\\gambar\\"+nrp+"."+jenisFile;
                Files.copy(Paths.get(asalFile), Paths.get(linkFile));
                String link = linkFile.replace("\\", "\\\\");
                stm.executeUpdate("INSERT INTO mahasiswa VALUES('"+nrp+"', '"+nama+"', '"+prodi+"', '"+statusMasuk+"','"+jenisKelamin+"', '"+agama+"', '"+alamat+"', '"+email+"', '"+noHpMhs+"', '"+ayah+"', '"+ktpAyah+"', '"+ibu+"', '"+telpOrtu+"', '"+alamatOrtu+"', '"+link+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                clearMahasiswa();
                refreshData();
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btnInputMahasiswaActionPerformed

    private void btnClearMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearMhsActionPerformed
        // TODO add your handling code here:
        clearMahasiswa();
    }//GEN-LAST:event_btnClearMhsActionPerformed

    private void btnUpdateMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMhsActionPerformed
        // TODO add your handling code here:
        String nrp = tfNrp.getText();
        String nama = tfNamaMhs.getText();
        String prodi = cbProdi.getSelectedItem().toString();
        String statusMasuk = cbStatus.getSelectedItem().toString();
        String jenisKelamin = "Pria";
        if(rbPriaMhs.isSelected()){
            jenisKelamin = "Pria";
        }else if(rbWanitaMhs.isSelected()){
            jenisKelamin = "Wanita";
        }
        String agama = cbAgama.getSelectedItem().toString();
        String alamat = tfAlamatMhs.getText();
        String email = tfEmailMhs.getText();
        String noHpMhs = tfNoHpMhs.getText();
        String ayah = tfNamaAyah.getText();
        String ktpAyah = tfKtpAyah.getText();
        String ibu = tfNamaIbu.getText();
        String telpOrtu = tfTelpOrtu.getText();
        String alamatOrtu = tfAlamatOrtu.getText();
        if(!"".equals(nrp) & !"".equals(nama) & !"".equals(prodi) & !"".equals(jenisKelamin) & !"".equals(agama) & !"".equals(alamat) & !"".equals(email) & !"".equals(noHpMhs) & !"".equals(ayah) & !"".equals(ktpAyah) & !"".equals(ibu) & !"".equals(telpOrtu) & !"".equals(alamatOrtu)  & !"".equals(asalFile)){
            try {
                int index = asalFile.lastIndexOf('.');
                String jenisFile = asalFile.substring(index + 1);
                String linkFile = "C:\\Tugas\\agfdaf\\Semester 2\\Algoritma dan Pemrograman II\\Tugas\\14_pertemuan\\TugasAkhir\\src\\gambar\\"+nrp+"."+jenisFile;
                rs = stm.executeQuery("SELECT * FROM mahasiswa WHERE NRP = '"+nrp+"'");
                rs.next();
                String fileLama = rs.getString("link_foto").replace("\\", "\\\\");
                String link = linkFile.replace("\\", "\\\\");
                stm.executeUpdate("UPDATE `mahasiswa` SET `NAMA_MAHASISWA`='"+nama+"',`PRODI`='"+prodi+"',`STATUS_MASUK`='"+statusMasuk+"',`JENIS_KELAMIN`='"+jenisKelamin+"',`AGAMA`='"+agama+"',`ALAMAT`='"+alamat+"',`EMAIL`='"+email+"',`NO_HP`='"+noHpMhs+"',`NAMA_AYAH`='"+ayah+"',`NOMOR_KTP_AYAH`='"+ktpAyah+"',`NAMA_IBU`='"+ibu+"',`TELEPON_ORANG_TUA`='"+telpOrtu+"',`ALAMAT_ORANG_TUA`='"+alamatOrtu+"', link_foto = '"+link+"' WHERE NRP = '"+nrp+"'");
                if(!fileLama.equals(link)){
                    Files.delete(Paths.get(fileLama));
                    Files.copy(Paths.get(asalFile), Paths.get(linkFile));
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil Di-update");
                clearMahasiswa();
                refreshData();
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btnUpdateMhsActionPerformed

    private void btnDeleteMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMhsActionPerformed
        // TODO add your handling code here:
        String nrp = tfNrp.getText();
        if(!"".equals(nrp)){
            try{
                rs = stm.executeQuery("SELECT link_foto FROM mahasiswa WHERE NRP = '"+nrp+"'");
                rs.next();
                String fileFoto = rs.getString("link_foto");
                stm.executeUpdate("DELETE FROM mengambil WHERE NRP = '"+nrp+"'");
                stm.executeUpdate("DELETE FROM mahasiswa WHERE NRP = '"+nrp+"'");
                Files.delete(Paths.get(fileFoto));
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearMahasiswa();
                refreshData();
            }catch(SQLException | IOException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Kolom NRP harus diisi!");
        }
    }//GEN-LAST:event_btnDeleteMhsActionPerformed

    private void tabelMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMahasiswaMouseClicked
        // TODO add your handling code here:
        int row = tabelMahasiswa.getSelectedRow();
        tfNrp.setText(tabelMahasiswa.getValueAt(row, 0).toString());
        tfNamaMhs.setText(tabelMahasiswa.getValueAt(row, 1).toString());
        
        if(tabelMahasiswa.getValueAt(row, 2).equals("Teknik Informatika")){
            cbProdi.setSelectedIndex(0);
        }else if(tabelMahasiswa.getValueAt(row, 2).equals("Sistem Informasi")){
            cbProdi.setSelectedIndex(1);
        }else if(tabelMahasiswa.getValueAt(row, 2).equals("Manajemen Informatika")){
            cbProdi.setSelectedIndex(2);
        }else if(tabelMahasiswa.getValueAt(row, 2).equals("Desain Komunikasi Visual")){
            cbProdi.setSelectedIndex(3);
        }
        
        if(tabelMahasiswa.getValueAt(row, 3).equals("Masuk")){
            cbStatus.setSelectedIndex(0);
        }else if(tabelMahasiswa.getValueAt(row, 3).equals("Tidak Masuk")){
            cbStatus.setSelectedIndex(1);
        }
        
        if(tabelMahasiswa.getValueAt(row, 4).equals("Pria")){
            rbPriaMhs.setSelected(true);
        }else if (tabelMahasiswa.getValueAt(row, 4).equals("Wanita")){
            rbWanitaMhs.setSelected(true);
        }
        
        if(tabelMahasiswa.getValueAt(row, 5).equals("Islam")){
            cbAgama.setSelectedIndex(0);
        }else if(tabelMahasiswa.getValueAt(row, 5).equals("Kristen")){
            cbAgama.setSelectedIndex(1);
        }else if(tabelMahasiswa.getValueAt(row, 5).equals("Katolik")){
            cbAgama.setSelectedIndex(2);
        }else if(tabelMahasiswa.getValueAt(row, 5).equals("Hindu")){
            cbAgama.setSelectedIndex(3);
        }else if(tabelMahasiswa.getValueAt(row, 5).equals("Budha")){
            cbAgama.setSelectedIndex(4);
        }else if(tabelMahasiswa.getValueAt(row, 5).equals("Kong Hu Chu")){
            cbAgama.setSelectedIndex(5);
        }
        tfAlamatMhs.setText(tabelMahasiswa.getValueAt(row, 6).toString());
        tfEmailMhs.setText(tabelMahasiswa.getValueAt(row, 7).toString());
        tfNoHpMhs.setText(tabelMahasiswa.getValueAt(row, 8).toString());
        tfNamaAyah.setText(tabelMahasiswa.getValueAt(row, 9).toString());
        tfKtpAyah.setText(tabelMahasiswa.getValueAt(row, 10).toString());
        tfNamaIbu.setText(tabelMahasiswa.getValueAt(row, 11).toString());
        tfTelpOrtu.setText(tabelMahasiswa.getValueAt(row, 12).toString());
        tfAlamatOrtu.setText(tabelMahasiswa.getValueAt(row, 13).toString());

        try {
            BufferedImage img = ImageIO.read(new File(dataFoto[row]));
            asalFile = dataFoto[row];
            Image resizedImage = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            lbl_image.setText("");
            lbl_image.setIcon(icon);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_tabelMahasiswaMouseClicked

    private void tfnamamatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnamamatkulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnamamatkulActionPerformed

    private void btinputkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btinputkelasActionPerformed
        // TODO add your handling code here:
        String idkelas = tfidkelas.getText();
        String namakelas = tfnamakelas.getText();
        String pertemuan = tfpertemuan.getText();
        String waktu = tfwaktu.getText();
        String ruang = cbruang.getSelectedItem().toString();
        if(!"".equals(idkelas) & !"".equals(namakelas) & !"".equals(pertemuan)){
           try {
                stm.executeUpdate("INSERT INTO kelas VALUES('"+idkelas+"', '"+namakelas+"', '"+pertemuan+"', "
                    + "'"+waktu+"', '"+ruang+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                clearKelas();
                refreshData();
            }
           catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btinputkelasActionPerformed
    
    private void tfpertemuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfpertemuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfpertemuanActionPerformed

    private void btupdatekelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btupdatekelasActionPerformed
        // TODO add your handling code here:
        String idkelas = tfidkelas.getText();
        String namakelas = tfnamakelas.getText();
        String pertemuan = tfpertemuan.getText();
        String waktu = tfwaktu.getText();
        String ruang = cbruang.getSelectedItem().toString();
        if(!"".equals(idkelas) & !"".equals(namakelas) & !"".equals(pertemuan)){
           try {
                stm.executeUpdate("UPDATE kelas SET `KELAS`='"+namakelas+"',`PERTEMUAN`='"+pertemuan+"',`WAKTU`='"+waktu+"',`RUANG`='"+ruang+"' WHERE ID_KELAS = '"+idkelas+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Di-update");
                clearKelas();
                refreshData();
            }
           catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btupdatekelasActionPerformed

    private void MenuItemKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemKelasActionPerformed
        // TODO add your handling code here:
        CardLayout cl= (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageKelas");
    }//GEN-LAST:event_MenuItemKelasActionPerformed

    private void btdeletekelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdeletekelasActionPerformed
        // TODO add your handling code here:
        String idkelas = tfidkelas.getText();
        if(!"".equals(idkelas)){
            sql = "UPDATE matakuliah SET ID_KELAS = '000' WHERE ID_KELAS = '"+idkelas+"'";
            sql = "DELETE FROM KELAS WHERE ID_KELAS = '"+idkelas+"'";
            try{
                stm.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearKelas();
                refreshData();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Kolom ID Kelas harus diisi!");
        }
        
    }//GEN-LAST:event_btdeletekelasActionPerformed

    private void btclearkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btclearkelasActionPerformed
        // TODO add your handling code here:
        clearKelas();
    }//GEN-LAST:event_btclearkelasActionPerformed

    private void tabelKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKelasMouseClicked
        // TODO add your handling code here:
        int row = tabelKelas.getSelectedRow();
        tfidkelas.setText(tabelKelas.getValueAt(row, 0).toString());
        tfnamakelas.setText(tabelKelas.getValueAt(row, 1).toString());
        tfpertemuan.setText(tabelKelas.getValueAt(row, 2).toString());
        tfwaktu.setText(tabelKelas.getValueAt(row, 3).toString());
        if(tabelKelas.getValueAt(row, 4).equals("A.1.1")){
            cbruang.setSelectedIndex(0);
        }else if(tabelKelas.getValueAt(row, 4).equals("A.2.2")){
            cbruang.setSelectedIndex(1);
        }else if(tabelKelas.getValueAt(row, 4).equals("A.3.3")){
            cbruang.setSelectedIndex(2);
        }else if(tabelKelas.getValueAt(row, 4).equals("B.1.1")){
            cbruang.setSelectedIndex(3);
        }else if(tabelKelas.getValueAt(row, 4).equals("B.2.2")){
            cbruang.setSelectedIndex(4);
        }else if(tabelKelas.getValueAt(row, 4).equals("B.3.3")){
            cbruang.setSelectedIndex(5);
        }else if(tabelKelas.getValueAt(row, 4).equals("C.1.1")){
            cbruang.setSelectedIndex(6);
        }else if(tabelKelas.getValueAt(row, 4).equals("C.2.2")){
            cbruang.setSelectedIndex(7);
        }else if(tabelKelas.getValueAt(row, 4).equals("C.3.3")){
            cbruang.setSelectedIndex(8);
        }else if(tabelKelas.getValueAt(row, 4).equals("D.1.1")){
            cbruang.setSelectedIndex(9);
        }else if(tabelKelas.getValueAt(row, 4).equals("D.2.2")){
            cbruang.setSelectedIndex(10);
        }else if(tabelKelas.getValueAt(row, 4).equals("D.3.3")){
            cbruang.setSelectedIndex(11);
        }
       
    }//GEN-LAST:event_tabelKelasMouseClicked

    private void btnPilihGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihGambarActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        asalFile = f.getAbsolutePath();
        try {
            BufferedImage img = ImageIO.read(new File(asalFile));
            Image resizedImage = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            lbl_image.setText("");
            lbl_image.setIcon(icon);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnPilihGambarActionPerformed

    private void menuItemMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMatkulActionPerformed
        // TODO add your handling code here:
        CardLayout cl= (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageMatkul");
    }//GEN-LAST:event_menuItemMatkulActionPerformed

    private void btinputmatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btinputmatkulActionPerformed
        // TODO add your handling code here:
        String kdmatkul = tfkdmatkul.getText();
        int indexDosen = cbnipdosen.getSelectedIndex();
        String nipdosen = dataDosen[indexDosen];
        int indexKelas = cbidkelas.getSelectedIndex();
        String idkelas = dataKelas[indexKelas];
        String periode = tfperiode.getText();
        String namamatkul = tfnamamatkul.getText();
        if(!"".equals(kdmatkul) & !"".equals(nipdosen) & !"".equals(nipdosen) & !"".equals(idkelas) & !"".equals(periode) & !"".equals(namamatkul))
        {
            try {
                stm.executeUpdate("INSERT INTO matakuliah VALUES('"+kdmatkul+"', '"+nipdosen+"', '"+idkelas+"', '"+periode+"', '"+namamatkul+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                clearMatkul();
                refreshData();
            } catch (SQLException  ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btinputmatkulActionPerformed

    private void btclearmatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btclearmatkulActionPerformed
        // TODO add your handling code here:
        clearMatkul();
    }//GEN-LAST:event_btclearmatkulActionPerformed

    private void btdeletematkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdeletematkulActionPerformed
        // TODO add your handling code here:
        String kdmatkul = tfkdmatkul.getText();
        if(!"".equals(kdmatkul)){
            try{
                stm.executeUpdate("DELETE FROM matakuliah WHERE kode_mata_kuliah = '"+kdmatkul+"'");
                stm.executeUpdate("DELETE FROM mengambil WHERE kode_mata_kuliah = '"+kdmatkul+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearMatkul();
                refreshData();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Kolom Kode Mata Kuliah harus diisi!");
        }
    }//GEN-LAST:event_btdeletematkulActionPerformed

    private void tabelMatkulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMatkulMouseClicked
        // TODO add your handling code here:
        int row = tabelMatkul.getSelectedRow();
        tfkdmatkul.setText(tabelMatkul.getValueAt(row, 0).toString());
        for(int i = 0; i < dataMatkul.length; i++){
            if(tabelMatkul.getValueAt(row, 1).toString().equals(cbnipdosen.getItemAt(i))){
                cbnipdosen.setSelectedIndex(i);
            }
        }
        for(int i = 0; i < dataKelas.length; i++){
            if(tabelMatkul.getValueAt(row, 2).toString().equals(cbidkelas.getItemAt(i))){
                cbidkelas.setSelectedIndex(i);
            }
        }
        
        tfperiode.setText(tabelMatkul.getValueAt(row, 3).toString());
        tfnamamatkul.setText(tabelMatkul.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tabelMatkulMouseClicked

    private void menuAmbilMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAmbilMkActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageAmbilMk");
    }//GEN-LAST:event_menuAmbilMkActionPerformed

    private void btnInputAmbilMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputAmbilMkActionPerformed
        // TODO add your handling code here:
        String nrp = tfNrpAmbilMk.getText();
        String kode_matkul = tfMatkulAmbilMk.getText();
        if(!"".equals(nrp) & !"".equals(kode_matkul)){
            try{
                rs = stm.executeQuery("SELECT * FROM mengambil WHERE NRP = '"+nrp+"' AND KODE_MATA_KULIAH = '"+kode_matkul+"'");
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Mahasiswa sudah mengambil matkul tersebut!");
                }else{
                    stm.executeUpdate("INSERT INTO mengambil(NRP, KODE_MATA_KULIAH) VALUES('"+nrp+"', '"+kode_matkul+"')");
                    JOptionPane.showMessageDialog(null, "Data berhasil di-input");
                    clearAmbilMk();
                    refreshData();
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_btnInputAmbilMkActionPerformed

    private void btnClearAmbilMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAmbilMkActionPerformed
        // TODO add your handling code here:
        clearAmbilMk();
    }//GEN-LAST:event_btnClearAmbilMkActionPerformed

    private void tfkdmatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfkdmatkulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfkdmatkulActionPerformed

    private void btnDeleteAmbilMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAmbilMkActionPerformed
        // TODO add your handling code here:
        String ambilMk = tfKodeAmbilMk.getText();
        if(!"".equals(ambilMk)){
            try{
                stm.executeUpdate("delete from mengambil WHERE NRP ='"+ambilMk+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearAmbilMk();
                refreshData();
            }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, ex); 
            }
        }else{
          JOptionPane.showMessageDialog(null, "Kolom Kode Ambil MK kosong silahkan klik data pada tabel untuk mengisinya!");  
        }
    }//GEN-LAST:event_btnDeleteAmbilMkActionPerformed

    private void btupdatematkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btupdatematkulActionPerformed
        // TODO add your handling code here:
        String kodeMk = tfkdmatkul.getText();
        int nipDos = cbnipdosen.getSelectedIndex();
        String nipDosen = dataDosen[nipDos];
        int idKelas = cbidkelas.getSelectedIndex();
        String idKls = dataKelas[idKelas]; 
        String periode = tfperiode.getText();
        String namamatkul = tfnamamatkul.getText();
        if(!"".equals(kodeMk) && !"".equals(nipDosen) && !"".equals(periode) && !"".equals(namamatkul) && !"".equals(idKls)){
            try{
                stm.executeUpdate("UPDATE matakuliah SET NIP_DOSEN = '"+nipDosen+"', ID_KELAS = '"+idKls+"', PERIODE = '"+periode+"', NAMA_MATA_KULIAH = '"+namamatkul+"'");
                JOptionPane.showMessageDialog(null, "Data berhasil di-update");
                clearMatkul();
                refreshData();            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
    }//GEN-LAST:event_btupdatematkulActionPerformed

    private void tblAmbilMkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAmbilMkMouseClicked
        // TODO add your handling code here:
        int row = tblAmbilMk.getSelectedRow();
        tfNrpAmbilMk.setText(tblAmbilMk.getValueAt(row, 1).toString());
        tfMatkulAmbilMk.setText(tblAmbilMk.getValueAt(row, 3).toString());
        tfKodeAmbilMk.setText(tblAmbilMk.getValueAt(row, 0).toString());
    }//GEN-LAST:event_tblAmbilMkMouseClicked

    private void tabelMatkulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMatkulMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelMatkulMouseEntered

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
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItemKelas;
    private javax.swing.JButton btclearkelas;
    private javax.swing.JButton btclearmatkul;
    private javax.swing.JButton btdeletekelas;
    private javax.swing.JButton btdeletematkul;
    private javax.swing.JButton btinputkelas;
    private javax.swing.JButton btinputmatkul;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearAmbilMk;
    private javax.swing.JButton btnClearMhs;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteAmbilMk;
    private javax.swing.JButton btnDeleteMhs;
    private javax.swing.JButton btnInput;
    private javax.swing.JButton btnInputAmbilMk;
    private javax.swing.JButton btnInputMahasiswa;
    private javax.swing.JButton btnPilihGambar;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateMhs;
    private javax.swing.JButton btupdatekelas;
    private javax.swing.JButton btupdatematkul;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbAgama;
    private javax.swing.JComboBox<String> cbPendidikanDosen;
    private javax.swing.JComboBox<String> cbProdi;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbStatusKepegawaian;
    private javax.swing.JComboBox<String> cbidkelas;
    private javax.swing.JComboBox<String> cbnipdosen;
    private javax.swing.JComboBox<String> cbruang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JPanel manageAmbilMk;
    private javax.swing.JPanel manageDosen;
    private javax.swing.JPanel manageKelas;
    private javax.swing.JPanel manageMahasiswa;
    private javax.swing.JPanel manageMatkul;
    private javax.swing.JMenuItem menuAmbilMk;
    private javax.swing.JMenuItem menuItemDosen;
    private javax.swing.JMenuItem menuItemMahasiswa;
    private javax.swing.JMenuItem menuItemMatkul;
    private javax.swing.JMenu menuKeluar;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JRadioButton rbPria;
    private javax.swing.JRadioButton rbPriaMhs;
    private javax.swing.JRadioButton rbWanita;
    private javax.swing.JRadioButton rbWanitaMhs;
    private javax.swing.JTable tabelDosen;
    private javax.swing.JTable tabelKelas;
    private javax.swing.JTable tabelMahasiswa;
    private javax.swing.JTable tabelMatkul;
    private javax.swing.JTable tblAmbilMk;
    private javax.swing.JTextField tfAlamatDosen;
    private javax.swing.JTextField tfAlamatMhs;
    private javax.swing.JTextField tfAlamatOrtu;
    private javax.swing.JTextField tfEmailDosen;
    private javax.swing.JTextField tfEmailMhs;
    private javax.swing.JTextField tfJabatanAkademik;
    private javax.swing.JTextField tfKodeAmbilMk;
    private javax.swing.JTextField tfKtpAyah;
    private javax.swing.JTextField tfMatkulAmbilMk;
    private javax.swing.JTextField tfNamaAyah;
    private javax.swing.JTextField tfNamaDosen;
    private javax.swing.JTextField tfNamaIbu;
    private javax.swing.JTextField tfNamaMhs;
    private javax.swing.JTextField tfNidn;
    private javax.swing.JTextField tfNip;
    private javax.swing.JTextField tfNoHpMhs;
    private javax.swing.JTextField tfNrp;
    private javax.swing.JTextField tfNrpAmbilMk;
    private javax.swing.JTextField tfTeleponDosen;
    private javax.swing.JTextField tfTelpOrtu;
    private javax.swing.JTextField tfidkelas;
    private javax.swing.JTextField tfkdmatkul;
    private javax.swing.JTextField tfnamakelas;
    private javax.swing.JTextField tfnamamatkul;
    private javax.swing.JTextField tfperiode;
    private javax.swing.JTextField tfpertemuan;
    private javax.swing.JTextField tfwaktu;
    private javax.swing.JTextField ttlDosen;
    // End of variables declaration//GEN-END:variables
}
