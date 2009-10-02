// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

package Glacier2;

/**
 * A helper class for using Glacier2 with GUI applications.
 * 
 * Applications should create a session factory for each Glacier2 router to which the application will
 * connect. To connect with the Glacier2 router, call {@link SessionFactory#connect}. The callback object is
 * notified of the various life cycle events. Once the session is torn down for whatever reason, the application
 * can use the session factory to create another connection.
 */
public class SessionFactoryHelper
{
    /**
     * A callback class to get notifications of status changes in the Glacier2 session.
     * All callbacks on the <code>Callback</code> interface occur in the main swing thread.
     */
    public interface Callback
    {
        /**
         * Notifies the application that the communicator was created.
         *
         * @param session The Glacier2 session.
         */
        void
        createdCommunicator(SessionHelper session);

        /**
         * Notifies the application that the Glacier2 session has been established.
         *  
         * @param session The established session.
         */

        void
        connected(SessionHelper session)
            throws SessionNotExistException;
        
        /**
         * Notifies the application that the Glacier2 session has been disconnected.
         *
         * @param session The disconnected session.
         */
        void
        disconnected(SessionHelper session);

        /**
         * Notifies the application that the Glacier2 session establishment failed. 
         * 
         * @param session The session reporting the connection
         * failure.
         * @param ex The exception.
         */
        void
        connectFailed(SessionHelper session, Throwable ex);
    }

    /**
     * Creates a SessionFactory object.
     * 
     * @param callback The callback object for notifications.
     * @throws {@link Ice.InitializationException}
     */
    public
    SessionFactoryHelper(Callback callback) throws Ice.InitializationException
    {
        initialize(callback, new Ice.InitializationData(), Ice.Util.createProperties());
    }

    /**
     * Creates a SessionFactory object.
     * 
     * @param initData The initialization data to use when creating the communicator.
     * @param callback The callback object for notifications.
     * @throws {@link Ice.InitializationException}
     */
    public
    SessionFactoryHelper(Ice.InitializationData initData, Callback callback) throws Ice.InitializationException
    {
        initialize(callback, initData, initData.properties);
    }

    /**
     * Creates a SessionFactory object.
     *
     * @param properties The properties to use when creating the communicator.
     * @param callback The callback object for notifications.
     * @throws {@link Ice.InitializationException}
     */
    public
    SessionFactoryHelper(Ice.Properties properties, Callback callback) throws Ice.InitializationException
    {
        initialize(callback, new Ice.InitializationData(), properties);
    }

    private void
    initialize(Callback callback, Ice.InitializationData initData, Ice.Properties properties)
        throws Ice.InitializationException
    {
        if(callback == null)
        {
            throw new Ice.InitializationException("Attempt to create a SessionFactoryHelper with a null Callback" +
                                                  "argument");
        }

        if(initData == null)
        {
            throw new Ice.InitializationException("Attempt to create a SessionFactoryHelper with a null " +
                                                  "InitializationData argument");
        }

        if(properties == null)
        {
            throw new Ice.InitializationException("Attempt to create a SessionFactoryHelper with a null Properties " +
                                                  "argument");
        }

        _callback = callback;
        _initData = initData;
        _initData.properties = properties;

        //
        // Set default properties;
        //
        _initData.properties.setProperty("Ice.ACM.Client", "0");
        _initData.properties.setProperty("Ice.RetryIntervals", "-1");
    }

    /**
     * Set the router object identity.
     *
     * @return The Glacier2 router's identity.
     */
    synchronized public void
    setRouterIdentity(Ice.Identity identity)
    {
        _identity = identity;
    }

    /**
     * Returns the object identity of the Glacier2 router.
     *
     * @return The Glacier2 router's identity.
     */
    synchronized public Ice.Identity
    getRouterIdentity()
    {
        return _identity;
    }

    /**
     * Sets the host on which the Glacier2 router runs.
     *
     * @param hostname The host name (or IP address) of the router host.
     */
    synchronized public void
    setRouterHost(String hostname)
    {
        _routerHost = hostname;
    }

    /**
     * Returns the host on which the Glacier2 router runs.
     *
     * @return The Glacier2 router host.
     */
    synchronized public String
    getRouterHost()
    {
        return _routerHost;
    }

