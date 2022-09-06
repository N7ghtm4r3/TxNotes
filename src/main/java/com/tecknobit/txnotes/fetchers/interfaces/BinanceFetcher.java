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
     * {@code binanceTraderBot} is instance of {@link BinanceTraderBot} helpful to fetch transactions
     **/
    private final BinanceTraderBot binanceTraderBot;

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:    your Binance's api key
     * @param secretKey: your Binance's secret key
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey) throws Exception {
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey);
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
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, baseEndpoint);
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
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, refreshTime);
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
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, baseEndpoint, refreshTime);
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param refreshTime:     is time in seconds to set for refresh the latest prices
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, ArrayList<String> quoteCurrencies,
                          int refreshTime) throws Exception {
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, quoteCurrencies, refreshTime);
    }

    // TODO: 06/09/2022 CHANGE ENDPOINTS HINT

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:          your Binance's api key
     * @param secretKey:       your Binance's secret key
     * @param refreshTime:     is time in seconds to set for refresh the latest prices
     * @param baseEndpoint:    base endpoint choose from BinanceManager.BASE_ENDPOINTS array
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceFetcher(String apiKey, String secretKey, String baseEndpoint, ArrayList<String> quoteCurrencies,
                          int refreshTime) throws Exception {
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, baseEndpoint, quoteCurrencies, refreshTime);
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
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, quoteCurrencies);
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
        binanceTraderBot = new BinanceTraderBot(apiKey, secretKey, baseEndpoint, quoteCurrencies);
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
        long time = 1662455785000L;
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
        }
        /*for (Transaction transaction : binanceTraderBot.getAllTransactions(false)) {
            String status = transaction.getSide();
            long timestamp = transaction.getTransactionTimestamp();
            double lastPrice = 2; // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD
            if(!txNotesDeleted.contains(timestamp) && txNotes.get(timestamp) == null) {
                double value = transaction.getValue();
                double quantity = transaction.getQuantity();
                TxNote txNote = new TxNote(transaction.getSymbol(), status, timestamp, value, quantity, lastPrice);
                // TODO: 06/09/2022 USE LIBRARY CONSTANTS
                if(status.equals("SELL")){
                    txNote.setSellDate(timestamp);
                    txNote.setSellPrice(value / quantity);
                }
                txNotes.put(timestamp, txNote);
            }
        }*/
        mergeTxNotesList();
        return txNotes.values();
    }

    /**
     * This method is used to get error of any requests<br>
     * Any params required
     **/
    @Override
    public String getErrorMessage() {
        return binanceTraderBot.getErrorResponse();
    }

    /**
     * Method to print error response<br>
     * Any params required
     **/
    @Override
    public void printErrorMessage() {
        // TODO: 06/09/2022 TO IMPLEMENT FROM LIBRARY
        System.out.println("to implement");
    }

}
