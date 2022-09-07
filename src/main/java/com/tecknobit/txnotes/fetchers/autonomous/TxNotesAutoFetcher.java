package com.tecknobit.txnotes.fetchers.autonomous;

import com.tecknobit.traderbot.Routines.Interfaces.RoutineMessages;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.fetchers.autonomous.interfaces.BinanceAutoFetcher;
import com.tecknobit.txnotes.fetchers.autonomous.interfaces.CoinbaseAutoFetcher;
import com.tecknobit.txnotes.records.TxNote;

import java.util.Collection;

import static com.tecknobit.traderbot.Records.Portfolio.Transaction.getDate;
import static java.lang.System.currentTimeMillis;

/**
 * The {@code TxNotesFetcher} class is useful to fetch all transactions from exchange's account autonomously<br>
 * This class give basic methods for auto fetcher's workflow inherited from {@link TxNotesFetcher}
 *
 * @author Tecknobit N7ghtm4r3
 * @see BinanceAutoFetcher
 * @see CoinbaseAutoFetcher
 * @see TxNotesFetcher
 **/

public abstract class TxNotesAutoFetcher extends TxNotesFetcher implements RoutineMessages {

    /**
     * {@code printRoutineMessages} is instance that memorizes flag to insert to print or not routine messages
     **/
    protected boolean printRoutineMessages;

    /**
     * {@code runningFetcher} is instance that memorizes flag that indicates if the fetcher is running or not
     **/
    protected boolean runningFetcher;

    /**
     * Constructor to init {@link TxNotesAutoFetcher}
     *
     * @param autoFetcherPlatform: fetcher platform to fetch transactions
     * @implNote these keys will NOT store by library anywhere.
     **/
    public TxNotesAutoFetcher(TraderCoreRoutines autoFetcherPlatform) {
        super(autoFetcherPlatform);
        printRoutineMessages = true;
    }

    /**
     * This method is used to start fetcher's workflow <br>
     * Any params required
     **/
    public void start() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        while (runningFetcher) {
                            printLog("FETCHING", true);
                            if (printRoutineMessages) {
                                Collection<TxNote> notes = txNotes.values();
                                if (notes.size() > 0)
                                    for (TxNote txNote : notes)
                                        System.out.println(txNote);
                                else
                                    printLog("Empty list", false);
                            }
                            sleep(fetcherPlatform.getRefreshPricesTime());
                        }
                        printLog("WAITING", false);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    printRed(e.getMessage());
                }
            }
        }.start();
    }

    /**
     * This method is used to set time to refresh data
     *
     * @param refreshTime: is time in seconds to set to refresh data
     * @throws IllegalArgumentException if {@code refreshTime} value is less than 5(5s) and if is bigger than 3600(1h)
     **/
    public void setRefreshTime(long refreshTime) {
        fetcherPlatform.setRefreshPricesTime((int) refreshTime);
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
     * This method is used to get if fetcher is in running mode
     *
     * @return flag that indicates if the fetcher is running
     **/
    public boolean isRunningFetcher() {
        return runningFetcher;
    }

    /**
     * This method is used to enable running mode of fetcher
     **/
    public void startFetcher() {
        runningFetcher = true;
    }

    /**
     * This method is used to disable running mode of fetcher
     **/
    public void stopFetcher() {
        runningFetcher = false;
    }

    /**
     * This method is used to set flag to print routine messages
     *
     * @param printRoutineMessages: flag to insert to print or not routine messages
     **/
    @Override
    public void setPrintRoutineMessages(boolean printRoutineMessages) {
        this.printRoutineMessages = printRoutineMessages;
    }

    /**
     * This method is used to get flag to print or not routine messages
     *
     * @return flag that indicates the possibility or not to print or not routine messages
     **/
    public boolean canPrintRoutineMessages() {
        return printRoutineMessages;
    }

    // TODO: 07/09/2022 IMPLEMENT FROM LIBRARY

    /**
     * This method is used to print message about an operation made<br>
     *
     * @param msg:        message to print out
     * @param greenPrint: flag to print green or red
     **/
    private void printLog(String msg, boolean greenPrint) {
        if (printRoutineMessages) {
            if (greenPrint) {
                printGreen(getDate(currentTimeMillis()) + " -> " + msg);
            } else
                printRed(getDate(currentTimeMillis()) + " -> " + msg);
        }
    }

}
