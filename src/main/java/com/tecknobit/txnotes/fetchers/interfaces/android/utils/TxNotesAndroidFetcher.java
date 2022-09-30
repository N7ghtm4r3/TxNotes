package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Records.Account.BotDetails;
import com.tecknobit.traderbot.Routines.Android.AndroidBotController;
import com.tecknobit.traderbot.Routines.Android.AndroidCoreRoutines;
import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.AndroidBinanceFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.AndroidCoinbaseFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesWorkflow.TxNotesCredentials;
import com.tecknobit.txnotes.records.TxNote;
import com.tecknobit.txnotes.records.Wallet;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.tecknobit.traderbot.Records.Account.BotDetails.RUNNING_BOT_STATUS;
import static com.tecknobit.traderbot.Records.Account.BotDetails.STOPPED_BOT_STATUS;
import static com.tecknobit.traderbot.Records.Portfolio.Cryptocurrency.QUOTE_ASSET_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Cryptocurrency.SYMBOL_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Token.BASE_ASSET_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Token.QUANTITY_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTIONS_KEY;
import static com.tecknobit.traderbot.Routines.Android.AndroidWorkflow.Credentials;
import static com.tecknobit.traderbot.Routines.Android.ServerRequest.response;
import static com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesServerRequest.TX_HOST;
import static com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesServerRequest.TX_PORT;
import static com.tecknobit.txnotes.records.TxNote.*;
import static com.tecknobit.txnotes.records.Wallet.DELETED_TX_NOTES_KEY;

/**
 * The {@code TxNotesAndroidFetcher} class is useful to fetch all transactions from exchange's account autonomously <br>
 * This class give basic methods for an android fetcher's workflow
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote this fetcher type will perform autonomously all the routines
 * @implNote This is an Android's interface you can
 * find the apk source at <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>
 * @see AndroidBinanceFetcher
 * @see AndroidCoinbaseFetcher
 **/
public class TxNotesAndroidFetcher extends TxNotesFetcher implements AndroidCoreRoutines, AndroidBotController {

    /**
     * {@code txNotesWorkflow} is instance helpful to manage TxNotes Android's workflow
     **/
    private final TxNotesWorkflow txNotesWorkflow;

    /**
     * {@code botDetails} is instance helpful to manage trader details
     **/
    private final BotDetails botDetails;

    /**
     * {@code runningFetcher} is instance that memorizes flag that indicates if the fetcher is running
     **/
    private boolean runningFetcher;

    /**
     * {@code pastWallet} is instance that memorizes past wallet value
     **/
    private JSONObject pastWallet;

    /**
     * Constructor to init {@link TxNotesAndroidFetcher}
     *
     * @param fetcherPlatform:     fetcher platform to fetch transactions
     * @param botDetails:          details of bot used
     * @param txCredentials:       is object that contains your Tecknobit's account txCredentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     **/
    public TxNotesAndroidFetcher(TraderCoreRoutines fetcherPlatform, BotDetails botDetails, TxNotesCredentials txCredentials,
                                 boolean printRoutineMessages, String baseCurrency, int refreshTime) throws Exception {
        super(fetcherPlatform, baseCurrency);
        this.botDetails = botDetails;
        Credentials credentials = txCredentials.getCredentials();
        initCredentials(credentials);
        txNotesWorkflow = new TxNotesWorkflow(new TxNotesServerRequest(credentials.getIvSpec(), credentials.getSecretKey(),
                credentials.getAuthToken(), credentials.getToken(), TX_HOST, TX_PORT), fetcherPlatform, credentials,
                printRoutineMessages, this);
        runningFetcher = false;
        fetcherPlatform.setRefreshTime(refreshTime);
    }

