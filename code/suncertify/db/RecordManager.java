package suncertify.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import suncertify.common.Record;


/**
 * Helper class, used by the <CODE>Data</CODE> object as a singleton to retrieve and persist
 * records from the database(file) . Contains a simple cache, which is used
 * for reading records, and a list of the indexes of the records which have been deleted
 *
 * @author Diego Amicabile
 */
class RecordManager {
    private static RecordManager recordManager = null;
    private List deletedRecords = new ArrayList();
    private FileManager fileManager = new FileManager();
    private int maxRecords = -1;
    private Map recordCache = new HashMap();
    private Logger log = Logger.getLogger("suncertify.db");

    private RecordManager() {
        fileManager.init();
        refreshCache();
        
    }

    /**
     * Retrieves a <CODE>RecordManager</CODE> singleton instance
     * @return RecordManager singleton instance
     * @throws java.io.IOException
     */
    public static RecordManager getInstance() {
        if (recordManager == null) {
            recordManager = new RecordManager();
        }

        return recordManager;
    }

    /**
     * Clears the cache (to get records that have been modified by a third party)
     * and puts them in memory
     */

	public synchronized void refreshCache() {
		this.recordCache.clear();
		this.deletedRecords.clear();
		this.maxRecords = -1;
		for (int i = 0; i < this.getMaxRecords(); i++) {
			this.getRecord(i);
			
		}
	}
    /**
     * Converts a record to the format we use to store records in our binary file.
     * Spaces are added to make sure fields take up the space which is reserved for them.
     * @param data fields' content to be converted
     */
    void normalize(String[] data) {
        for (int i = 0; i < data.length; i++) {
            int dataLength = data[i].length();
            String result = data[i];

            for (int j = 0;
                    j < (fileManager.fileFormat.schemaDescription[i].fieldLength -
                    dataLength); j++) {
                result += " ";
            }

            data[i] = result;
        }
    }

    /**
     * Retrieves the number of records in the binary file
     * @return number of records found
     **/
    synchronized int getMaxRecords() throws DataAccessException {
        try {
            if (this.maxRecords == -1) {
                maxRecords = fileManager.getMaxRecordsFromFile();
            }
        } catch (IOException e) {
            Logger.getLogger("suncertify.file").severe("IOException in RecordManager.getMaxRecords()");

            throw new DataAccessException(e);
        }

        return maxRecords;
    }

    /**
     * Method used to find out which record is deleted and may be used
     * to store a newly created record
     * @return the position of the first record which is available
     * @throws RecordManagerException
     */
    
    synchronized int getNextAvailableRecordNo() throws DataAccessException {
        if (this.deletedRecords.isEmpty()) {
            return this.getMaxRecords();
        } else {
            return ((Integer) this.deletedRecords.get(0)).intValue();
        }
    }

    /**
     * Persists a record to the database (file)
     * @param recNo the index at which we are persisting the record
     * @param record the record that we are persisting
     * @throws RecordNotFoundException
     */
    synchronized void putRecord(int recNo, Record record)
        throws DataAccessException {
        try {
            record.setRecNo(recNo);
            fileManager.saveDataToFile(recNo, record);
            maxRecords = fileManager.getMaxRecordsFromFile();
            putIntoCache(recNo, record);
        } catch (IOException e) {
            log.severe("IOException in RecordManager.saveData");
            throw new DataAccessException(e);
        }
    }

    /**
     * Retrieves the record at the given position
     * @param recNo position of the record
     * @return the retrieved record object
     * @throws RecordNotFoundException
     */
    public synchronized Record getRecord(int recNo)
        throws RecordNotFoundException, DataAccessException {
        Object object = getFromCache(recNo);

        if (object != null) {
            Record record = (Record) object;

            return record;
        } else {
            Record record;

            try {
                record = fileManager.readFromFile(recNo);
                putIntoCache(recNo, record);

                return record;
            } catch (IOException e) {
                log.severe("IOException in RecordManager.getRecord");
                throw new DataAccessException(e);
            }
        }
    }

    /**
     * After every save operation, this method is called to update the list of deleted entries
     * @param record
     */
    private synchronized void updateDeletedList(Record record) {
        Integer recNoInteger = new Integer(record.getRecNo());

        if (record.isDeleted()) {
            if (!deletedRecords.contains(recNoInteger)) {
                this.deletedRecords.add(recNoInteger);
            }
        } else {
            if (deletedRecords.contains(recNoInteger)) {
                this.deletedRecords.remove(recNoInteger);
            }
        }
    }

    /**
     * Puts the instance of the record into the cache
     * @param intKey the record number
     * @param record the record instance
     */
    private synchronized void putIntoCache(int intKey, Record record) {
        Record clonedRecord = (Record) record.clone();
        log.finer("Putting in cache " + intKey + " = " + clonedRecord);
        updateDeletedList(record);
        recordCache.put(new Integer(intKey), clonedRecord);
    }

    /**
     * Retrieves the instance of the record from the cache
     * @param intKey the record number
     */
    private synchronized Record getFromCache(int intKey) {
        Record record = (Record) recordCache.get(new Integer(intKey));

        if (record != null) {
            Record clonedRecord = (Record) record.clone();
            log.finer("Getting from  cache " + intKey + " = " + record);
            updateDeletedList(record);

            return clonedRecord;
        } else {
            return null;
        }
    }
}
