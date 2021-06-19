
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        cbDosenPembimbing.setSelectedIndex(0);
        tabelMahasiswa.clearSelection();
    }
    
    private int getNumberDosen(){
        int row = 0;
        try {
            rs = stm.executeQuery("SELECT COUNT(*) FROM dosen WHERE NIP != 000");
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
        model.addColumn("Dosen Pembimbing");
        tabelMahasiswa.setModel(model);
        
        sql = "SELECT * FROM mahasiswa INNER JOIN dosen ON mahasiswa.NIP_DOSEN = dosen.NIP_DOSEN";
        try{
            rs = stm.executeQuery(sql);
            while(rs.next()){
                Object[] data = new Object[15];
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
                data[14] = rs.getString("NAMA_DOSEN");
                model.addRow(data);
                tabelMahasiswa.setModel(model);
            }
            
            try{
                int row = getNumberDosen();
                rs = stm.executeQuery("SELECT * FROM dosen WHERE NIP_DOSEN != 000");
                dataDosen = new String[row];
                for(int i = 0; i < row; i++){
                    rs.next();
                    dataDosen[i] = rs.getString("NIP_DOSEN");
                }
                rs.beforeFirst();
                while(rs.next()){
                    String namaDosen = rs.getString("NAMA_DOSEN");
                    cbDosenPembimbing.addItem(namaDosen);
                }
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
        jLabel28 = new javax.swing.JLabel();
        cbDosenPembimbing = new javax.swing.JComboBox<>();
        btnInputMahasiswa = new javax.swing.JButton();
        btnUpdateMhs = new javax.swing.JButton();
        btnDeleteMhs = new javax.swing.JButton();
        btnClearMhs = new javax.swing.JButton();
        manageKelas = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        manageMatkul = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField6 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemMahasiswa = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parentPanel.setLayout(new java.awt.CardLayout());

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        parentPanel.add(manageDosen, "manageDosen");

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

        jLabel28.setText("Dosen Pembimbing");

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
                        .addGap(18, 69, Short.MAX_VALUE)
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
                                        .addComponent(jLabel27)
                                        .addComponent(jLabel28))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfTelpOrtu)
                                        .addComponent(tfAlamatOrtu)
                                        .addComponent(cbDosenPembimbing, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMahasiswaLayout.createSequentialGroup()
                                .addComponent(btnClearMhs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDeleteMhs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdateMhs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInputMahasiswa)))))
                .addContainerGap())
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
                    .addComponent(tfAlamatMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(cbDosenPembimbing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfEmailMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInputMahasiswa)
                    .addComponent(btnUpdateMhs)
                    .addComponent(btnDeleteMhs)
                    .addComponent(btnClearMhs))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        parentPanel.add(manageMahasiswa, "manageMahasiswa");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Manage Data Kelas");

        jLabel30.setText("ID Kelas");

        jLabel31.setText("Kelas");

        jLabel32.setText("Pertemuan");

        jLabel33.setText("Waktu");

        jLabel34.setText("Ruang");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable1);

        jButton1.setText("Input");

        jButton2.setText("Update");

        jButton3.setText("Delete");

        jButton4.setText("Clear");

        javax.swing.GroupLayout manageKelasLayout = new javax.swing.GroupLayout(manageKelas);
        manageKelas.setLayout(manageKelasLayout);
        manageKelasLayout.setHorizontalGroup(
            manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageKelasLayout.createSequentialGroup()
                        .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(manageKelasLayout.createSequentialGroup()
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox1, 0, 500, Short.MAX_VALUE)))
                            .addGroup(manageKelasLayout.createSequentialGroup()
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(41, 41, 41))
                    .addGroup(manageKelasLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(manageKelasLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageKelasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        manageKelasLayout.setVerticalGroup(
            manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(manageKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        parentPanel.add(manageKelas, "card4");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Manage Mata Kuliah");

        jLabel36.setText("Kode Mata Kuliah");

        jLabel37.setText("NIP Dosen");

        jLabel38.setText("ID Kelas");

        jLabel39.setText("Periode");

        jLabel40.setText("Nama Mata Kuliah");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable2);

        jButton5.setText("Input");

        jButton6.setText("Update");

        jButton7.setText("Delete");

        jButton8.setText("Clear");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                                    .addGroup(manageMatkulLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(manageMatkulLayout.createSequentialGroup()
                                                .addGap(0, 1, Short.MAX_VALUE)
                                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextField6)))
                                    .addGroup(manageMatkulLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(manageMatkulLayout.createSequentialGroup()
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(41, 41, 41))
                    .addGroup(manageMatkulLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(manageMatkulLayout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMatkulLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
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
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(manageMatkulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        parentPanel.add(manageMatkul, "card5");

        jMenu1.setText("Menu");

        menuItemMahasiswa.setText("Manage Mahasiswa");
        menuItemMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMahasiswaActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemMahasiswa);

        jMenuItem2.setText("Manage Dosen");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Manage Kelas");
        jMenu1.add(jMenuItem1);

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
                    updateTabelDosen();
                    updateTabelMahasiswa();
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
            
            sql = "DELETE FROM dosen WHERE NIP_DOSEN = '"+nipDosen+"'";
            try{
                stm.executeUpdate("UPDATE mahasiswa SET NIP_DOSEN = '000' WHERE NIP_DOSEN = '"+nipDosen+"'");
                stm.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearDosen();
                updateTabelDosen();
                updateTabelMahasiswa();
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
            sql = "UPDATE dosen SET `NIP_DOSEN`='"+nipDosen+"',`NIDN_DOSEN`='"+nidnDosen+"',"
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
                    updateTabelDosen();
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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        CardLayout cl= (CardLayout) parentPanel.getLayout();
        cl.show(parentPanel, "manageDosen");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
        int dosPem = cbDosenPembimbing.getSelectedIndex();
        String nipDospem = dataDosen[dosPem];
        if(!"".equals(nrp) & !"".equals(nama) & !"".equals(prodi) & !"".equals(jenisKelamin) & !"".equals(agama) & !"".equals(alamat) & !"".equals(email) & !"".equals(noHpMhs) & !"".equals(ayah) & !"".equals(ktpAyah) & !"".equals(ibu) & !"".equals(telpOrtu) & !"".equals(alamatOrtu) & !"".equals(nipDospem)){
            try {
                stm.executeUpdate("INSERT INTO mahasiswa VALUES('"+nrp+"', '"+nipDospem+"', '"+nama+"', '"+prodi+"', '"+statusMasuk+"','"+jenisKelamin+"', '"+agama+"', '"+alamat+"', '"+email+"', '"+noHpMhs+"', '"+ayah+"', '"+ktpAyah+"', '"+ibu+"', '"+telpOrtu+"', '"+alamatOrtu+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                clearMahasiswa();
                updateTabelMahasiswa();
            } catch (SQLException ex) {
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
        int dosPem = cbDosenPembimbing.getSelectedIndex();
        String nipDospem = dataDosen[dosPem];
        if(!"".equals(nrp) & !"".equals(nama) & !"".equals(prodi) & !"".equals(jenisKelamin) & !"".equals(agama) & !"".equals(alamat) & !"".equals(email) & !"".equals(noHpMhs) & !"".equals(ayah) & !"".equals(ktpAyah) & !"".equals(ibu) & !"".equals(telpOrtu) & !"".equals(alamatOrtu) & !"".equals(nipDospem)){
            try {
                stm.executeUpdate("UPDATE `mahasiswa` SET `NRP`='"+nrp+"',`NIP_DOSEN`='"+nipDospem+"',`NAMA_MAHASISWA`='"+nama+"',`PRODI`='"+prodi+"',`STATUS_MASUK`='"+statusMasuk+"',`JENIS_KELAMIN`='"+jenisKelamin+"',`AGAMA`='"+agama+"',`ALAMAT`='"+alamat+"',`EMAIL`='"+email+"',`NO_HP`='"+noHpMhs+"',`NAMA_AYAH`='"+ayah+"',`NOMOR_KTP_AYAH`='"+ktpAyah+"',`NAMA_IBU`='"+ibu+"',`TELEPON_ORANG_TUA`='"+telpOrtu+"',`ALAMAT_ORANG_TUA`='"+alamatOrtu+"' WHERE NRP = '"+nrp+"'");
                JOptionPane.showMessageDialog(null, "Data Berhasil Di-update");
                clearMahasiswa();
                updateTabelMahasiswa();
            } catch (SQLException ex) {
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
            sql = "DELETE FROM mahasiswa WHERE NRP = '"+nrp+"'";
            try{
                stm.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                clearMahasiswa();
                updateTabelMahasiswa();
            }catch(SQLException e){
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
            rbPria.setSelected(true);
        }else{
            rbWanita.setSelected(true);
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
        for(int i = 0; i < dataDosen.length; i++){
            if(tabelMahasiswa.getValueAt(row, 14).toString().equals(cbDosenPembimbing.getItemAt(i))){
                cbDosenPembimbing.setSelectedIndex(i);
            }
        }
    }//GEN-LAST:event_tabelMahasiswaMouseClicked

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

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
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearMhs;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteMhs;
    private javax.swing.JButton btnInput;
    private javax.swing.JButton btnInputMahasiswa;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateMhs;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbAgama;
    private javax.swing.JComboBox<String> cbDosenPembimbing;
    private javax.swing.JComboBox<String> cbPendidikanDosen;
    private javax.swing.JComboBox<String> cbProdi;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbStatusKepegawaian;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
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
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel manageDosen;
    private javax.swing.JPanel manageKelas;
    private javax.swing.JPanel manageMahasiswa;
    private javax.swing.JPanel manageMatkul;
    private javax.swing.JMenuItem menuItemMahasiswa;
    private javax.swing.JMenu menuKeluar;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JRadioButton rbPria;
    private javax.swing.JRadioButton rbPriaMhs;
    private javax.swing.JRadioButton rbWanita;
    private javax.swing.JRadioButton rbWanitaMhs;
    private javax.swing.JTable tabelDosen;
    private javax.swing.JTable tabelMahasiswa;
    private javax.swing.JTextField tfAlamatDosen;
    private javax.swing.JTextField tfAlamatMhs;
    private javax.swing.JTextField tfAlamatOrtu;
    private javax.swing.JTextField tfEmailDosen;
    private javax.swing.JTextField tfEmailMhs;
    private javax.swing.JTextField tfJabatanAkademik;
    private javax.swing.JTextField tfKtpAyah;
    private javax.swing.JTextField tfNamaAyah;
    private javax.swing.JTextField tfNamaDosen;
    private javax.swing.JTextField tfNamaIbu;
    private javax.swing.JTextField tfNamaMhs;
    private javax.swing.JTextField tfNidn;
    private javax.swing.JTextField tfNip;
    private javax.swing.JTextField tfNoHpMhs;
    private javax.swing.JTextField tfNrp;
    private javax.swing.JTextField tfTeleponDosen;
    private javax.swing.JTextField tfTelpOrtu;
    private javax.swing.JTextField ttlDosen;
    // End of variables declaration//GEN-END:variables
}
