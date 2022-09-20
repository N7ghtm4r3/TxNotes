package com.tecknobit.txnotes.fetchers.interfaces;

import com.tecknobit.traderbot.Traders.Interfaces.Native.CoinbaseTraderBot;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.records.TxNote;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@code CoinbaseFetcher} class is useful to fetch all transactions from your Coinbase's account. <br>
 * Extends its methods from {@link TxNotesFetcher}
 *
 * @author Tecknobit N7ghtm4r3
 * @implSpec this class work with {@link CoinbaseTraderBot}
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see TxNotesFetcher
 **/

public class CoinbaseFetcher extends TxNotesFetcher {

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           int timeout, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param timeout:     custom timeout for request
     * @param baseCurrency : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout,
                           String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param baseCurrency : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                           ArrayList<String> quoteCurrencies, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, quoteCurrencies),
                baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param timeout:         custom timeout for request
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout,
                           ArrayList<String> quoteCurrencies, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, quoteCurrencies), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           ArrayList<String> quoteCurrencies, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, quoteCurrencies), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase,
                           ArrayList<String> quoteCurrencies, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, quoteCurrencies), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           int timeout, int refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param timeout:     custom timeout for request
     * @param refreshTime: is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout, int refreshTime,
                           String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           short refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param refreshTime: is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, short refreshTime,
                           String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                           ArrayList<String> quoteCurrencies, int refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, quoteCurrencies,
                refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param timeout:         custom timeout for request
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, int timeout, ArrayList<String> quoteCurrencies,
                           int refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, quoteCurrencies, refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                           ArrayList<String> quoteCurrencies, int refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, quoteCurrencies,
                refreshTime), baseCurrency);
    }

    /**
     * Constructor to init {@link CoinbaseFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param baseCurrency         : base currency to get all amount value of traders routine es. EUR
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseFetcher(String apiKey, String apiSecret, String passphrase, ArrayList<String> quoteCurrencies,
                           int refreshTime, String baseCurrency) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, quoteCurrencies, refreshTime), baseCurrency);
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
