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
    private String name = new String();
    private ArrayList<Assignment> assignments = new ArrayList();
    
    public ArrayList getAssignments(){
        return assignments;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    
}
