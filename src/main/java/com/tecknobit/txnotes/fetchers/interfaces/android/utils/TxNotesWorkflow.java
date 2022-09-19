package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Android.ServerRequest;
import com.tecknobit.traderbot.Routines.Interfaces.RoutineMessages;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import org.json.JSONObject;

import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTIONS_KEY;
import static com.tecknobit.traderbot.Routines.Android.ServerRequest.*;

/**
 * The {@code TxNotesWorkflow} class is useful to manage {@code TxNotes} Android's fetchers workflow<br>
 * Is useful for Android's type fetchers.
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see AndroidWorkflow
 * @see RoutineMessages
 **/

public class TxNotesWorkflow extends AndroidWorkflow {

    /**
     * {@code TX_HOST} host value
     **/
    // TODO: 19/09/2022 TO CHANGE
    public static final String TX_HOST = "localhost";

    /**
     * {@code TX_PORT} port value
     **/
    public static final int TX_PORT = 6898;

    /**
     * Constructor to init {@link TxNotesWorkflow}
     *
     * @param serverRequest        : instance to make server request for Android's fetchers
     * @param trader               : instance of Android's traders used
     * @param credentials          : instance contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     **/
    public TxNotesWorkflow(ServerRequest serverRequest, TraderCoreRoutines trader, Credentials credentials,
                           boolean printRoutineMessages) {
        super(serverRequest, trader, credentials, printRoutineMessages);
    }

    /**
     * This method is used to insert wallet<br>
     *
     * @param wallet: value of wallet to insert
     **/
    public void insertWallet(JSONObject wallet) {
        // TODO: 12/09/2022 TO WORK ON AND SET WALLET KEY FOR PAYLOAD
        try {
            serverRequest.sendTokenRequest(new JSONObject().put(TRANSACTIONS_KEY, wallet), INSERT_WALLET_LIST_OPE);
            response = serverRequest.readResponse();
            if (response != null) {
                switch (response.getInt(STATUS_CODE)) {
                    case SUCCESSFUL_RESPONSE:
                        if (printRoutineMessages)
                            printOperationSuccess(INSERT_WALLET_LIST_OPE);
                        break;
                    case GENERIC_ERROR_RESPONSE:
                        printOperationStatus("[" + INSERT_WALLET_LIST_OPE + "] Wallet list must be valid", false);
                        break;
                    default:
                        printOperationFailed(INSERT_WALLET_LIST_OPE);
                }
            }else
                printOperationFailed(INSERT_WALLET_LIST_OPE);
        } catch (Exception e) {
            printOperationFailed(INSERT_WALLET_LIST_OPE);
        }
    }

}
