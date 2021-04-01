package com.zerodev.kasremaja.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import org.jetbrains.anko.db.*

class SQLHelper
    (context: Context) :
    ManagedSQLiteOpenHelper(
        context,
        "downloaded.db",
        null,
        1
    ) {


    companion object {
        private var instance: SQLHelper? = null

        fun getInstance(context: Context): SQLHelper {
            if (instance == null) {
                instance = SQLHelper(context)
            }
            return instance as SQLHelper
        }

    }


    override fun onCreate(db: SQLiteDatabase?) {
        //Buat tabel pada database
        db?.createTable(
            DataBrosur.TABLE_BROSUR,
            true,
            DataBrosur.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DataBrosur.TITLE to TEXT,
            DataBrosur.URL to TEXT,
            DataBrosur.URLVIEW to TEXT,
            DataBrosur.DOWNLOADED to TEXT,
            DataBrosur.DIR to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DataBrosur.TABLE_BROSUR, true)
    }

}
