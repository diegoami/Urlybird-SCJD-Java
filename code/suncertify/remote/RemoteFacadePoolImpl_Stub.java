// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package suncertify.remote;

public final class RemoteFacadePoolImpl_Stub
    extends java.rmi.server.RemoteStub
    implements suncertify.remote.RemoteDataFacadePool, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_registerDataFacade_0;
    private static java.lang.reflect.Method $method_releaseDataFacade_1;
    
    static {
	try {
	    $method_registerDataFacade_0 = suncertify.remote.RemoteDataFacadePool.class.getMethod("registerDataFacade", new java.lang.Class[] {});
	    $method_releaseDataFacade_1 = suncertify.remote.RemoteDataFacadePool.class.getMethod("releaseDataFacade", new java.lang.Class[] {java.lang.String.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public RemoteFacadePoolImpl_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of registerDataFacade()
    public java.lang.String registerDataFacade()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_registerDataFacade_0, null, -1832055951313194394L);
	    return ((java.lang.String) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of releaseDataFacade(String)
    public void releaseDataFacade(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_releaseDataFacade_1, new java.lang.Object[] {$param_String_1}, -5196105498498848382L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
