package com.tecknobit.txnotes.fetchers.interfaces.android;

import com.tecknobit.traderbot.Records.Account.BotDetails;
import com.tecknobit.traderbot.Traders.Interfaces.Native.CoinbaseTraderBot;
import com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesAndroidFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesWorkflow;
import com.tecknobit.txnotes.records.TxNote;

import java.util.Collection;

import static com.tecknobit.traderbot.Routines.Interfaces.TraderBotConstants.*;
import static com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesWorkflow.TxNotesCredentials;

/**
 * The {@code AndroidCoinbaseFetcher} class is useful to fetch all transactions from your Coinbase's account autonomously<br>
 * Extends its methods from {@link TxNotesAndroidFetcher} <br>
 * This class give basic methods for an android fetcher's workflow <br>
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote this fetcher type will perform autonomously all the routines
 * @implNote This is an Android's interface you can <br>
 * find the apk source at <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>
 * @implSpec this class work with {@link CoinbaseTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesAndroidFetcher
 **/

public class AndroidCoinbaseFetcher extends TxNotesAndroidFetcher {

    /**
     * {@code featherDetails} is instance that memorize fetcher details as {@link BotDetails} object
     **/
    private static final BotDetails featherDetails;

    static {
        long timestamp = System.currentTimeMillis();
        featherDetails = new BotDetails(timestamp, BOT_TYPE_MANUAL, RUNNING_BOT_STATUS, COINBASE_PLATFORM,
                10000, timestamp);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                                  TxNotesWorkflow.TxNotesCredentials credentials, boolean printRoutineMessages, String baseCurrency,
                                  int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout), featherDetails, credentials,
                printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param timeout:             custom timeout for request
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout, TxNotesWorkflow.TxNotesCredentials credentials,
                                  boolean printRoutineMessages, String baseCurrency, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout), featherDetails, credentials, printRoutineMessages,
                baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                                  TxNotesWorkflow.TxNotesCredentials credentials, boolean printRoutineMessages, String baseCurrency,
                                  int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage), featherDetails, credentials,
                printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, TxNotesCredentials credentials,
                                  boolean printRoutineMessages, String baseCurrency, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase), featherDetails, credentials, printRoutineMessages,
                baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                                  int timeout, int refreshTime, TxNotesCredentials credentials, boolean printRoutineMessages,
                                  String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, refreshTime), featherDetails,
                credentials, printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param timeout:             custom timeout for request
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout, int refreshTime,
                                  TxNotesWorkflow.TxNotesCredentials credentials, boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, refreshTime), featherDetails, credentials,
                printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                                  int refreshTime, TxNotesWorkflow.TxNotesCredentials credentials, boolean printRoutineMessages,
                                  String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, refreshTime), featherDetails,
                credentials, printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidCoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param credentials          : is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidCoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int refreshTime,
                                  TxNotesWorkflow.TxNotesCredentials credentials, boolean printRoutineMessages,
                                  String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, refreshTime), featherDetails, credentials,
                printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * This method is used to assemble a {@link TxNote}'s list fetched from your Coinbase's account<br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @throws Exception when an operation fails
     **/
    @Override
    public Collection<TxNote> fetchTxNotesList() throws Exception {
        return super.fetchTxNotesList();
    }

}
