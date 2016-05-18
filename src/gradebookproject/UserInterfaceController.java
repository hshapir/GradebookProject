/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentSection = GradebookProject.getCurrentSection();
        /** This just creates some tester students and assignments
         * and won't be present in the actual program
        */
        /*currentSection.addStudent(new Student(currentSection, "John"));
        currentSection.addStudent(new Student(currentSection, "Jane"));
        currentSection.addStudent(new Student(currentSection, "David"));
        currentSection.addAssignment(new Assignment(currentSection, "Test"));
        currentSection.addAssignment(new Assignment(currentSection, "Quiz"));
        currentSection.addAssignment(new Assignment(currentSection, "Homework"));*/
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
        updateMenu();
        
    }
    
    public void updateMenu(){
        //Give submenus for deleting and modifying assignments and deleting students
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
            System.out.println("Your name: " + result.get());
        }
    }
    
    public void deleteStudent(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Delete Student");
        dialog.setHeaderText("");
        dialog.setContentText("Student's Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }
    
    public void renameStudent(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Rename Student");
        dialog.setHeaderText("");
        dialog.setContentText("Student's Current Name:");
        dialog.setContentText("Student's New Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }
    
    public void deleteAssignment(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Delete Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("Assignment Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }
    
    public void createAssignment(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("New Assignment Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }
    
    public void changeAssignment(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Change Assignment");
        dialog.setHeaderText("");
        dialog.setContentText("Assignment Name:");
        dialog.setContentText("New Assignment Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }
}
