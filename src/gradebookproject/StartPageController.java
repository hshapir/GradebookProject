/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author csstudent
 */
public class StartPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox chooseClass;
    
    @FXML
    private MenuItem addClass;
    
    @FXML
    private MenuItem renameClass;
    
    @FXML
    private MenuItem deleteClass;
    
    private List allClasses;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allClasses = GradebookProject.returnAllClasses();
        updateList();
    }    
    
    public void updateList(){
         chooseClass = new ChoiceBox();
         for(String s : GradebookProject.getNames()){
             chooseClass.getItems().addAll(s);
         }
         
    }
    
    public void addClass(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Class");
        dialog.setHeaderText("");
        dialog.setContentText("Class Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(GradebookProject.findClass(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Class Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two classes with the same name. Please enter an unused class name or add a '#1' or '#2'");
                invalidNameAlert.showAndWait();
                
            } else{
                GradebookProject.addClass(new ClassSection(result.get()));
            }
            
        }
        updateList();
    }
    
    
    public void renameClass(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", GradebookProject.getNames());
        dialog.setTitle("Rename Class");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Class:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            TextInputDialog newNameDialog = new TextInputDialog("");
            newNameDialog.setTitle("Rename Class");
            newNameDialog.setHeaderText("");
            TextField newName = new TextField();
            newName.setPromptText("New Name");
            newNameDialog.setContentText("Assignment's New Name:");
            Optional<String> resultName = newNameDialog.showAndWait();
            if (resultName.isPresent()){
                if(GradebookProject.findClass(result.get()) != null){
                    Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                    invalidNameAlert.setTitle("Invalid Class Name");
                    invalidNameAlert.setHeaderText(null);
                    invalidNameAlert.setContentText("You cannot have two classes with the same name. Please enter an unused name.");
                    invalidNameAlert.showAndWait();
                } else{
                    GradebookProject.findClass(result.get()).setName(resultName.get());
                }
            }
        }
        updateList();
    }
    
    public void deleteClass(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", GradebookProject.getNames());
        dialog.setTitle("Rename Class");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Class:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            GradebookProject.removeClass(result.get());
        }
        updateList();
    }
    
}
