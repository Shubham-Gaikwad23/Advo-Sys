/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys;

import advosys.SubModules.About.*;
import advosys.SubModules.Report.*;
import advosys.SubModules.Report.ClientRegisterController;
import advosys.SubModules.Client.UpdateController;
import advosys.SubModules.Account.FeesEntryController;
import advosys.SubModules.Case.InfoController;
import advosys.SubModules.Case.ProceedingController;
import advosys.SubModules.Case.EntryController;
import advosys.SubModules.User.ModifyController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class MainController implements Initializable {

    static Stage clientEntryStage,mainStage,caseEntryStage,proceedingStage,FeesStage,modifyStage;
    

    private static Stage infoStage;
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public static void setMainStage(Stage stage){
        mainStage=stage;
    }

    public static void hideProceedingStage(){
        proceedingStage.hide();
    }
    public static void hideFeesStage(){
        FeesStage.hide();
    }
    public static void hideModifyStage(){
        modifyStage.hide();
    }
    
    //------------------- User menu------------------------
    @FXML
    private void handleModifyUserAction(ActionEvent chosen) {
        FXMLLoader fxmlLoader = new FXMLLoader(ModifyController.class.getResource("Modify.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Modify.fxml load failed");
            Logger.getLogger(ModifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Change user details");
        stage.show();
        modifyStage=stage;
    }

    //---------------End of User menu ---------------------
    //---------------Client menu---------------------------
    @FXML
    private void handleCreateNewClientAction(ActionEvent entry) {
        FXMLLoader fxmlLoader = new FXMLLoader(advosys.SubModules.Client.EntryController.class.getResource("Entry.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Entry.fxml load failed");
            Logger.getLogger(advosys.SubModules.Client.EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage clientEntryStage = new Stage();
        clientEntryStage.setScene(new Scene(root));
        clientEntryStage.setTitle("Client Entry");
        clientEntryStage.show();
        this.clientEntryStage = clientEntryStage;
        advosys.SubModules.Client.EntryController.setEntryStage(clientEntryStage); 
       
        advosys.SubModules.Client.EntryController.setMainStage(mainStage);
        
    }

    public static void hideClientEntryStage() {
        clientEntryStage.hide();
    }

    @FXML
    private void handleUpdateClientAction(ActionEvent update) {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateController.class.getResource("Update.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Update.fxml load failed");
            Logger.getLogger(advosys.SubModules.Client.EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage updateStage = new Stage();
        updateStage.setScene(new Scene(root));
        updateStage.setTitle("Client Information");
        updateStage.show();
        UpdateController.setUpdateStage(updateStage);
    }

    //---------------------End of Client menu -------------------------
    //---------------------Case menu-----------------------------------
    @FXML
    private void handleCreateNewCaseAction(ActionEvent create) {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryController.class.getResource("Entry.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Entry.fxml load failed");
            Logger.getLogger(EntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Case Entry");
        stage.show();
        caseEntryStage=stage;
    }
    
    public static void hideCaseEntryStage(){
        caseEntryStage.hide();
    }

    @FXML
    private void handleCaseProceedingAction(ActionEvent proceeding) {
        FXMLLoader fxmlLoader = new FXMLLoader(ProceedingController.class.getResource("Proceeding.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Proceeding.fxml load failed");
            Logger.getLogger(ProceedingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Case Proceeding");
        stage.show();
        proceedingStage=stage;
    }

    @FXML
    private void handleCaseInfoAction(ActionEvent info) {
        FXMLLoader fxmlLoader = new FXMLLoader(InfoController.class.getResource("Info.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("Info.fxml load failed");
            Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Case Information");
        stage.show();
        infoStage=stage;
    }

    //----------------------End of case menu--------------------------
    //----------------------Account menu---------------------------
    @FXML
    private void handleFeesEntryAction(ActionEvent fees) {
        FXMLLoader fxmlLoader = new FXMLLoader(FeesEntryController.class.getResource("FeesEntry.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("FeesEntry.fxml load failed");
            Logger.getLogger(FeesEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Fees Entry");
        stage.show();
        FeesStage=stage;
    }

    //------------------------End of Account menu-----------------------
    //------------------------Report Menu-------------------------------
    @FXML
    private void handleClientRegisterAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientRegisterController.class.getResource("ClientRegister.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("ClientRegister.fxml load failed");
            Logger.getLogger(ClientRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Client Register");
        stage.show();
    }

    @FXML
    private void handleCaseRegisterAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(CaseRegisterController.class.getResource("CaseRegister.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("CaseRegister.fxml load failed");
            Logger.getLogger(CaseRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Case Register");
        stage.show();
    }

    @FXML
    private void handleCaseHistoryAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(CaseRegisterController.class.getResource("CaseHistory.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("CaseHistory.fxml load failed");
            Logger.getLogger(CaseHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Case History");
        stage.show();
    }

    //-------------------------End of report menu--------------------------
    //---------------------------About menu--------------------------------
    @FXML
    private void handleAboutProjectAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AboutProjectController.class.getResource("AboutProject.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println("AboutProject.fxml load failed");
            Logger.getLogger(AboutProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("About this project");
        stage.show();
    }
    
    public static void closeInfoStage(){
        infoStage.hide();
    }
}
