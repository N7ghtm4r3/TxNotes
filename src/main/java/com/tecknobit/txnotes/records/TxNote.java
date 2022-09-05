package com.tecknobit.txnotes.records;

import com.tecknobit.traderbot.Records.Portfolio.Transaction;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.*;
import static java.lang.Math.toIntExact;
import static java.util.concurrent.TimeUnit.DAYS;

/**
 * The {@code TxNote} class defines characteristics of a transaction note. <br>
 * This object is useful to fetch and format transaction details.
 *
 * @author Tecknobit N7ghtm4r3
 * @implNote this class extends {@link Transaction} class of {@code TraderBot} library
 * @apiNote origin library at: <a href="https://github.com/N7ghtm4r3/TraderBot">https://github.com/N7ghtm4r3/TraderBot</a>
 * @see Transaction
 **/

public class TxNote extends Transaction {

    /**
     * {@code initialBalance} is instance that memorizes initial balance value
     **/
    private final double initialBalance;

    /**
     * {@code sellPrice} is instance that memorizes sell price value
     **/
    private double sellPrice;

    /**
     * {@code sellDateTimestamp} is instance that memorizes sell date timestamp value
     **/
    private long sellDateTimestamp;

    /**
     * {@code sellDate} is instance that memorizes sell date value
     **/
    private String sellDate;

