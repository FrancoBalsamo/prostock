package com.frabasoft.providusstock.SQLite.Cartuchos;

public class TablaCartuchos {
    //Nombre de la tabla
    public static final String CARTUCHOS_TABLA = "CARTUCHOS";

    //Campos de la tabla
    public static final String ID_CARTUCHO = "idCartucho";
    public static final String MODELO_CARTUCHO = "modelo";
    public static final String COLOR_CARTUCHO = "color";
    public static final String CANTIDAD_CARTUCHO = "cantidad";
    public static final String FECHA_MODIFICACION_CANTIDAD_CARTUCHO = "fechaModificacionCantidad";

    //Creación de la tabla

    public static final String TABLA_CARTUCHO_SQL =
            "CREATE TABLE IF NOT EXISTS " + CARTUCHOS_TABLA + " ("
                    + ID_CARTUCHO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + MODELO_CARTUCHO + " TEXT NOT NULL, "
                    + COLOR_CARTUCHO + " TEXT NOT NULL, "
                    + CANTIDAD_CARTUCHO + " TEXT NOT NULL, "
                    + FECHA_MODIFICACION_CANTIDAD_CARTUCHO + " TEXT NOT NULL);";
}
