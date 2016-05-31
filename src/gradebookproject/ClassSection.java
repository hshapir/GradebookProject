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
     private List<String> assignmentTypes;
     
     public ClassSection(){
         students = new ArrayList<Student>();
         assignments = new ArrayList<Assignment>();
         gradeRanges = new double[] {99.0, 94.0, 90.0, 88.0, 83.0, 80.0, 78.0, 73.0, 70.0, 68.0, 63.0, 60.0};
     }
     
     public void updateAssignmentTypes(){
         if(assignmentTypes == null){
             assignmentTypes = new ArrayList<String>();
         }
         for(Assignment a : assignments){
             if(!assignmentTypes.contains(a.getAssignmentType()) && a.getAssignmentType() != null){
                 assignmentTypes.add(a.getAssignmentType());
             }
         }
     }
     
     public List<String> getAssignmentTypes(){
         updateAssignmentTypes();
         return assignmentTypes;
     }
    
    public List getNames(){
        List<String> names = new ArrayList<String>();
        for(Student student: students){
            names.add(student.getName());
        }
        return names;
    }
    
    public double[] getGradeRanges(){
        return gradeRanges;
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
            if( a.getName().equals(s)){
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
                dataRow.put(a.toString(), a.getGrade(s).toString());
            }
            if(s.getAverage().toString().length() < 5){
                dataRow.put("Average Score", s.getAverage().toString());
            } else{
            dataRow.put("Average Score", s.getAverage().toString().substring(0, 5));
            }
            dataRow.put("Letter Grade", Grade.getLetterGrade(this, s.getAverage()));
            int i = 0;
            for(Double d : s.getAssignmentTypeAverages()){
                if(d != null){
                    if(d.toString().length() < 5){
                        dataRow.put(this.getAssignmentTypes().get(i) + " Average", d.toString());
                    } else{
                        dataRow.put(this.getAssignmentTypes().get(i) + " Average", d.toString().substring(0, 5));
                        i++;
                    }
                }
            }
            allData.add(dataRow);
        }
        Map<String, String> averageScoresRow = new HashMap<>();
        for(Assignment a : assignments){
            averageScoresRow.put("Students", "Class Average");
            if(a.getAverageScore().toString().length() < 5){
                averageScoresRow.put(a.toString(), a.getAverageScore().toString());
            } else{
                averageScoresRow.put(a.toString(), a.getAverageScore().toString().substring(0,5));
            }
            
        }
        allData.add(averageScoresRow);
        return allData;
    }
    
}
