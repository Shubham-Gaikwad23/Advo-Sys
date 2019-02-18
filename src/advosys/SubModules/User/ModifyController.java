/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.User;

import advosys.DataConnection;
import advosys.MainController;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class ModifyController implements Initializable {

    @FXML
    private TextField newUserName;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField retype;
    @FXML
    private Button submit;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws NoSuchAlgorithmException {

        if (newUserName.getText().isEmpty() || newPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection user = db.getCollection("User");

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(newPassword.getText().getBytes());
        byte[] digest = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        String hexDigest = sb.toString();

        BasicDBObject newRow = new BasicDBObject();
        newRow.append("user_name", newUserName.getText());
        newRow.append("password", hexDigest);

        if (newPassword.getText().equals(retype.getText())) {
            DBCursor cur = user.find();
            DBObject oldRow = cur.next();
            user.update(oldRow, newRow);
        } else {
            JOptionPane.showMessageDialog(null, "Error", "Wrong user name or password", JOptionPane.ERROR_MESSAGE);
        }
        conn.close();
        MainController.hideModifyStage();
        JOptionPane.showMessageDialog(null, "User details have been changed", "User details modifications", JOptionPane.INFORMATION_MESSAGE);
    }
}
