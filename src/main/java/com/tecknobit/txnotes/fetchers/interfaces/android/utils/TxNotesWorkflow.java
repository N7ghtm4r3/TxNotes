package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Android.ServerRequest;
import com.tecknobit.traderbot.Routines.Interfaces.RoutineMessages;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.records.TxNote;
import org.json.JSONObject;

import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTIONS_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTION_KEY;
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
// TODO: 21/09/2022 EXECUTE CUSTOM ROUTINES
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
     * {@code ALLOW_ALL_TXS_NOTE_OPE} request
     **/
    public static final String ALLOW_ALL_TXS_NOTE_OPE = "allow_all_txs_ope";

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
            } else
                printOperationFailed(INSERT_WALLET_LIST_OPE);
        } catch (Exception e) {
            printOperationFailed(INSERT_WALLET_LIST_OPE);
        }
    }

    /**
     * This method is used to delete a transaction note from {@link TxNote}s list
     *
     * @param asset:      identifier of the asset with transaction has been made
     * @param removeDate: timestamp of the date to delete from {@link TxNote}s list
     * @return result of the operation as boolean, if true operation has been correctly completed wherever false not
     * @apiNote this method use only the buy date of a transaction
     **/
    public boolean deleteTxNote(String asset, long removeDate) {
        try {
            serverRequest.sendServerRequest(new JSONObject().put(TRANSACTION_KEY, asset + "-" + removeDate),
                    DELETE_TRANSACTION_OPE);
            response = serverRequest.readResponse();
            switch (response.getInt(STATUS_CODE)) {
                case SUCCESSFUL_RESPONSE:
                    if (printRoutineMessages)
                        printOperationSuccess(DELETE_TRANSACTION_OPE);
                    return true;
                case GENERIC_ERROR_RESPONSE:
                    printOperationStatus("[" + DELETE_TRANSACTION_OPE + "] Tx note's key must be valid", false);
                    return false;
                default:
                    printOperationFailed(DELETE_TRANSACTION_OPE);
                    return false;
            }
        } catch (Exception e) {
            printOperationFailed(DELETE_TRANSACTION_OPE);
            return false;
        }
    }

    /**
     * This method is used to clear list to readmit all {@link TxNote} available but deleted<br>
     * Any params required
     *
     * @apiNote this means that if these transactions are still available on the platform they will be re-entered, but if they are not
     * more stored on the exchange platform will no longer be recoverable
     **/
    public boolean allowAllTxNotes() {
        try {
            serverRequest.sendServerRequest(new JSONObject(), ALLOW_ALL_TXS_NOTE_OPE);
            response = serverRequest.readResponse();
            if (response.getInt(STATUS_CODE) == SUCCESSFUL_RESPONSE) {
                if (printRoutineMessages)
                    printOperationSuccess(ALLOW_ALL_TXS_NOTE_OPE);
                return true;
            } else {
                printOperationFailed(ALLOW_ALL_TXS_NOTE_OPE);
                return false;
            }
        } catch (Exception e) {
            printOperationFailed(ALLOW_ALL_TXS_NOTE_OPE);
            return false;
        }
    }

}
