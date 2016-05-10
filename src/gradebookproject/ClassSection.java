/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.util.ArrayList;

/**
 *
 * @author csstudent
 */
class ClassSection {
     private ArrayList<Student> students;
    
    
    public void addStudent(Student newStudent){
        students.add(newStudent);
    }
    
    public void removeStudent(Student thisStudent){
        students.remove(thisStudent);
    }
    
    public ArrayList getStudentList(){
        return students;
    }
    
}
