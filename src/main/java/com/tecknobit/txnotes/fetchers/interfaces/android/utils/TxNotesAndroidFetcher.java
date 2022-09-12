package com.tecknobit.txnotes.fetchers.interfaces.android.utils;

import com.tecknobit.traderbot.Records.Account.BotDetails;
import com.tecknobit.traderbot.Routines.Android.AndroidCoreRoutines;
import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow;
import com.tecknobit.traderbot.Routines.Android.ServerRequest;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.AndroidBinanceFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.AndroidCoinbaseFetcher;
import com.tecknobit.txnotes.records.TxNote;
import com.tecknobit.txnotes.records.Wallet;
import org.json.JSONObject;

import static com.tecknobit.traderbot.Records.Account.BotDetails.RUNNING_TRADER_STATUS;
import static com.tecknobit.traderbot.Records.Account.BotDetails.STOPPED_TRADER_STATUS;
import static com.tecknobit.traderbot.Routines.Android.AndroidWorkflow.Credentials;

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

public class TxNotesAndroidFetcher extends TxNotesFetcher implements AndroidCoreRoutines {

    /**
     * {@code txNotesWorkflow} is instance helpful to manage TxNotes Android's workflow
     **/
    private final TxNotesWorkflow txNotesWorkflow;

    /**
     * {@code botDetails} is instance helpful to manage trader details
     **/
    private final BotDetails botDetails;

    /**
     * {@code baseCurrency} is instance that memorizes base currency to get all amount value of traders routine es. EUR
     **/
    private String baseCurrency;

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
     * @param credentials:         is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     **/
    public TxNotesAndroidFetcher(TraderCoreRoutines fetcherPlatform, BotDetails botDetails, Credentials credentials,
                                 boolean printRoutineMessages, String baseCurrency, int refreshTime) throws Exception {
        super(fetcherPlatform);
        this.baseCurrency = baseCurrency;
        this.botDetails = botDetails;
        // TODO: 12/09/2022 INSERT HOST AND PORT AND INIT CREDENTIALS
        //initCredentials(credentials);
        txNotesWorkflow = new TxNotesWorkflow(new ServerRequest(credentials.getIvSpec(), credentials.getSecretKey(),
                credentials.getAuthToken(), credentials.getToken(), "194.50.168.19", 7898), fetcherPlatform, credentials,
                printRoutineMessages);
        runningFetcher = false;
        fetcherPlatform.setRefreshTime(refreshTime);
        workflowHandler();
    }

    /**
     * This method is used to init a {@link Credentials} object to start {@link AndroidWorkflow}
     *
     * @param credentials: is object that contains your Tecknobit's account credentials, not your private exchange keys
     **/
    @Override
    public void initCredentials(Credentials credentials) throws Exception {
        checkCredentialsValidity(credentials);
        credentials.setTraderDetails(botDetails);
        if (credentials.getToken() == null)
            credentials.sendRegistrationRequest();
        else
            credentials.sendLoginRequest(baseCurrency, fetcherPlatform.getQuoteCurrencies());
    }

    /**
     * This method is used to handle {@link TxNotesWorkflow} <br>
     * Any params required
     **/
    @Override
    public void workflowHandler() {
        enableTrader();
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
                            for (Wallet wallet : wallets.values())
                                currentWallet.put(wallet.getIndex(), wallet.getWallet());
                            if (pastWallet == null || !pastWallet.equals(currentWallet)) {
                                txNotesWorkflow.insertWallet(currentWallet);
                                pastWallet = currentWallet;
                            }
                            sleep(fetcherPlatform.getRefreshTime());
                        }
                    }
                } catch (Exception e) {
                    printRed(e.getMessage());
                }
            }
        }.start();
    }

    /**
     * This method is used to set time to refresh data
     *
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote in Android's interfaces this method updates also {@link #botDetails} instance
     **/
    @Override
    public void setRefreshTime(int refreshTime) {
        super.setRefreshTime(refreshTime);
        botDetails.setRefreshTime(refreshTime);
    }

    /**
     * This method is used to get if bot is in running mode
     *
     * @return flag that indicates if the bot is running
     **/
    @Override
    public boolean isTraderRunning() {
        return runningFetcher;
    }

    /**
     * This method is used to disable running mode of fetcher
     *
     * @implNote in Android's interfaces this method updates also
     * {@link #botDetails} status instance to STOPPED_TRADER_STATUS
     **/
    @Override
    public void disableTrader() {
        runningFetcher = false;
        botDetails.setTraderStatus(STOPPED_TRADER_STATUS);
    }

    /**
     * This method is used to enable running mode of fetcher
     *
     * @implNote in Android's interfaces this method updates also
     * {@link #botDetails} status instance to STOPPED_TRADER_STATUS
     **/
    @Override
    public void enableTrader() {
        runningFetcher = true;
        botDetails.setTraderStatus(RUNNING_TRADER_STATUS);
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
     * This method is used to set base currency for change amount value
     *
     * @param baseCurrency: base currency to get all amount value of traders routine es. EUR
     **/
    @Override
    public void setBaseCurrency(String baseCurrency) {
        if (baseCurrency == null || baseCurrency.isEmpty())
            throw new IllegalArgumentException("Currency cannot be null or empty, but for example EUR or USDT");
        this.baseCurrency = baseCurrency;
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

}
