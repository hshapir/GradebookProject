/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author csstudent
 */
public class GradebookProject extends Application {
    private static List<ClassSection> allClasses;
    private static ClassSection currentSection;
    
    @Override
    public void start(Stage stage) throws Exception {
        if(Settings.getClasses() != null){
            allClasses = Settings.getClasses();
            currentSection = allClasses.get(0);
        } else{
            GradebookProject.allClasses = new ArrayList<ClassSection>();
            allClasses.add(new ClassSection());
            currentSection = allClasses.get(0);
        }
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
    
    @Override
    public void stop() {
        GradebookProject.save();
    }
    
    public static ClassSection getCurrentSection(){
        return currentSection;
    }
    
    public static void setCurrentSection(ClassSection cSec){
        GradebookProject.currentSection = cSec;
    }
    
    public static void save(){
        Settings.setClasses(allClasses);
        Settings.save();
    }
    
}
