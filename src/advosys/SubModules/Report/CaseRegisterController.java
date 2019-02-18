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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class CaseRegisterController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TableColumn caseTitle;
    @FXML
    private TableColumn clientID;
    @FXML
    private TableColumn proLaw;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn cause;
    
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
        DBCollection cases = db.getCollection("Cases");
        DBCursor doc = cases.find();

        ObservableList<CaseRegister> data = FXCollections.observableArrayList();

        while (doc.hasNext()) {
            DBObject row = doc.next();
            String caseTitle = row.get("title").toString();
            String clientID = row.get("client_id").toString();
            String proLaw = row.get("provision_of_law").toString();
            String cause = row.get("opposite_cause").toString();
            String date = row.get("registration_date").toString();
            
            data.add(new CaseRegister(caseTitle,clientID,proLaw,cause,date));
        }
        caseTitle.setCellValueFactory(new PropertyValueFactory<CaseRegister, String>("caseTitle"));
        clientID.setCellValueFactory(new PropertyValueFactory<CaseRegister, String>("clientID"));
        proLaw.setCellValueFactory(new PropertyValueFactory<CaseRegister, String>("proLaw"));
        cause.setCellValueFactory(new PropertyValueFactory<CaseRegister, String>("cause"));
        date.setCellValueFactory(new PropertyValueFactory<CaseRegister, String>("date"));
        table.setItems(data);
        conn.close();
    }
    public static class CaseRegister {

        private final SimpleStringProperty caseTitle;
        private final SimpleStringProperty clientID;
        private final SimpleStringProperty proLaw;
        private final SimpleStringProperty cause;
        private final SimpleStringProperty date;

        private CaseRegister(String caseTitle, String clientID, String proLaw, String cause, String date) {
            this.caseTitle = new SimpleStringProperty(caseTitle);
            this.clientID = new SimpleStringProperty(clientID);
            this.proLaw = new SimpleStringProperty(proLaw);
            this.cause = new SimpleStringProperty(cause);
            this.date = new SimpleStringProperty(date);
        }

        public String getCaseTitle() {
            return caseTitle.get();
        }

        public String getClientID() {
            return clientID.get();
        }

        public String getProLaw() {
            return proLaw.get();
        }

        public String getCause() {
            return cause.get();
        }

        public String getDate() {
            return date.get();
        }

        
        public void setCaseTitle(String fname) {
            caseTitle.set(fname);
        }

        public void setClientID(String fname) {
            clientID.set(fname);
        }

        public void setProLaw(String fname) {
            proLaw.set(fname);
        }

        public void setCause(String fname) {
            cause.set(fname);
        }

        public void setDate(String fname) {
            date.set(fname);
        }
    }
}
