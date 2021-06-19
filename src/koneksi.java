
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author User
 */
public class koneksi {
    Connection con;
    Statement stm;
    
    public void config(){
        try{
            String dbUrl = "jdbc:mysql://localhost/mahasiswa_sakti";
            String username = "root";
            String password = "12345";
            con = DriverManager.getConnection(dbUrl, username, password);
            stm = con.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}