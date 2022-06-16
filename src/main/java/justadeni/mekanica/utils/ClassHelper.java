package justadeni.mekanica.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import justadeni.mekanica.Mekanica;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClassHelper {

    private static HashMap<Integer, Class> classIndex = new HashMap<>();
    //TODO: make all indexes file based upon inactivity

    public static void indexClassesItems(){
        try {
            for (Class aClass : getMachineClasses()) {
                Method method = aClass.getMethod("getNew");
                ItemManager itemManager = ((Machine) method.invoke(null)).getItem();

                classIndex.put(itemManager.getId(), aClass);
                ItemManager.itemManagerIndex.put(itemManager.getId(), itemManager);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
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
        /*
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
        */
        for (Class aClass : classIndex.values()){
            String s = aClass.getName();
            String[] strings = s.split("[.]");
            if (strings[strings.length-1].equals(name)){
                return s;
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


    public static Machine getNewMachineById(int id){
        try {
            /*
            for (Class aClass : getMachineClasses()) {
                //ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
                Machine machine = (Machine) aClass.getMethod("getNew").invoke(null);
                ItemManager itemManager = machine.getItem();
                if (itemManager.getId() == id) {
                    return machine;
                }
            }
            return null;
            */
            return (Machine) classIndex.get(id).getMethod("getNew").invoke(null);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /*
    public static int getIdByObject(Machine machine){

        for (Class aClass : getMachineClasses()){
            if (obj.getClass().getName().equals(aClass.getName())){
                ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(aClass);
                return itemManager.getId();
            }
        }
        return 0;

        return machine.getItem().getId();
    }
    */

    /*
    public static ItemManager getItemManager(int id) throws Exception {

        for (Class aClass : getMachineClasses()){
            //ItemManager itemManager = (ItemManager) aClass.getField("itemManager").get(null);
            //ItemManager itemManager = (ItemManager) aClass.getMethod("getItem").invoke(getNewClassObject(id));
            Machine machine = (Machine) getNewClassObject(id);
            if (machine.getItem().getId() == id){
                return machine.getItem();
            }
        }
        return null;


    }
    */

}
