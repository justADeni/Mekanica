package justadeni.mekanica.machines.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

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

}