    /**
     * This method is used to init a {@link Credentials} object to start {@link AndroidWorkflow}
     *
     * @param credentials: is object that contains your Tecknobit's account credentials, not your private exchange keys
     **/
    @Override
    public void initCredentials(Credentials credentials) throws Exception {
        checkCredentialsValidity(credentials);
        credentials.setBotDetails(botDetails);
        if (credentials.getToken() == null)
            credentials.sendRegistrationRequest(TX_HOST, TX_PORT);
        else {
            credentials.sendLoginRequest(baseCurrency, TX_HOST, TX_PORT, null);
            JSONArray deletedNotes = response.getJSONArray(DELETED_TX_NOTES_KEY);
            for (int j = 0; j < deletedNotes.length(); j++)
                txNotesDeleted.add(deletedNotes.getString(j));
            JSONArray txNotes = response.getJSONArray(TRANSACTIONS_KEY);
            for (int j = 0; j < txNotes.length(); j++) {
                JSONObject mTxNote = txNotes.getJSONObject(j);
                String baseAsset = mTxNote.getString(BASE_ASSET_KEY);
                long buyDate = mTxNote.getLong(BUY_DATE_KEY);
                TxNote txNote = new TxNote(mTxNote.getString(SYMBOL_KEY),
                        mTxNote.getString(STATUS_KEY),
                        buyDate,
                        mTxNote.getDouble(INITIAL_BALANCE_KEY),
                        mTxNote.getDouble(QUANTITY_KEY),
                        0,
                        baseAsset,
                        mTxNote.getString(QUOTE_ASSET_KEY)
                );
                if (mTxNote.has(SELL_DATE_KEY)) {
                    txNote.setSellPrice(mTxNote.getDouble(SELL_PRICE_KEY));
                    txNote.setSellDate(mTxNote.getLong(SELL_DATE_KEY));
                }
                this.txNotes.put(baseAsset + buyDate, txNote);
            }
        }
    }

    /**
     * This method is used to start {@link TxNotesWorkflow} <br>
     * Any params required
     **/
    public void startWorkflow() {
        workflowHandler();
    }

    /**
     * This method is used to handle {@link TxNotesWorkflow} <br>
     * Any params required
     **/
    @Override
    public void workflowHandler() {
        enableBot();
        refreshWalletList();
        txNotesWorkflow.startWorkflow();
    }

