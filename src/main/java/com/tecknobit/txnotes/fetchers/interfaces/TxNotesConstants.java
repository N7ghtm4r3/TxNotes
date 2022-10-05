package com.tecknobit.txnotes.fetchers.interfaces;

import com.tecknobit.traderbot.Routines.Interfaces.TraderBotConstants;

/**
 * The {@code TxNotesConstants} interface defines all constants used in TxNotes library that are useful also
 * for a custom easy use.
 *
 * @author Tecknobit N7ghtm4r3
 **/

public interface TxNotesConstants extends TraderBotConstants {

    /**
     * {@code STATUS_KEY} key
     **/
    String STATUS_KEY = "status";

    /**
     * {@code BUY_DATE_KEY} key
     **/
    String BUY_DATE_KEY = "buy_date";

    /**
     * {@code INITIAL_BALANCE_KEY} key
     **/
    String INITIAL_BALANCE_KEY = "initial_balance";

    /**
     * {@code SELL_DATE_KEY} key
     **/
    String SELL_DATE_KEY = "sell_date";

    /**
     * {@code SELL_PRICE_KEY} key
     **/
    String SELL_PRICE_KEY = "sell_price";

    /**
     * {@code TRADE_DAYS_KEY} key
     **/
    String TRADE_DAYS_KEY = "trade_days";

    /**
     * {@code WALLET_KEY} is instance that memorizes wallet key
     **/
    String WALLET_KEY = "wallet";

    /**
     * {@code DELETED_TX_NOTES_KEY} is instance that memorizes deleted tx notes key
     **/
    String DELETED_TX_NOTES_KEY = "deleted_tx_notes";

    /**
     * {@code ALLOW_ALL_TXS_NOTE_OPE} request
     **/
    String ALLOW_ALL_TXS_NOTE_OPE = "allow_all_txs_ope";

}
