/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.util.*;

/**
 *
 * @author brucemelton
 */
public class Assignment implements Comparable<Assignment>{
    private ClassSection section;
    private String name;
    private Map<Student, Integer> scores;
    
    
    public Assignment(ClassSection c, String n){
        section = c;
        name = n;
        scores = new TreeMap<Student, Integer>();
        updateStudentMap();
    }
    
    public void updateStudentMap(){
        for(Student s : section.getStudentList()){
            if(!scores.keySet().contains(s)){
                scores.put(s, 100);
            }
            
        }
    }
    
    public String getGrade(Student s){
        updateStudentMap();
        return scores.get(s).toString();
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
