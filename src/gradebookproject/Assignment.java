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
public class Assignment implements Comparable<Assignment>, Serializable{
    private ClassSection section;
    private String name;
    private Map<Student, Grade> scores;
    private Date dueDate;
    private String assignmentType;
    
    public Assignment(ClassSection c, String n){
        section = c;
        name = n;
        scores = new TreeMap<Student, Grade>();
        updateStudentMap();
        assignmentType = null;
        dueDate = null;
    }
    
    public void updateStudentMap(){
        for(Student s : section.getStudentList()){
            if(!scores.keySet().contains(s)){
                scores.put(s, new Grade(100.0, section));
            }
            
        }
    }
    
    public String getName(){
        return name;
    }
    
    public Double getAverageScore(){
        updateStudentMap();
        Double sum = 0.0;
        Double numScores = 0.0;
        for(Student s : scores.keySet()){
            sum += getGrade(s).getNumericalValue();
            if(!getGrade(s).excused()){
                numScores++;
            }
        }
        return sum / numScores;
    }
    
    public void setName(String s){
        name = s;
    }
    
    public void setGrade(Student s, String newValue){
        scores.remove(s);
        scores.put(s, new Grade(newValue, section));
    }
    
    public Grade getGrade(Student s){
        updateStudentMap();
        if(scores.containsKey(s)){
            return scores.get(s);
        }
        return null;
    }
    
    @Override
    public String toString(){
        if(assignmentType != null && dueDate != null){
            return name + " (" + assignmentType + " Due on: " + dateString() + ")";
        } else if(assignmentType != null){
            return name + " (" + assignmentType + ")";
        } else if(dueDate !=null){
            return name + " (Due on: " + dateString() + ")";
        }
        return name;
    }
    
    public String dateString(){
        String ret = dueDate.toString();
        ret = ret.replace("00:00:00 CST ", "");
        return ret;
    }
    
    public int compareTo(Assignment a){
        if(name.equals(a.getName())){
            return 0;
        }
        return 1;
    }
    
    public void setDate(int year, int month, int day){
        dueDate = new Date(year, month, day);
    }
    
    public void setDueDate(String s){
        if(s == null){
            return;
        }
        Integer month = null;
        try{
            month = Integer.parseInt(s.substring(0, s.indexOf("/")));
            s=s.substring(s.indexOf("/") + 1);
        } catch(Exception e){}
        Integer day = null;
        try{
            day = Integer.parseInt(s.substring(0, s.indexOf("/")));
            s=s.substring(s.indexOf("/") + 1);
        } catch(Exception e){}
        Integer year = null;
        try{
            year = Integer.parseInt(s);
        } catch(Exception e){}
        if(month != null && day != null && year != null){
            setDate(year - 1900, month - 1, day);
        }
    }
    
    public Date getDueDate(){
        return dueDate;
    }
    
    public void setAssignmentType(String s){
        assignmentType = s;
        section.updateAssignmentTypes();
    }
    
    public String getAssignmentType(){
        return assignmentType;
    }
    
}
