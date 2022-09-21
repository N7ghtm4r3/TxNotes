package com.tecknobit.txnotes.fetchers.interfaces.android;

import com.tecknobit.traderbot.Records.Account.BotDetails;
import com.tecknobit.traderbot.Routines.Android.AndroidWorkflow.Credentials;
import com.tecknobit.traderbot.Traders.Interfaces.Native.BinanceTraderBot;
import com.tecknobit.txnotes.fetchers.interfaces.android.utils.TxNotesAndroidFetcher;
import com.tecknobit.txnotes.records.TxNote;

import java.util.Collection;

import static com.tecknobit.traderbot.Records.Account.BotDetails.*;

/**
 * The {@code AndroidBinanceFetcher} class is useful to fetch all transactions from your Binance's account autonomously <br>
 * Extends its methods from {@link TxNotesAndroidFetcher} <br>
 * This class give basic methods for an android fetcher's workflow <br>
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote this fetcher type will perform autonomously all the routines
 * @implNote This is an Android's interface you can <br>
 * find the apk source at <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">TxNotes</a>
 * @implSpec this class work with {@link BinanceTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesAndroidFetcher
 **/

public class AndroidBinanceFetcher extends TxNotesAndroidFetcher {

    /**
     * {@code BINANCE_BASE_ENDPOINTS} is a list constant that contains list of Binance's main endpoints
     **/
    public static final String[] BINANCE_BASE_ENDPOINTS = BinanceTraderBot.BINANCE_BASE_ENDPOINTS;

    /**
     * {@code featherDetails} is instance that memorize fetcher details as {@link BotDetails} object
     **/
    private static final BotDetails featherDetails;

    static {
        long timestamp = System.currentTimeMillis();
        featherDetails = new BotDetails(timestamp, BOT_TYPE_MANUAL, RUNNING_BOT_STATUS, BINANCE_PLATFORM,
                10000, timestamp);
    }

    /**
     * Constructor to init {@link AndroidBinanceFetcher}
     *
     * @param apiKey:              your Binance's api key
     * @param secretKey:           your Binance's secret key
     * @param credentials:         is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidBinanceFetcher(String apiKey, String secretKey, Credentials credentials, boolean printRoutineMessages,
                                 String baseCurrency, int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey), featherDetails, credentials, printRoutineMessages, baseCurrency,
                refreshTime);
    }

    /**
     * Constructor to init {@link AndroidBinanceFetcher}
     *
     * @param apiKey:              your Binance's api key
     * @param secretKey:           your Binance's secret key
     * @param baseEndpoint:        base endpoint choose from {@link #BINANCE_BASE_ENDPOINTS} array
     * @param credentials:         is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @param refreshTime          : is time in seconds to set to refresh data
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidBinanceFetcher(String apiKey, String secretKey, String baseEndpoint, Credentials credentials,
                                 boolean printRoutineMessages, String baseCurrency, int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint), featherDetails, credentials, printRoutineMessages,
                baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidBinanceFetcher}
     *
     * @param apiKey:              your Binance's api key
     * @param secretKey:           your Binance's secret key
     * @param refreshTime:         is time in seconds to set for refresh the latest prices
     * @param credentials:         is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidBinanceFetcher(String apiKey, String secretKey, int refreshTime, Credentials credentials,
                                 boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, refreshTime), featherDetails, credentials, printRoutineMessages,
                baseCurrency, refreshTime);
    }

    /**
     * Constructor to init {@link AndroidBinanceFetcher}
     *
     * @param apiKey:              your Binance's api key
     * @param secretKey:           your Binance's secret key
     * @param baseEndpoint:        base endpoint choose from {@link #BINANCE_BASE_ENDPOINTS} array
     * @param refreshTime:         is time in seconds to set for refresh the latest prices
     * @param credentials:         is object that contains your Tecknobit's account credentials, not your private exchange keys
     * @param printRoutineMessages : flag to insert to print or not routine messages
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public AndroidBinanceFetcher(String apiKey, String secretKey, String baseEndpoint, int refreshTime, Credentials credentials,
                                 boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint, refreshTime), featherDetails, credentials,
                printRoutineMessages, baseCurrency, refreshTime);
    }

    /**
     * This method is used to assemble a {@link TxNote}'s list fetched from your Binance's account<br>
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
