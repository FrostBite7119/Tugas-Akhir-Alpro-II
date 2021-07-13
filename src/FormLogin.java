import javax.swing.JOptionPane;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author INI LAPTOP
 */
public class FormLogin extends javax.swing.JFrame {

    Connection conn;
    Statement stm;
    ResultSet rs;
    public FormLogin() {
        initComponents();
        koneksi DB = new koneksi();
        DB.config();
        conn = DB.con;
        stm = DB.stm;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbexitadmin = new javax.swing.JLabel();
        tfuname = new javax.swing.JTextField();
        tfpw = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btlogin = new javax.swing.JButton();
        btnBeralih = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 500));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbexitadmin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbexitadmin.setForeground(new java.awt.Color(255, 255, 255));
        lbexitadmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbexitadmin.setText("EXIT");
        lbexitadmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbexitadminMouseClicked(evt);
            }
        });
        getContentPane().add(lbexitadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 60, 40));

        tfuname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfuname.setBorder(null);
        tfuname.setOpaque(false);
        getContentPane().add(tfuname, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 210, 30));

        tfpw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfpw.setBorder(null);
        tfpw.setOpaque(false);
        getContentPane().add(tfpw, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 210, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg/1.jpg"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(800, 500));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        btlogin.setText("LOGIN");
        btlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btloginActionPerformed(evt);
            }
        });
        getContentPane().add(btlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 280, 40));

        btnBeralih.setText("Beralih Ke Mahasiswa");
        btnBeralih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeralihActionPerformed(evt);
            }
        });
        getContentPane().add(btnBeralih, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 260, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btloginActionPerformed
        // TODO add your handling code here:
        try {
            rs = stm.executeQuery("SELECT * FROM login WHERE username='"+tfuname.getText()+"' AND password='"+tfpw.getText()+"'");
            if (rs.next()){
                if(tfuname.getText().equals(rs.getString("username"))
                && tfpw.getText().equals(rs.getString("password"))) {
                ManageData menu = new ManageData();
                menu.setVisible(true);
                this.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Maaf, kombinasi" +
                " Username dan Password anda salah");
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btloginActionPerformed

    private void btnBeralihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeralihActionPerformed
        // TODO add your handling code here:
        new FormLoginMhs().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBeralihActionPerformed

    private void lbexitadminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbexitadminMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_lbexitadminMouseClicked

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
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btlogin;
    private javax.swing.JButton btnBeralih;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbexitadmin;
    private javax.swing.JPasswordField tfpw;
    private javax.swing.JTextField tfuname;
    // End of variables declaration//GEN-END:variables
}
