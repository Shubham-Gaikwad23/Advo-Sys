/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Account;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class FeesEntryController implements Initializable {

    @FXML
    private Label status;
    @FXML
    private ComboBox clientName;
    @FXML
    private ComboBox clientID;
    @FXML
    private ComboBox caseTitle;
    @FXML
    private ComboBox caseID;
    @FXML
    private ComboBox paymentMode;
    @FXML
    private TextField reciptNo;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amount;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        paymentMode.getItems().addAll("Cash", "Cheque", "Bank Transfer", "Other");

        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        DBCollection cases = db.getCollection("Cases");

        DBCursor clientDoc = clients.find();
        DBCursor caseDoc = cases.find();

        while (clientDoc.hasNext()) {
            DBObject row = clientDoc.next();
            clientName.getItems().add(row.get("first_name").toString() + " " + row.get("middle_name").toString() + " " + row.get("last_name").toString());
            clientID.getItems().add(row.get("id"));
        }
        while (caseDoc.hasNext()) {
            DBObject row = caseDoc.next();
            caseTitle.getItems().add(row.get("title").toString());
            caseID.getItems().add(row.get("id"));
        }
        conn.close();
        clientName.getSelectionModel().selectFirst();
        clientID.getSelectionModel().selectFirst();
        caseTitle.getSelectionModel().selectFirst();
        caseID.getSelectionModel().selectFirst();
        paymentMode.getSelectionModel().selectFirst();
        date.setValue(LocalDate.now());
    }
    
    private boolean validate(){
        if(reciptNo.getText().isEmpty()){
            status.setText("Recipt No is empty");
            return false;
        }
        if(amount.getText().isEmpty()){
            status.setText("Amount is empty");
            return false;
        }
        try{
            Integer.parseInt(reciptNo.getText());
        }
        catch(NumberFormatException ex){
            status.setText("Recipt number must be numeric");
            return false;
        }
        try{
            Integer.parseInt(amount.getText());
        }
        catch(NumberFormatException ex){
            status.setText("Amount must be numeric");
            return false;
        }
        return true;
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        if(!validate())
            return ;
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection acc = db.getCollection("Account");
        BasicDBObject doc = new BasicDBObject();

        doc.append("recipt_no", reciptNo.getText());
        doc.append("date", date.getValue().toString());
        doc.append("amount", amount.getText());
        doc.append("payment_mode", paymentMode.getSelectionModel().getSelectedItem().toString());

        acc.insert(doc);
        MainController.hideFeesStage();
        conn.close();
        JOptionPane.showMessageDialog(null, "Account information updated");

    }

}
