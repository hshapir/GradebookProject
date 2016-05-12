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
public class Student {
    private String name;
    private ClassSection enrolledClass;
    private List<Integer> scores = new ArrayList<Integer>();
    
    public Student(ClassSection c, String n) {
        name = n;
        enrolledClass = c;
        for(Assignment a : enrolledClass.getAssignments()){
            scores.add(new Integer(0));
        }
    }

    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    
}
