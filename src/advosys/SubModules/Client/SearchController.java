/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Client;

import advosys.DataConnection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class SearchController implements Initializable {

    static Stage searchStage, updateStage;
    @FXML
    private ComboBox firstName;
    @FXML
    private ComboBox middleName;
    @FXML
    private ComboBox lastName;
    @FXML
    private ComboBox client_id;
    

    static UpdateController instance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        DBCursor cursor = clients.find();

        DBObject doc;

        while (cursor.hasNext()) {
            doc = cursor.next();
            String fname = doc.get("first_name").toString();
            String mname = doc.get("middle_name").toString();
            String lname = doc.get("last_name").toString();
            String id = doc.get("id").toString();

            firstName.getItems().add(fname);
            middleName.getItems().add(mname);
            lastName.getItems().add(lname);
            client_id.getItems().add(id);
            conn.close();
        }
    }

    static public void setInstance(UpdateController inst) {
        instance = inst;
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String fname = firstName.getSelectionModel().getSelectedItem().toString();
        String mname = middleName.getSelectionModel().getSelectedItem().toString();
        String lname = lastName.getSelectionModel().getSelectedItem().toString();
        int id = Integer.parseInt(client_id.getSelectionModel().getSelectedItem().toString());
                
        
        instance.loadClientDetails(fname, mname, lname,id);
        searchStage.hide();
        updateStage.show();
        UpdateController.setEditable();
    }

    static void setSearchStage(Stage stage) {
        searchStage = stage;
    }

    static void setUpdateStage(Stage stage) {
        updateStage = stage;
    }
}
