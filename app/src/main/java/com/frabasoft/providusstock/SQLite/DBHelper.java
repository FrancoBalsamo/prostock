package com.frabasoft.providusstock.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.frabasoft.providusstock.SQLite.Cartuchos.TablaCartuchos;
import com.frabasoft.providusstock.SQLite.Toners.TablaToners;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, NombreVersionDB.NOMBRE_DB, null, NombreVersionDB.VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(TablaCartuchos.TABLA_CARTUCHO_SQL);
            sqLiteDatabase.execSQL(TablaToners.TABLA_TONER_SQL);
        }catch (Exception e){
            Log.d("DBHelper", "onCreateDBHelper: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaCartuchos.CARTUCHOS_TABLA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablaToners.TONERS_TABLA);
        onCreate(sqLiteDatabase);
    }
}