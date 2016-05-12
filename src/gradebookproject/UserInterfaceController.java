/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        updateTable();
    }    
    
    public void updateTable(){
        gradebook.setEditable(false);
        TableColumn students = new TableColumn("Students");
        students.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        gradebook.set(currentSection.getAssignments()));
        List<TableColumn> columns = new ArrayList<TableColumn>();
        for(Assignment a : currentSection.getAssignments()){
            columns.add(new TableColumn(a.toString));
        }
        gradebook.getColumns().addAll(columns);
        for(Student s : currentSection.getStudentList()){
            gradebook.addRow(rowIndex, s.gradeDisplay());
        }
        
    public void close() {
        System.exit(0);
    }
    
}