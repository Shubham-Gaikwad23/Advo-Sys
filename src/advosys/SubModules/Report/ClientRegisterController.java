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
public class ClientRegisterController implements Initializable {

    @FXML
    private TableView table;
    
    @FXML
    private TableColumn firstName;
    @FXML
    private TableColumn middleName;
    @FXML
    private TableColumn lastName;
    @FXML
    private TableColumn mobile;
    @FXML
    private TableColumn email;
    @FXML
    private TableColumn city;
    @FXML
    private TableColumn state;
    @FXML
    private TableColumn pincode;
    @FXML
    private TableColumn salary;
    @FXML
    private TableColumn gender;
       
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
        DBCursor doc = clients.find();

        ObservableList<ClientRegister> data = FXCollections.observableArrayList();

        while (doc.hasNext()) {
            DBObject row = doc.next();
            String firstName = row.get("first_name").toString();
            String middleName = row.get("middle_name").toString();
            String lastName = row.get("last_name").toString();
            String mobile = row.get("mobile").toString();
            String email = row.get("email").toString();
            String city = row.get("city").toString();
            String state = row.get("state").toString();
            String pincode = row.get("pincode").toString();
            String salary = row.get("salary").toString();
            String gender = row.get("gender").toString();
            data.add(new ClientRegister(firstName,middleName,lastName,mobile,email,city,state,pincode,salary,gender));
        }
        firstName.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("firstName"));
        middleName.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("middleName"));
        lastName.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("lastName"));
        mobile.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("mobile"));
        email.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("email"));
        city.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("city"));
        state.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("state"));
        pincode.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("pincode"));
        gender.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("gender"));
        salary.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("salary"));
        gender.setCellValueFactory(new PropertyValueFactory<ClientRegister, String>("gender"));
        table.setItems(data);
        conn.close();
    }

    public static class ClientRegister {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty middleName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        private final SimpleStringProperty city;
        private final SimpleStringProperty state;
        private final SimpleStringProperty pincode;
        private final SimpleStringProperty salary;
        private final SimpleStringProperty gender;

        private ClientRegister(String fname, String mname, String lname, String mobile, String email, String city, String state, String pincode, String salary,String gender) {
            this.firstName = new SimpleStringProperty(fname);
            this.middleName = new SimpleStringProperty(mname);
            this.lastName = new SimpleStringProperty(lname);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            this.city = new SimpleStringProperty(city);
            this.state = new SimpleStringProperty(state);
            this.pincode = new SimpleStringProperty(pincode);
            this.salary = new SimpleStringProperty(salary);
            this.gender = new SimpleStringProperty(gender);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public String getMiddleName() {
            return middleName.get();
        }

        public String getLastName() {
            return lastName.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getCity() {
            return city.get();
        }

        public String getState() {
            return state.get();
        }

        public String getPincode() {
            return pincode.get();
        }

        public String getSalary() {
            return salary.get();
        }
        
        public String getGender(){
            return gender.get();
        }

        public void setFirstName(String fname) {
            firstName.set(fname);
        }

        public void setMiddleName(String fname) {
            middleName.set(fname);
        }

        public void setLastName(String fname) {
            lastName.set(fname);
        }

        public void setMobile(String fname) {
            mobile.set(fname);
        }

        public void setEmail(String fname) {
            email.set(fname);
        }

        public void setCity(String fname) {
            city.set(fname);
        }

        public void setState(String fname) {
            state.set(fname);
        }

        public void setPincode(String fname) {
            pincode.set(fname);
        }

        public void setSalary(String fname) {
            salary.set(fname);
        }
        public void setGender(String fname){
            gender.set(fname);
        }
    }
}
