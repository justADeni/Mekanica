package justadeni.mekanica.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Filewalker {

    private static HashSet<Path> pathSet = new HashSet<>();

    public static Set<Path> getPathSet(){
        return Collections.synchronizedSet(pathSet);
    }

    public void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                //Mekanica.log("Dir:" + f.getAbsoluteFile());
            } else {
                //Mekanica.log("File:" + f.getAbsoluteFile());
                pathSet.add(Paths.get(f.getAbsolutePath()));
            }
        }
    }

}
