/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.refactor.model;

import e.utility.ZipUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hangarita
 */
public class ZipFile2Change {
    ZipUtility zipFile;
    HashMap<String, File> fileChanges;
    boolean addFilesNoExisting;

    public ZipFile2Change(File fileChange, HashMap<String, File> fileChanges, Boolean addFilesNoExisting) throws IOException {
        this.zipFile = new ZipUtility(fileChange);
        this.fileChanges = fileChanges;
        this.addFilesNoExisting = addFilesNoExisting;
    }

    public HashMap<String, File> getFileChanges() {
        return fileChanges;
    }

    public void setFileChanges(HashMap<String, File> fileChanges) throws FileNotFoundException {
        
        HashMap<String, InputStream> fileChanges2 = new HashMap<>();
        
        /*for (Map.Entry<String, File> entry : fileChanges.entrySet()) {
                String key = entry.getKey();
                File value = entry.getValue();
                fileChanges2.put(key, new FileInputStream(value));
        }*/
        
        this.fileChanges = fileChanges;
    }

    public void SaveChanges() throws IOException{
        zipFile.WriteFiles(fileChanges, addFilesNoExisting);
    }
}
