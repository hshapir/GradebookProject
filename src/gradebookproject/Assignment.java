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
    
    
    public Assignment(ClassSection c, String n){
        section = c;
        name = n;
        scores = new TreeMap<Student, Grade>();
        updateStudentMap();
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
    
    public void setName(String s){
        name = s;
    }
    
    public void setGrade(Student s, String newValue){
        scores.remove(s);
        scores.put(s, new Grade(newValue, section));
    }
    
    public String getGrade(Student s){
        updateStudentMap();
        if(scores.containsKey(s)){
            return scores.get(s).toString();
        }
        return null;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public int compareTo(Assignment a){
        if(name.equals(a.toString())){
            return 0;
        }
        return 1;
    }
    
}
