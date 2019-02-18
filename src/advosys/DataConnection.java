/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advosys;

import java.net.UnknownHostException;
import com.mongodb.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lenovo
 */
public class DataConnection {

    MongoClient client = null;
    DB db;

    public DB connect() {

        try {
            client = new MongoClient("localhost", 27017);
        } catch (UnknownHostException ex) {
            System.err.println("Unknown Host Exception");
            JOptionPane.showMessageDialog(null, "Database connection failed", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        db = client.getDB("db");
        return db;
    }
    public void close(){
        client.close();
    }
}
