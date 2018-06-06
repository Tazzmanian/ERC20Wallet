package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionsManager {
    static final String TableName = "Transactions";
    static final String ColumnFrom = "FromHash";
    static final String ColumnTo = "ToHash";
    static final String ColumnValue = "Value";
    static final String ColumnHash = "Hash";
    static final String ColumnCurrency = "Currency";

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, "
            + ColumnFrom + " text, "
            + ColumnTo + " text, "
            + ColumnValue + " text, "
            + ColumnHash + " text, "
            + ColumnCurrency +" text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    public void createTable(SQLiteDatabase db) {
        db.execSQL(CreateTable);
    }

    public void updateTable(SQLiteDatabase db) {
        db.execSQL(DropTable);
    }
}