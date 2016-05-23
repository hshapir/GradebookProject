/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

/**
 *
 * @author csstudent
 */
class Grade {
    private Double value;
    private String stringValue;
    private ClassSection section;
    
    public Grade(Double inputValue, ClassSection csec){
        value = inputValue;
        stringValue = inputValue.toString();
        section = csec;
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
        }
            }
        } 
        
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
        }
    }

    
    
    
    
    
}
