/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Client;

import advosys.DataConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class UpdateController implements Initializable {
    
    private int id;
    static private DBObject currentdoc;
    private static Stage updateStage;
    static private boolean edit;
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private TextArea address;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField pincode;
    @FXML
    private TextField salary;
    @FXML
    private TextField occupation;
    @FXML
    private TextField landline;
    @FXML
    private ChoiceBox gender;
    @FXML
    private Label clientID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        edit = false;
        gender.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    @FXML
    private void handleSearchAction(ActionEvent search) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Search.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Search.fxml load failed");
            Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        SearchController.setSearchStage(stage);
        SearchController.setInstance(this);
        SearchController.setUpdateStage(updateStage);
        updateStage.hide();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        updateStage.hide();
    }

    static public void setUpdateStage(Stage stage) {
        updateStage = stage;
    }

    public void loadClientDetails(String fname, String mname, String lname, int cid) {
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("first_name", fname);
        whereQuery.put("last_name", lname);
        whereQuery.put("middle_name", mname);
        whereQuery.put("id", cid);
        DBCursor cursor = clients.find(whereQuery);
        DBObject doc = cursor.next();
        currentdoc = doc;
        id=cid;

        firstName.setText(doc.get("first_name").toString());
        lastName.setText(doc.get("last_name").toString());
        middleName.setText(doc.get("middle_name").toString());
        mobile.setText(doc.get("mobile").toString());
        landline.setText(doc.get("landline").toString());
        email.setText(doc.get("email").toString());
        address.setText(doc.get("address").toString());
        city.setText(doc.get("city").toString());
        state.setText(doc.get("state").toString());
        pincode.setText(doc.get("pincode").toString());
        salary.setText(doc.get("salary").toString());
        occupation.setText(doc.get("occupation").toString());
        clientID.setText("Your (client) ID is "+cid);

        if (doc.get("gender").toString().equals("Male")) {
            gender.getSelectionModel().selectFirst();
        } else {
            gender.getSelectionModel().selectLast();
        }
        conn.close();
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        if (edit) {
            firstName.setEditable(true);
            middleName.setEditable(true);
            lastName.setEditable(true);
            mobile.setEditable(true);
            landline.setEditable(true);
            email.setEditable(true);
            address.setEditable(true);
            city.setEditable(true);
            state.setEditable(true);
            pincode.setEditable(true);
            salary.setEditable(true);
            occupation.setEditable(true);
            gender.setDisable(false);

        } else {
            JOptionPane.showMessageDialog(null, "Client not selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        BasicDBObject newdoc = new BasicDBObject();

        newdoc.append("first_name", firstName.getText());
        newdoc.append("middle_name", middleName.getText());
        newdoc.append("last_name", lastName.getText());
        newdoc.append("mobile", Integer.parseInt(mobile.getText()));
        newdoc.append("landline", Integer.parseInt(landline.getText()));
        newdoc.append("email", email.getText());
        newdoc.append("address", address.getText());
        newdoc.append("salary", Integer.parseInt(salary.getText()));
        newdoc.append("occupation", occupation.getText());
        newdoc.append("gender", (gender.getSelectionModel().getSelectedIndex() == 0) ? "Male" : "Female");
        newdoc.append("city", city.getText());
        newdoc.append("state", state.getText());
        newdoc.append("pincode", Integer.parseInt(pincode.getText()));
        newdoc.append("id", id);

        clients.update(currentdoc, newdoc);
        JOptionPane.showMessageDialog(null, "Client details updated", "Update", JOptionPane.INFORMATION_MESSAGE);
        updateStage.hide();
        conn.close();
    }

    public static void setEditable() {
        edit = true;
    }
}
