package com.tecknobit.txnotes.records;

import com.tecknobit.apimanager.Tools.Trading.CryptocurrencyTool;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.textualizeAssetPercent;

/**
 * The {@code Wallet} class defines characteristics of a crypto wallet.<br>
 * This object is useful to fetch and format details about a crypto wallet.
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 **/

public class Wallet {

    // TODO: 06/09/2022 IMPLEMENT CORRECTLY CRYPTOCURRENCYTOOL

    /**
     * {@code index} is instance that memorizes index value
     **/
    private final String index;

    /**
     * {@code assetName} is instance that memorizes asset name
     **/
    private final String assetName;

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
     * @param assetName: asset name
     * @param lastPrice: last price value
     * @param trend:     trend value
     * @param txNotes:   list of {@link TxNote}
     **/
    public Wallet(String index, String assetName, double lastPrice, double trend, ArrayList<TxNote> txNotes) {
        this.index = index;
        this.assetName = assetName;
        this.lastPrice = lastPrice;
        this.trend = trend;
        this.txNotes = txNotes;
    }

    /**
     * Constructor to init {@link Wallet}
     *
     * @param index:     index value es. BTC
     * @param assetName: asset name
     * @param lastPrice: last price value
     * @param trend:     trend value
     **/
    public Wallet(String index, String assetName, double lastPrice, double trend) {
        this.index = index;
        this.assetName = assetName;
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
     * @param txNotes:   list of {@link TxNote}
     * @implSpec this constructor inserts automatically the asset name
     **/
    public Wallet(String index, double lastPrice, double trend, ArrayList<TxNote> txNotes) {
        String assetName;
        this.index = index;
        try {
            assetName = new CryptocurrencyTool().getCryptocurrencyName(index);
        } catch (IOException e) {
            assetName = null;
        }
        this.assetName = assetName;
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
     * @implSpec this constructor inserts automatically the asset name
     **/
    public Wallet(String index, double lastPrice, double trend) {
        String assetName;
        this.index = index;
        try {
            assetName = new CryptocurrencyTool().getCryptocurrencyName(index);
        } catch (IOException e) {
            assetName = null;
        }
        this.assetName = assetName;
        this.lastPrice = lastPrice;
        this.trend = trend;
        txNotes = new ArrayList<>();
    }

    public String getIndex() {
        return index;
    }

    public String getAssetName() {
        return assetName;
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

    public ArrayList<TxNote> getTxNotes() {
        return txNotes;
    }

    public void setTxNotes(ArrayList<TxNote> txNotes) {
        this.txNotes = txNotes;
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
     * This method is used get balance of the wallet <br>
     * Any params required
     *
     * @return balance of the wallet as double
     * @implNote balance is calculated only when {@link TxNote} has {@code BUY} as status
     **/
    public double getBalance() {
        double balance = 0;
        for (TxNote txNote : txNotes)
            if (txNote.getStatus().equals("BUY"))
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
            if (txNote.getStatus().equals("BUY"))
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
            if (txNote.getStatus().equals("BUY"))
                totalQuantity += txNote.getQuantity();
        return totalQuantity;
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

    @Override
    public String toString() {
        return "Wallet{" +
                "index='" + index + '\'' +
                ", assetName='" + assetName + '\'' +
                ", lastPrice=" + lastPrice +
                ", trend=" + trend +
                ", txNotes=" + txNotes +
                '}';
    }

}
