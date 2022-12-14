package com.tecknobit.txnotes.records;

import com.tecknobit.traderbot.records.portfolio.Transaction;
import com.tecknobit.traderbot.routines.interfaces.RecordDetails;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.tecknobit.apimanager.trading.TradingTools.*;
import static com.tecknobit.traderbot.routines.interfaces.RoutineMessages.*;
import static com.tecknobit.traderbot.routines.interfaces.TraderBotConstants.*;
import static com.tecknobit.traderbot.routines.interfaces.TraderBotConstants.Side.BUY;
import static com.tecknobit.traderbot.routines.interfaces.TraderBotConstants.Side.SELL;
import static com.tecknobit.txnotes.fetchers.interfaces.TxNotesConstants.*;
import static java.lang.Math.toIntExact;
import static java.lang.System.out;
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

public class TxNote extends Transaction implements RecordDetails {

    /**
     * {@code startPrice} is instance that memorizes start price value
     **/
    protected final double startPrice;

    /**
     * {@code status} is instance that memorizes status of transaction -> BUY or SELL
     **/
    protected Side status;

    /**
     * {@code sellPrice} is instance that memorizes sell price value
     **/
    protected double sellPrice;

    /**
     * {@code sellDateTimestamp} is instance that memorizes sell date timestamp value
     **/
    protected long sellDateTimestamp;

    /**
     * {@code sellDate} is instance that memorizes sell date value
     **/
    protected String sellDate;

