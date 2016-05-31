/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradebookproject;

  import java.io.*;
import java.util.List;
/**
 *
 * @author csstudent
 */



public class Settings implements java.io.Serializable {
  
    public transient static Settings instance;
    private Settings() {}
    private List<ClassSection> allClasses;
    
    public static void init() {
        if(instance == null) {
            try
            {
                FileInputStream fileIn = new FileInputStream("gradebook.gbk");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Settings.instance = (Settings) in.readObject();
                in.close();
                fileIn.close();
            }catch(IOException i)
            {
            Settings.instance = new Settings();
            return;
            }catch(ClassNotFoundException c)
            {
            System.out.println("Settings class not found");
            c.printStackTrace();
            return;
      }
        }
    }
    
    public static void save() {
        init();
        try
        {
         FileOutputStream fileOut = new FileOutputStream("gradebook.gbk");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(Settings.instance);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in gradebook.gbk");
        }catch(IOException i)
        {
          i.printStackTrace();
        }
    }
    
    public static List<ClassSection> getClasses(){
        init();
        return Settings.instance.allClasses;
    }
    
    public static void setClasses(List<ClassSection> classList){
        init();
        Settings.instance.allClasses = classList;
    }
    
}

