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
    
    @FXML
    private MenuItem helpButton;
    
    public void toStart() {
        try{
            GradebookProject.getGradebookInstance(currentSection.toString()).showStart();
        } catch (IOException e){}
    }
    
    public void helpMenu(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Gradebook Information");
        alert.setContentText("Welcome to our Gradebook, presented by Harrison! In order to edit names and scores, please double-click on what you wish to change. If you would like to add or delete anything, go to the 'Edit' menu. "
                + "If you would like to maniuplate assignments, their due dates, categories, and point values, please choose 'Edit Assignment' in the edit menu. Grades are initialized to 100% by default. The following inputs are acceptable: "
                + "any raw numbers; letter grades such as A, C+, etc.; and Pass/Fail. If a student is excused from an assignment, please write 'excused' or 'Excused' into the grade blank and that assignment will not be factored into the student's"
                + " average. You can go to reset in the File menu to reset your class data and fill the field with a sample class. Enjoy!");
        alert.showAndWait();
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
                        a.setGrade(modifiedGradeStudent, t.getNewValue());
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
        
        TableColumn<Map, String> finalLetterGrades = new TableColumn<>("Letter Grade");
        finalLetterGrades.setCellValueFactory(new MapValueFactory("Letter Grade"));
        gradebook.getColumns().addAll(finalLetterGrades);
        
        for(String s : currentSection.getAssignmentTypes()){
            if(s != null){
                TableColumn<Map, String> newColumn = new TableColumn<>(s + " Category Average");
                newColumn.setCellValueFactory(new MapValueFactory(s + " Average"));
                gradebook.getColumns().addAll(newColumn);
            }
        }
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
            if(currentSection.getNames().indexOf(result.get()) != -1){
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
        if (result.isPresent() && !result.equals("")){
            if(currentSection.findAssignment(result.get()) != null){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Assignment Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two assignments with the same name. Please enter an unused name or a date or number, like 'Test #2' or 'Homework 5/18'");
                invalidNameAlert.showAndWait();
                createAssignment();
            } else{
                currentSection.addAssignment(new Assignment(currentSection, result.get()));
                currentSection.findAssignment(result.get()).setDueDate(this.assignmentDateDialog());
                String newType = this.assignmentTypeDialog();
                if(!newType.equals("") || newType == null){
                    currentSection.findAssignment(result.get()).setAssignmentType(newType);
                }
                currentSection.updateAssignmentTypes();
                currentSection.findAssignment(result.get()).setPointValue(this.assignmentPointValueDialog());
            }
            updateTable();
        }
    }
    
    public String assignmentDateDialog(){
        TextInputDialog newDateDialog = new TextInputDialog("");
        newDateDialog.setTitle("New Assignment Due Date");
        newDateDialog.setHeaderText(null);
        TextField newDate = new TextField();
        newDate.setPromptText("New Name");
        newDateDialog.setContentText("Assignment's New Due Date in the format MM/DD/YYYY only:");
        Optional<String> result = newDateDialog.showAndWait();
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
    
    public String assignmentTypeDialog(){
        TextInputDialog newTypeDialog = new TextInputDialog("");
        newTypeDialog.setTitle("New Assignment Type");
        newTypeDialog.setHeaderText(null);
        TextField newType = new TextField();
        newType.setPromptText("Assignment Type");
        newTypeDialog.setContentText("Type of assignment, like 'Test,' 'Quiz,' etc.");
        Optional<String> result = newTypeDialog.showAndWait();
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
    
    public Double assignmentPointValueDialog(){
        Double ret = null;
        TextInputDialog newTypeDialog = new TextInputDialog("");
        newTypeDialog.setTitle("Assignment Point Value");
        newTypeDialog.setHeaderText(null);
        TextField newType = new TextField();
        newType.setPromptText("Point Value");
        newTypeDialog.setContentText("Point Value of this assignment. Please enter any positive number without additional text");
        Optional<String> result = newTypeDialog.showAndWait();
        if(result.isPresent()){
            try{
                ret = Double.parseDouble(result.get());
            } catch (Exception e){
                if(!result.get().equals("") && result.get() != null){
                    Alert invalidValueAlert = new Alert(Alert.AlertType.INFORMATION);
                    invalidValueAlert.setTitle("Invalid Value");
                    invalidValueAlert.setHeaderText(null);
                    invalidValueAlert.setContentText("Please enter a valid number or leave the field blank");
                    invalidValueAlert.showAndWait();
                    ret = assignmentPointValueDialog();
                }
            }
        }
        return ret;
    }
    
    public void changeAssignment(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", currentSection.getAssignmentNames());
        dialog.setTitle("Select Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("Choose Assignment to Rename:");
        Optional<String> oldName = dialog.showAndWait();
        if(oldName.isPresent()){
            TextInputDialog newNameDialog = new TextInputDialog("");
            newNameDialog.setTitle("Edit Assignment");
            newNameDialog.setHeaderText("");
            TextField newName = new TextField();
            newName.setPromptText("New Name");
            newNameDialog.setContentText("Set Assignment Name:");
            Optional<String> result = newNameDialog.showAndWait();
            if (result.isPresent()){
                if(currentSection.findAssignment(result.get()) != null && ! result.get().equals(oldName.get())){
                Alert invalidNameAlert = new Alert(Alert.AlertType.INFORMATION);
                invalidNameAlert.setTitle("Invalid Assignment Name");
                invalidNameAlert.setHeaderText(null);
                invalidNameAlert.setContentText("You cannot have two assignments with the same name. Please enter an unused name or a date or number, like 'Test #2' or 'Homework 5/18'");
                invalidNameAlert.showAndWait();
                } else{
                    String newNameString = result.get();
                    if(!newNameString.equals("")){
                        currentSection.findAssignment(oldName.get()).setName(newNameString);
                    } else{
                        newNameString = oldName.get();
                    }
                    currentSection.findAssignment(newNameString).setDueDate(this.assignmentDateDialog());
                    String newType = this.assignmentTypeDialog();
                    if(!newType.equals("")){
                        currentSection.findAssignment(newNameString).setAssignmentType(newType);
                    }
                    currentSection.updateAssignmentTypes();
                    currentSection.findAssignment(newNameString).setPointValue(this.assignmentPointValueDialog());
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
