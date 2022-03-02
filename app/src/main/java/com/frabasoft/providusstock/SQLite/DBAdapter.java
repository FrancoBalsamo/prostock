package com.frabasoft.providusstock.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.frabasoft.providusstock.Clases.Cartuchos;
import com.frabasoft.providusstock.Clases.Toners;
import com.frabasoft.providusstock.SQLite.Cartuchos.TablaCartuchos;
import com.frabasoft.providusstock.SQLite.Toners.TablaToners;

public class DBAdapter {
    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void abrirDB(){
        try{
            sqLiteDatabase = dbHelper.getWritableDatabase();
        }catch (SQLException sqlException){
            Log.d("DBAdapter", "abrirDB: " + sqlException.getMessage());
        }
    }

    public void cerrarDB(){
        try{
            dbHelper.close();
        }catch (SQLException sqlException){
            Log.d("DBAdapter", "cerrarDB: " + sqlException.getMessage());
        }
    }

    private ContentValues mapaCartuchos(Cartuchos cartuchos){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablaCartuchos.MODELO_CARTUCHO, cartuchos.getModelo());
        contentValues.put(TablaCartuchos.COLOR_CARTUCHO, cartuchos.getColor());
        contentValues.put(TablaCartuchos.CANTIDAD_CARTUCHO, cartuchos.getCantidad());
        contentValues.put(TablaCartuchos.FECHA_MODIFICACION_CANTIDAD_CARTUCHO, cartuchos.getFechaModificacion());
        return contentValues;
    }

    private ContentValues mapaToners(Toners toners){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablaToners.MODELO_TONER, toners.getModelo());
        contentValues.put(TablaToners.COLOR_TONER, toners.getColor());
        contentValues.put(TablaToners.CANTIDAD_TONER, toners.getCantidad());
        contentValues.put(TablaToners.FECHA_MODIFICACION_CANTIDAD_TONER, toners.getFechaModificacion());
        return contentValues;
    }

    public void insertarCartuchos(Cartuchos cartuchos){
        try{
            abrirDB();
            sqLiteDatabase.insert(TablaCartuchos.CARTUCHOS_TABLA, null, mapaCartuchos(cartuchos));
        }catch(SQLException sqlException){
            Log.d("DBAdapter", "insertarCartuchos: " + sqlException.getMessage());
        }
    }

    public void editarCartuchos(Cartuchos cartuchos, String id){
        try{
            abrirDB();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TablaCartuchos.CANTIDAD_CARTUCHO, cartuchos.getCantidad());
            contentValues.put(TablaCartuchos.FECHA_MODIFICACION_CANTIDAD_CARTUCHO, cartuchos.getFechaModificacion());
            String[] idValor = {String.valueOf(id)};
            sqLiteDatabase.update(TablaCartuchos.CARTUCHOS_TABLA, contentValues, TablaCartuchos.ID_CARTUCHO + " = ?", idValor);
        }catch(SQLException sqlException){
            Log.d("DBAdapter", "editarCartuchos: ");
        }
    }

    public void eliminarCartucho(int id){
        abrirDB();
        sqLiteDatabase.delete(TablaCartuchos.CARTUCHOS_TABLA, TablaCartuchos.ID_CARTUCHO + " = " + id, null);
    }

    public boolean validarInsertCartuchos(String modelo, String color){
        this.abrirDB();
        String[]model = {String.valueOf(modelo)};
        String consultaModelo = "SELECT * FROM "
                + TablaCartuchos.CARTUCHOS_TABLA
                + " WHERE " + TablaCartuchos.MODELO_CARTUCHO + " = ? AND " + TablaCartuchos.COLOR_CARTUCHO + " IN ('" + color + "');";
        Cursor cursor = sqLiteDatabase.rawQuery(consultaModelo, model);
        if(cursor.getCount() > 0){
            cursor.close();
            Toast.makeText(context, "Lo sentimos, este cartucho ya existe en el registro.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Cursor traerTodosToners(){
        String[] columnas = {TablaToners.ID_TONER, TablaToners.MODELO_TONER,
                TablaToners.COLOR_TONER, TablaToners.CANTIDAD_TONER,
                TablaToners.FECHA_MODIFICACION_CANTIDAD_TONER};
        return sqLiteDatabase.query(TablaToners.TONERS_TABLA, columnas, null, null, null, null,
                TablaToners.MODELO_TONER +" ASC, " + TablaToners.COLOR_TONER + " ASC");
    }

    public void insertarToners(Toners toners){
        try{
            abrirDB();
            sqLiteDatabase.insert(TablaToners.TONERS_TABLA, null, mapaToners(toners));
        }catch(SQLException sqlException){
            Log.d("DBAdapter", "insertarToners: " + sqlException.getMessage());
        }
    }

    public void editarToners(Toners toners, String id){
        try{
            abrirDB();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TablaToners.CANTIDAD_TONER, toners.getCantidad());
            contentValues.put(TablaToners.FECHA_MODIFICACION_CANTIDAD_TONER, toners.getFechaModificacion());
            String[] idValor = {String.valueOf(id)};
            sqLiteDatabase.update(TablaToners.TONERS_TABLA, contentValues, TablaToners.ID_TONER + " = ?", idValor);
        }catch(SQLException sqlException){
            Log.d("DBAdapter", "editarToners: ");
        }
    }

    public void eliminarToners(int id){
        abrirDB();
        sqLiteDatabase.delete(TablaToners.TONERS_TABLA, TablaToners.ID_TONER + " = " + id, null);
    }

    public boolean validarInsertToners(String modelo, String color){
        this.abrirDB();
        String[]model = {String.valueOf(modelo)};
        String consultaModelo = "SELECT * FROM "
                + TablaToners.TONERS_TABLA
                + " WHERE " + TablaToners.MODELO_TONER + " = ? AND " + TablaToners.COLOR_TONER + " IN ('" + color + "');";
        Cursor cursor = sqLiteDatabase.rawQuery(consultaModelo, model);
        if(cursor.getCount() > 0) {
            cursor.close();
            Toast.makeText(context, "Lo sentimos, este toner ya existe en el registro.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Cursor traerTodosCartuchos(){
        String[] columnas = {TablaCartuchos.ID_CARTUCHO, TablaCartuchos.MODELO_CARTUCHO,
                TablaCartuchos.COLOR_CARTUCHO, TablaCartuchos.CANTIDAD_CARTUCHO,
                TablaCartuchos.FECHA_MODIFICACION_CANTIDAD_CARTUCHO};
        return sqLiteDatabase.query(TablaCartuchos.CARTUCHOS_TABLA, columnas, null, null, null, null,
                 TablaCartuchos.MODELO_CARTUCHO +" ASC, " + TablaCartuchos.COLOR_CARTUCHO + " ASC");
    }
}