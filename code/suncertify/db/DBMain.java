package suncertify.db;


/**
 * DBMain interface, implemented by the <CODE>Data</CODE> class
 * @author Diego Amicabile
 */
public interface DBMain {
    /**
     * Reads a record from the file. Returns an array where each
     * element is a record value.
     * @param recNo position
     * @return An array containing the fields' values
     * @throws RecordNotFoundException
     */
    public String[] read(int recNo) throws RecordNotFoundException;

    /**
     * Modifies the fields of a record. The new value for field <CODE>n</CODE>
     * appears in <CODE>data[n]</CODE>.
     * @param recNo Position of the record we are about to update
     * @param data An array containing the fields' new values
     * @throws RecordNotFoundException
     **/
    public void update(int recNo, String[] data) throws RecordNotFoundException;

    /**
     * Deletes a record, making the record number and associated disk
     *  storage available for reuse.
     * @param recNo Position of the record we are about to delete
     * @throws RecordNotFoundException
     */
    public void delete(int recNo) throws RecordNotFoundException;

    /**
     * Returns an array of record numbers that match the specified
     * criteria. Field <CODE>n</CODE> in the database file is described by
     * <CODE>criteria[n]</CODE>. A null value in <CODE>criteria[n]</CODE> matches any field
     * value. A non-null  value in <CODE>criteria[n]</CODE> matches any field
     * value that begins with <CODE>criteria[n]</CODE>
     * @param criteria Array containing the search criteria
     * @return An array of int that are the position where the matching records are found
     * @throws RecordNotFoundException
     */
    public int[] find(String[] criteria) throws RecordNotFoundException;

    /**
     * Creates a new record in the database (possibly reusing a
     * deleted entry). Inserts the given data, and returns the record
     * number of the new record.
     * @param data an array of Strings which are the fields' content
     * @return the record number of the new record
     * @throws DuplicateKeyException
     */
    public int create(String[] data) throws DuplicateKeyException;

    /**
    * Locks a record so that it can only be updated or deleted by this client.
    * If the specified record is already locked, the current thread gives up
    * the CPU and consumes no CPU cycles until the record is unlocked.
    * @param recNo the index of the record we are about to lock
    * @throws RecordNotFoundException
    */
    public void lock(int recNo) throws RecordNotFoundException;

    /**
     * Releases the lock on a record.
     * @param recNo The index of the record we are about to unlock
     * @throws RecordNotFoundException
     */
    public void unlock(int recNo) throws RecordNotFoundException;

    /**
    * Determines if a record is currenly locked. Returns true if the
     * record is locked, false otherwise.
     * @param recNo The index of the record whose locked status we want to check
     * @return Whether a record is locked
     * @throws RecordNotFoundException
     */
    public boolean isLocked(int recNo) throws RecordNotFoundException;
}
