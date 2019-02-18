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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class ProceedingController implements Initializable {

    @FXML
    private Label status;
    @FXML
    private ComboBox clientID;
    @FXML
    private ComboBox caseID;
    @FXML
    private ComboBox clientName;
    @FXML
    private ComboBox caseTitle;
    @FXML
    private Button submit;
    @FXML
    private TextArea synopsis;
    @FXML
    private TextArea summary;
    @FXML
    private TextArea result;
    @FXML
    private DatePicker proceedingDate;
    @FXML
    private DatePicker hearingDate;
    @FXML
    private DatePicker disposalDate;

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
        DBCollection cases = db.getCollection("Cases");
        
        DBCursor clientDoc = clients.find();
        DBCursor caseDoc = cases.find();
        
        while(clientDoc.hasNext()){
            DBObject row = clientDoc.next();
            clientName.getItems().add(row.get("first_name").toString()+" "+row.get("middle_name").toString()+" "+row.get("last_name").toString());
            clientID.getItems().add(row.get("id"));
        }
        while(caseDoc.hasNext()){
            DBObject row = caseDoc.next();
            caseTitle.getItems().add(row.get("title").toString());
            caseID.getItems().add(row.get("id"));
        }
        conn.close();
        clientName.getSelectionModel().selectFirst();
        clientID.getSelectionModel().selectFirst();
        caseTitle.getSelectionModel().selectFirst();
        caseID.getSelectionModel().selectFirst();
        proceedingDate.setValue(LocalDate.now());
        hearingDate.setValue(LocalDate.now());
        disposalDate.setValue(LocalDate.now());
    }
    
    private boolean validate(){
        if(synopsis.getText().isEmpty()){
            status.setText("Synopsis is empty");
            return false;
        }
        if(summary.getText().isEmpty()){
            status.setText("Summary is empty");
            return false;
        }
        if(result.getText().isEmpty()){
            status.setText("Result is empty");
            return false;
        }
        return true;
    }
    
    @FXML
    private void handleSubmitAction(ActionEvent event){
        if(!validate())
            return ;
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection proced = db.getCollection("Case_proceeding");
        BasicDBObject doc = new BasicDBObject();
        
        doc.append("synopsis", synopsis.getText());
        doc.append("proceeding_date", proceedingDate.getValue().toString());
        doc.append("proceeding_summary", summary.getText());
        doc.append("next_hearing_date",hearingDate.getValue().toString());
        doc.append("disposal_date", disposalDate.getValue().toString());
        doc.append("result_date", result.getText());
        
        proced.insert(doc);
        
        MainController.hideProceedingStage();
        conn.close();
        JOptionPane.showMessageDialog(null, "The proceeding information is of case is added", "Proceeding", JOptionPane.INFORMATION_MESSAGE);
    }
}
