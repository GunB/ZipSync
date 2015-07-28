/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.refactor.view;

import e.utility.FilesUtility;
import e.utility.JFolderChooser;
import e.utility.ZipUtility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author hangarita
 */
public class Run implements Runnable {

    private boolean isCopy = true;

    private String strPath;
    private String strPathOrigen;

    private JLabel lblData = null;
    //private boolean addFilesNoExisting = false;
    private String strRegInclude = "";

    final String CHANGE_DIR = "change_files/";
    final String CONFIG_FILE = "config.txt";
    private File baseFileDirectory;
    PrintWriter newLog;
    String strNameLog = "READLOG";

    long unixTime = System.currentTimeMillis() / 1000L;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());

    public Run(Object params) {
        Object[] objData = (Object[]) params;

        this.lblData = ((JLabel) objData[2]);
        this.isCopy = ((Boolean) objData[3]);
        this.strRegInclude = ((String) objData[4]);
        //this.addFilesNoExisting = ((Boolean) objData[3]);

        this.strPath = ((String) objData[0]);
        this.strPathOrigen = ((String) objData[1]);

    }

    private Run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void Log(String strLog) {
        try {
            this.lblData.setText(strLog);
        } catch (NullPointerException localNullPointerException1) {
        }

        try {
            this.newLog.println(timeStamp + " - " + strLog);
        } catch (NullPointerException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(strLog);
    }

    public static void main(String[] args) {
        new Run().Exec(args);
    }

    @Override
    public void run() {
        if (this.isCopy) {
            File CopyFolder = FilesUtility.CopyFolder(this.strPath);
            Log("Copiando archivos...");
            this.strPath = CopyFolder.getPath();
            String[] strparams = {this.strPath};

            Exec(strparams);
        } else {
            String[] strparams = {this.strPath};
            Exec(strparams);
        }
    }

    private void Init(String[] args) {
        System.out.println("Program Arguments:");
        for (String arg : args) {
            System.out.println("\t" + arg);
        }

        baseFileDirectory = new File(args[0]);

        this.strNameLog = baseFileDirectory.getPath().concat(File.separator).concat(this.strNameLog + "_" + this.unixTime).concat(".txt");
        try {
            this.newLog = new PrintWriter(this.strNameLog, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Exec(String[] args) {
        Init(args);

        ArrayList<File> listFilesForFolder = JFolderChooser.listRawFilesForFolder(baseFileDirectory, true);
        HashMap<String, File> listFilesForOrigen = JFolderChooser.mapRawFilesForFolder(new File(this.strPathOrigen), true);

        ArrayList<String> listChanges = new ArrayList<>();

        Pattern p = Pattern.compile(this.strRegInclude);
        listFilesForFolder.removeIf(s -> !p.matcher(s.getName()).matches());

        File configFile = new File(FilesUtility.strRoot.concat(File.separator).concat(CONFIG_FILE));
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(configFile));
            String line;
            while ((line = br.readLine()) != null) {
                listChanges.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (File file : listFilesForFolder) {
            if (file.getName().endsWith(".zip") && listFilesForOrigen.containsKey(file.getName())) {
                File fileOrigen = listFilesForOrigen.get(file.getName());
                try {
                    ZipUtility zip = new ZipUtility(fileOrigen);
                    HashMap<String, Object> inputs = zip.ReadFiles(listChanges);

                    String toString = "";
                    for (String set : inputs.keySet()) {
                        toString = toString.concat(set);
                    }

                    Log("Archivo [Reading]:\t" + fileOrigen.getAbsolutePath());
                    Log("Archivo    [Data]:\t" + toString);

                    ZipUtility zip2 = new ZipUtility(file);
                    zip2.WriteFiles(inputs, true);
                    Log("Archivo  [Saving]:\t" + file.getAbsolutePath());

                    zip.CloseFiles();

                } catch (IOException ex) {
                    Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                    Log("Archivo [ERROR DE LECTURA]:\t" + file.getAbsolutePath() + "\n" + ex.getLocalizedMessage());
                }

            } else {
                Log("Archivo [NO-ENCONTRADO]:\t" + file.getName());
            }
        }

        /*
         listChangeFolder.stream().forEach((file) -> {
         if (arrconfigFile.containsKey(file.getName())) {
         arrConfig.put(arrconfigFile.get(file.getName()), file);
         Log("Preparing...: \t[" + file.getName() + "]");
         } else {
         Log("NOT FOUND: \t[" + file.getName() + "]");
         }
         });

         //ArrayList<ZipFile2Change> arrFiles = new ArrayList<>();
        
        
         //*/
        this.newLog.close();
        JOptionPane.showMessageDialog(null, "Operación exitosa");
        System.exit(0);
    }

}
