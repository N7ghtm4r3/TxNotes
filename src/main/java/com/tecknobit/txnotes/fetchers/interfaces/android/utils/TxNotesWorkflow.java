package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Records.Android.Routine;
import com.tecknobit.traderbot.Routines.Android.AndroidCoreRoutines;
import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Android.ServerRequest;
import com.tecknobit.traderbot.Routines.Interfaces.RoutineMessages;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.records.TxNote;
import org.json.JSONObject;

import static com.tecknobit.traderbot.Records.Account.BotDetails.RUNNING_BOT_STATUS;
import static com.tecknobit.traderbot.Records.Account.BotDetails.STOPPED_BOT_STATUS;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTIONS_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTION_KEY;
import static com.tecknobit.traderbot.Routines.Android.ServerRequest.*;
import static com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesServerRequest.ALLOW_ALL_TXS_NOTE_OPE;
import static java.lang.Integer.parseInt;

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
     * {@code fetcher} instance of Android's fetcher used
     **/
    private final TxNotesFetcher fetcher;

    /**
     * Constructor to init {@link TxNotesWorkflow}
     *
     * @param serverRequest        : instance to make server request for Android's fetchers
     * @param trader               : instance of Android's traders used
     * @param credentials          : instance contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     **/
    public TxNotesWorkflow(TxNotesServerRequest serverRequest, TraderCoreRoutines trader, Credentials credentials,
                           boolean printRoutineMessages, TxNotesAndroidFetcher fetcher) {
        super(serverRequest, trader, credentials, printRoutineMessages);
        this.fetcher = fetcher;
    }

    /**
     * This method is used to perform routines of Android's workflow <br>
     * Any params required
     **/
    @Override
    protected void performRoutines() {
        getRoutines();
        for (Routine routine : routines) {
            switch (routine.getRoutine()) {
                case CHANGE_EMAIL_OPE:
                    credentials.setEmail(routine.getExtraValue());
                    printOperationStatus("[" + CHANGE_EMAIL_OPE + "] Email successfully changed", true);
                    break;
                case CHANGE_PASSWORD_OPE:
                    credentials.setPassword(routine.getExtraValue());
                    printOperationStatus("[" + CHANGE_PASSWORD_OPE + "] Password successfully changed", true);
                    break;
                case CHANGE_REFRESH_TIME_OPE:
                    if (fetcher instanceof TxNotesAndroidFetcher) {
                        int refreshTime = parseInt(routine.getExtraValue());
                        if (fetcher.getRefreshTimeSeconds() != refreshTime) {
                            fetcher.setRefreshTime(refreshTime);
                            printOperationStatus("[" + CHANGE_REFRESH_TIME_OPE + "] Refresh prices time successfully changed",
                                    true);
                        }
                    }
                    break;
                case CHANGE_BOT_STATUS_OPE:
                    String status = routine.getExtraValue();
                    if (fetcher instanceof TxNotesAndroidFetcher) {
                        if (RUNNING_BOT_STATUS.equals(status)) {
                            if (!((TxNotesAndroidFetcher) fetcher).isBotRunning()) {
                                printOperationStatus("[" + CHANGE_BOT_STATUS_OPE + "] Bot status successfully changed",
                                        true);
                                ((AndroidCoreRoutines) fetcher).enableBot();
                            }
                            printOperationStatus("Bot status: [" + RUNNING_BOT_STATUS + "]", true);
                        } else {
                            if (((TxNotesAndroidFetcher) fetcher).isBotRunning()) {
                                printOperationStatus("[" + CHANGE_BOT_STATUS_OPE + "] Bot status successfully changed",
                                        true);
                                ((AndroidCoreRoutines) fetcher).disableBot();
                            }
                            printOperationStatus("Bot status: [" + STOPPED_BOT_STATUS + "]", false);
                        }
                    }
                    break;
                case CHANGE_CURRENCY_OPE:
                    if (fetcher instanceof TxNotesAndroidFetcher) {
                        String currency = routine.getExtraValue();
                        if (!fetcher.getBaseCurrency().equals(currency)) {
                            fetcher.setBaseCurrency(currency);
                            printOperationStatus("[" + CHANGE_CURRENCY_OPE + "] Base currency successfully changed", true);
                        }
                    }
                    break;
                case DELETE_TRANSACTION_OPE:
                    String txToDelete = routine.getExtraValue();
                    if (!fetcher.getTxNotesDeleted().contains(txToDelete)) {
                        String[] key = txToDelete.split("-");
                        if (fetcher.deleteTxNote(key[0], Long.parseLong(key[1])))
                            printOperationStatus("[" + DELETE_TRANSACTION_OPE + "] Transaction successfully deleted", true);
                    }
                    break;
                case ALLOW_ALL_TXS_NOTE_OPE:
                    fetcher.allowsAllTxNotes();
                    printOperationStatus("[" + ALLOW_ALL_TXS_NOTE_OPE + "] All notes are allowed now", true);
            }
        }
        routines.clear();
    }

    /**
     * This method is used to change refresh time
     *
     * @param refreshTime: is time in seconds to set to refresh data
     * @return result of the operation as boolean, if true operation has been correctly completed wherever false not
     **/
    @Override
    public boolean changeRefreshTime(int refreshTime) {
        if (fetcher.getRefreshTimeSeconds() != refreshTime)
            return super.changeRefreshTime(refreshTime);
        return true;
    }

    /**
     * This method is used to set base currency for change amount value
     *
     * @param baseCurrency: base currency to get all amount value of traders routine es. EUR
     * @return result of the operation as boolean, if true operation has been correctly completed wherever false not
     **/
    @Override
    public boolean changeBaseCurrency(String baseCurrency) {
        if (!fetcher.getBaseCurrency().equals(baseCurrency))
            return super.changeBaseCurrency(baseCurrency);
        return true;
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
        if (!fetcher.getTxNotesDeleted().contains(asset + removeDate)) {
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
        return true;
    }

    /**
     * This method is used to clear list to readmit all {@link TxNote} available but deleted<br>
     * Any params required
     *
     * @apiNote this means that if these transactions are still available on the platform they will be re-entered, but if they are not
     * more stored on the exchange platform will no longer be recoverable
     **/
    public void allowAllTxNotes() {
        if (!fetcher.getTxNotesDeleted().isEmpty()) {
            try {
                serverRequest.sendServerRequest(new JSONObject(), ALLOW_ALL_TXS_NOTE_OPE);
                response = serverRequest.readResponse();
                if (response.getInt(STATUS_CODE) == SUCCESSFUL_RESPONSE) {
                    if (printRoutineMessages)
                        printOperationSuccess(ALLOW_ALL_TXS_NOTE_OPE);
                } else {
                    printOperationFailed(ALLOW_ALL_TXS_NOTE_OPE);
                }
            } catch (Exception e) {
                printOperationFailed(ALLOW_ALL_TXS_NOTE_OPE);
            }
        }
    }

    /**
     * The {@code TxNotesCredentials} class is object for Tecknobit's account credentials
     *
     * @author Tecknobit N7ghtm4r3
     * @implNote it not saves your exchange keys
     * Is useful for Android's type traders.
     **/

    public static class TxNotesCredentials {

        /**
         * {@code credentials} instance to init {@link Credentials} for Android's bots
         **/
        private final Credentials credentials;

        /**
         * Constructor to init {@link TxNotesCredentials}
         *
         * @param authToken: is instance that memorizes identifier of server trader to log in and requests operations
         * @param email:     is instance that memorizes email of user
         * @param password:  is instance that memorizes password of user
         * @param token:     instance that memorizes identifier of user to log in and requests operations
         * @param ivSpec:    instance initialization vector used in server requests
         * @param secretKey: is instance secret key used in server requests
         * @implNote this constructor must call to log in
         **/
        public TxNotesCredentials(String authToken, String email, String password, String token, String ivSpec,
                                  String secretKey) {
            credentials = new Credentials(authToken, email, password, token, ivSpec, secretKey);
        }

        /**
         * Constructor to init {@link TxNotesCredentials}
         *
         * @param email:    is instance that memorizes email of user
         * @param password: is instance that memorizes password of user
         * @implNote this constructor must call to register a new account
         **/
        public TxNotesCredentials(String email, String password) {
            credentials = new Credentials(email, password);
        }

        /**
         * Constructor to init {@link TxNotesCredentials}
         *
         * @param credentials : instance that contains your Tecknobit's account credentials, not your private exchange keys in {@link JSONObject}
         *                    format
         * @implNote this constructor is to create dynamically a {@link Credentials} object
         * @apiNote to work correctly it needs specific keys for credentials -> available on {@link ServerRequest}
         * @see ServerRequest
         **/
        public TxNotesCredentials(JSONObject credentials) {
            this.credentials = new Credentials(credentials);
        }

        /**
         * This method is used to get credentials inserted for auth operation
         *
         * @return Tecknobit's credentials as {@link Credentials} object
         **/
        public Credentials getCredentials() {
            return credentials;
        }

    }

}
