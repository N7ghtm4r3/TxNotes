package com.tecknobit.txnotes.fetchers.autonomous.interfaces;

import com.tecknobit.traderbot.Traders.Interfaces.Native.CoinbaseTraderBot;
import com.tecknobit.txnotes.fetchers.autonomous.TxNotesAutoFetcher;

import java.util.ArrayList;

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
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               int timeout) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:     your Coinbase's api key
     * @param apiSecret:  your Coinbase's secret key
     * @param passphrase: your Coinbase's api passphrase
     * @param timeout:    custom timeout for request
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:     your Coinbase's api key
     * @param apiSecret:  your Coinbase's secret key
     * @param passphrase: your Coinbase's api passphrase
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                               ArrayList<String> quoteCurrencies) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout,
                quoteCurrencies));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param timeout:         custom timeout for request
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout,
                               ArrayList<String> quoteCurrencies) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, quoteCurrencies));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               ArrayList<String> quoteCurrencies) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, quoteCurrencies));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase,
                               ArrayList<String> quoteCurrencies) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, quoteCurrencies));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param refreshTime:         is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               int timeout, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param timeout:     custom timeout for request
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param refreshTime:         is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               short refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:      your Coinbase's api key
     * @param apiSecret:   your Coinbase's secret key
     * @param passphrase:  your Coinbase's api passphrase
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, short refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage, int timeout,
                               ArrayList<String> quoteCurrencies, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, timeout,
                quoteCurrencies, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param timeout:         custom timeout for request
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, int timeout,
                               ArrayList<String> quoteCurrencies, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, timeout, quoteCurrencies, refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:              your Coinbase's api key
     * @param apiSecret:           your Coinbase's secret key
     * @param passphrase:          your Coinbase's api passphrase
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param refreshTime:         is time in seconds to set to refresh data
     * @param quoteCurrencies:     is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, String defaultErrorMessage,
                               ArrayList<String> quoteCurrencies, int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, defaultErrorMessage, quoteCurrencies,
                refreshTime));
    }

    /**
     * Constructor to init {@link CoinbaseAutoFetcher}
     *
     * @param apiKey:          your Coinbase's api key
     * @param apiSecret:       your Coinbase's secret key
     * @param passphrase:      your Coinbase's api passphrase
     * @param refreshTime:     is time in seconds to set to refresh data
     * @param quoteCurrencies: is a list of quote currencies used in past orders es (USD or EUR)
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     * @implNote these keys will NOT store by library anywhere.
     **/
    public CoinbaseAutoFetcher(String apiKey, String apiSecret, String passphrase, ArrayList<String> quoteCurrencies,
                               int refreshTime) throws Exception {
        super(new CoinbaseTraderBot(apiKey, apiSecret, passphrase, quoteCurrencies, refreshTime));
    }

}
