package com.tecknobit.txnotes.fetchers;

import com.tecknobit.txnotes.records.TxNote;

import java.util.ArrayList;

public abstract class TxNotesFetcher {

    public abstract ArrayList<TxNote> getTxNotesList();

}
