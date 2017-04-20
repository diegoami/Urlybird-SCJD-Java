package suncertify.db;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

import suncertify.common.Record;
import suncertify.runner.PropertiesManager;


/**
 *
 * Class containing all operations that can be performed on the file
 * @author Diego Amicabile
 */

class FileManager {
    /**
     * Contains information about the database file format
     */
    FileFormat fileFormat = null;

    private String getFileName() {
        return PropertiesManager.getFileName();
    }

    /**
     * Initializes the fileFormat field checking if the file format is valid
     */
    void init() {
        fileFormat = retrieveFileFormat();
    }

    /**
     * Saves a record to the file
     * @param recNo the position of the record
     * @param record the record data
     * @throws IOException
     */
    void saveDataToFile(int recNo, Record record) throws IOException {
        long offset = fileFormat.dataOffset +
            (recNo * fileFormat.recordLength);
        RandomAccessFile randomAccessFile = getRandomAccessFile();

        try {
            if (randomAccessFile.length() >= offset) {
                randomAccessFile.seek(offset);
                randomAccessFile.writeByte(record.getDeletedByte());

                for (short s = 0; s < fileFormat.numberOfFields; s++) {
                    byte[] columnDataBytes = (byte[]) record.getData()[s].getBytes();
                    randomAccessFile.write(columnDataBytes);
                }
            } else {
                throw new RecordNotFoundException(
                    "Record not found while writing");
            }
        } finally {
            randomAccessFile.close();
        }
    }

    /**
     * looks up how many records are in the file
     * @return how many records in the file
     * @throws java.io.IOException
     */
    int getMaxRecordsFromFile() throws java.io.IOException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        int result = (int) ((randomAccessFile.length() - fileFormat.dataOffset) / fileFormat.recordLength);
        randomAccessFile.close();

        return result;
    }

    /**
     * Reads a record from the file
     * @param recNo position in the file
     * @return the record filled with data
     * @throws RecordNotFoundException
     * @throws IOException
     */
    Record readFromFile(int recNo) throws RecordNotFoundException, IOException {
        RandomAccessFile randomAccessFile = null;
        Record record = new Record();
        record.setRecNo(recNo);

        try {
            long offset = fileFormat.dataOffset +
                (recNo * fileFormat.recordLength);
            randomAccessFile = getRandomAccessFile();

            if (randomAccessFile.length() > offset) {
                randomAccessFile.seek(offset);
                record.setData(new String[fileFormat.numberOfFields]);
                record.setDeletedByte(randomAccessFile.readByte());

                for (short s = 0; s < fileFormat.numberOfFields; s++) {
                    byte[] columnDataBytes = new byte[fileFormat.schemaDescription[s].fieldLength];
                    randomAccessFile.read(columnDataBytes);

                    String columnData = new String(columnDataBytes);
                    record.getData()[s] = columnData;
                }
            } else {
                throw new RecordNotFoundException(
                    "Record not found while reading");
            }
        } catch (FileNotFoundException e) {
            this.fileNotFoundMessage(getFileName());
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                throw new RecordNotFoundException(e);
            }
        }

        return record;
    }

    /**
    * Reads the file and creates a <CODE>FileFormat</CODE> object , filling it
    * with information about the way data is stored in the database
    * @return the newly created <CODE>FileFormat</CODE> instance
    * @throws java.io.IOException
    */
    FileFormat retrieveFileFormat() {
        FileFormat fileFormat = new FileFormat();

        try {
            DataInputStream dataInputStream = getDataInputStream();
            fileFormat = new FileFormat();
            fileFormat.magicCookieValue = dataInputStream.readInt();
            fileFormat.numberOfFields = dataInputStream.readShort();
            fileFormat.schemaDescription = new FieldDescription[fileFormat.numberOfFields];
            fileFormat.dataOffset = 6;
            fileFormat.recordLength = 1;

            for (short s = 0; s < fileFormat.numberOfFields; s++) {
                FieldDescription fieldDescription = new FieldDescription();
                fieldDescription.fieldNameLength = dataInputStream.readByte();

                byte[] fieldNameBytes = new byte[fieldDescription.fieldNameLength];
                dataInputStream.read(fieldNameBytes);
                fieldDescription.fieldName = new String(fieldNameBytes);
                fieldDescription.fieldLength = dataInputStream.readByte();
                fileFormat.schemaDescription[s] = fieldDescription;
                fileFormat.recordLength += fieldDescription.fieldLength;
                fileFormat.dataOffset += (1 + fieldDescription.fieldNameLength +
                1);
            }

            dataInputStream.close();

            Logger.getLogger("suncertify.db").fine("FileFormat = " +
                fileFormat);

            return fileFormat;
        } catch (FileNotFoundException e) {
            fileNotFoundMessage(getFileName());
        } catch (IOException e) {
            System.err.println("Could not recognize the file's format");
            System.exit(-1);
        }

        return fileFormat;
    }

    private DataInputStream getDataInputStream() throws FileNotFoundException {
        DataInputStream dataIn = new DataInputStream(new FileInputStream(
                    getFile()));

        return dataIn;
    }

    private void fileNotFoundMessage(String fileName) {
        System.err.println("Could not open file " + fileName);
        System.exit(-1);
    }

    private File getFile() {
        File file = new File(getFileName());

        return file;
    }

    private RandomAccessFile getRandomAccessFile() throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(getFile(), "rw");

        return randomAccessFile;
    }
}