    /**
     * Sets whether to connect with the Glacier2 router securely.
     *
     * @param secure If <code>true</code>, the client connects to the router
     * via SSL; otherwise, the client connects via TCP.
     */
    synchronized public void
    setSecure(boolean secure)
    {
        _secure = secure;
    }

    /**
     * Returns whether the session factory will establish a secure connection to the Glacier2 router.
     *
     * @return The secure flag.
     */
    synchronized public boolean
    getSecure()
    {
        return _secure;
    }

    /**
     * Sets the connect and connection timeout for the Glacier2 router.
     *
     * @param timeoutMillisecs The timeout in milliseconds. A zero
     * or negative timeout value indicates that the router proxy has no associated timeout.
     */
    synchronized public void
    setTimeout(int timeoutMillisecs)
    {
        _timeout = timeoutMillisecs;
    }

    /**
     * Returns the connect and connection timeout associated with the Glacier2 router.
     *
     * @return The timeout.
     */
    synchronized public int
    getTimeout()
    {
        return _timeout;
    }

    /**
     * Sets the Glacier2 router port to connect to.
     *
     * @param port The port. If 0, then the default port (4063 for TCP or 4064 for SSL) is used.
     */
    synchronized public void
    setPort(int port)
    {
        _port = port;
    }

    /**
     * Returns the Glacier2 router port to connect to.
     *
     * @return The port.
     */
    synchronized public int
    getPort()
    {
        return _port == 0 ? (_secure ? GLACIER2_TCP_PORT : GLACIER2_SSL_PORT) : _port;
    }

    /**
     * Returns the initialization data used to initialize the communicator.
     *
     * @return The initialization data.
     */
    synchronized public Ice.InitializationData
    getInitializationData()
    {
        return _initData;
    }

    /**
     * Connects to the Glacier2 router using the associated SSL credentials.
     *
     * Once the connection is established, {@link Callback#connected} is called on the callback object;
     * upon failure, {@link Callback#connectFailed} is called with the exception.
     *
     * @return The connected session.
     */
    synchronized public SessionHelper
    connect()
    {
        SessionHelper session = new SessionHelper(_callback, createInitData());
        session.connect();
        return session;
    }

    /**
     * Connect the Glacier2 session using user name and password credentials.
     *
     * Once the connection is established, {@link Callback#connected} is called on the callback object;
     * upon failure, {@link Callback#connectFailed) is called with the exception. 
     * 
     * @param username The user name.
     * @param password The password.
     * @return The connected session.
     */
    synchronized public SessionHelper
    connect(final String username, final String password)
    {
        SessionHelper session = new SessionHelper(_callback, createInitData());
        session.connect(username, password);
        return session;
    }

    private Ice.InitializationData
    createInitData()
    {
        // Clone the initialization data and properties.
        Ice.InitializationData initData = (Ice.InitializationData)_initData.clone();
        initData.properties = initData.properties._clone();

        if(initData.properties.getProperty("Ice.Default.Router").length() == 0)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("\"");
            sb.append(Ice.Util.identityToString(_identity));
            sb.append("\"");
            sb.append(":");

            if(_secure)
            {
                sb.append("ssl -p ");
            }
            else
            {
                sb.append("tcp -p ");
            }

            if(_port != 0)
            {
                sb.append(_port);
            }
            else
            {
                if(_secure)
                {
                    sb.append(GLACIER2_SSL_PORT);
                }
                else
                {
                    sb.append(GLACIER2_TCP_PORT);
                }
            }

            sb.append(" -h ");
            sb.append(_routerHost);
            if(_timeout > 0)
            {
                sb.append(" -t ");
                sb.append(_timeout);
            }

            initData.properties.setProperty("Ice.Default.Router", sb.toString());
        }
        return initData;
    }

    private Callback _callback;
    private String _routerHost = "127.0.0.1";
    private Ice.InitializationData _initData;
    private Ice.Identity _identity = new Ice.Identity("router", "Glacier2");
    private boolean _secure = true;
    private int _port = 0;
    private int _timeout = 10000;
    private static final int GLACIER2_SSL_PORT = 4064;
    private static final int GLACIER2_TCP_PORT = 4063;
}