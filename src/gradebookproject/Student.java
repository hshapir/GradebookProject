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
    private Integer idNumber;
    
    public Student(ClassSection c, String n) {
        name = n;
        enrolledClass = c;
        for(Assignment a : enrolledClass.getAssignments()){
            scores.add(new Grade(0.0, enrolledClass, a));
        }
        updateAverage();
    }
      
    public void setIdNumber(int i){
        idNumber = i;
    }
    
    public int getIdNumber(){
        return idNumber;
    }

    public void updateAverage(){
        double sum = 0;
        double pointsAvailable = 0;
        for(Assignment a : enrolledClass.getAssignments()){
            if(a.getGrade(this) != null){
                sum += a.getGrade(this).getNumericalValue();
                if(!a.getGrade(this).excused()){
                    pointsAvailable += a.getPointValue();
                }
            }
        }
        if(pointsAvailable > 0){
            average = sum/pointsAvailable * 100.0;
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
        enrolledClass.getIdMap().put(name, idNumber);
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public int compareTo(Student s){
        if(idNumber != null){
            if(s.idNumber == this.idNumber){
                return 0;
            } else {
                return 1;
            }
        }
        else{
            if(name.equals(s.toString())){
                return 0;
            }
            return 1;
        }
    }
    
    public String[] getAssignmentTypeAverages(){
        enrolledClass.updateAssignmentTypes();
        String[] ret = new String[enrolledClass.getAssignmentTypes().size()];
        int i = 0;
        for(String s : enrolledClass.getAssignmentTypes()){
            if(s != null){
                Double typeSum = 0.0;
                double typePointsAvailable = 0;
                for(Assignment a : enrolledClass.getAssignments()){
                    if(a.getAssignmentType() != null){
                        if(a.getAssignmentType().equals(s)){
                            typeSum += a.getGrade(this).getNumericalValue();
                            if(!a.getGrade(this).excused()){
                                typePointsAvailable += a.getPointValue();
                            }
                        }
                    }
                }
                if(typePointsAvailable != 0){
                    Double retDoub = typeSum / typePointsAvailable * 100.0;
                    ret[i] = retDoub.toString();
                    i++;
                } else{
                    if(typeSum > 0){
                        ret[i] = "+" + typeSum.toString();
                        i++;
                    } else{
                        ret[i] = null;
                        i++;
                    }
                }
            }
        }
        return ret;
    }
    
}
