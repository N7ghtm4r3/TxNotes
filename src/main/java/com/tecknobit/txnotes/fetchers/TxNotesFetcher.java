package com.tecknobit.txnotes.fetchers;

import com.tecknobit.txnotes.fetchers.interfaces.BinanceFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.CoinbaseFetcher;
import com.tecknobit.txnotes.records.TxNote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static com.tecknobit.traderbot.Records.Portfolio.Transaction.getDateTimestamp;

/**
 * The {@code TxNotesFetcher} class is useful to fetch all transactions from exchange's account <br>
 * This class give basic methods for a fetcher's workflow
 *
 * @author Tecknobit N7ghtm4r3
 * @see BinanceFetcher
 * @see CoinbaseFetcher
 **/

public abstract class TxNotesFetcher {

    /**
     * {@code txNotesDeleted} is instance of {@link Long} helpful to indicate which {@link TxNote} must not available to be reinserted
     *
     * @implNote when you delete a {@link TxNote} that will be deleted only in {@code TxNotes} infrastructure not on
     * exchange's account
     **/
    protected final ArrayList<Long> txNotesDeleted = new ArrayList<>();

    /**
     * {@code txNotes} is instance of {@link TxNote} as value and {@link Long} representing the buy date as key
     **/
    protected final HashMap<Long, TxNote> txNotes = new HashMap<>();

    /**
     * This method is used to assemble a {@link TxNote}'s list fetched from your exchange's account<br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @throws Exception when an operation fails
     **/
    public abstract Collection<TxNote> fetchTxNotesList() throws Exception;

    // TODO: 06/09/2022 TEST AFTER FIXED TRADERBOT AND USE CONSTANT IN DOCU STRING FOR SELL AND BUY

    /**
     * This method is used to create and reorganize a {@link TxNote}'s list.<br>
     * From a complete list this method merge all {@link TxNote} that has SELL as status and
     * when discover a {@link TxNote} that has BUY as status, with the same symbol, it will operate to merge
     * the sold {@link TxNote} with the main {@link TxNote} marked as BUY <br>
     * Any params required
     *
     * @implNote if sold quantity equals to {@link TxNote} marked as BUY will be deleted
     * from {@link #txNotes} meanwhile not {@link TxNote} marked as BUY will have different quantity and initial balance value
     **/
    protected void mergeTxNotesList() {
        for (TxNote soldTx : txNotes.values()) {
            // TODO: 06/09/2022 USE LIBRARY CONSTANTS
            long sellDateTimestamp = soldTx.getSellDateTimestamp();
            if (sellDateTimestamp != 0 && soldTx.getBuyDateTimestamp() == sellDateTimestamp) {
                for (TxNote boughtTx : txNotes.values()) {
                    // TODO: 06/09/2022 USE LIBRARY CONSTANTS
                    String boughtSymbol = boughtTx.getSymbol();
                    if (boughtTx.getStatus().equals("BUY") && boughtSymbol.equals(soldTx.getSymbol())) {
                        System.out.println("gagaga");
                        boolean replaceSoldTx = true;
                        long boughtTimestamp = boughtTx.getBuyDateTimestamp();
                        double soldInitialBalance = soldTx.getInitialBalance();
                        double soldQuantity = soldTx.getQuantity();
                        double boughtQuantity = boughtTx.getQuantity();
                        if (soldQuantity < boughtQuantity) {
                            System.out.println("gagaga");
                            System.out.println(txNotes.get(boughtTimestamp));
                            txNotes.replace(boughtTimestamp, new TxNote(boughtSymbol,
                                    "BUY", // TODO: 06/09/2022 USE LIBRARY CONSTANTS
                                    boughtTimestamp,
                                    boughtTx.getInitialBalance() - soldInitialBalance,
                                    boughtQuantity - soldQuantity,
                                    2 // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD
                            ));
                            System.out.println(txNotes.get(boughtTimestamp));
                        } else if (soldQuantity == boughtQuantity)
                            txNotes.replace(boughtTimestamp, null);
                        else
                            replaceSoldTx = false;
                        if (replaceSoldTx) {
                            txNotes.replace(sellDateTimestamp, new TxNote(boughtSymbol,
                                    "SELL", // TODO: 06/09/2022 USE LIBRARY CONSTANTS
                                    boughtTimestamp,
                                    soldInitialBalance,
                                    soldQuantity,
                                    2, // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD
                                    soldTx.getSellPrice(),
                                    sellDateTimestamp
                            ));
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is used to get error of any requests<br>
     * Any params required
     **/
    public abstract String getErrorMessage();

    /**
     * Method to print error response<br>
     * Any params required
     **/
    public abstract void printErrorMessage();

    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL 

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    public TxNote fetchTxNote(long checkDate) {
        return txNotes.get(checkDate);
    }

    // TODO: 06/09/2022 CHECK WORKFLOW
    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL 

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    public TxNote fetchTxNote(String checkDate) {
        return txNotes.get(getDateTimestamp(checkDate));
    }

    // TODO: 06/09/2022 CHECK WORKFLOW
    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    public TxNote fetchTxNote(Date checkDate) {
        return fetchTxNote(checkDate.getTime());
    }

    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL 

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    public TxNote fetchTxNoteSold(long checkDate) {
        for (TxNote txNote : txNotes.values())
            // TODO: 06/09/2022 IMPLEMENT LIBRARY CONSTANT
            if (txNote.getStatus().equals("SELL") && txNote.getSellDateTimestamp() == checkDate)
                return txNote;
        return null;
    }

    // TODO: 06/09/2022 CHECK WORKFLOW
    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL 

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    public TxNote fetchTxNoteSold(String checkDate) {
        for (TxNote txNote : txNotes.values())
            // TODO: 06/09/2022 IMPLEMENT LIBRARY CONSTANT
            if (txNote.getStatus().equals("SELL") && txNote.getSellDate().equals(checkDate))
                return txNote;
        return null;
    }

    // TODO: 06/09/2022 INSERT RIGHT CONSTANT FOR BUY AND SELL

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    public TxNote fetchTxNoteSold(Date checkDate) {
        return fetchTxNoteSold(checkDate.getTime());
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to delete from {@link #txNotes} list
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with checkDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    public boolean deleteTxNote(long checkDate) {
        boolean delete = txNotes.remove(checkDate) != null;
        if (delete)
            txNotesDeleted.add(checkDate);
        return delete;
    }

    // TODO: 06/09/2022 CHECK WORKFLOW

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to delete from {@link #txNotes} list as {@link String}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with checkDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    public boolean deleteTxNote(String checkDate) {
        return deleteTxNote(getDateTimestamp(checkDate));
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to delete from {@link #txNotes} list as {@link Date}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with checkDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    public boolean deleteTxNote(Date checkDate) {
        return deleteTxNote(checkDate.getTime());
    }

}
