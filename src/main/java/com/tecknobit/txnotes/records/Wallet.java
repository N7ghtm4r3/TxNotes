package com.tecknobit.txnotes.records;

import com.tecknobit.traderbot.Routines.Interfaces.RecordDetails;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.tecknobit.apimanager.Tools.Trading.CryptocurrencyTool.getCryptocurrencyName;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.textualizeAssetPercent;
import static com.tecknobit.traderbot.Records.Portfolio.Cryptocurrency.*;
import static com.tecknobit.traderbot.Records.Portfolio.Token.QUANTITY_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.TRANSACTION_KEY;
import static com.tecknobit.traderbot.Records.Portfolio.Transaction.getDateTimestamp;
import static com.tecknobit.traderbot.Routines.Android.ServerRequest.BALANCE_KEY;
import static com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines.BUY;
import static com.tecknobit.txnotes.records.TxNote.getDetailsColoured;
import static java.lang.System.out;

/**
 * The {@code Wallet} class defines characteristics of a crypto wallet.<br>
 * This object is useful to fetch and format details about a crypto wallet.
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 **/

public class Wallet implements TxNote.TxNotesListManager, RecordDetails {

    /**
     * {@code WALLET_KEY} is instance that memorizes activation wallet key
     **/
    public static final String WALLET_KEY = "wallet";

    /**
     * {@code index} is instance that memorizes index value
     **/
    private final String index;

    /**
     * {@code name} is instance that memorizes name of the asset of this wallet es. Bitcoin
     **/
    private final String name;

    /**
     * {@code lastPrice} is instance that memorizes last price value
     **/
    private double lastPrice;

    /**
     * {@code trend} is instance that memorizes trend value
     **/
    private double trend;

    /**
     * {@code txNotes} is instance that memorizes list of {@link TxNote}
     **/
    private ArrayList<TxNote> txNotes;

    /**
     * Constructor to init {@link Wallet}
     *
     * @param index:     index value es. BTC
     * @param name:      name of the asset of this wallet es. Bitcoin
     * @param lastPrice: last price value
     * @param trend:     trend value
     * @param txNotes:   {@link #txNotes}
     **/
    public Wallet(String index, String name, double lastPrice, double trend, ArrayList<TxNote> txNotes) {
        this.index = index;
        this.name = name;
        this.lastPrice = lastPrice;
        this.trend = trend;
        this.txNotes = txNotes;
    }

    /**
     * Constructor to init {@link Wallet}
     *
     * @param index:     index value es. BTC
     * @param name:      name of the asset of this wallet es. Bitcoin
     * @param lastPrice: last price value
     * @param trend:     trend value
     **/
    public Wallet(String index, String name, double lastPrice, double trend) {
        this.index = index;
        this.name = name;
        this.lastPrice = lastPrice;
        this.trend = trend;
        txNotes = new ArrayList<>();
    }

    /**
     * Constructor to init {@link Wallet}
     *
     * @param index:     index value es. BTC
     * @param lastPrice: last price value
     * @param trend:     trend value
     * @param txNotes:   {@link #txNotes}
     * @implSpec this constructor inserts automatically the name of the asset of this wallet es. Bitcoin
     **/
    public Wallet(String index, double lastPrice, double trend, ArrayList<TxNote> txNotes) {
        String assetName;
        this.index = index;
        assetName = getCryptocurrencyName(index);
        this.name = assetName;
        this.lastPrice = lastPrice;
        this.trend = trend;
        this.txNotes = txNotes;
    }

