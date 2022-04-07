package com.frabasoft.providusstock.SQLite.Toners;

public class TablaToners {
    //Nombre de la tabla
    public static final String TONERS_TABLA = "TONERS";

    //Campos de la tabla
    public static final String ID_TONER = "idToner";
    public static final String MODELO_TONER = "modelo";
    public static final String COLOR_TONER = "color";
    public static final String CANTIDAD_TONER = "cantidad";
    public static final String FECHA_MODIFICACION_CANTIDAD_TONER = "fechaModificacionCantidad";
    public static final String PROVEEDOR_TONER = "proveedor";

    //Creación de la tabla

    public static final String TABLA_TONER_SQL =
            "CREATE TABLE IF NOT EXISTS " + TONERS_TABLA + " ("
                    + ID_TONER + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + MODELO_TONER + " TEXT NOT NULL, "
                    + COLOR_TONER + " TEXT NOT NULL, "
                    + CANTIDAD_TONER + " TEXT NOT NULL, "
                    + FECHA_MODIFICACION_CANTIDAD_TONER + " TEXT NOT NULL);";
}
