package com.tecknobit.txnotes.fetchers.interfaces;

import com.tecknobit.traderbot.Traders.Interfaces.Native.BinanceTraderBot;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.records.TxNote;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@code BinanceFetcher} class is useful to fetch all transactions from your Binance's account. <br>
 * Extends its methods from {@link TxNotesFetcher}
 *
 * @author Tecknobit N7ghtm4r3
 * @implSpec this class work with {@link BinanceTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesFetcher
 **/

public class BinanceFetcher extends TxNotesFetcher {

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:    your Binance's api key
     * @param secretKey: your Binance's secret key
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey));
    }

    // TODO: 06/09/2022 CHANGE ENDPOINTS HINT 

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:       your Binance's api key
     * @param secretKey:    your Binance's secret key
     * @param baseEndpoint: base endpoint choose from BinanceManager.BASE_ENDPOINTS array
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, String baseEndpoint) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint));
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:      your Binance's api key
     * @param secretKey:   your Binance's secret key
     * @param refreshTime: is time in seconds to set for refresh the latest prices
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, refreshTime));
    }

    // TODO: 06/09/2022 CHANGE ENDPOINTS HINT 

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:       your Binance's api key
     * @param secretKey:    your Binance's secret key
     * @param refreshTime:  is time in seconds to set for refresh the latest prices
     * @param baseEndpoint: base endpoint choose from BinanceManager.BASE_ENDPOINTS array
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, String baseEndpoint, int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint, refreshTime));
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, ArrayList<String> quoteCurrencies,
                          int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, quoteCurrencies, refreshTime));
    }

    // TODO: 06/09/2022 CHANGE ENDPOINTS HINT

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param baseEndpoint:    base endpoint choose from BinanceManager.BASE_ENDPOINTS array
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, String baseEndpoint, ArrayList<String> quoteCurrencies,
                          int refreshTime) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint, quoteCurrencies, refreshTime));
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, ArrayList<String> quoteCurrencies) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, quoteCurrencies));
    }

    // TODO: 06/09/2022 CHANGE ENDPOINTS HINT

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param baseEndpoint:    base endpoint choose from BinanceManager.BASE_ENDPOINTS array
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, String baseEndpoint,
                          ArrayList<String> quoteCurrencies) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint, quoteCurrencies));
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
        /*long time = 1662455785000L;
        long[] times = new long[]{time, time + 1000, time + 2000, time + 3000, time + 4000};
        if (!txNotesDeleted.contains(time)) {
            txNotes.put(times[0], new TxNote("BTCBUSD",
                    "BUY",
                    times[0],
                    10,
                    10,
                    10
            ));
            txNotes.put(times[1], new TxNote("BTCBUSD",
                    "SELL",
                    times[1],
                    10,
                    2,
                    11,
                    22,
                    times[1]
            ));
        }*/
        return super.fetchTxNotesList();
    }

}
