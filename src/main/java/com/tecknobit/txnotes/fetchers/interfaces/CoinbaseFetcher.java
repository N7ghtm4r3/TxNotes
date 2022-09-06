package com.tecknobit.txnotes.fetchers.interfaces;

import com.tecknobit.traderbot.Traders.Interfaces.Native.CoinbaseTraderBot;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.records.TxNote;

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
     * This method is used to assemble a {@link TxNote}'s list fetched from your Coinbase's account<br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @throws Exception when an operation fails
     **/
    @Override
    public Collection<TxNote> fetchTxNotesList() throws Exception {
        return null;
    }

    /**
     * This method is used to get error of any requests<br>
     * Any params required
     **/
    @Override
    public String getErrorMessage() {
        return null;
    }

    /**
     * Method to print error response <br>
     * Any params required
     **/
    @Override
    public void printErrorMessage() {
        // TODO: 06/09/2022 TO IMPLEMENT FROM LIBRARY
        System.out.println("to implement");
    }

}
