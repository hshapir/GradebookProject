/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author csstudent
 */
class ClassSection {
     private ArrayList<Student> students;
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
    
    public List<Student> getStudentList(){
        return students;
    }
    
}
