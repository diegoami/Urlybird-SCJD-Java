package suncertify.remote;

import java.rmi.Remote;

import suncertify.common.DataFacade;


/**
 * An extension of the <CODE>DataFacade</CODE> interface which
 * extends <CODE>Remote</CODE>
 *
 * @author Diego Amicabile
 */
public interface RemoteDataFacade extends Remote, DataFacade {
}
