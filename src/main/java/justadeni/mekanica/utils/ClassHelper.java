package justadeni.mekanica.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import justadeni.mekanica.items.ItemManager;

import java.util.ArrayList;
import java.util.List;


public class ClassHelper {

    public static String getFullClassName(String name){
        List<String> classNames;
        try (ScanResult scanResult = new ClassGraph().acceptPackages("justadeni.mekanica.machines.")
                .enableClassInfo().scan()) {
            classNames = scanResult.getAllClasses().getNames();
        }
        if (!classNames.isEmpty()) {
            for (String s : classNames) {
                String[] strings = s.split("[.]");
                if (strings[strings.length-1].equals(name)){
                    return s;
                }
            }
        }

        return null;
    }


    private static List<Class> getMachineClasses() throws ClassNotFoundException {
        List<ClassInfo> classInfos;
        try (ScanResult scanResult = new ClassGraph().acceptPackages("justadeni.mekanica.machines.")
                .enableClassInfo().scan()) {
            classInfos = scanResult.getAllClasses();
        }
        if (!classInfos.isEmpty()) {
            List<Class> classList = new ArrayList<>();
            for (ClassInfo info : classInfos){
                if (!(info.getSimpleName().equals("Machine"))) {
                    classList.add(Class.forName(info.getName()));
                }
            }
            return classList;
        }

        return null;
    }


    private static Class getClassById(int id) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        for (Class aClass : getMachineClasses()){
            ItemManager itemManager = (ItemManager) aClass.getDeclaredField("itemManager").get(null);
            if (itemManager.getId() == id){
                return aClass;
            }
        }
        return null;
    }

    public static int getIdByClass(Class clazz) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        for (Class aClass : getMachineClasses()){
            if (aClass.equals(clazz)){
                ItemManager itemManager = (ItemManager) aClass.getDeclaredField("itemManager").get(null);
                return itemManager.getId();
            }
        }
        return 0;
    }

    public static ItemManager getItemManager(int id) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        for (Class aClass : getMachineClasses()){
            ItemManager itemManager = (ItemManager) aClass.getDeclaredField("itemManager").get(null);
            if (itemManager.getId() == id){
                return itemManager;
            }
        }
        return null;
    }

    //TODO: this throws errors
    public static Class getNewClassObject(int id) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Class aClass = getClassById(id);
        return (Class) aClass.getDeclaredField("getNew").get(null);
    }

}