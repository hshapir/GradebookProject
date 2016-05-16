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
public class Student implements Comparable<Student>{
    private String name;
    private ClassSection enrolledClass;
    private List<Integer> scores = new ArrayList<Integer>();
    private int average;
    
    public Student(ClassSection c, String n) {
        name = n;
        enrolledClass = c;
        for(Assignment a : enrolledClass.getAssignments()){
            scores.add(new Integer(0));
        }
    }

    public int getAverage(){
        int sum = 0;
        int numScores = 0;
        if(scores.size()>0){
            for(int i = 0; i<scores.size(); i++){
                sum+=scores.get(i);
                numScores++;
            }
            average = sum/numScores;
        return average;
        }
        return 100;
        
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
