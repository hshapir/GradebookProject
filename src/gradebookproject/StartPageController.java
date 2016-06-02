/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class StartPageController implements Initializable, ChangeListener<String> {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox chooseClass;
    
    public void changed(ObservableValue ov, String value, String newValue) {
         try {
            GradebookProject.getGradebookInstance(chooseClass.getValue().toString()).showClass();
        } catch (IOException ex) {
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    @FXML
    private MenuItem addClass;
    
    @FXML
    private MenuItem renameClass;
    
    @FXML
    private MenuItem deleteClass;
    
    private List<ClassSection> allClasses;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooseClass.getSelectionModel().selectedItemProperty().addListener(this);
        allClasses = GradebookProject.returnAllClasses();
        updateList();
    }   
     
    
    public void updateList(){
         chooseClass.setItems(GradebookProject.getClassSectionNamesObservable());
         //chooseClass.setValue(GradebookProject.getClassSectionNamesObservable().get(0));
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
                updateList();
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
            newNameDialog.setContentText("Class's New Name:");
            Optional<String> resultName = newNameDialog.showAndWait();
            if (resultName.isPresent()){
                if(GradebookProject.findClass(resultName.get()) != null){
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
    
    public void close() {
        Platform.exit();
    }
}
