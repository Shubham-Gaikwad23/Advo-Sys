/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys.SubModules.Client;

import advosys.DataConnection;
import advosys.MainController;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class EntryController implements Initializable {

    static Stage entryStage,mainStage;
    @FXML
    private Label status;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.setItems(FXCollections.observableArrayList("Male", "Female"));
        gender.getSelectionModel().selectFirst();
    }

    public static void setEntryStage(Stage stage) {
        entryStage = stage;
    }
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    private boolean validate(){
        String fname,mname,lname,email,address,city,state,occupation, mobile,pincode,landline,salary;
        
        fname=firstName.getText();
        mname=middleName.getText();
        lname=lastName.getText();
        email=this.email.getText();
        address=this.address.getText();
        city=this.city.getText();
        state=this.state.getText();
        occupation=this.occupation.getText();
        mobile=this.mobile.getText();
        landline=this.landline.getText();
        pincode=this.pincode.getText();
        salary=this.salary.getText();
        
        
        if(fname.isEmpty()){
            status.setText("First name is empty");
            return false;
        }
        if(mname.isEmpty()){
            status.setText("Middle name is empty");
            return false;
        }
        if(lname.isEmpty()){
            status.setText("Last name is empty");
            return false;
        }
        if(email.isEmpty()){
            status.setText("Email is empty");
            return false;
        }
        if(address.isEmpty()){
            status.setText("address is empty");
            return false;
        }
        if(city.isEmpty()){
            status.setText("City name is empty");
            return false;
        }
        if(state.isEmpty()){
            status.setText("State field is empty");
            return false;
        }
        if(occupation.isEmpty()){
            status.setText("Occupation field is empty");
            return false;
        }
        if(salary.isEmpty()){
            status.setText("Salary is empty");
            return false;
        }
        if(pincode.isEmpty()){
            status.setText("Pincode is empty");
            return false;
        }
        if(mobile.isEmpty()){
            status.setText("Mobile phone number is empty");
            return false;
        }
        if(landline.isEmpty()){
            status.setText("Landline number is empty");
            return false;
        }
        try{
            Integer.parseInt(mobile);
        }
        catch(NumberFormatException err){
            status.setText("Mobile number should be numeric");
            return false;
        }
        try{
            Integer.parseInt(landline);
        }
        catch(NumberFormatException err){
            status.setText("Landline number should be numeric");
            return false;
        }
        try{
            Integer.parseInt(salary);
        }
        catch(NumberFormatException err){
            status.setText("Salary should be numeric");
            return false;
        }
        try{
            Integer.parseInt(pincode);
        }
        catch(NumberFormatException err){
            status.setText("Pincode number should be numeric");
            return false;
        }
        if(mobile.length()>10){
            status.setText("Invalid mobile number");
            return false;
        }
        if(landline.length()>11){
            status.setText("Invalid landline number");
            return false;
        }
        if(pincode.length()!=6){
            status.setText("Invalid pincode number");
            return false;
        }
        String emailregex;
        emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!email.matches(emailregex)){
            status.setText("Invalid email address");
            return false;
        }
        return true;
    }
    
    @FXML
    private void handleClientEntryAction(ActionEvent event) {
        
        if(!validate())
            return;
        
        DataConnection conn = new DataConnection();
        DB db = conn.connect();
        DBCollection clients = db.getCollection("Clients");
        BasicDBObject doc = new BasicDBObject();

        doc.append("first_name", firstName.getText());
        doc.append("middle_name", middleName.getText());
        doc.append("last_name", lastName.getText());
        doc.append("mobile", Integer.parseInt(mobile.getText()));
        doc.append("landline", Integer.parseInt(landline.getText()));
        doc.append("email", email.getText());
        doc.append("address", address.getText());
        doc.append("salary", Integer.parseInt(salary.getText()));
        doc.append("occupation", occupation.getText());
        doc.append("gender", (gender.getSelectionModel().getSelectedIndex() == 0) ? "Male" : "Female");
        doc.append("city", city.getText());
        doc.append("state", state.getText());
        doc.append("pincode", Integer.parseInt(pincode.getText()));
        int cid=(int) (Math.random()*20000);
        doc.append("id",cid );

        clients.insert(doc);
        MainController.hideClientEntryStage();
        JOptionPane.showMessageDialog(null, "Your (client) ID is : "+cid, "Client added", JOptionPane.INFORMATION_MESSAGE);
        conn.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        entryStage.hide();
    }

}
