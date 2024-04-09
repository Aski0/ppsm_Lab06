package edu.ppsm.lab6

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException

class CDBMaterial(context: Context?) {
    private val dbHelper: CDBHelper = CDBHelper(context)
    private var dbMaterial: SQLiteDatabase? = null

    fun open() {
        try {
            dbMaterial = dbHelper.writableDatabase
        } catch (e: SQLException) {
           Log.i("DB open error", e.message!!)
        }
    }

    fun close() {
        dbMaterial?.close()
    }

    fun addMaterial(typ: String?, norma:String?) {
        val cv=ContentValues()
        cv.put(TYPE_TYPENAME,typ)
        cv.put(TYPE_STANDARD,norma)
        try{
            dbMaterial?.insert(TABLE_TYPE,null,cv)
        }catch (e:SQLException){
            Log.i("DB write error",e.message!!)
        }

    }

    val materials: Cursor
        get()=dbMaterial!!.query(TABLE_TYPE,null,null,null,null,null, TYPE_ID)
}