/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.refactor.model;

import e.utility.ZipUtility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author hangarita
 */
public class ZipFile2Change {
    ZipUtility zipFile;
    HashMap<String, Object> fileChanges;
    boolean addFilesNoExisting;

    public ZipFile2Change(File fileChange, HashMap<String, Object> fileChanges, Boolean addFilesNoExisting) throws IOException {
        this.zipFile = new ZipUtility(fileChange);
        this.fileChanges = fileChanges;
        this.addFilesNoExisting = addFilesNoExisting;
    }

    public HashMap<String, Object> getFileChanges() {
        return fileChanges;
    }

    public void setFileChanges(HashMap<String, Object> fileChanges) throws FileNotFoundException {        
        this.fileChanges = fileChanges;
    }

    public void SaveChanges() throws IOException{
        zipFile.WriteFiles(fileChanges, addFilesNoExisting);
    }
}