    /**
     * {@code lastPrice} is instance that memorizes last price value
     **/
    protected double lastPrice;

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in long format es. 1656623302000
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, long buyDate, double initialBalance, double quantity, double lastPrice) {
        super(symbol, null, buyDate, initialBalance, quantity);
        this.status = status;
        this.lastPrice = lastPrice;
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, String buyDate, double initialBalance, double quantity, double lastPrice) {
        super(symbol, null, buyDate, initialBalance, quantity);
        this.status = status;
        this.lastPrice = lastPrice;
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in long format es. 1656623302000
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param sellPrice:      sell price value
     * @param sellDate:       date when this trade has being sold in long format es. 1656624150000
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, long buyDate, double initialBalance, double quantity, double lastPrice,
                  double sellPrice, long sellDate) {
        super(symbol, null, buyDate, initialBalance, quantity);
        this.status = status;
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDateTimestamp);
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param sellPrice:      sell price value
     * @param sellDate:       date when this trade has being sold in {@link String} format es. 21:22:30 30/06/2022
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, String buyDate, double initialBalance, double quantity, double lastPrice,
                  double sellPrice, String sellDate) {
        super(symbol, null, buyDate, initialBalance, quantity);
        this.status = status;
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in long format es. 1656623302000
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param baseAsset:      base asset used in that transaction es. BTC
     * @param quoteAsset:     quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, long buyDate, double initialBalance, double quantity, double lastPrice,
                  String baseAsset, String quoteAsset) {
        super(symbol, null, buyDate, initialBalance, quantity, quoteAsset, baseAsset);
        this.status = status;
        this.lastPrice = lastPrice;
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param baseAsset:      base asset used in that transaction es. BTC
     * @param quoteAsset:     quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, String buyDate, double initialBalance, double quantity, double lastPrice,
                  String baseAsset, String quoteAsset) {
        super(symbol, null, buyDate, initialBalance, quantity, quoteAsset, baseAsset);
        this.status = status;
        this.lastPrice = lastPrice;
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in long format es. 1656623302000
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param sellPrice:      sell price value
     * @param sellDate:       date when this trade has being sold in long format es. 1656624150000
     * @param baseAsset:      base asset used in that transaction es. BTC
     * @param quoteAsset:     quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, long buyDate, double initialBalance, double quantity, double lastPrice,
                  double sellPrice, long sellDate, String baseAsset, String quoteAsset) {
        super(symbol, null, buyDate, initialBalance, quantity, quoteAsset, baseAsset);
        this.status = status;
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDateTimestamp);
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * Constructor to init {@link TxNote}
     *
     * @param symbol:         symbol used in transaction es. BTCBUSD or BTC-USD
     * @param status:         status of transaction BUY or SELL
     * @param buyDate:        date when this transaction has being done in {@link String} format es. 21:08:22 30/06/2022
     * @param initialBalance: initial balance when this transaction has being done
     * @param quantity:       quantity transfered in transaction
     * @param lastPrice:      last price value
     * @param sellPrice:      sell price value
     * @param sellDate:       date when this trade has being sold in {@link String} format es. 21:22:30 30/06/2022
     * @param baseAsset:      base asset used in that transaction es. BTC
     * @param quoteAsset:     quote asset used in that transaction es. EUR
     * @throws IllegalArgumentException when parameters inserted do not respect right value form.
     **/
    public TxNote(String symbol, Side status, String buyDate, double initialBalance, double quantity, double lastPrice,
                  double sellPrice, String sellDate, String baseAsset, String quoteAsset) {
        super(symbol, null, buyDate, initialBalance, quantity, quoteAsset, baseAsset);
        this.status = status;
        this.lastPrice = lastPrice;
        this.sellPrice = sellPrice;
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
        startPrice = roundValue(initialBalance / quantity, 8);
    }

    /**
     * This method is used to get detail of {@link TxNote} coloured
     *
     * @param tail:  tail of detail
     * @param value: value of detail
     * @return details coloured
     **/
    public static String getDetailsColoured(String tail, String value) {
        String color = "";
        if (value.equals(BUY.name()) || value.contains("+"))
            color = ANSI_GREEN;
        else if (!value.contains("="))
            color = ANSI_RED;
        return "## " + tail + ": " + color + value + ANSI_RESET + "\n";
    }

    /**
     * Method to get {@link #status} instance <br>
     * Any params required
     *
     * @return {@link #status} instance as {@link Side}
     **/
    public Side getStatus() {
        return status;
    }

    /**
     * Method to get {@link #transactionDate} instance <br>
     * Any params required
     *
     * @return {@link #transactionDate} instance as {@link String}
     **/
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

    /**
     * Method to get {@link #startPrice} instance <br>
     * Any params required
     *
     * @return {@link #startPrice} instance as double
     **/
    public double getStartPrice() {
        return startPrice;
    }

    /**
     * This method is used get start price value
     *
     * @param decimals: number of decimal digits es. 2
     * @return start price value formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getStartPrice(int decimals) {
        return roundValue(startPrice, decimals);
    }

    /**
     * Method to get {@link #value} instance <br>
     * Any params required
     *
     * @return {@link #value} instance as double
     **/
    public double getInitialBalance() {
        return value;
    }

    /**
     * This method is used get initial balance value
     *
     * @param decimals: number of decimal digits es. 2
     * @return initial balance value formatted as 21.22
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInitialBalance(int decimals) {
        return roundValue(value, decimals);
    }

    /**
     * Method to get {@link #sellPrice} instance <br>
     * Any params required
     *
     * @return {@link #sellPrice} instance as double
     **/
    public double getSellPrice() {
        return sellPrice;
    }

    /**
     * Method to set {@link #sellPrice}
     *
     * @param sellPrice: sell price value
     **/
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

    /**
     * Method to get {@link #sellDate} instance <br>
     * Any params required
     *
     * @return {@link #sellDate} instance as {@link String}
     **/
    public String getSellDate() {
        return sellDate;
    }

    /**
     * Method to set {@link #sellDate}
     *
     * @param sellDate: sell date value
     **/
    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
        sellDateTimestamp = getDateTimestamp(sellDate);
    }

    /**
     * Method to set {@link #sellDate}
     *
     * @param sellDate: sell date value
     **/
    public void setSellDate(long sellDate) {
        this.sellDateTimestamp = sellDate;
        this.sellDate = getDate(sellDate);
    }

    /**
     * This method is used get sell date formatted as long<br>
     * Any params required
     *
     * @return buy date timestamp as long
     **/
    public long getSellDateTimestamp() {
        return sellDateTimestamp;
    }

    /**
     * Method to get {@link #lastPrice} instance <br>
     * Any params required
     *
     * @return {@link #lastPrice} instance as double
     **/
    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * Method to set {@link #lastPrice}
     *
     * @param lastPrice: last price value
     **/
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
     * This method is used set status of transaction as SELL <br>
     * Any params required
     *
     * @throws IllegalStateException when transaction is already in a SELL status
     **/
    public void markAsSold() {
        if (!status.equals(SELL))
            status = SELL;
        else
            throw new IllegalStateException("This transaction were already mark as sold");
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
        return computeAssetPercent(startPrice, lastValue, 8);
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
        return roundValue(quantity * price, 8);
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
     * This method is used to get {@link TxNote} details <br>
     * Any params required
     * @return {@link TxNote} details as {@link HashMap} of {@link Object}
     **/
    public HashMap<String, Object> getTxNote() {
        HashMap<String, Object> txNote = new HashMap<>();
        txNote.put(SYMBOL_KEY, symbol);
        txNote.put(STATUS_KEY, status);
        txNote.put(INITIAL_BALANCE_KEY, value);
        txNote.put(BUY_DATE_KEY, getBuyDateTimestamp());
        txNote.put(FIRST_PRICE_KEY, startPrice);
        txNote.put(BASE_ASSET_KEY, baseAsset);
        txNote.put(QUOTE_ASSET_KEY, quoteAsset);
        txNote.put(QUANTITY_KEY, quantity);
        txNote.put(LAST_PRICE_KEY, lastPrice);
        if (sellPrice != 0) {
            txNote.put(SELL_DATE_KEY, sellDateTimestamp);
            txNote.put(SELL_PRICE_KEY, sellPrice);
        }
        return txNote;
    }

    /**
     * This method is used to get all {@link TxNote} details
     *
     * @param decimals: number of decimal digits es. 2
     * @return {@link TxNote} details as {@link HashMap} of {@link Object}
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public HashMap<String, Object> getTxNote(int decimals) {
        HashMap<String, Object> txNote = new HashMap<>(getTxNote());
        txNote.put(VALUE_KEY, getValue(decimals));
        txNote.put(QUANTITY_KEY, getQuantity(decimals));
        txNote.put(INCOME_PERCENT_KEY, getIncomePercentText(decimals));
        txNote.put(FIRST_PRICE_KEY, getStartPrice(decimals));
        txNote.put(LAST_PRICE_KEY, getLastPrice(decimals));
        txNote.put(INITIAL_BALANCE_KEY, getInitialBalance(decimals));
        return txNote;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        String txNote = "## [" + symbol + "]\n" +
                getDetailsColoured("Status", status.name()) +
                "## Initial balance: " + value + "\n" +
                "## Buy-Date: " + transactionDate + "\n" +
                "## Start price: " + startPrice + "\n" +
                "## Amount value: " + getValue() + "\n" +
                "## Quantity: " + quantity + "\n" +
                getDetailsColoured("Income percent", getIncomePercentText()) +
                "## Base asset: " + baseAsset + "\n" +
                "## Quote asset: " + quoteAsset + "\n";
        if (sellPrice != 0) {
            txNote += "## Sell-Date: " + sellDate + "\n" +
                    "## Sell price: " + sellPrice + "\n" +
                    "## Days traded: " + getTradeDays() + "\n";
        }
        return txNote + "######################";
    }

    /**
     * Returns a string pretty printed of the representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    public String prettyPrint() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code TxNotesListManager} interface allows to manage a list of {@link TxNote} giving base methods to work
     * on it
     **/
    public interface TxNotesListManager {

        /**
         * This method is used to fetch a transaction note from list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: timestamp of the date to fetch from list of {@link TxNote}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNote(String asset, long checkDate);

        /**
         * This method is used to fetch a transaction note from list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: check date to fetch from list of {@link TxNote} as {@link String}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNote(String asset, String checkDate);

        /**
         * This method is used to fetch a transaction note from list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: check date to fetch from list of {@link TxNote} as {@link Date}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNote(String asset, Date checkDate);

        /**
         * This method is used to fetch a transaction note from list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: timestamp of the date to fetch from list of {@link TxNote}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNoteSold(String asset, long checkDate);

        /**
         * This method is used to fetch a transaction note from list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: check date to fetch from list of {@link TxNote} as {@link String}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNoteSold(String asset, String checkDate);

        /**
         * This method is used to fetch a transaction note from a list of {@link TxNote}
         *
         * @param asset:     identifier of the asset with transaction has been made
         * @param checkDate: check date to fetch from list as {@link Date}
         * @return transaction note as {@link TxNote} custom object
         **/
        TxNote fetchTxNoteSold(String asset, Date checkDate);

        /**
         * This method is used to delete a transaction note from list of {@link TxNote}
         *
         * @param asset:      identifier of the asset with transaction has been made
         * @param removeDate: timestamp of the date to delete from list of {@link TxNote}
         * @return result of deletion as boolean
         **/
        boolean deleteTxNote(String asset, long removeDate);

        /**
         * This method is used to delete a transaction note from list of {@link TxNote}
         *
         * @param asset:      identifier of the asset with transaction has been made
         * @param removeDate: check date to delete from list of {@link TxNote} as {@link String}
         * @return result of deletion as boolean
         **/
        boolean deleteTxNote(String asset, String removeDate);

        /**
         * This method is used to delete a transaction note from list of {@link TxNote}
         *
         * @param asset:      identifier of the asset with transaction has been made
         * @param removeDate: check date to delete from list of {@link TxNote} as {@link Date}
         * @return result of deletion as boolean
         **/
        boolean deleteTxNote(String asset, Date removeDate);

    }

}
