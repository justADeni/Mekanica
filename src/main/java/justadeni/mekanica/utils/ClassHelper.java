package justadeni.mekanica.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import justadeni.mekanica.Mekanica;
import justadeni.mekanica.items.ItemManager;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ClassHelper {

    public static String getName(Object obj) {
        Class aClass = obj.getClass();
        String[] packageName = aClass.getPackageName().split(".");
        return aClass.getSimpleName().split(".")[0] + " " +
                StringUtils.capitalize(StringUtils.chop(packageName[packageName.length - 1]));
    }
    public static void registerListeners() {
        try {
            for (ClassInfo classInfo : new ClassGraph().acceptPackages("justadeni.mekanica.listeners.")
                    .enableClassInfo().scan().getAllClasses()) {

                Class<?> c = Class.forName(classInfo.getName());
                if (Listener.class.isAssignableFrom(c)) {
                    Bukkit.getServer().getPluginManager().registerEvents((Listener) c.getDeclaredConstructor().newInstance(), Mekanica.getPlugin());
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

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


    private static List<Class> getMachineClasses() {
        try {

            List<ClassInfo> classInfos;
            try (ScanResult scanResult = new ClassGraph().acceptPackages("justadeni.mekanica.machines.")
                    .enableClassInfo().scan()) {
                classInfos = scanResult.getAllClasses();
            }
            if (!classInfos.isEmpty()) {
                List<Class> classList = new ArrayList<>();
                for (ClassInfo info : classInfos) {
                    if (!(info.getSimpleName().equals("Machine"))) {
                        classList.add(Class.forName(info.getName()));
                    }
                }
                return classList;
            }

            return null;

        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }


    public static Class getClassById(int id) throws Exception{
        for (Class aClass : getMachineClasses()){
            ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
            if (itemManager.getId() == id){
                return aClass;
            }
        }
        return null;
    }

    public static int getIdByClass(Class clazz) throws Exception {
        for (Class aClass : getMachineClasses()){
            if (aClass.equals(clazz)){
                ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
                return itemManager.getId();
            }
        }
        return 0;
    }

    public static int getIdByObject(Object obj) throws Exception {
        for (Class aClass : getMachineClasses()){
            if (obj.getClass().getName().equals(aClass.getName())){
                ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
                return itemManager.getId();
            }
        }
        return 0;
    }

    public static ItemManager getItemManager(int id) throws Exception {
        for (Class aClass : getMachineClasses()){
            //ItemManager itemManager = (ItemManager) aClass.getField("itemManager").get(null);
            ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
            if (itemManager.getId() == id){
                return itemManager;
            }
        }
        return null;
    }

    public static Object getNewClassObject(int id) throws Exception{
        Class aClass = getClassById(id);
        Method method = aClass.getMethod("getNew");
        return method.invoke(null);
    }

}
