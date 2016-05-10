/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

/**
 *
 * @author csstudent
 */
public class UserInterfaceController implements Initializable {
    
    private static List<ClassSection> sections;
    
    @FXML
    private GridPane gradebook;
    
    @FXML
    private MenuItem closeButton;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void close() {
        System.exit(0);
    }
    
}
