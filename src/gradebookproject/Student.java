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
    private List<Grade> scores = new ArrayList<Grade>();
    private Double average;
    
    public Student(ClassSection c, String n) {
        name = n;
        enrolledClass = c;
        for(Assignment a : enrolledClass.getAssignments()){
            scores.add(new Grade(0.0, enrolledClass));
        }
        updateAverage();
    }

    public void updateAverage(){
        double sum = 0;
        double numGrades = 0;
        for(Assignment a : enrolledClass.getAssignments()){
            if(a.getGrade(this) != null){
                sum += a.getGrade(this).getNumericalValue();
                if(!a.getGrade(this).excused()){
                    numGrades++;
                }
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
    
    public Double[] getAssignmentTypeAverages(){
        enrolledClass.updateAssignmentTypes();
        Double[] ret = new Double[enrolledClass.getAssignmentTypes().size()];
        int i = 0;
        for(String s : enrolledClass.getAssignmentTypes()){
            if(s != null){
                double typeSum = 0;
                double numOfType = 0;
                for(Assignment a : enrolledClass.getAssignments()){
                    if(a.getAssignmentType() != null){
                        if(a.getAssignmentType().equals(s)){
                            typeSum += a.getGrade(this).getNumericalValue();
                            if(!a.getGrade(this).excused()){
                                numOfType++;
                            }
                        }
                    }
                }
                if(numOfType != 0){
                    ret[i] = typeSum / numOfType;
                    i++;
                } else{
                    ret[i] = 0.0;
                    i++;
                }
            }
        }
        return ret;
    }
    
}
