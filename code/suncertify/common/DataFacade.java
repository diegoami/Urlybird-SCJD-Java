package suncertify.common;

import java.io.IOException;
import java.util.List;

import suncertify.db.RecordNotFoundException;


/**
 * Interface exposed to the client to communicate both locally and remotely
 */
public interface DataFacade {
    /**
     *
     * Performs a full booking operation (book / unbooking). Sequence is lock record,
     * read it, compare it with the old record (how it was before booking) and book it if it
     * has not been changed by another client
     * @param customerNo the customer who is booking, empty if it is an unbook operation
     * @param oldRecord the record,how it was read from the database
     * @return true if operation was successful,false otherwise
     * @throws RecordNotFoundException
     * @throws RemoteException
     * @throws AlreadyBookedException
     */
    public boolean remoteBookOperation(String customerNo, Record oldRecord)
        throws RecordNotFoundException, IOException, AlreadyBookedException;

    /**
      * Retrieves a record
      * @param recNo position of the record in the file
      * @return an instance of Record filled with data from the file
      */
    public Record retrieveRecord(int recNo)
        throws IOException, RecordNotFoundException;

    /**
      * Retrieves a list of records which match the given search criteria
      * @param hotelName the hotel name search criteria
      * @param cityName  the city search criteria
      * @param refreshCache whether we have to reload data from the cache 
      * @return a list containing all the records which match the search criteria
      */
    public List getRecordList(String hotelName, String cityName, boolean refreshCache)
        throws IOException, RecordNotFoundException;

    /**
     * Dummy method to make sure the object exists (when working
     * remotely)
     * @throws IOException
     */
    public void ping() throws IOException;
}
