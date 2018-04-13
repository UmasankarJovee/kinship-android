/*
package com.joveeinfotech.bloodex.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
//import android.provider.SyncStateContract.Helpers.
//import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

*
 * Created by prandex-and-05 on 10/4/18.


class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun Instance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(database : SQLiteDatabase) {
        //createTable(Book.TABLE_NAME, true, Book.COLUMN_ID to INTEGER + PRIMARY_KEY, Book.COLUMN_TITLE to TEXT, Book.COLUMN_AUTHOR to TEXT)
        database.use {
            createTable("Customer", true,
                    "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                    "name" to TEXT,
                    "photo" to BLOB)
        }
        database.use {
            insert()
        }
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropTable(Book.TABLE_NAME, true)
    }
}
*/
