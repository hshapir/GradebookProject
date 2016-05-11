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
        gradebook.addRow(0, currentSection.getAssignments());
        List<TableColumn> columns = new ArrayList<TableColumn>();
        for(Assignment a : currentSection.getAssignments()){
            columns.add(new TableColumn(a.toString));
        }
        gradebook.getColumns().addAll(columns);
        for(Student s : currentSection.getStudentList()){
            gradebook.addRow(rowIndex, s.gradeDisplay());
        }
        
    }
    
}
