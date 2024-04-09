package edu.ppsm.lab6

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

const val TABLE_TYPE = "TYPES"
const val TYPE_ID = "_id"
const val TYPE_TYPENAME = "typ"
const val TYPE_STANDARD = "norma"

private const val DB_NAME = "Material.db"
private const val DB_VERSION = 1

private const val CREATE_TABLE_TYPE =
    "CREATE TABLE $TABLE_TYPE (" +
            "$TYPE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$TYPE_TYPENAME TEXT, $TYPE_STANDARD TEXT)"


class CDBHelper(context: Context?): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        try{
            db?.execSQL(CREATE_TABLE_TYPE)
        }catch(ex: SQLException){
            Log.i("SQLite error", ex.message!!)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try{
            db?.execSQL("DROP TABLE IF EXISTS %s".format(TABLE_TYPE))
            onCreate(db)
        }catch (ex:SQLException){
            Log.i("SQLite error", ex.message!!)
        }
    }
}