/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author csstudent
 */
class ClassSection implements Serializable {
     private List<Student> students;
     private ArrayList<Assignment> assignments;
     private double[] gradeRanges; 
     
     public ClassSection(){
         students = new ArrayList<Student>();
         assignments = new ArrayList<Assignment>();
         gradeRanges = new double[12];
     }
    
    public List getNames(){
        List<String> names = new ArrayList<String>();
        for(Student student: students){
            names.add(student.getName());
        }
        return names;
    }
    
    public List getAssignmentNames(){
        List<String> assignmentNames = new ArrayList<String>();
        for(Assignment assignment: assignments){
            assignmentNames.add(assignment.getName());
        }
        return assignmentNames;
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
    
    public Student findStudent(String s){
        for(Student stud : students){
            if( stud.toString().equals(s)){
                return stud;
            }
        }
        return null;
    }
    
    public Assignment findAssignment(String s){
        for(Assignment a : assignments){
            if( a.toString().equals(s)){
                return a;
            }
        }
        return null;
    }
    
    public void removeAssignment(Assignment a){
        assignments.remove(assignments.indexOf(a));
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
    
    public ObservableList<Map> getObservableStudentMap(){
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for(Student s : students){
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put("Students", s.toString());
            for(Assignment a : assignments){
                dataRow.put(a.toString(), a.getGrade(s));
            }
            if(s.getAverage().toString().length() < 5){
                dataRow.put("Average Score", s.getAverage().toString());
            } else{
            dataRow.put("Average Score", s.getAverage().toString().substring(0, 5));
            }
            allData.add(dataRow);
        }
        return allData;
    }
    
}
