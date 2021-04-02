package com.zerodev.kasremaja.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import com.zerodev.kasremaja.root.App
import org.jetbrains.anko.db.*

class SQLHelper
    (context: Context) :
    ManagedSQLiteOpenHelper(
        context,
        "downloaded.db",
        null,
        1
    ) {

    private lateinit var db: SQLiteDatabase

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
            DataBrosur.SIZE to TEXT,
            DataBrosur.DATE to TEXT,
            DataBrosur.URL to TEXT,
            DataBrosur.URLVIEW to TEXT,
            DataBrosur.DOWNLOADED to TEXT,
            DataBrosur.DIR to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DataBrosur.TABLE_BROSUR, true)
    }

    fun insert(data: DataBrosur) {
        this.db = App.database!!.readableDatabase
        val values = ContentValues()
        values.put(DataBrosur.ID, data.id_brosur)
        values.put(DataBrosur.TITLE, data.title_brosur)
        values.put(DataBrosur.SIZE, data.size_brosur)
        values.put(DataBrosur.DATE, data.date_brosur)
        values.put(DataBrosur.URL, data.url_brosur)
        values.put(DataBrosur.URLVIEW, data.urlview_brosur)
        values.put(DataBrosur.DOWNLOADED, "TRUE")
        values.put(
            DataBrosur.DIR,
            Environment.DIRECTORY_DOWNLOADS + "/${data.title_brosur}.pdf"
        )
        Log.e("Data", values.toString())
        db.insert(DataBrosur.TABLE_BROSUR, null, values)
    }

    fun getAllBrosur(): List<DataBrosur> {
        this.db = App.database!!.readableDatabase

        val list: MutableList<DataBrosur> = ArrayList()

        val cursor = db.rawQuery("SELECT * FROM ${DataBrosur.TABLE_BROSUR}", null)

        for (count in 0 until cursor.count) {
            cursor.moveToPosition(count)
            list.add(
                DataBrosur(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    true
                )
            )
        }

        return list
    }

}
