package com.tecknobit.txnotes.fetchers.autonomous;

import com.tecknobit.traderbot.traders.interfaces.CoinbaseTraderBot;

/**
 * The {@code CoinbaseAutoFetcher} class is useful to fetch all transactions from your Coinbase's account autonomously <br>
 * Extends its methods from {@link TxNotesAutoFetcher}
 *
 * @author Tecknobit N7ghtm4r3
 * @implSpec this class work with {@link CoinbaseTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesAutoFetcher
 **/

public class CoinbaseAutoFetcher extends TxNotesAutoFetcher {

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param defaultErrorMessage:  custom error to show when is not a request error
     * @param timeout:              custom timeout for request
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               int timeout, boolean autoLoadWalletList, boolean printRoutineMessages,
                               String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout), baseCurrency,
                autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param timeout:              custom timeout for request
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout, boolean autoLoadWalletList,
                               boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout), baseCurrency, autoLoadWalletList,
                printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param defaultErrorMessage:  custom error to show when is not a request error
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               boolean autoLoadWalletList, boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage), baseCurrency, autoLoadWalletList,
                printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, boolean autoLoadWalletList,
                               boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase), baseCurrency, autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param defaultErrorMessage:  custom error to show when is not a request error
     * @param timeout:              custom timeout for request
     * @param refreshTime:          is time in seconds to set to refresh data
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                               int refreshTime, boolean autoLoadWalletList, boolean printRoutineMessages,
                               String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, refreshTime),
                baseCurrency, autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param timeout:              custom timeout for request
     * @param refreshTime:          is time in seconds to set to refresh data
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout, int refreshTime,
                               boolean autoLoadWalletList, boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, refreshTime), baseCurrency,
                autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param defaultErrorMessage:  custom error to show when is not a request error
     * @param refreshTime:          is time in seconds to set to refresh data
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               short refreshTime, boolean autoLoadWalletList, boolean printRoutineMessages,
                               String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, refreshTime),
                baseCurrency, autoLoadWalletList, printRoutineMessages);
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:               your Coinbase's api key
     * @param apiSecret:            your Coinbase's secret key
     * @param passphrase:           your Coinbase's api passphrase
     * @param refreshTime:          is time in seconds to set to refresh data
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @param baseCurrency          : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, short refreshTime,
                               boolean autoLoadWalletList, boolean printRoutineMessages, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, refreshTime), baseCurrency, autoLoadWalletList,
                printRoutineMessages);
    }

}
