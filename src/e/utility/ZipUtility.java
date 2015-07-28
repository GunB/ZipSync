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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
    HashMap<String, Object> arrInputs = null;
    
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
    
    public HashMap<String, Object> ReadFiles(ArrayList<String> arrstrFiles) throws IOException {
        HashMap<String, Object> arrFiles = new HashMap<>();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        
        String strRegexp = "";
        
        for (String strFile : arrstrFiles) {
            strRegexp = strRegexp + strFile + "|";
        }
        
        if (strRegexp.endsWith("|")) {
            strRegexp = strRegexp.substring(0, strRegexp.length() - 1);
        }
        
        Pattern p = Pattern.compile(strRegexp);
        
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (p.matcher(entry.getName()).matches()) {
                arrFiles.put(entry.getName(), zipFile.getInputStream(entry));
            }
        }
        
        this.arrInputs = arrFiles;
        return arrFiles;
    }
    
    public void CloseFiles() throws IOException {
        try {
            for (Map.Entry<String, Object> entry : arrInputs.entrySet()) {
                Object value = entry.getValue();
                InputStream is = (InputStream) value;
                is.close();
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(ZipUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrInputs = null;
    }
    
    public void WriteFiles(HashMap<String, Object> files2Changes, boolean writeNewFiles) throws IOException {
        String strOldFile = this.strPath.concat(File.separator).concat(this.strName);
        
        String strNewFile = this.strPath.concat(File.separator).concat(this.strName).concat(this.strNewSufij);
        File newFile = new File(strNewFile);
        
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFile), StandardCharsets.UTF_8);
        System.out.println("Saving [File]: " + strNewFile);
        
        HashMap<String, Object> files2Change = (HashMap<String, Object>) files2Changes.clone();
        
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
                System.out.println(entryIn.getName());
                ZipEntry destEntry = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);
                
                Object ob = files2Change.get(entryIn.getName());
                InputStream is = null;
                
                if (ob instanceof File) {
                    is = new FileInputStream((File) ob);
                } else if (ob instanceof InputStream) {
                    is = (InputStream) ob;
                }

                //InputStream is = new FileInputStream(files2Change.get(entryIn.getName()));
                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                
                files2Change.remove(entryIn.getName());
                //is.reset();

                if (ob instanceof File) {
                    is.close();
                }
                
            }
            zos.closeEntry();
        }
        if (writeNewFiles) {
            
            for (Map.Entry<String, Object> entry : files2Change.entrySet()) {
                String key = entry.getKey();
                Object ob = entry.getValue();
                
                System.out.println(key);
                ZipEntry destEntry = new ZipEntry(key);
                zos.putNextEntry(destEntry);
                
                InputStream is = null;
                if (ob instanceof File) {
                    is = new FileInputStream((File) ob);
                } else if (ob instanceof InputStream) {
                    is = (InputStream) ob;
                }
                
                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }

                //is.reset();
                if (ob instanceof File) {
                    is.close();
                }
                zos.closeEntry();
            }
        }
        
        zos.close();
        
        this.zipFile.close();
        
        File oldFile = new File(strOldFile);
        oldFile.delete();
        
        newFile.renameTo(oldFile);
    }
    
}