    /**
     * Constructor to init {@link Wallet}
     *
     * @param index:     index value es. BTC
     * @param lastPrice: last price value
     * @param trend:     trend value
     * @implSpec this constructor inserts automatically the name of the asset of this wallet es. Bitcoin
     **/
    public Wallet(String index, double lastPrice, double trend) {
        String assetName;
        this.index = index;
        assetName = getCryptocurrencyName(index);
        this.name = assetName;
        this.lastPrice = lastPrice;
        this.trend = trend;
        txNotes = new ArrayList<>();
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    /**
     * This method is used get {@link #lastPrice} instance
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link #lastPrice} formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    public double getTrend() {
        return trend;
    }

    public void setTrend(double trend) {
        this.trend = trend;
    }

    /**
     * This method is used get {@link #trend} instance
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link #trend} formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTrend(int decimals) {
        return roundValue(trend, decimals);
    }

    /**
     * This method is used get {@link #trend} instance<br>
     * Any params required
     *
     * @return {@link #trend} formatted as +1.262% as {@link String}
     **/
    public String getTrendText() {
        return textualizeAssetPercent(trend);
    }

    /**
     * This method is used get {@link #trend} instance
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link #trend} formatted as +1.26% as {@link String}
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public String getTrendText(int decimals) {
        return textualizeAssetPercent(trend, decimals);
    }

    /**
     * This method is used get balance of the wallet <br>
     * Any params required
     *
     * @return balance of the wallet as double
     * @implNote balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getBalance() {
        double balance = 0;
        for (TxNote txNote : txNotes)
            if (txNote.getStatus().equals(BUY))
                balance += txNote.getValue();
        return balance;
    }

    /**
     * This method is used get balance of the wallet
     *
     * @param decimals: number of decimal digits es. 2
     * @return balance of the wallet as double
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implNote balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getBalance(int decimals) {
        return roundValue(getBalance(), decimals);
    }

    /**
     * This method is used get initial balance of the wallet <br>
     * Any params required
     *
     * @return initial balance of the wallet as double
     * @implNote initial balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getInitialBalance() {
        double initialBalance = 0;
        for (TxNote txNote : txNotes)
            if (txNote.getStatus().equals(BUY))
                initialBalance += txNote.getInitialBalance();
        return initialBalance;
    }

    /**
     * This method is used get initial balance of the wallet
     *
     * @param decimals: number of decimal digits es. 2
     * @return initial balance of the wallet as double
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implNote balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getInitialBalance(int decimals) {
        return roundValue(getInitialBalance(), decimals);
    }

    /**
     * This method is used get total quantity in the wallet <br>
     * Any params required
     *
     * @return quantity in the wallet as double
     * @implNote quantity is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getTotalQuantity() {
        double totalQuantity = 0;
        for (TxNote txNote : txNotes)
            if (txNote.getStatus().equals(BUY))
                totalQuantity += txNote.getQuantity();
        return roundValue(totalQuantity, 8);
    }

    /**
     * This method is used get total quantity in the wallet
     *
     * @param decimals: number of decimal digits es. 2
     * @return total quantity in the wallet as double
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implNote balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getTotalQuantity(int decimals) {
        return roundValue(getTotalQuantity(), decimals);
    }

    /**
     * This method is used get total income of the wallet <br>
     * Any params required
     *
     * @return total income of the wallet as double
     **/
    public double getTotalIncomePercent() {
        double totalIncomePercent = 0;
        int notesSize = txNotes.size();
        if (notesSize == 0)
            return 0;
        for (TxNote txNote : txNotes)
            totalIncomePercent += txNote.getIncomePercent();
        return totalIncomePercent;
    }

    /**
     * This method is used get total income of the wallet
     *
     * @param decimals: number of decimal digits es. 2
     * @return total income of the wallet as double
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalIncomePercent(int decimals) {
        return roundValue(getTotalIncomePercent(), decimals);
    }

    /**
     * This method is used get total income of the wallet <br>
     * Any params required
     *
     * @return total income of the wallet as {@link String} es. +3.21%
     **/
    public String getTotalIncomePercentText() {
        return textualizeAssetPercent(getTotalIncomePercent());
    }

    /**
     * This method is used get total income of the wallet
     *
     * @param decimals: number of decimal digits es. 2
     * @return total income of the wallet as {@link String} es. +3.21%
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public String getTotalIncomePercentText(int decimals) {
        return textualizeAssetPercent(getTotalIncomePercent(), decimals);
    }

    public ArrayList<TxNote> getTxNotes() {
        return txNotes;
    }

    public void setTxNotes(ArrayList<TxNote> txNotes) {
        this.txNotes = txNotes;
    }

    public void addTxNote(TxNote txNote) {
        txNotes.add(txNote);
    }

    /**
     * This method is used get number of transactions notes in this wallet <br>
     * Any params required
     *
     * @return number of transactions notes as int
     **/
    public int txNotesNumber() {
        return txNotes.size();
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(long checkDate) {
        for (TxNote txNote : txNotes)
            if (txNote.getBuyDateTimestamp() == checkDate)
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search for both status of a transactions, so BUY and SELL
     **/
    @Override
    public TxNote fetchTxNote(String checkDate) {
        return fetchTxNote(getDateTimestamp(checkDate));
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} as {@link Date}
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
     * @param checkDate: timestamp of the date to fetch from {@link #txNotes}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(long checkDate) {
        for (TxNote txNote : txNotes)
            if (txNote.getSellDateTimestamp() == checkDate)
                return txNote;
        return null;
    }

    /**
     * This method is used to fetch a transaction note from {@link #txNotes}
     *
     * @param checkDate: check date to fetch from {@link #txNotes} as {@link String}
     * @return transaction note as {@link TxNote} custom object
     * @implNote this method search only for SELL status of a transactions
     **/
    @Override
    public TxNote fetchTxNoteSold(String checkDate) {
        return fetchTxNoteSold(getDateTimestamp(checkDate));
    }

    /**
     * This method is used to fetch a transaction note from a {@link #txNotes}
     *
     * @param checkDate: check date to fetch from list as {@link Date}
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
     * @param removeDate: timestamp of the date to delete from {@link #txNotes}
     * @return result of deletion as boolean
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(long removeDate) {
        for (TxNote txNote : txNotes)
            if (txNote.getBuyDateTimestamp() == removeDate)
                return true;
        return false;
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param removeDate: check date to delete from {@link #txNotes} as {@link String}
     * @return result of deletion as boolean
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(String removeDate) {
        return deleteTxNote(getDateTimestamp(removeDate));
    }

    /**
     * This method is used to delete a transaction note from {@link #txNotes}
     *
     * @param removeDate: check date to delete from {@link #txNotes} as {@link Date}
     * @return result of deletion as boolean
     * @apiNote this method search only for the buy date of a transaction
     **/
    @Override
    public boolean deleteTxNote(Date removeDate) {
        return deleteTxNote(removeDate.getTime());
    }

    /**
     * This method is used to print details of {@link TxNote} object <br>
     * Any params required
     **/
    @Override
    public void printDetails() {
        out.println(this);
    }

    /**
     * This method is used to get {@link Wallet} details <br>
     * Any params required
     *
     * @return {@link Wallet} details as {@link HashMap} of {@link Object}
     **/
    public HashMap<String, Object> getWallet() {
        HashMap<String, Object> wallet = new HashMap<>();
        wallet.put(SYMBOL_KEY, index);
        wallet.put(ASSET_NAME_KEY, name);
        wallet.put(LAST_PRICE_KEY, lastPrice);
        wallet.put(BALANCE_KEY, getBalance());
        wallet.put(PRICE_CHANGE_PERCENT_KEY, trend);
        wallet.put(INCOME_PERCENT_KEY, getTotalIncomePercentText());
        wallet.put(QUANTITY_KEY, getTotalQuantity());
        JSONArray notes = new JSONArray();
        for (TxNote txNote : txNotes)
            notes.put(txNote.getTxNote());
        wallet.put(TRANSACTION_KEY, notes);
        return wallet;
    }

    @Override
    public String toString() {
        return "## [" + index + "]\n" +
                "## Name: " + name + "\n" +
                "## Last price: " + lastPrice + "\n" +
                getDetailsColoured("Trend", getTrendText()) +
                "## Balance: " + getBalance() + "\n" +
                "## Total quantity: " + getTotalQuantity() + "\n" +
                getDetailsColoured("Total income", getTotalIncomePercentText()) +
                "## Transaction notes size: " + txNotes.size() + "\n" +
                "######################";
    }

}
