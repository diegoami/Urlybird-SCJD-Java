package suncertify.db;

import java.util.logging.Logger;

import suncertify.common.DefaultRecordValidator;
import suncertify.common.Record;
import suncertify.common.ValidateException;


/**
 * Class used to access the database (which is a binary file)
 * @author Diego Amicabile
 */
public class Data implements DBMain {
    private static LockManager lockManager = LockManager.getInstance();
    private static RecordManager recordManager = RecordManager.getInstance();
    private DefaultRecordValidator recordValidator = new DefaultRecordValidator();
    private Logger log = Logger.getLogger("suncertify.db");
    
    /**
     * Implementation of the create method. It synchronizes on the Data class so that only one 
     * thread can be creating a record at a given time. It asks the record manager which is the next
     * available record number and then tries to lock it and puts the newly created record into it.
     * It repeats the operation until successful. DuplicateKeyException is never actually thrown.
     * @param data the content of the record
     * @return the position of the newly created record
     * @throws DuplicateKeyException
     */

    public int create(String[] data) throws DuplicateKeyException {
        int recNo = 0;
        Record record = new Record();
        saveDataToRecord(record, data);

        boolean created = false;

        while (!created) {
            synchronized (Data.class) { // can create only one record at a time
                try {
                    recNo = recordManager.getNextAvailableRecordNo();

                    lock(recNo);

                    if (recNo == recordManager.getNextAvailableRecordNo()) { // got the lock on the right record, do create
                        log.finer(this + " is writing to record " + recNo);

                        recordManager.putRecord(recNo, record);

                        created = true;
                    }
                } catch (RecordNotFoundException re) {
                    log.finer(this + " skipping record " + recNo + " : " +
                        re.getMessage());
                } finally {
                    try {
                        unlock(recNo);
                    } catch (RecordNotFoundException e1) {
                        log.warning(this + " could not unlock " + recNo);
                    }
                }
            }
        }

        return recNo;
    }

    /**
     * Sets the fields' data in a record after validating
     * @param data Fields' content
     * @param record Record to modify
     */
    private void saveDataToRecord(Record record, String[] data)
        throws ValidateException {
        recordValidator.validate(data);
        recordManager.normalize(data);

        record.setData(data);
        record.setDeleted(false);
    }

    public void delete(int recNo) throws RecordNotFoundException {
        // Cannot delete a record which is not locked
        if (!isLocked(recNo)) {
            throw new RecordNotLockedException(this +
                " Cannot delete a record which is not locked");
        }

        Record record = recordManager.getRecord(recNo);

        if (record.isDeleted()) {
            throw new RecordNotFoundException("Record is already deleted");
        }

        record.setDeleted(true);
        recordManager.putRecord(recNo, record);
    }

    public int[] find(String[] criteria) throws RecordNotFoundException {
        int[] result = new int[0];
        int maxRecords = recordManager.getMaxRecords();

		//uses a temporary int array whose length is the total records
        int[] matchingNumbersTempArray = new int[maxRecords]; 

        int foundSoFar = 0;

		// fills the temporary array with all the ints whose associated records match the search criteria
        for (int recordCounter = 0; recordCounter < maxRecords;
                recordCounter++) {
            Record record = recordManager.getRecord(recordCounter);

            if (!record.isDeleted() && record.matches(criteria)) {
                matchingNumbersTempArray[foundSoFar++] = recordCounter;
            }
        }

		// now that we know how many records match the search criteria, we build the actual array we
		// are going to return 
		
        if (foundSoFar > 0) {
            result = new int[foundSoFar];
            
            System.arraycopy(matchingNumbersTempArray, 0, result, 0, foundSoFar);
        }

        return result;
    }

    public boolean isLocked(int recNo) throws RecordNotFoundException {
        return !lockManager.isLockedBy(recNo, null);
    }

    public void lock(int recNo) throws RecordNotFoundException {
        lockManager.lock(recNo, this);
        log.fine(this + " has locked " + recNo);
    }

    public String[] read(int recNo) throws RecordNotFoundException {
        Record record = recordManager.getRecord(recNo);

        if (!record.isDeleted()) {
            return record.getData();
        } else {
            throw new RecordNotFoundException(
                "RecordNotFoundException: record is deleted");
        }
    }

    public void unlock(int recNo) throws RecordNotFoundException {
        lockManager.unlock(recNo, this);
        log.fine(this + " has unlocked " + recNo);
    }

    public void update(int recNo, String[] data) throws RecordNotFoundException {
        
        if (isLocked(recNo)) {
        
            Record record = recordManager.getRecord(recNo);
        
            if (record.isDeleted()) {
                throw new RecordNotFoundException(
                    "RecordNotFoundException; record is deleted");
            } else {
                this.saveDataToRecord(record, data);

                recordManager.putRecord(recNo, record);
            }
        } else {
            throw new RecordNotLockedException(this +
                "Cannot update a record which is not locked");
        }

    }
}
