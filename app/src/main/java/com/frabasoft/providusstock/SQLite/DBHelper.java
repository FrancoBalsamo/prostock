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
        if(newVersion > oldVersion){
            sqLiteDatabase.execSQL("ALTER TABLE " + TablaToners.TONERS_TABLA + " ADD COLUMN proveedor TEXT" );
            sqLiteDatabase.execSQL("UPDATE " + TablaToners.TONERS_TABLA + " SET proveedor = MACROX WHERE " + TablaToners.ID_TONER + " IN (1,2,3,4,13,16,17,18,19,20,21)" );
            sqLiteDatabase.execSQL("UPDATE " + TablaToners.TONERS_TABLA + " SET proveedor = EVOLUSOFT WHERE " + TablaToners.ID_TONER + " IN (5,6,7,8,9,10,11,12,14,15,25,22,23)" );
        }
        onCreate(sqLiteDatabase);
    }
}