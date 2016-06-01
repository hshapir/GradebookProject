/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.Serializable;

/**
 *
 * @author csstudent
 */
class Grade implements Serializable {
    private Double value;
    private String stringValue;
    private ClassSection section;
    private Assignment assignment;
    
    public Grade(Double inputValue, ClassSection csec, Assignment a){
        value = inputValue;
        stringValue = inputValue.toString();
        section = csec;
        assignment = a;
    }
    
    public Grade(String inputValue, ClassSection csec){
        stringValue = inputValue;
        section = csec;
        try{
            this.value = 0.0+ Integer.parseInt(inputValue);
        } catch(Exception e){
            try{
                value = Double.parseDouble(inputValue);
            } catch(Exception e2){
                if(inputValue.equals("A+")){
                    value = csec.getGradeRanges()[0];
                } else if(inputValue.equals("A")){
                    value = csec.getGradeRanges()[1];
                } else if(inputValue.equals("A-")){
                    value = csec.getGradeRanges()[2];
                } else if(inputValue.equals("B+")){
                    value = csec.getGradeRanges()[3];
                } else if(inputValue.equals("B")){
                    value = csec.getGradeRanges()[4];
                } else if(inputValue.equals("B-")){
                    value = csec.getGradeRanges()[5];
                } else if(inputValue.equals("C+")){
                    value = csec.getGradeRanges()[6];
                } else if(inputValue.equals("C")){
                    value = csec.getGradeRanges()[7];
                } else if(inputValue.equals("C-")){
                    value = csec.getGradeRanges()[8];
                } else if(inputValue.equals("D+")){
                    value = csec.getGradeRanges()[9];
                } else if(inputValue.equals("D")){
                    value = csec.getGradeRanges()[10];
                } else if(inputValue.equals("D-")){
                    value = csec.getGradeRanges()[11];
                } else if(inputValue.equals("F")){
                    value = 0.0;
                } else if(inputValue.equals("Pass")){
                    value = 100.0;
                } else if(inputValue.equals("Fail")){
                    value = 0.0;
                } else if(inputValue.equals("Excused")){
                    value = 0.0;
                } else if(inputValue.equals("excused")){
                    value = 0.0;
                }
                value = value * assignment.getPointValue() / 100.0;
            }
        } 
        
    }

    public String toString(){
        return stringValue;
    }
    
    public static String getLetterGrade(ClassSection csec, Double gradeValue){
        if(gradeValue < csec.getGradeRanges()[11]){
            return "F";
        } else if(gradeValue < csec.getGradeRanges()[10]){
            return "D-";
        } else if(gradeValue < csec.getGradeRanges()[9]){
            return "D";
        } else if(gradeValue < csec.getGradeRanges()[8]){
            return "D+";
        } else if(gradeValue < csec.getGradeRanges()[7]){
            return "C-";
        } else if(gradeValue < csec.getGradeRanges()[6]){
            return "C";
        } else if(gradeValue < csec.getGradeRanges()[5]){
            return "C+";
        } else if(gradeValue < csec.getGradeRanges()[4]){
            return "B-";
        } else if(gradeValue < csec.getGradeRanges()[3]){
            return "B";
        } else if(gradeValue < csec.getGradeRanges()[2]){
            return "B+";
        } else if(gradeValue < csec.getGradeRanges()[1]){
            return "A-";
        } else if(gradeValue < csec.getGradeRanges()[0]){
            return "A";
        } else {
            return "A+";
        }
    }
    
    public Double getNumericalValue(){
        return value;
    }
    
    public boolean excused(){
        if(stringValue.equals("Excused") || stringValue.equals("excused")){
            return true;
        }
        return false;
    }
    
    
}
