/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author brucemelton
 */
public class Student implements Comparable<Student>, Serializable{
    private String name;
    private ClassSection enrolledClass;
    private List<Integer> scores = new ArrayList<Integer>();
    private Double average;
    
    public Student(ClassSection c, String n) {
        name = n;
        enrolledClass = c;
        for(Assignment a : enrolledClass.getAssignments()){
            scores.add(new Integer(0));
        }
        updateAverage();
    }

    public void updateAverage(){
        double sum = 0;
        double numGrades = 0;
        for(Assignment a : enrolledClass.getAssignments()){
            if(a.getGrade(this) != null){
                sum += Double.parseDouble(a.getGrade(this));
                numGrades++;
            }
        }
        if(numGrades > 0){
            average = sum/numGrades;
        } else {
        average = 100.0;
        }
    }
    
    public Double getAverage(){
        return average;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public int compareTo(Student s){
        if(name.equals(s.toString())){
            return 0;
        }
        return 1;
    }
    
}
