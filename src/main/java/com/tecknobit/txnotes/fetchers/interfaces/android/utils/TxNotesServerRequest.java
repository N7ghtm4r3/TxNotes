package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Android.ServerRequest;

/**
 * The {@code TxNotesServerRequest} class is useful to make server request for Android's bots<br>
 * Is useful for Android's type bots.
 *
 * @author Tecknobit N7ghtm4r3
 **/

public class TxNotesServerRequest extends ServerRequest {

    /**
     * {@code TX_HOST} host value
     **/
    public static final String TX_HOST = HOST;

    /**
     * {@code TX_PORT} port value
     **/
    public static final int TX_PORT = 6898;

    /**
     * Constructor to init {@link TxNotesServerRequest}
     *
     * @param ivSpec    : initialization vector used in server requests
     * @param secretKey : secret key used in server requests
     * @param authToken : identifier of server trader to log in and requests operations
     * @param token     : identifier of user to log in and requests operations
     * @param host:     host value
     * @param port:     port value
     **/
    public TxNotesServerRequest(String ivSpec, String secretKey, String authToken, String token, String host,
                                int port) throws Exception {
        super(ivSpec, secretKey, authToken, token, host, port);
    }

    /**
     * Constructor to init {@link TxNotesServerRequest}
     *
     * @param ivSpec    : initialization vector used in server requests
     * @param secretKey : secret key used in server requests
     * @param host:     host value
     * @param port:     port value
     **/
    public TxNotesServerRequest(String ivSpec, String secretKey, String host, int port) throws Exception {
        super(ivSpec, secretKey, host, port);
    }

    /**
     * Constructor to init {@link TxNotesServerRequest}
     *
     * @param credentials : instance that contains your Tecknobit's account credentials, not your private exchange keys
     * @param host:       host value
     * @param port:       port value
     **/
    public TxNotesServerRequest(AndroidWorkflow.Credentials credentials, String host, int port) throws Exception {
        super(credentials, host, port);
    }

    /**
     * Constructor to init {@link TxNotesServerRequest}
     *
     * @param host: host value
     * @param port: port value
     **/
    public TxNotesServerRequest(String host, int port) {
        super(host, port);
    }

}
