package suncertify.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;


/**
 *
 * Singleton helper class used from the <CODE>Data</CODE> class to manage the locking logic.
 * A virtual lock is set on a record number. Virtual locks are saved in a <CODE>HashMap</CODE>  instance
 * where the key is an Integer (record number) and the value is the Data instance which is holding
 * the lock.
 * @author Diego Amicabile
 */
public class LockManager {
    private static LockManager lockManager = new LockManager();
    private Map lockMap = new HashMap();
    private boolean isShuttingDown = false;
    Logger log = Logger.getLogger("suncertify.db");

    private LockManager() {
    }

    /**
     * Unlock the record and notify the waiting threads. Make sure the locker is the
     * same <CODE>Data</CODE> instance which is trying to unlock
     * @param recNo the position of the record we try to unlock
     * @param source the object which is trying to unlock
     *
     */
    void unlock(int recNo, Object source) throws RecordNotFoundException {
        synchronized (source) {
            while (isLockedBy(recNo, source)) {
                tryToUnlockEntry(recNo, source);

                log.finer("LockManager.unlock(" + recNo + "," + source +
                    ") >> unlocked");
            }

            source.notifyAll();
        }
    }

    /**
     * Removes locks from a given object (<CODE>Data</CODE> instance)
     * @param object The object whose locks we want to remove
     */
    public void removeLocksFor(Object object) {
        for (Iterator iterator = this.lockMap.entrySet().iterator();
                iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();

            Object locker = entry.getValue();
            int recNo = ((Integer) entry.getKey()).intValue();

            if (locker == object) {
                this.tryToUnlockEntry(recNo, object);
            }
        }
    }

    /**
     * Cleans up the lock manager, called just before shutdown
     *
     */
    public synchronized void cleanUp() {
        try {
            while (!lockMap.isEmpty()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
        }
    }

    /**
     * Retrieves the singleton instance of <CODE>LockManager</CODE>
     * @return A LockManager Instance
     */
    public static LockManager getInstance() {
        return lockManager;
    }

    /**
     * Lock the record for an object if it is not already locked. If somebody else has the lock,
     * wait for the lock on the record to be released and then try to lock it again.
     *
     * @param recNo Position of the record to lock
     * @param source The objecth which is trying to lock
     */
    void lock(int recNo, Object source) throws RecordLockedException {
        while (!isLockedBy(recNo, source)) {
            Object locker = getLocker(recNo);

            if (locker == null) {
                tryToLockEntry(recNo, source);
            } else {
                synchronized (locker) {
                    log.fine("LockManager.lock(" + recNo + "," + source +
                        ") is waiting for " + locker);

                    try {
                        locker.wait(500);
                    } catch (InterruptedException e) {
                        throw new RecordLockedException(e);
                    }

                    log.fine("LockManager.lock(" + recNo + "," + source +
                        ") after " + locker + " released ? ");
                    tryToLockEntry(recNo, source);
                }
            }
        }

        log.fine("LockManager.lock(" + recNo + "," + source + ")");
    }

    /**
     * Check if a record is locked by a given object
     * @param recNo Position of the record whose locked status we want to check
     * @param source The object which would like to lock
     * @return Whether the record is locked
     */
    boolean isLockedBy(int recNo, Object source) {
        boolean result;
        Object currentLocker = this.getLocker(recNo);
        result = (currentLocker == source);
        log.finer("LockManager.isLockedBy(" + recNo + "," + source + ")= " +
            result + " ( " + currentLocker + ")");

        return result;
    }

    private synchronized void tryToLockEntry(int recNo, Object source) {
        if (!isShuttingDown) {
            Integer recNoInteger = new Integer(recNo);

            if (lockMap.get(recNoInteger) == null) {
                lockMap.put(recNoInteger, source);
            }
        }
    }

    private synchronized void tryToUnlockEntry(int recNo, Object source) {
        Integer recNoInteger = new Integer(recNo);

        if (lockMap.get(recNoInteger) == source) {
            lockMap.remove(recNoInteger);
        }
    }

    private Object getLocker(int recNo) {
        Integer recNoInteger = new Integer(recNo);

        return lockMap.get(recNoInteger);
    }
}
