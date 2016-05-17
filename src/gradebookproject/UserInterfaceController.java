/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author csstudent
 */
public class UserInterfaceController implements Initializable {
    
    private static List<ClassSection> sections;
    private static ClassSection currentSection;
    
    @FXML
    private TableView gradebook;
    
    @FXML
    private MenuItem closeButton;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentSection = new ClassSection();
        currentSection.addStudent(new Student(currentSection, "John"));
        currentSection.addStudent(new Student(currentSection, "Jane"));
        currentSection.addStudent(new Student(currentSection, "David"));
        currentSection.addAssignment(new Assignment(currentSection, "Test"));
        currentSection.addAssignment(new Assignment(currentSection, "Quiz"));
        currentSection.addAssignment(new Assignment(currentSection, "Homework"));
        updateTable();
    }    
    
    public void updateTable(){
        gradebook.getColumns().removeAll(gradebook.getColumns());
        
        
        //final Label label = new Label("Gradebook");
        //label.setFont(new Font("Times", 20));
        
        //gradebook = new TableView<Student>();
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
        
        /*final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, gradebook);*/


        /*List<TableColumn> columns = new ArrayList<TableColumn>();
        for(Assignment a : currentSection.getAssignments()){
            columns.add(new TableColumn(a.toString()));
        }
        
        for(int i = 0; i < columns.size(); i++){
            TableColumn currentColumn = columns.get(i);
            currentColumn.setCellValueFactory(new PropertyValueFactory<>)
        }
            
            
        gradebook.getColumns().addAll(columns);
        for(Student s : currentSection.getStudentList()){
            gradebook.addRow(rowIndex, s.gradeDisplay());
        }*/
    }
        
    public void close() {
        System.exit(0);
    }
    
    public double calculate() {
        return -1;
    }
}
