/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
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
    private Stage mainWindow;
    private static GradebookProject gradebookInstance;
    
    
    public GradebookProject(){
        //reset();
    }
    
    public static GradebookProject getGradebookInstance(String className){
        gradebookInstance.currentSection = findClass(className);
        return gradebookInstance;
    }
    
    public void showClass() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        this.mainWindow.setScene(scene);
        this.mainWindow.show();
    }
    
    public void showStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        this.mainWindow.setScene(scene);
        this.mainWindow.show();
    }
    

    public static void reset() {
        int i = allClasses.indexOf(currentSection);
        allClasses.set(i, new ClassSection(currentSection.getName()));
        currentSection = allClasses.get(i);
    }
    
    public static List<ClassSection> returnAllClasses(){
        return gradebookInstance.allClasses;
    }
    
    public static ObservableList<String> getClassSectionNamesObservable(){
        ObservableList<String> ret = FXCollections.observableArrayList();
        for(String s : GradebookProject.getNames()){
            ret.add(s);
        }
        return ret;
    }
    
    public static void addClass(ClassSection newClass){
        gradebookInstance.allClasses.add(newClass);
    }
    
    public static List<String> getNames(){
        List<String> names = new ArrayList<String>();
        for(ClassSection className: gradebookInstance.allClasses){
            names.add(className.getName());
        }
        return names;
    }
    
    public static void removeClass(String name){
        for(int i = 0; i< gradebookInstance.allClasses.size();i++){
            if(gradebookInstance.allClasses.get(i).getName().matches(name)){
                gradebookInstance.allClasses.remove(i);
                return;
            }
        }
    }
    
    public static ClassSection findClass(String name){
        for(int i = 0; i<gradebookInstance.allClasses.size();i++){
            if(gradebookInstance.allClasses.get(i).getName().matches(name)){
                return gradebookInstance.allClasses.get(i);
            }
        }
        return null;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        if(Settings.getClasses() != null){
            allClasses = Settings.getClasses();
            currentSection = allClasses.get(0);
        } else{
            allClasses = new ArrayList<ClassSection>();
            allClasses.add(new ClassSection("New Class"));
            currentSection = allClasses.get(0);
        }
        this.gradebookInstance = this;
        this.mainWindow = stage;
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        
        showStart();
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
        Settings.setClasses(gradebookInstance.allClasses);
        Settings.save();
    }
    
}
