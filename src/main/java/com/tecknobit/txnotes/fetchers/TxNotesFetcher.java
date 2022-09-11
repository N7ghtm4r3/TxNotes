package com.tecknobit.txnotes.fetchers;

import com.tecknobit.traderbot.Records.Portfolio.Transaction;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.autonomous.TxNotesAutoFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.BinanceFetcher;
import com.tecknobit.txnotes.fetchers.interfaces.CoinbaseFetcher;
import com.tecknobit.txnotes.records.TxNote;
import com.tecknobit.txnotes.records.Wallet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.tecknobit.apimanager.Tools.Trading.CryptocurrencyTool.getCryptocurrencySymbol;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.getDateTimestamp;

/**
 * The {@code TxNotesFetcher} class is useful to fetch all transactions from exchange's account <br>
 * This class give basic methods for a fetcher's workflow
 *
 * @author Tecknobit N7ghtm4r3
 * @see BinanceFetcher
 * @see CoinbaseFetcher
 **/

public abstract class TxNotesFetcher implements TxNote.TxNotesListManager {

    /**
     * {@code BUY} is constant for buy side
     **/
    public static final String BUY = TraderCoreRoutines.BUY;

    /**
     * {@code SELL} is constant for sell side
     **/
    public static final String SELL = TraderCoreRoutines.SELL;

    /**
     * {@code fetcherPlatform} is instance of {@link TraderCoreRoutines} to use to fetch transactions
     **/
    protected final TraderCoreRoutines fetcherPlatform;

    /**
     * {@code txNotes} is instance of list with {@link TxNote} as value and {@link Long} representing the buy date as key
     **/
    protected final ConcurrentHashMap<Long, TxNote> txNotes = new ConcurrentHashMap<>();

    /**
     * {@code txNotesDeleted} is instance of {@link Long} helpful to indicate which {@link TxNote} must not available to be reinserted
     *
     * @implNote when you delete a {@link TxNote} that will be deleted only in {@code TxNotes} infrastructure not on
     * exchange's account
     **/
    protected final ArrayList<Long> txNotesDeleted = new ArrayList<>();

    /**
     * {@code txNotes} is instance of list with {@link Wallet} as value and {@link String} representing index of wallet
     **/
    protected final ConcurrentHashMap<String, Wallet> wallets = new ConcurrentHashMap<>();

    /**
     * {@code txNotesDeleted} is instance of {@link String} helpful to indicate which {@link Wallet} must not available to be reinserted
     *
     * @implNote when you delete a {@link Wallet} that will be deleted only in {@code TxNotes} infrastructure not on
     * exchange's account
     **/
    protected final ArrayList<String> walletsDeleted = new ArrayList<>();

    /**
     * Constructor to init {@link TxNotesAutoFetcher}
     *
     * @param fetcherPlatform: fetcher platform to fetch transactions
     * @implNote these keys will NOT store by library anywhere.
     **/
    public TxNotesFetcher(TraderCoreRoutines fetcherPlatform) {
        this.fetcherPlatform = fetcherPlatform;
    }

    /**
     * This method is used to load all data that {@link TxNotesFetcher} has processed. <br>
     * Methods that have been called are {@link #fetchTxNotesList()} and {@link #loadWalletList()} <br>
     * Any params required
     **/
    public void loadAllData() throws Exception {
        fetchTxNotesList();
        loadWalletList();
    }

