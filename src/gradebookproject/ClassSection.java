/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author csstudent
 */
class ClassSection {
     private List<Student> students;
     private ArrayList<Assignment> assignments;
     
     public ClassSection(){
         students = new ArrayList<Student>();
         assignments = new ArrayList<Assignment>();
     }
    
    
    public void addStudent(Student newStudent){
        students.add(newStudent);
    }
    
    public void removeStudent(Student thisStudent){
        students.remove(thisStudent);
    }
    
    public void addAssignment(Assignment a){
        assignments.add(a);
    }
    
    public List<Assignment> getAssignments(){
        return assignments;
    }
    
    public List<Student> getStudentList(){
        return students;
    }
    
    public ObservableList<Student> getObservableStudentList(){
        ObservableList<Student> ret = FXCollections.observableArrayList();
        for(Student s : students){
            ret.add(s);
        }
        return ret;
    }
    
}
