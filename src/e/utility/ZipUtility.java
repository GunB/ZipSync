/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author hangarita
 */
public class ZipUtility {
    
    //File newFile;
    
    String strPath;
    String strName;
    String strNewSufij = ".eFixed";

    ZipFile zipFile = null;

    public ZipUtility(String strPath, String strName) throws IOException {
        this.strPath = strPath;
        this.strName = strName;
        String strFile = strPath.concat(File.separator).concat(strName);
        this.zipFile = new ZipFile(strFile);
        System.out.println("Path: " + this.strPath);
        System.out.println("Name: " + this.strName);
        System.out.println("File: " + this.zipFile.getName());
    }

    public ZipUtility(File fileData) throws IOException {
        this.strPath = fileData.getParent();
        this.strName = fileData.getName();
        this.zipFile = new ZipFile(fileData);
        System.out.println("Path: " + this.strPath);
        System.out.println("Name: " + this.strName);
        System.out.println("File: " + this.zipFile.getName());
    }
    
    public HashMap<String, InputStream> ReadFiles(){
        return null;
    }
    
    public void WriteFiles(HashMap<String, InputStream> files2Change)
            throws IOException {
        String strOldFile = this.strPath.concat(File.separator).concat(this.strName);

        String strNewFile = this.strPath.concat(File.separator).concat(this.strName).concat(this.strNewSufij);
        File newFile = new File(strNewFile);

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFile), StandardCharsets.UTF_8);
        System.out.println("Saving [File]: " + strNewFile);
        
        //XMLUtility.printDocument(doc, System.out);

        for (Enumeration e = this.zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = new ZipEntry((ZipEntry) e.nextElement());

            if (!files2Change.containsKey(entryIn.getName())) {

                ZipEntry destEntry = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);

                InputStream is = this.zipFile.getInputStream(entryIn);
                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
            } else {
                ZipEntry destEntry = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);
                
                //InputStream is = new FileInputStream(files2Change.get(entryIn.getName()));
                
                InputStream is = files2Change.get(entryIn.getName());
                
                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                files2Change.remove(entryIn.getName());
                //XMLUtility.printDocument(doc, zos);
            }
            zos.closeEntry();
        }
        
        
        
        zos.close();

        this.zipFile.close();

        File oldFile = new File(strOldFile);
        oldFile.delete();

        newFile.renameTo(oldFile);
    }
}
