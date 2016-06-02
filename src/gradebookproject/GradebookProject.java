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
        reset();
    }
    
    public static GradebookProject getGradebookInstance(String className){
        currentSection = findClass(className);
        return gradebookInstance;
    }
    
    public void showClass() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserInterfaceController.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        this.mainWindow.setScene(scene);
        this.mainWindow.show();
    }
    
    public void showStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPageController.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        this.mainWindow.setScene(scene);
        this.mainWindow.show();
    }
    

    public static void reset() {
        allClasses = new ArrayList<ClassSection>();
        allClasses.add(new ClassSection("New Class"));
        currentSection = allClasses.get(0);
    }
    
    public static List<ClassSection> returnAllClasses(){
        return allClasses;
    }
    
    public static ObservableList<String> getClassSectionNamesObservable(){
        ObservableList<String> ret = FXCollections.observableArrayList();
        for(String s : GradebookProject.getNames()){
            ret.add(s);
        }
        return ret;
    }
    
    public static void addClass(ClassSection newClass){
        allClasses.add(newClass);
    }
    
    public static List<String> getNames(){
        List<String> names = new ArrayList<String>();
        for(ClassSection className: allClasses){
            names.add(className.getName());
        }
        return names;
    }
    
    public static void removeClass(String name){
        for(int i = 0; i<allClasses.size();i++){
            if(allClasses.get(i).getName().matches(name)){
                allClasses.remove(i);
            }
        }
    }
    
    public static ClassSection findClass(String name){
        for(int i = 0; i<allClasses.size();i++){
            if(allClasses.get(i).getName().matches(name)){
                return allClasses.get(i);
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
            GradebookProject.allClasses = new ArrayList<ClassSection>();
            allClasses.add(new ClassSection("New Class"));
            currentSection = allClasses.get(0);
        }
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        
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