    /**
     * This method is used to update account wallet list <br>
     * Any params required
     **/
    @Override
    public void refreshWalletList() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject currentWallet = new JSONObject();
                    while (true) {
                        while (runningFetcher) {
                            loadAllData();
                            currentWallet.clear();
                            for (Wallet wallet : wallets.values()) {
                                if (canPrintRoutineMessages())
                                    wallet.printDetails();
                                currentWallet.put(wallet.getIndex(), wallet.getWallet());
                            }
                            if (pastWallet == null || !pastWallet.toMap().equals(new JSONObject(currentWallet.toString()).toMap())) {
                                txNotesWorkflow.insertWallet(currentWallet);
                                pastWallet = new JSONObject(currentWallet.toString());
                            }
                            sleep(fetcherPlatform.getRefreshTime());
                        }
                        System.out.println("Bot is stopped, waiting for reactivation");
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    printRed(e.getMessage());
                }
            }
        }.start();
    }

    /**
     * This method is used to get if bot is in running mode
     *
     * @return flag that indicates if the bot is running
     **/
    @Override
    public boolean isBotRunning() {
        return runningFetcher;
    }

    /**
     * This method is used to disable running mode of fetcher
     *
     * @implNote in Android's interfaces this method updates also
     * {@link #botDetails} status instance to STOPPED_TRADER_STATUS
     **/
    @Override
    public void disableBot() {
        if (isBotRunning()) {
            runningFetcher = false;
            botDetails.setBotStatus(STOPPED_BOT_STATUS);
        }
    }

    /**
     * This method is used to enable running mode of fetcher
     *
     * @implNote in Android's interfaces this method updates also
     * {@link #botDetails} status instance to STOPPED_TRADER_STATUS
     **/
    @Override
    public void enableBot() {
        if (!isBotRunning()) {
            runningFetcher = true;
            botDetails.setBotStatus(RUNNING_BOT_STATUS);
        }
    }

    /**
     * This method is used to get sales at loss
     *
     * @return sales at loss
     * @implNote if {@link #runningFetcher} is false will return -1
     **/
    @Override
    public double getSalesAtLoss() {
        if (runningFetcher) {
            int lossSales = 0;
            for (TxNote txNote : txNotes.values())
                if (txNote.getStatus().equals(SELL) && txNote.getIncomePercent() < 0)
                    lossSales++;
            return lossSales;
        }
        return -1;
    }

    /**
     * This method is used to get sales at gain
     *
     * @return sales at gain
     * @implNote if {@link #runningFetcher} is false will return -1
     **/
    @Override
    public double getSalesAtGain() {
        if (runningFetcher) {
            int gainSales = 0;
            for (TxNote txNote : txNotes.values())
                if (txNote.getStatus().equals(SELL) && txNote.getIncomePercent() > 0)
                    gainSales++;
            return gainSales;
        }
        return -1;
    }

    /**
     * This method is used to get sales at pair
     *
     * @return sales at pair
     * @implNote if {@link #runningFetcher} is false will return -1
     **/
    @Override
    public double getSalesInPair() {
        if (runningFetcher) {
            int pairSales = 0;
            for (TxNote txNote : txNotes.values())
                if (txNote.getStatus().equals(SELL) && txNote.getIncomePercent() == 0)
                    pairSales++;
            return pairSales;
        }
        return -1;
    }

    /**
     * This method is used to get total sales
     *
     * @return total sales
     * @implNote if {@link #runningFetcher} is false will return -1
     **/
    @Override
    public double getTotalSales() {
        if (runningFetcher) {
            int totalSales = 0;
            for (TxNote txNote : txNotes.values())
                if (txNote.getStatus().equals(SELL))
                    totalSales++;
            return totalSales;
        }
        return -1;
    }

    /**
     * This method is used to get base currency for change amount value <br>
     * Any params required
     *
     * @implNote if {@link #runningFetcher} is false will return null
     **/
    @Override
    public String getBaseCurrency() {
        if (runningFetcher)
            return baseCurrency;
        return null;
    }

    /**
     * This method is used to get credentials inserted for trader login
     *
     * @return trader credentials as {@link Credentials} object
     **/
    @Override
    public Credentials getCredentials() {
        return txNotesWorkflow.getCredentials();
    }

    /**
     * This method is used to set flag to print routine messages
     *
     * @param printRoutineMessages: flag to insert to print or not routine messages
     **/
    @Override
    public void setPrintRoutineMessages(boolean printRoutineMessages) {
        txNotesWorkflow.setPrintRoutineMessages(printRoutineMessages);
    }

    /**
     * This method is used to get flag to print or not routine messages
     *
     * @return flag that indicates the possibility or not to print or not routine messages
     **/
    @Override
    public boolean canPrintRoutineMessages() {
        return txNotesWorkflow.canPrintRoutineMessages();
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param asset:      identifier of the asset with transaction has been made
     * @param removeDate: timestamp of the date to delete from {@link #txNotes} list
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    public boolean removeTxNote(String asset, long removeDate) {
        if (txNotesWorkflow.deleteTxNote(asset, removeDate))
            return deleteTxNote(asset, removeDate);
        return false;
    }

    /**
     * This method is used to clear {@link #txNotesDeleted} list to readmit all {@link TxNote} available<br>
     * Any params required
     *
     * @apiNote this means that if these transactions are still available on the platform they will be re-entered, but if they are not
     * more stored on the exchange platform will no longer be recoverable
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    public void reinsertTxNotesDeleted() {
        txNotesWorkflow.allowAllTxNotes();
        super.allowsAllTxNotes();
    }

    /**
     * This method is used to run the bot
     *
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    @Override
    public void runBot() {
        if (!runningFetcher)
            if (txNotesWorkflow.enableBot())
                enableBot();
    }

    /**
     * This method is used to stop the bot
     *
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    @Override
    public void stopBot() {
        if (runningFetcher)
            if (txNotesWorkflow.disableBot())
                disableBot();
    }

    /**
     * This method is used to change time to refresh data
     *
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote in Android's interfaces this method updates also {@link #botDetails} instance
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    @Override
    public void changeRefreshTime(int refreshTime) {
        if (refreshTime >= 5 && refreshTime <= 3600) {
            if (fetcherPlatform.getRefreshTimeSeconds() != refreshTime) {
                if (txNotesWorkflow != null) {
                    if (txNotesWorkflow.changeRefreshTime(refreshTime)) {
                        botDetails.setRefreshTime(refreshTime);
                        fetcherPlatform.setRefreshTime(refreshTime);
                    }
                } else {
                    botDetails.setRefreshTime(refreshTime);
                    fetcherPlatform.setRefreshTime(refreshTime);
                }
            }
        } else
            throw new IllegalArgumentException("Refresh time must be more than 5 (5s) and less than 3600 (1h)");
    }

    /**
     * This method is used to set base currency for change amount value
     *
     * @param baseCurrency: base currency to get all amount value of traders routine es. EUR
     * @apiNote this method is useful to interact with
     * <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>'s interface
     **/
    @Override
    public void changeBaseCurrency(String baseCurrency) {
        if (baseCurrency == null || baseCurrency.isEmpty())
            throw new IllegalArgumentException("Currency cannot be null or empty, but for example EUR or USD");
        if (!this.baseCurrency.equals(baseCurrency)) {
            if (txNotesWorkflow.changeBaseCurrency(baseCurrency))
                this.baseCurrency = baseCurrency;
        }
    }

}
