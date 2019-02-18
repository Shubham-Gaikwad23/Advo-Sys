/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author lenovo
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public LoginController() {
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent clicked) throws NoSuchAlgorithmException {

        DataConnection conn = new DataConnection();  // Connect to database
        DB db = conn.connect();
        //----------Get user name password from database
        DBCollection user = db.getCollection("User");
        DBCursor cursor = user.find();
        DBObject row = cursor.next();
        //-------------------end------------------

        String username = row.get("user_name").toString();
        String dbpassword = row.get("password").toString();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getText().getBytes());
        byte[] digest = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        String hexDigest = sb.toString();

        if (username.equals(this.username.getText()) && dbpassword.equals(hexDigest)) {

            Login.hideStage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException ex) {
                System.err.println("Main.fxml load failed");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Advocate System");
            mainStage.show();
            MainController.setMainStage(mainStage);
        } else {
            JOptionPane.showMessageDialog(null, "User name or password is invalid", "Login failed", JOptionPane.ERROR_MESSAGE);
            Login.toBack();
        }
        conn.close();
    }
}
