/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;



/**
 *
 * @author csstudent
 */
public class UserInterfaceController implements Initializable {
    
    private static ClassSection currentSection;
    
    @FXML
    private TableView gradebook;
    
    @FXML
    private MenuItem closeButton;
    
    @FXML
    private MenuItem addStudentButton;
    
    @FXML
    private MenuItem SaveButton;
    
    @FXML
    private MenuItem deleteStudentButton;
    
    @FXML
    private MenuItem renameStudentButton;
    
    @FXML
    private MenuItem deleteAssignmentButton;
    
    @FXML
    private MenuItem createAssignmentButton;
    
    @FXML
    private MenuItem changeAssignmentButton;
    
    @FXML
    private MenuItem backToStartButton;
    
    public void toStart() {
        try{
            GradebookProject.getGradebookInstance(currentSection.toString()).showStart();
        } catch (IOException e){}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentSection = GradebookProject.getCurrentSection();
        /** This just creates some tester students and assignments
         * and won't be present in the actual program
        */
        
        updateTable();
    }    
    
    public void updateTable(){
        //This prevents the table from creating a new table without deleting the old one
        gradebook.getColumns().removeAll(gradebook.getColumns());
        //This sets the data set to the 
        gradebook.setItems(currentSection.getObservableStudentMap());
        gradebook.setEditable(true);
        TableColumn<Map, String> students = new TableColumn<>("Students");
        students.setCellValueFactory(new MapValueFactory("Students"));
        students.setCellFactory(TextFieldTableCell.forTableColumn());
        students.setOnEditCommit(
                new EventHandler<CellEditEvent<Map, String>>() {
                @Override
                    public void handle(CellEditEvent<Map, String> t) {
                        String oldName = t.getTableView().getItems().get(t.getTablePosition().getRow()).get("Students").toString();
                        Student editedStudent = currentSection.findStudent(oldName);
                        editedStudent.setName(t.getNewValue());
                    }
                }
        
        );
        gradebook.getColumns().addAll(students);
        
        for(Assignment a : currentSection.getAssignments()){
            TableColumn<Map, String> newColumn = new TableColumn<>(a.toString());
            newColumn.setCellValueFactory(new MapValueFactory(a.toString()));
            newColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            newColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Map, String>>() {
                @Override
                    public void handle(CellEditEvent<Map, String> t) {
                        String studentName = t.getTableView().getItems().get(t.getTablePosition().getRow()).get("Students").toString();
                        Student modifiedGradeStudent = currentSection.findStudent(studentName);
                        a.setGrade(modifiedGradeStudent, Double.parseDouble(t.getNewValue()));
                        modifiedGradeStudent.updateAverage();
                        updateTable();
                    }
                }
        
        );
            gradebook.getColumns().addAll(newColumn);
            
        }
        TableColumn<Map, String> averageScores = new TableColumn<>("Average Score");
        averageScores.setCellValueFactory(new MapValueFactory("Average Score"));
        gradebook.getColumns().addAll(averageScores);
    }
    
    
        
    public void close() {
        Platform.exit();
    }
    
    public void save(){
        GradebookProject.save();
    }
    
    public void editAssignment() {
        currentSection.getAssignments();
        updateTable();
    }
    
    public void addStudent(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Student");
        dialog.setHeaderText("");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(currentSection.findStudent(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Student Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two students with the same name. Please enter an unused student name or add a '#1' or '#2'");
                invalidNameAlert.showAndWait();
                addStudent();
            } else{
                currentSection.addStudent(new Student(currentSection, result.get()));
            }
            updateTable();
        }
    }
    
    public void deleteStudent(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", currentSection.getNames());
        dialog.setTitle("Delete Student");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Student:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            currentSection.removeStudent(currentSection.findStudent(result.get()));
        }
        updateTable();
        
    }
    
    public void renameStudent(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", currentSection.getNames());
        dialog.setTitle("Select Student");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Student to Rename:");
        Optional<String> oldName = dialog.showAndWait();
        if(oldName.isPresent()){
            TextInputDialog newNameDialog = new TextInputDialog("");
            newNameDialog.setTitle("Rename Student");
            newNameDialog.setHeaderText("");
            TextField newName = new TextField();
            newName.setPromptText("Username");
            newNameDialog.setContentText("Student's New Name:");
            Optional<String> result = newNameDialog.showAndWait();
            if (result.isPresent()){
                if(currentSection.findStudent(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Student Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two students with the same name. Please enter an unused student name or add a '#1' or '#2'");
                invalidNameAlert.showAndWait();
                } else{
                currentSection.findStudent(oldName.get()).setName(result.get());
                }
            }
        }
        updateTable();
    }
    
    public void deleteAssignment(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", currentSection.getAssignmentNames());
        dialog.setTitle("Delete Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Assignment:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            currentSection.removeAssignment(currentSection.findAssignment(result.get()));
        }
        
        updateTable();
        
    }
    
    public void createAssignment(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("New Assignment Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(currentSection.findAssignment(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Assignment Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two assignments with the same name. Please enter an unused name or a date or number, like 'Test #2' or 'Homework 5/18'");
                invalidNameAlert.showAndWait();
                createAssignment();
            } else{
                currentSection.addAssignment(new Assignment(currentSection, result.get()));
            }
            updateTable();
        }
    }
    
    public void changeAssignment(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", currentSection.getAssignmentNames());
        dialog.setTitle("Select Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Assignment to Rename:");
        Optional<String> oldName = dialog.showAndWait();
        if(oldName.isPresent()){
            TextInputDialog newNameDialog = new TextInputDialog("");
            newNameDialog.setTitle("Rename Assignment");
            newNameDialog.setHeaderText("");
            TextField newName = new TextField();
            newName.setPromptText("New Name");
            newNameDialog.setContentText("Assignment's New Name:");
            Optional<String> result = newNameDialog.showAndWait();
            if (result.isPresent()){
                if(currentSection.findAssignment(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Assignment Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two assignments with the same name. Please enter an unused name or a date or number, like 'Test #2' or 'Homework 5/18'");
                invalidNameAlert.showAndWait();
                } else{
                currentSection.findAssignment(oldName.get()).setName(result.get());
                }
            }
        }
        updateTable();
    }
    
    /**
     *
     */

    public void resetClass() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Are You Sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to reset your gradebook? All past students, grades, and assignments will be lost forever");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            GradebookProject.reset();
            currentSection = GradebookProject.getCurrentSection();
            currentSection.addStudent(new Student(currentSection, "John"));
            currentSection.addStudent(new Student(currentSection, "Jane"));
            currentSection.addStudent(new Student(currentSection, "David"));
            currentSection.addAssignment(new Assignment(currentSection, "Test"));
            currentSection.addAssignment(new Assignment(currentSection, "Quiz"));
            currentSection.addAssignment(new Assignment(currentSection, "Homework"));
            updateTable();
        }

        
    }
}
