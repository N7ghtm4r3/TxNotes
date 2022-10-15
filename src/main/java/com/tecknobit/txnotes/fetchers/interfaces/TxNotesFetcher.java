package com.tecknobit.txnotes.fetchers.interfaces;

import com.tecknobit.traderbot.records.portfolio.MarketCoin;
import com.tecknobit.traderbot.records.portfolio.Transaction;
import com.tecknobit.traderbot.routines.interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.autonomous.TxNotesAutoFetcher;
import com.tecknobit.txnotes.records.TxNote;
import com.tecknobit.txnotes.records.Wallet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.tecknobit.apimanager.Tools.Trading.CryptocurrencyTool.getCryptocurrencySymbol;
import static com.tecknobit.traderbot.records.portfolio.Transaction.getDateTimestamp;
import static com.tecknobit.traderbot.routines.interfaces.TraderBotConstants.BUY;
import static com.tecknobit.traderbot.routines.interfaces.TraderBotConstants.SELL;

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
     * {@code fetcherPlatform} is instance of {@link TraderCoreRoutines} to use to fetch transactions
     **/
    protected final TraderCoreRoutines fetcherPlatform;

    /**
     * {@code txNotes} is instance of list with {@link TxNote} as value and {@link Long} representing the buy date as key
     **/
    protected final ConcurrentHashMap<String, TxNote> txNotes = new ConcurrentHashMap<>();

    /**
     * {@code txNotesDeleted} is instance of {@link Long} helpful to indicate which {@link TxNote} must not available to be reinserted
     *
     * @implNote when you delete a {@link TxNote} that will be deleted only in {@code TxNotes} infrastructure not on
     * exchange's account
     **/
    protected final ArrayList<String> txNotesDeleted = new ArrayList<>();

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
     * {@code baseCurrency} is instance that memorizes base currency to get all amount value of traders routine es. EUR
     **/
    protected String baseCurrency;

    /**
     * Constructor to init {@link TxNotesAutoFetcher}
     *
     * @param fetcherPlatform: fetcher platform to fetch transactions
     * @implNote these keys will NOT store by library anywhere.
     **/
    public TxNotesFetcher(TraderCoreRoutines fetcherPlatform, String baseCurrency) {
        this.fetcherPlatform = fetcherPlatform;
        this.baseCurrency = baseCurrency;
    }

    /**
     * This method is used to load all data that {@link TxNotesFetcher} has processed. <br>
     * Methods that have been called are {@link #fetchTxNotesList()} and {@link #loadWalletList()} <br>
     * Any params required
     **/
    public void loadAllData() throws Exception {
        fetcherPlatform.refreshLatestPrice();
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
        for (Transaction transaction : fetcherPlatform.getTransactionsList(baseCurrency,
                "dd/MM/yyyy HH:mm:ss", true)) {
            String symbol = transaction.getSymbol();
            String status = transaction.getSide();
            long timestamp = transaction.getTransactionTimestamp();
            String txKey = transaction.getBaseAsset() + timestamp;
            double lastPrice = fetcherPlatform.getLastPrice(symbol).getLastPrice();
            if (!txNotesDeleted.contains(txKey) && txNotes.get(txKey) == null) {
                double value = transaction.getValue();
                double quantity = transaction.getQuantity();
                TxNote txNote = new TxNote(symbol, status, timestamp, value, quantity, lastPrice,
                        transaction.getBaseAsset(), transaction.getQuoteAsset());
                if (status.equals(SELL)) {
                    txNote.setSellDate(timestamp);
                    txNote.setSellPrice(value / quantity);
                }
                txNotes.put(txKey, txNote);
            }
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
                ArrayList<String> keys = new ArrayList<>(txNotes.keySet());
                boolean continueLoop = true;
                for (int j = 0; j < txNotes.size() && continueLoop; j++) {
                    TxNote boughtTx = txNotes.get(keys.get(j));
                    String boughtSymbol = boughtTx.getSymbol();
                    if (boughtTx.getStatus().equals(BUY) && boughtSymbol.equals(soldTx.getSymbol())) {
                        String baseAsset = boughtTx.getBaseAsset();
                        String quoteAsset = boughtTx.getQuoteAsset();
                        long boughtTimestamp = boughtTx.getBuyDateTimestamp();
                        String txNoteKey = baseAsset + boughtTimestamp;
                        double soldInitialBalance = soldTx.getInitialBalance();
                        double soldQuantity = soldTx.getQuantity();
                        double boughtQuantity = boughtTx.getQuantity();
                        double lastPrice = fetcherPlatform.getLastPrice(boughtSymbol).getLastPrice();
                        boolean replaceSoldTx = true;
                        if (soldQuantity < boughtQuantity) {
                            txNotes.replace(txNoteKey, new TxNote(boughtSymbol,
                                    BUY,
                                    boughtTimestamp,
                                    boughtTx.getInitialBalance() - soldInitialBalance,
                                    boughtQuantity - soldQuantity,
                                    lastPrice,
                                    baseAsset,
                                    quoteAsset
                            ));
                        } else if (soldQuantity == boughtQuantity)
                            txNotes.remove(txNoteKey);
                        else
                            replaceSoldTx = false;
                        if (replaceSoldTx) {
                            txNotes.replace(txNoteKey, new TxNote(boughtSymbol,
                                    SELL,
                                    boughtTimestamp,
                                    soldInitialBalance,
                                    soldQuantity,
                                    lastPrice,
                                    soldTx.getSellPrice(),
                                    sellDateTimestamp,
                                    baseAsset,
                                    quoteAsset
                            ));
                            continueLoop = false;
                        }
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
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(String asset, long checkDate) {
        return txNotes.get(asset + checkDate);
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(String asset, String checkDate) {
        return txNotes.get(asset + getDateTimestamp(checkDate));
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(String asset, Date checkDate) {
        return fetchTxNote(asset, checkDate.getTime());
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes} list
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(String asset, long checkDate) {
        for (TxNote txNote : txNotes.values())
            if (txNote.getStatus().equals(SELL) && txNote.getBaseAsset().equals(asset)
                    && txNote.getSellDateTimestamp() == checkDate)
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(String asset, String checkDate) {
        for (TxNote txNote : txNotes.values())
            if (txNote.getStatus().equals(SELL) && txNote.getBaseAsset().equals(asset)
                    && txNote.getSellDate().equals(checkDate))
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param asset:     identifier of the asset with transaction has been made
     * @param checkDate: check date to fetch from {@link #txNotes} list as {@link Date}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(String asset, Date checkDate) {
        return fetchTxNoteSold(asset, checkDate.getTime());
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param asset:      identifier of the asset with transaction has been made
     * @param removeDate: timestamp of the date to delete from {@link #txNotes} list
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(String asset, long removeDate) {
        if (!txNotesDeleted.contains(asset + removeDate)) {
            boolean delete = txNotes.remove(asset + removeDate) != null;
            if (delete)
                txNotesDeleted.add(asset + removeDate);
            return delete;
        }
        return false;
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param asset:      identifier of the asset with transaction has been made
     * @param removeDate: check date to delete from {@link #txNotes} list as {@link String}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(String asset, String removeDate) {
        return deleteTxNote(asset, getDateTimestamp(removeDate));
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param asset:      identifier of the asset with transaction has been made
     * @param removeDate: check date to delete from {@link #txNotes} list as {@link Date}
     * @return result of deletion as boolean
     * @implNote if a {@link TxNote} corresponds with removeDate param its timestamp will be inserted
     * in {@link #txNotesDeleted} list so to not be reinserted anymore in {@link #txNotes} main list
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(String asset, Date removeDate) {
        return deleteTxNote(asset, removeDate.getTime());
    }

    /**
     * This method is used to clear {@link #txNotesDeleted} list to readmit all {@link TxNote} available<br>
     * Any params required
     *
     * @apiNote this means that if these transactions are still available on the platform they will be re-entered, but if they are not
     * more stored on the exchange platform will no longer be recoverable
     **/
    public void allowsAllTxNotes() {
        if (!txNotesDeleted.isEmpty())
            txNotesDeleted.clear();
    }

    /**
     * This method is used to get list of {@link TxNote} that have been deleted
     *
     * @return list of {@link TxNote} deleted
     **/
    public ArrayList<String> getTxNotesDeleted() {
        return txNotesDeleted;
    }

    /**
     * This method is used to load list of your account wallets by current {@link TxNote} details <br>
     * Any params required
     **/
    public void loadWalletList() {
        wallets.clear();
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
            MarketCoin market = fetcherPlatform.getLastPrice(index + baseCurrency);
            wallets.put(index, new Wallet(index,
                    market.getLastPrice(),
                    market.getPriceChangePercent(),
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
     * This method is used to set time to refresh data
     *
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     **/
    public void setRefreshTime(int refreshTime) {
        fetcherPlatform.setRefreshTime(refreshTime);
    }

    /**
     * This method is used to get time to refresh data
     * Any params required.
     *
     * @return time to refresh data as int
     **/
    public int getRefreshTime() {
        return fetcherPlatform.getRefreshTime();
    }

    /**
     * This method is used to get time to refresh data in seconds (s) format
     * Any params required.
     *
     * @return time to refresh data in seconds (s) format as int
     **/
    public int getRefreshTimeSeconds() {
        return fetcherPlatform.getRefreshTimeSeconds();
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

    /**
     * This method is used to get base currency for change amount value <br>
     * Any params required
     **/
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * This method is used to set base currency for change amount value
     *
     * @param baseCurrency: base currency to get all amount value of traders routine es. EUR
     **/
    public void setBaseCurrency(String baseCurrency) {
        if (baseCurrency == null || baseCurrency.isEmpty())
            throw new IllegalArgumentException("Currency cannot be null or empty, but for example EUR or USDT");
        this.baseCurrency = baseCurrency;
    }

}
