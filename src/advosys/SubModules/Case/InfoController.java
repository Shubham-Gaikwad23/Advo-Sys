/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Case;

import advosys.DataConnection;
import advosys.MainController;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class InfoController implements Initializable {

    @FXML
    private ComboBox firstName;
    @FXML
    private ComboBox middleName;
    @FXML
    private ComboBox lastName;
    @FXML
    private ComboBox clientID;
    @FXML
    private ComboBox caseID;
    
    @FXML
    private TextField caseTitle;
    @FXML
    private TextField caseType;
    @FXML
    private TextField proOfLaw;
    @FXML
    private TextField behalfOf;
    @FXML
    private TextField oppositeCause;
    @FXML
    private TextField date;
    @FXML
    private TextArea synopsis;
    @FXML
    private Button close;
    @FXML
    private Button search;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        DBCollection cases = db.getCollection("Cases");
        
        DBCursor clientDoc = clients.find();
        DBCursor casesDoc = cases.find();
        
        while(clientDoc.hasNext()){
            DBObject clientRow = clientDoc.next();
            firstName.getItems().add(clientRow.get("first_name"));
            middleName.getItems().add(clientRow.get("middle_name"));
            lastName.getItems().add(clientRow.get("last_name"));
            clientID.getItems().add(clientRow.get("id"));
        }
        while(casesDoc.hasNext()){
            DBObject caseRow = casesDoc.next();
            caseID.getItems().add(caseRow.get("id"));
        }
        conn.close();
        firstName.getSelectionModel().selectFirst();
        middleName.getSelectionModel().selectFirst();
        lastName.getSelectionModel().selectFirst();
        caseID.getSelectionModel().selectFirst();
        clientID.getSelectionModel().selectFirst();
    }
    @FXML
    private void handleSearchAction(ActionEvent event){
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection cases = db.getCollection("Cases");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("client_id", Integer.parseInt(clientID.getSelectionModel().getSelectedItem().toString()));
        searchQuery.put("id", Integer.parseInt(caseID.getSelectionModel().getSelectedItem().toString()));
        
        DBCursor casesDoc = cases.find(searchQuery);
        DBObject row = casesDoc.next();
        caseTitle.setText(row.get("title").toString());
        caseType.setText(row.get("type").toString());
        proOfLaw.setText(row.get("provision_of_law").toString());
        behalfOf.setText(row.get("file_on_behalf_of").toString());
        oppositeCause.setText(row.get("opposite_cause").toString());
        synopsis.setText(row.get("synopsis").toString());
        date.setText(row.get("registration_date").toString());
        conn.close();
    }
    @FXML
    private void handleCloseAction(ActionEvent event){
        MainController.closeInfoStage();
    }
}