    /**
     * {@code lastPrice} is instance that memorizes last price value
     **/
    private double lastPrice;

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in long format es. 1656623302000
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, long buyDate, double startPrice, double quantity, double lastPrice) {
        super(symbol, status, buyDate, startPrice, quantity);
        this.lastPrice = lastPrice;
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, String buyDate, double startPrice, double quantity, double lastPrice) {
        super(symbol, status, buyDate, startPrice, quantity);
        this.lastPrice = lastPrice;
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in long format es. 1656623302000
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param sellPrice:  sell price value
     * @param sellDate:   date when this trade has being sold in long format es. 1656624150000
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, long buyDate, double startPrice, double quantity, double lastPrice,
                  double sellPrice, long sellDate) {
        super(symbol, status, buyDate, startPrice, quantity);
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDateTimestamp);
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param sellPrice:  sell price value
     * @param sellDate:   date when this trade has being sold in {@link String} format es. 21:22:30 30/06/2022
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, String buyDate, double startPrice, double quantity, double lastPrice,
                  double sellPrice, String sellDate) {
        super(symbol, status, buyDate, startPrice, quantity);
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in long format es. 1656623302000
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param baseAsset:  base asset used in that transaction es. BTC
     * @param quoteAsset: quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, long buyDate, double startPrice, double quantity, double lastPrice,
                  String baseAsset, String quoteAsset) {
        super(symbol, status, buyDate, startPrice, quantity, baseAsset, quoteAsset);
        this.lastPrice = lastPrice;
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param baseAsset:  base asset used in that transaction es. BTC
     * @param quoteAsset: quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, String buyDate, double startPrice, double quantity, double lastPrice,
                  String baseAsset, String quoteAsset) {
        super(symbol, status, buyDate, startPrice, quantity, baseAsset, quoteAsset);
        this.lastPrice = lastPrice;
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in long format es. 1656623302000
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param sellPrice:  sell price value
     * @param sellDate:   date when this trade has being sold in long format es. 1656624150000
     * @param baseAsset:  base asset used in that transaction es. BTC
     * @param quoteAsset: quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, long buyDate, double startPrice, double quantity, double lastPrice,
                  double sellPrice, long sellDate, String baseAsset, String quoteAsset) {
        super(symbol, status, buyDate, startPrice, quantity);
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDateTimestamp);
        initialBalance = startPrice * quantity;
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:     symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:     status of transaction BUY or SELL
     * @param buyDate:    date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param startPrice: price when this transaction has being done
     * @param quantity:   quantity transfered in transaction
     * @param lastPrice:  last price value
     * @param sellPrice:  sell price value
     * @param sellDate:   date when this trade has being sold in {@link String} format es. 21:22:30 30/06/2022
     * @param baseAsset:  base asset used in that transaction es. BTC
     * @param quoteAsset: quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, String status, String buyDate, double startPrice, double quantity, double lastPrice,
                  double sellPrice, String sellDate, String baseAsset, String quoteAsset) {
        super(symbol, status, buyDate, startPrice, quantity, baseAsset, quoteAsset);
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
        initialBalance = startPrice * quantity;
    }

    public String getStatus() {
        return side;
    }

    public String getBuyDate() {
        return transactionDate;
    }

    /**
     * This method is used get buy date formatted as long<br>
     * Any params required
     *
     * @return buy date timestamp as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public long getBuyDateTimestamp() {
        return getTransactionTimestamp();
    }

    public double getStartPrice() {
        return value;
    }

    /**
     * This method is used get start price value
     *
     * @param decimals: number of decimal digits es. 2
     * @return start price value formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getStartPrice(int decimals) {
        return roundValue(value, decimals);
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    /**
     * This method is used get {@link #initialBalance} instance
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link #initialBalance} formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInitialBalance(int decimals) {
        return roundValue(initialBalance, decimals);
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * This method is used get {@link #sellPrice} instance
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link #sellPrice} formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getSellPrice(int decimals) {
        return roundValue(sellPrice, decimals);
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
    }

    public void setSellDate(long sellDate) {
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDate);
    }

    /**
     * This method is used get sell date formatted as long<br>
     * Any params required
     *
     * @return buy date timestamp as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public long getSellDateTimestamp() {
        return sellDateTimestamp;
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

    /**
     * This method is used get days occurred between buy and sell <br>
     * Any params required
     *
     * @return days occurred between buy and sell
     **/
    public int getTradeDays() {
        return toIntExact(DAYS.convert(sellDateTimestamp - getBuyDateTimestamp(), TimeUnit.MILLISECONDS));
    }

    /**
     * This method is used get transaction income value <br>
     * Any params required
     *
     * @return transaction income value as double
     **/
    @Override
    public double getIncomePercent() {
        double lastValue = lastPrice;
        if (sellPrice != 0)
            lastValue = sellPrice;
        return computeAssetPercent(value, lastValue);
    }

    /**
     * This method is used get transaction income value
     *
     * @param decimals: number of decimal digits es. 2
     * @return transaction income value as double
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    @Override
    public double getIncomePercent(int decimals) {
        return roundValue(getIncomePercent(), decimals);
    }

    /**
     * This method is used get transaction income value <br>
     * Any params required
     *
     * @return transaction income value as {@link String} es. +2.3821%
     **/
    public String getIncomePercentText() {
        return textualizeAssetPercent(getIncomePercent());
    }

    /**
     * This method is used get transaction income value
     *
     * @param decimals: number of decimal digits es. 2
     * @return transaction income value as {@link String} es. +2.38%
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public String getIncomePercentText(int decimals) {
        return textualizeAssetPercent(getIncomePercent(decimals));
    }

    /**
     * This method is used get transaction value <br>
     * Any params required
     *
     * @return transaction value as double
     **/
    @Override
    public double getValue() {
        double price = lastPrice;
        if (sellPrice != 0)
            price = sellPrice;
        return quantity * price;
    }

    /**
     * This method is used get transaction value
     *
     * @param decimals: number of decimal digits es. 2
     * @return transaction value as double
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    @Override
    public double getValue(int decimals) {
        return roundValue(getValue(), decimals);
    }

    @Override
    public String toString() {
        return "TxNote{" +
                "initialBalance=" + initialBalance +
                ", sellPrice=" + sellPrice +
                ", sellDate=" + sellDate +
                ", sellPrice=" + sellPrice +
                ", lastPrice=" + lastPrice +
                ", symbol='" + symbol + '\'' +
                ", status='" + side + '\'' +
                ", buyDate='" + transactionDate + '\'' +
                ", startPrice=" + value +
                ", quantity=" + quantity +
                ", incomePercent=" + getIncomePercent() +
                ", value=" + getValue() +
                '}';
    }

}
