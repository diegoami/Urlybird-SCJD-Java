package suncertify.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import suncertify.common.Utils;


/**
 * Used to keep and retrieve information about the user-defined properties
 * : <b>hostname</b>, <b>filename</b>
 *
 * @author Diego Amicabile
 */
public class PropertiesManager {
    private static final String PROPERTIES_FILE = "suncertify.properties";
    private final static String PROPERTY_HOSTNAME_KEY = "suncertify.hostname";
    private final static String PROPERTY_FILENAME_KEY = "suncertify.filename";
    private final static String DEFAULT_HOSTNAME = "localhost";
    private final static String DEFAULT_FILENAME = "db-1x3.db";
    private static Properties properties = new Properties();
    private static Logger log = Logger.getLogger("suncertify.gui");

    /**
     * The file name where the data is stored, user-defined or by default "db-1x3.db". Used
     * both in "server" and "alone" mode
     *
     * @return the file name
     */
    public static String getFileName() {
        String fileNameProperty = properties.getProperty(PROPERTY_FILENAME_KEY);
        String result = null;

        if (!Utils.isEmptyString(fileNameProperty)) {
            result = fileNameProperty;
        } else {
            result = DEFAULT_FILENAME;
        }

        return result;
    }

    public static void setFileName(String newFileName)
        throws FileNotFoundException {
        File file = new File(newFileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File "+newFileName+" does not exist.\n Working directory is "+getCurrentDir());
        }

        properties.put(PROPERTY_FILENAME_KEY, newFileName);
    }

    public static void setHostName(String newHostName) {
        properties.put(PROPERTY_HOSTNAME_KEY, newHostName);
    }

    /**
     * The host name the client connects to, localhost by default
     *
     * @return the host name
     */
    public static String getHostName() {
        String result = properties.getProperty(PROPERTY_HOSTNAME_KEY);

        if (result == null) {
            result = DEFAULT_HOSTNAME;
        }

        return result;
    }

    /**
     * Retrieves a string containing the current working  directory
     * @return a string containing the current working  directory
     */
    public static String getCurrentDir() {
        return System.getProperty("user.dir") +
        System.getProperty("file.separator");
    }

    /**
     * Tries to read properties from a file and, if found, puts the properties
     * in the <CODE>System</CODE> properties
     */
    static void tryToloadProperties() {
        try {
            File file = new File(PROPERTIES_FILE);

            if (file.exists()) {
                InputStream is = new FileInputStream(file);
                properties.load(is);

                for (Enumeration enum = properties.keys();
                        enum.hasMoreElements();) {
                    String key = (String) enum.nextElement();
                    System.setProperty(key, (String) properties.get(key));
                }
            } else {
                log.warning("Could not find file " + getCurrentDir() +
                    PROPERTIES_FILE);
            }
        } catch (FileNotFoundException e) {
            log.warning("Could not find file suncertify.properties ");
        } catch (IOException e) {
            log.warning("IO error while reading file suncertify.properties ");
        }
    }

    /**
     * Saves the configuration information into the property file
     * "suncertify.properties"
     */
    public static void saveProperties() {
        try {
            File file = new File(PROPERTIES_FILE);
            OutputStream os = new FileOutputStream(file);
            properties.save(os, PROPERTIES_FILE);
        } catch (FileNotFoundException e) {
            log.warning("Could not find file suncertify.properties ");
        }
    }
}
