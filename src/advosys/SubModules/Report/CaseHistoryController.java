/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Report;

import advosys.DataConnection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class CaseHistoryController implements Initializable {

    @FXML
    private ComboBox clientName;
    @FXML
    private ComboBox clientID;
    @FXML
    private ComboBox caseTitle;
    @FXML
    private ComboBox caseID;
    
    @FXML
    private TableView table;
    
    @FXML
    private TableColumn proDate;
    @FXML
    private TableColumn summary;
    @FXML
    private TableColumn hearingDate;
    @FXML
    private TableColumn result;

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
    }

    public static class CaseHistory {

        private final SimpleStringProperty proDate;
        private final SimpleStringProperty summary;
        private final SimpleStringProperty hearingDate;
        private final SimpleStringProperty result;

        private CaseHistory(String fname, String mname, String lname, String mobile) {
            this.proDate = new SimpleStringProperty(fname);
            this.summary = new SimpleStringProperty(mname);
            this.hearingDate = new SimpleStringProperty(lname);
            this.result = new SimpleStringProperty(mobile);

        }

        public String getProDate() {
            return proDate.get();
        }

        public String getSummary() {
            return summary.get();
        }

        public String getHearingDate() {
            return hearingDate.get();
        }

        public String getResult() {
            return result.get();
        }

        public void setProDate(String fname) {
            proDate.set(fname);
        }

        public void setSummary(String fname) {
            summary.set(fname);
        }

        public void setHearingDate(String fname) {
            hearingDate.set(fname);
        }

        public void setResult(String fname) {
            result.set(fname);
        }
    }
    @FXML
    private void handleSearchAction(ActionEvent event){
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection casePro = db.getCollection("Case_proceeding");
        DBCursor doc = casePro.find();

        ObservableList<CaseHistory> data = FXCollections.observableArrayList();

        while (doc.hasNext()) {
            DBObject row = doc.next();
            String firstName = row.get("proceeding_date").toString();
            String middleName = row.get("proceeding_summary").toString();
            String lastName = row.get("next_hearing_date").toString();
            String mobile = row.get("result_date").toString();
            data.add(new CaseHistory(firstName,middleName,lastName,mobile));
        }
        proDate.setCellValueFactory(new PropertyValueFactory<ClientRegisterController.ClientRegister, String>("proDate"));
        summary.setCellValueFactory(new PropertyValueFactory<ClientRegisterController.ClientRegister, String>("summary"));
        hearingDate.setCellValueFactory(new PropertyValueFactory<ClientRegisterController.ClientRegister, String>("hearingDate"));
        result.setCellValueFactory(new PropertyValueFactory<ClientRegisterController.ClientRegister, String>("result"));
        
        table.setItems(data);
        conn.close();
    }
}
