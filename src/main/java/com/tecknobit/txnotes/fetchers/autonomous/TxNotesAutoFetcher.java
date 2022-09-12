package com.tecknobit.txnotes.fetchers.autonomous;

import com.tecknobit.traderbot.Routines.Interfaces.RoutineMessages;
import com.tecknobit.traderbot.Routines.Interfaces.TraderCoreRoutines;
import com.tecknobit.txnotes.fetchers.TxNotesFetcher;
import com.tecknobit.txnotes.records.TxNote;
import com.tecknobit.txnotes.records.Wallet;

import java.util.Collection;

/**
 * The {@code TxNotesAutoFetcher} class is useful to fetch all transactions from exchange's account autonomously<br>
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
     *
     * @apiNote default value is {@code false}
     **/
    protected boolean runningFetcher;

    /**
     * {@code autoLoadWalletList} is flag that allows the library to autoload {@link #wallets} list
     **/
    protected boolean autoLoadWalletList;

    /**
     * Constructor to init {@link TxNotesAutoFetcher}
     *
     * @param autoFetcherPlatform:  fetcher platform to fetch transactions
     * @param autoLoadWalletList:   flag that allows the library to autoload {@link #wallets} list
     * @param printRoutineMessages: flag to insert to print or not routine messages
     * @implNote these keys will NOT store by library anywhere.
     **/
    public TxNotesAutoFetcher(TraderCoreRoutines autoFetcherPlatform, boolean autoLoadWalletList, boolean printRoutineMessages) {
        super(autoFetcherPlatform);
        this.printRoutineMessages = printRoutineMessages;
        this.autoLoadWalletList = autoLoadWalletList;
        runningFetcher = false;
    }

    /**
     * This method is used to start fetcher's workflow <br>
     * Any params required
     **/
    public void start() {
        runningFetcher = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        while (runningFetcher) {
                            printLog("FETCHING", ANSI_GREEN);
                            fetchTxNotesList();
                            if (printRoutineMessages) {
                                Collection<TxNote> notes = getTxNotesFetched();
                                if (notes.size() > 0) {
                                    printLog("### Transaction notes list", ANSI_RED);
                                    for (TxNote txNote : notes)
                                        txNote.printDetails();
                                } else
                                    printLog("Empty notes list");
                                Collection<Wallet> wallets = getWallets();
                                if (wallets.size() > 0) {
                                    printLog("### Wallet list", ANSI_RED);
                                    for (Wallet wallet : wallets)
                                        wallet.printDetails();
                                } else
                                    printLog("Empty wallet list");
                            }
                            sleep(fetcherPlatform.getRefreshTime());
                        }
                        printLog("WAITING", ANSI_RED);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    printRed(e.getMessage());
                }
            }
        }.start();
    }

    /**
     * This method is used to assemble a {@link TxNote}'s list fetched from your exchange's account<br>
     * Any params required
     *
     * @return list as {@link Collection} of {@link TxNote}
     * @throws Exception when an operation fails
     * @apiNote if {@link #autoLoadWalletList} is set to true will be automatically call {@link #loadWalletList()} method
     **/
    @Override
    public Collection<TxNote> fetchTxNotesList() throws Exception {
        Collection<TxNote> txNotes = super.fetchTxNotesList();
        if (autoLoadWalletList)
            loadWalletList();
        return txNotes;
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

    /**
     * This method is used to get {@link #autoLoadWalletList} flag
     *
     * @return flag that allows the library to autoload {@link #wallets} list
     **/
    public boolean canAutoLoadWalletList() {
        return autoLoadWalletList;
    }

    /**
     * This method is used to enable autoload of the {@link #wallets} list
     **/
    public void enableAutoLoadWalletList() {
        autoLoadWalletList = true;
    }

    /**
     * This method is used to disable autoload of the {@link #wallets} list
     **/
    public void disableAutoLoadWalletList() {
        autoLoadWalletList = false;
    }

}
