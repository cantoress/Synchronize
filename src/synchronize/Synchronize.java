
package synchronize;

import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Главный класс синхронизации
 * @author Svetlana
 */
public class Synchronize {


    public static Config Config;

    /**
     * Путь к XML-файлу с конфигурациями синхронизации(пути к синхронизируемым директориям и к их предыдущим состояниям)
     */
    public static final String PATH_TO_PROP = "C:\\Users\\Зщльзователь\\Desktop\\ТП\\prop.xml";
    
    /**
     * Метод, загружающий свойства для синхронизации из файла либо, если его нет, из командной строки
     * @param args массив данных из командной строки
     * @return удалось/не удалось получить свойства для синхронизации
     */
    public static boolean getPropetiesToUse(String[] args){
        Config = new Config(PATH_TO_PROP);
        if(args.length==0){
            Config.loadFromXML();
            return true;
        } else if(args.length%2==0){
            for(int i =0; i<args.length;i = i+2){
                Config.setProperty(args[i], args[i+1]);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Метод, запускающий синхронизацию
     */
    public static void synchronize(){
        Directory first = new Directory(Config.getProperty("firstpath"));
        Directory second = new Directory(Config.getProperty("secondpath"));
        
        if((Config.getProperty("firstprevstate")==null)||(Config.getProperty("secondprevstate")==null)){
            first.putNewStateInSet();
            second.putNewStateInSet();
            
            first.saveNewState(new File(first.getPath()+File.separator+"temp1.txt"));
            second.saveNewState(new File(second.getPath()+File.separator+"temp2.txt"));
            
            first.takePrevState(new File(first.getPath()+File.separator+"temp1.txt"));
            second.takePrevState(new File(second.getPath()+File.separator+"temp2.txt"));
            
            first.letsSinchronize(second);
            
            first.putNewStateInSet();
            first.saveNewState(new File(first.getPath()+File.separator+"temp1.txt"));
                    
            second.putNewStateInSet();
            second.saveNewState(new File(second.getPath()+File.separator+"temp2.txt"));
        } else{
            first.putNewStateInSet();
            second.putNewStateInSet();
            
            first.takePrevState(new File(Config.getProperty("firstprevstate")));
            second.takePrevState(new File(Config.getProperty("secondprevstate")));
            
            first.letsSinchronize(second);
            
            first.putNewStateInSet();
            first.saveNewState(new File(Config.getProperty("firstprevstate")));
                    
            second.putNewStateInSet();
            second.saveNewState(new File(Config.getProperty("secondprevstate")));
        }
    }
    
    
    public static void main(String[] args) {
//        Directory first = new Directory("C:\\Users\\Зщльзователь\\Desktop\\ТП\\first");
//        Directory second = new Directory("C:\\Users\\Зщльзователь\\Desktop\\ТП\\second");
//        File firFile = new File("C:\\Users\\Зщльзователь\\Desktop\\ТП\\first\\try1.txt");
//        first.takePrevState(firFile);
//        File secFile = new File("C:\\Users\\Зщльзователь\\Desktop\\ТП\\second\\try1.txt");
//        second.takePrevState(secFile);
//        first.putNewStateInSet();
//        second.putNewStateInSet();
//        Iterator it = first.getPrevState().iterator();
//        Iterator it1 = first.getNewState().iterator();
//        Iterator it2 = second.getPrevState().iterator();
//        Iterator it3 = second.getNewState().iterator();
//        while(it.hasNext()){
//            FileInfo a = (FileInfo)it.next();
//            System.out.print(a.getPath()+" ");
//        }
//        System.out.println(" ");
//        while(it1.hasNext()){
//            FileInfo a = (FileInfo)it1.next();
//            System.out.print(a.getPath()+" ");
//        }
//        System.out.println(" ");
//        while(it2.hasNext()){
//            FileInfo a = (FileInfo)it2.next();
//            System.out.print(a.getPath()+" ");
//        }
//        System.out.println(" "); 
//        while(it3.hasNext()){
//            FileInfo a = (FileInfo)it3.next();
//            System.out.print(a.getPath()+" ");
//        }
//        System.out.println(" ");
//        
//        System.out.println(" ");
//        first.letsSinchronize(second);

          if ((getPropetiesToUse(args))&&(Config!=null)) {            
            synchronize();
        } else {
            System.out.println("Oops, try again");
        }
    }
    
    
}