    /**
     * This method is used to assemble a {@link TxNote}'s list fetched from your exchange's account<br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @throws Exception when an operation fails
     **/
    public Collection<TxNote> fetchTxNotesList() throws Exception {
        for (Transaction transaction : fetcherPlatform.getAllTransactions(false)) {
            String status = transaction.getSide();
            long timestamp = transaction.getTransactionTimestamp();
            double lastPrice = 2; // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD
            if (!txNotesDeleted.contains(timestamp) && txNotes.get(timestamp) == null) {
                double value = transaction.getValue();
                double quantity = transaction.getQuantity();
                TxNote txNote = new TxNote(transaction.getSymbol(), status, timestamp, value, quantity, lastPrice,
                        transaction.getBaseAsset(), transaction.getQuoteAsset());
                if (status.equals(SELL)) {
                    txNote.setSellDate(timestamp);
                    txNote.setSellPrice(value / quantity);
                }
                txNotes.put(timestamp, txNote);
            }
        }
        // TODO: 11/09/2022 REMOVE
        long time = 1662455785000L;
        long[] times = new long[]{time, time + 1000, time + 2000, time + 3000, time + 4000};
        if (!txNotesDeleted.contains(time)) {
            txNotes.put(times[0], new TxNote("BTCBUSD",
                    BUY,
                    times[0],
                    23,
                    100,
                    0.23,
                    "BTC",
                    "BUSD"
            ));
            txNotes.put(times[1], new TxNote("BTCBUSD",
                    SELL,
                    times[1],
                    23,
                    100,
                    11,
                    22,
                    times[1],
                    "BTC",
                    "BUSD"
            ));
            txNotes.put(times[2], new TxNote("BTCBUSD",
                    BUY,
                    times[2],
                    23,
                    100,
                    0.23,
                    "BTC",
                    "BUSD"
            ));
            txNotes.put(times[3], new TxNote("BTCBUSD",
                    SELL,
                    times[3],
                    23,
                    100,
                    11,
                    22,
                    times[3],
                    "BTC",
                    "BUSD"
            ));
            txNotes.put(times[4], new TxNote("ETHBUSD",
                    SELL,
                    times[4],
                    23,
                    100,
                    11,
                    22,
                    times[4],
                    "ETH",
                    "BUSD"
            ));
        }
        mergeTxNotesList();
        return txNotes.values();
    }

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
            long sellDateTimestamp = soldTx.getSellDateTimestamp();
            if (sellDateTimestamp != 0 && soldTx.getBuyDateTimestamp() == sellDateTimestamp) {
                for (TxNote boughtTx : txNotes.values()) {
                    String boughtSymbol = boughtTx.getSymbol();
                    if (boughtTx.getStatus().equals(BUY) && boughtSymbol.equals(soldTx.getSymbol())) {
                        String baseAsset = boughtTx.getBaseAsset();
                        String quoteAsset = boughtTx.getQuoteAsset();
                        boolean replaceSoldTx = true;
                        long boughtTimestamp = boughtTx.getBuyDateTimestamp();
                        double soldInitialBalance = soldTx.getInitialBalance();
                        double soldQuantity = soldTx.getQuantity();
                        double boughtQuantity = boughtTx.getQuantity();
                        if (soldQuantity < boughtQuantity) {
                            txNotes.replace(boughtTimestamp, new TxNote(boughtSymbol,
                                    BUY,
                                    boughtTimestamp,
                                    boughtTx.getInitialBalance() - soldInitialBalance,
                                    boughtQuantity - soldQuantity,
                                    2, // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD,
                                    baseAsset,
                                    quoteAsset
                            ));
                        } else if (soldQuantity == boughtQuantity)
                            txNotes.remove(boughtTimestamp);
                        else
                            replaceSoldTx = false;
                        if (replaceSoldTx) {
                            txNotes.replace(sellDateTimestamp, new TxNote(boughtSymbol,
                                    SELL,
                                    boughtTimestamp,
                                    soldInitialBalance,
                                    soldQuantity,
                                    2, // TODO: 06/09/2022 FETCH FROM LIBRARY METHOD
                                    soldTx.getSellPrice(),
                                    sellDateTimestamp,
                                    baseAsset,
                                    quoteAsset
                            ));
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method is used to get list of {@link TxNote} already fetched
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @implNote this method avoid the loading of the list if you need to use list already fetched but with past loading
     * cycle values
     **/
    public Collection<TxNote> getTxNotesFetched() {
        return txNotes.values();
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(long checkDate) {
        return txNotes.get(checkDate);
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(String checkDate) {
        return txNotes.get(getDateTimestamp(checkDate));
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(Date checkDate) {
        return fetchTxNote(checkDate.getTime());
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(long checkDate) {
        for (TxNote txNote : txNotes.values())
            if (txNote.getStatus().equals(SELL) && txNote.getSellDateTimestamp() == checkDate)
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(String checkDate) {
        for (TxNote txNote : txNotes.values())
            if (txNote.getStatus().equals(SELL) && txNote.getSellDate().equals(checkDate))
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(Date checkDate) {
        return fetchTxNoteSold(checkDate.getTime());
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param removeDate: timestamp of the date to delete from {@link #txNotes} list
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(long removeDate) {
        boolean delete = txNotes.remove(removeDate) != null;
        if (delete)
            txNotesDeleted.add(removeDate);
        return delete;
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param removeDate: check date to delete from {@link #txNotes} list as {@link String}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(String removeDate) {
        return deleteTxNote(getDateTimestamp(removeDate));
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param removeDate: check date to delete from {@link #txNotes} list as {@link Date}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(Date removeDate) {
        return deleteTxNote(removeDate.getTime());
    }

    /**
     * This method is used to clear {@link #txNotesDeleted} list to readmit all {@link TxNote} available<br>
     * Any params required
     **/
    public void reAllowsAllTxNotes() {
        txNotesDeleted.clear();
    }

    /**
     * This method is used to load list of your account wallets by current {@link TxNote} details <br>
     * Any params required
     **/
    public void loadWalletList() {
        HashMap<String, ArrayList<TxNote>> notes = new HashMap<>();
        for (TxNote txNote : txNotes.values()) {
            String index = txNote.getBaseAsset();
            if (!walletsDeleted.contains(index)) {
                ArrayList<TxNote> walletNotes = notes.get(index);
                if (walletNotes == null)
                    notes.put(index, new ArrayList<>(Arrays.asList(txNote)));
                else
                    walletNotes.add(txNote);
            }
        }
        for (String index : notes.keySet()) {
            wallets.put(index, new Wallet(index,
                    1, // TODO: 11/09/2022 FETCH VALUE
                    1, // TODO: 11/09/2022 FETCH VALUE
                    notes.get(index)
            ));
        }
    }

    /**
     * This method is used to get list of your account wallets by current {@link TxNote} details <br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link Wallet} custom object
     **/
    public Collection<Wallet> getWallets() {
        return wallets.values();
    }

    /**
     * This method is used to get a single wallet from {@link #wallets} list
     *
     * @param index: index of the wallet to fetch es. BTC
     * @return wallet as {@link Wallet} custom object
     **/
    public Wallet getWalletByIndex(String index) {
        return wallets.get(index);
    }

    /**
     * This method is used to get a single wallet from {@link #wallets} list
     *
     * @param name: name of the wallet to fetch es. Bitcoin
     * @return wallet as {@link Wallet} custom object
     **/
    public Wallet getWalletByName(String name) {
        String index = getCryptocurrencySymbol(name);
        if (index != null)
            return wallets.get(index);
        return null;
    }

    /**
     * This method is used to remove a single wallet from {@link #wallets} list
     *
     * @param index: index of the wallet to remove es. BTC
     * @return wallet as {@link Wallet} custom object
     **/
    public boolean removeWalletByIndex(String index) {
        if (index != null) {
            index = index.toUpperCase();
            boolean removed = wallets.remove(index) != null;
            if (removed)
                walletsDeleted.add(index);
            return removed;
        }
        return false;
    }

    /**
     * This method is used to remove a single wallet from {@link #wallets} list
     *
     * @param name: name of the wallet to remove es. Bitcoin
     * @return wallet as {@link Wallet} custom object
     **/
    public boolean removeWalletByName(String name) {
        return removeWalletByIndex(getCryptocurrencySymbol(name));
    }

    /**
     * This method is used to clear {@link #walletsDeleted} list to readmit all {@link Wallet} available<br>
     * Any params required
     **/
    public void reAllowsAllWallets() {
        walletsDeleted.clear();
    }

    /**
     * This method is used to get error of any requests<br>
     * Any params required
     **/
    public String getErrorMessage() {
        return fetcherPlatform.getErrorResponse();
    }

    /**
     * Method to print error response <br>
     * Any params required
     **/
    public void printErrorMessage() {
        fetcherPlatform.printErrorMessage();
    }

}
