package suncertify.db;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import suncertify.common.AlreadyBookedException;
import suncertify.common.ApplicationUtils;
import suncertify.common.DataFacade;
import suncertify.common.Record;
import suncertify.common.Utils;


/**
 * Implementation of <CODE>DataFacade</CODE>, which exposes business method to the Gui and
 * can be used also when running in standalone mode. Adapts the <CODE>Data</CODE> implementation
 * to an interface that is more manageable for the GUI
 *
 * @author Diego Amicabile
 *
 */
public class DataAdapter implements DataFacade {
    /**
     * The reference to the <CODE>DBMain</CODE> instance
     */
    protected DBMain dbMain = new Data();

    /**
    *
    *  Performs a full booking operation (book / unbooking). Sequence is lock record,
    *  read it, compare it with the old record (how it was before booking) and book it
    * @param customerNo the customer who is booking, empty if it is an unbook operation
    * @param oldRecord the record,how it was read from the database
    * @return true if operation was successful,false otherwise
    * @throws RecordNotFoundException
    * @throws RemoteException
    * @throws AlreadyBookedException
    */
    public boolean remoteBookOperation(String customerNo, Record oldRecord)
        throws RecordNotFoundException, RemoteException, AlreadyBookedException {
        String[] oldData = (String[]) oldRecord.getData().clone();
        boolean result = false;

        if (!Utils.isEmptyString(customerNo)) {
            oldRecord.book(customerNo);
        } else {
            oldRecord.unBook();
        }

        dbMain.lock(oldRecord.getRecNo());

        try {
            String[] refreshedData = dbMain.read(oldRecord.getRecNo());

            if (Utils.stringArrayEquals(refreshedData, oldData)) {
                dbMain.update(oldRecord.getRecNo(), oldRecord.getData());

                result = true;
            } else {
                result = false;
                throw new AlreadyBookedException(
                    "Data has been changed by another user");
            }
        } finally {
            dbMain.unlock(oldRecord.getRecNo());
        }

        return result;
    }

    /**
     * Retrieves a record
     * @param recNo position of the record in the file
     * @return an instance of Record filled with data from the file
     */
    
    public Record retrieveRecord(int recNo) throws RecordNotFoundException {
        Record record = new Record();
        String[] data = dbMain.read(recNo);
        record.setData(data);
        record.setRecNo(recNo);
        record.setDeleted(false);

        return record;
    }

    /**
     * Retrieves a list of records which match the given search criteria
     * @param hotelName the hotel name search criteria
     * @param cityName  the city search criteria
     * @return a list containing all the records which match the search criteria
     */
    
    public List getRecordList(String hotelName, String cityName, boolean refresh)
        throws RecordNotFoundException {
        String[] criteria = ApplicationUtils.getFullCriteria(hotelName, cityName);
        int[] recNos = null;
        List recordList = new ArrayList();
        if (refresh) {
        	RecordManager.getInstance().refreshCache();
        }
        recNos = (int[]) dbMain.find(criteria);

        for (int i = 0; i < recNos.length; i++) {
            Record record = retrieveRecord(recNos[i]);
            recordList.add(record);
        }

        return recordList;
    }

    public void ping() {
    }
}
