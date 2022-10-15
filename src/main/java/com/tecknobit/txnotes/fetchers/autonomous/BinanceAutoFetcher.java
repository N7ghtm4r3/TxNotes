package com.tecknobit.txnotes.fetchers.autonomous;

import com.tecknobit.traderbot.traders.interfaces.BinanceTraderBot;
import com.tecknobit.txnotes.fetchers.interfaces.BinanceFetcher;

/**
 * The {@code BinanceAutoFetcher} class is useful to fetch all transactions from your Binance's account autonomously <br>
 * Extends its methods from {@link TxNotesAutoFetcher}
 *
 * @author Tecknobit N7ghtm4r3
 * @implSpec this class work with {@link BinanceTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesAutoFetcher
 **/

public class BinanceAutoFetcher extends TxNotesAutoFetcher {

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:               your Binance's api key
     * @param secretKey:            your Binance's secret key
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceAutoFetcher(String apiKey, String secretKey, boolean autoLoadWalletList,
                              boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey), baseCurrency, autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:               your Binance's api key
     * @param secretKey:            your Binance's secret key
     * @param baseEndpoint:         base endpoint choose from {@link BinanceTraderBot#BINANCE_BASE_ENDPOINTS} array
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceAutoFetcher(String apiKey, String secretKey, String baseEndpoint, boolean autoLoadWalletList,
                              boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint), baseCurrency, autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:               your Binance's api key
     * @param secretKey:            your Binance's secret key
     * @param refreshTime:          is time in seconds to set for refresh the latest prices
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceAutoFetcher(String apiKey, String secretKey, int refreshTime, boolean autoLoadWalletList,
                              boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, refreshTime), baseCurrency, autoLoadWalletList,
                printRoutineMessages);
    }

    /**
     * Constructor to init {@link BinanceFetcher}
     *
     * @param apiKey:               your Binance's api key
     * @param secretKey:            your Binance's secret key
     * @param refreshTime:          is time in seconds to set for refresh the latest prices
     * @param baseEndpoint:         base endpoint choose from {@link BinanceTraderBot#BINANCE_BASE_ENDPOINTS} array
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public BinanceAutoFetcher(String apiKey, String secretKey, String baseEndpoint, int refreshTime,
                              boolean autoLoadWalletList, boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new BinanceTraderBot(apiKey, secretKey, baseEndpoint, refreshTime), baseCurrency, autoLoadWalletList,
                printRoutineMessages);
    }

}
