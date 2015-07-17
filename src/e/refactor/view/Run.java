/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.refactor.view;

import e.utility.FilesUtility;
import e.utility.JFolderChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.xml.transform.TransformerException;

/**
 *
 * @author hangarita
 */
public class Run implements Runnable {

    private boolean isCopy = true;
    private String strPath;
    private JLabel lblData = null;
    private boolean isIgnoreFather = false;
    private File baseFileDirectory;

    final String CHANGE_DIR = "change_files/";
    final String CONFIG_FILE = "config.txt";

    PrintWriter newLog;
    String strNameLog = "READLOG";

    long unixTime = System.currentTimeMillis() / 1000L;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());

    public Run(Object params) {
        Object[] objData = (Object[]) params;

        this.isCopy = ((Boolean) objData[2]);
        this.isIgnoreFather = ((Boolean) objData[3]);

        this.strPath = ((String) objData[0]);
        this.lblData = ((JLabel) objData[1]);
    }

    private Run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        File newFiles = new File(FilesUtility.strRoot.concat(File.separator).concat(CHANGE_DIR));
        ArrayList<File> listChangeFolder = JFolderChooser.listRawFilesForFolder(newFiles, true);

        File configFile = new File(FilesUtility.strRoot.concat(File.separator).concat(CONFIG_FILE));
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(configFile));

            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
            }
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (File file : listFilesForFolder) {

        }
    }

}
