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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class EntryController implements Initializable {

    @FXML
    private Label status;
    @FXML
    private ComboBox firstName;
    @FXML
    private ComboBox middleName;
    @FXML
    private ComboBox lastName;
    @FXML
    private ComboBox clientID;
    @FXML
    private TextArea synopsis;
    @FXML
    private TextField caseTitle;
    @FXML
    private TextField caseType;
    @FXML
    private TextField proOfLaw;
    @FXML
    private TextField oppositeCasuse;
    @FXML
    private TextField behalfOf;
    @FXML
    private Button submit;
    @FXML
    private DatePicker date;
   

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
            String cid = doc.get("id").toString();

            firstName.getItems().add(fname);
            middleName.getItems().add(mname);
            lastName.getItems().add(lname);
            clientID.getItems().add(cid);
        }
        conn.close();
        clientID.getSelectionModel().selectFirst();
        firstName.getSelectionModel().selectFirst();
        middleName.getSelectionModel().selectFirst();
        lastName.getSelectionModel().selectFirst();
        date.setValue(LocalDate.now());
    }
    private boolean validate(){
        if(caseTitle.getText().isEmpty()){
            status.setText("Case title is empty");
            return false;
        }
        if(caseType.getText().isEmpty()){
            status.setText("Case type is empty");
            return false;
        }
        if(proOfLaw.getText().isEmpty()){
            status.setText("Provision of law is empty");
            return false;
        }
        if(oppositeCasuse.getText().isEmpty()){
            status.setText("Opposite cause is empty");
            return false;
        }
        if(synopsis.getText().isEmpty()){
            status.setText("Synopsis is empty");
            return false;
        }
        if(behalfOf.getText().isEmpty()){
            status.setText("File on behalf of field is empty");
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void handleSubmitAction(ActionEvent event){
        
        if(!validate())
                return;
        
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection cases = db.getCollection("Cases");
        BasicDBObject doc = new BasicDBObject();
        
        doc.append("title", caseTitle.getText());
        doc.append("type", caseType.getText());
        doc.append("registration_date",date.getValue().toString());
        doc.append("provision_of_law", proOfLaw.getText());
        doc.append("file_on_behalf_of", behalfOf.getText());
        doc.append("opposite_cause", oppositeCasuse.getText());
        doc.append("synopsis", synopsis.getText());
        int id = (int)(Math.random() * 10000);
        doc.append("id", id);
        doc.append("client_id", Integer.parseInt(clientID.getSelectionModel().getSelectedItem().toString()));
        
        cases.insert(doc);
        JOptionPane.showMessageDialog(null,"Your case id is "+id,"Case has been filed",JOptionPane.INFORMATION_MESSAGE);
        MainController.hideCaseEntryStage();
        conn.close();
    }
}
