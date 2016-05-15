/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import javafx.geometry.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Font;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
        
        updateTable();
    }    
    
    public void updateTable(){
        
        
        //final Label label = new Label("Gradebook");
        //label.setFont(new Font("Times", 20));
        
        //gradebook = new TableView<Student>();
        gradebook.setItems(currentSection.getObservableStudentList());
        gradebook.setEditable(true);
        TableColumn students = new TableColumn<Student, String>("Students");
        students.setCellValueFactory(new PropertyValueFactory("name"));
        gradebook.getColumns().addAll(students);
        
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
    
}
