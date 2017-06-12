package com.factura.tomza.tomza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Belal on 1/27/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_CLIENTES = "CLIENTESDB";
    public static final String DB_FACTURATOMZA = "FACTURATOMZADB";
    public static final String DB_FACTURACILZA = "FACTURACILZADB";
    public static final String DB_GENERAL = "GENERALDB";
    public static final String CLIENTES_ID = "id";
    public static final String CLIENTES_FECHACREACION = "fechacreacion";
    public static final String CLIENTES_RUTA = "ruta";
    public static final String CLIENTES_CODIGO = "codigo";
    public static final String CLIENTES_NOMBRE = "nombre";
    public static final String CLIENTES_RAZONSOCIAL = "razonsocial";
    public static final String CLIENTES_TIPODEDOCUMENTO = "tipodedocumento";
    public static final String CLIENTES_DOCUMENTO = "documento";
    public static final String CLIENTES_CEDI = "cedi";
    public static final String CLIENTES_DIRECCION = "direccion";
    public static final String CLIENTES_TELEFONOFIJO = "telefonofijo";
    public static final String CLIENTES_TELEFONOCELULAR = "relefonocelular";
    public static final String CLIENTES_L = "l";
    public static final String CLIENTES_K = "k";
    public static final String CLIENTES_M = "m";
    public static final String CLIENTES_J = "j";
    public static final String CLIENTES_V = "v";
    public static final String CLIENTES_S = "s";
    public static final String CLIENTES_FRECUENCIA = "frecuencia";
    public static final String CLIENTES_ORDEN = "orden";
    public static final String CLIENTES_DESCUENTO = "descuento";
    public static final String CLIENTES_CREDITO = "credito";
    public static final String CLIENTES_PUEDOFACTURAR = "puedofacturar";
    public static final String CLIENTES_FORMADEPAGO = "formadepago";
    public static final String CLIENTES_PLAZO = "plazo";
    public static final String CLIENTES_COORDENADASLONG = "coordenadaslong";
    public static final String CLIENTES_COORDENADASLAT = "coordenadaslat";

    public static final String FACTURATOMZA_ID = "";
    public static final String FACTURATOMZA_FACNUM = "";
    public static final String FACTURATOMZA_CODIGOCLIENTE = "";
    public static final String FACTURATOMZA_Q20 = "";
    public static final String FACTURATOMZA_Q25 = "";
    public static final String FACTURATOMZA_Q35 = "";
    public static final String FACTURATOMZA_Q45 = "";
    public static final String FACTURATOMZA_Q100 = "";
    public static final String FACTURATOMZA_QLTS = "";
    public static final String FACTURATOMZA_QKGS = "";
    public static final String FACTURATOMZA_FECHA = "";

    public static final String FACTURACILZA_ID = "";
    public static final String FACTURACILZA_FACNUM = "";
    public static final String FACTURACILZA_CODIGOCLIENTE = "";
    public static final String FACTURACILZA_Q20 = "";
    public static final String FACTURACILZA_Q25 = "";
    public static final String FACTURACILZA_Q35 = "";
    public static final String FACTURACILZA_Q45 = "";
    public static final String FACTURACILZA_Q100 = "";
    public static final String FACTURACILZA_FECHA = "";

    public static final String GENERAL_ID = "";
    public static final String GENERAL_FECHA = "";
    public static final String GENERAL_LASTFACNUM = "";
    public static final String GENERAL_PRECIO = "";
    public static final String GENERAL_RUTA = "";
    public static final String GENERAL_CANAL = "";
    public static final String GENERAL_SUBCANAL = "";

    

    public static final String TABLE_NAME = "names";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_CLIENTES, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + DB_CLIENTES
                + "(" + CLIENTES_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLIENTES_RUTA +
                " VARCHAR, " + CLIENTES_CODIGO + " INTEGER, " +
                 CLIENTES_FECHACREACION + " TEXT, " +
                                 CLIENTES_CEDI +
                " VARCHAR, " + CLIENTES_NOMBRE +
                " VARCHAR, " + CLIENTES_RAZONSOCIAL +
                " VARCHAR, "+ CLIENTES_TIPODEDOCUMENTO +
                " VARCHAR, " + CLIENTES_DOCUMENTO +
                " VARCHAR, " + CLIENTES_DIRECCION +
                " VARCHAR, " + CLIENTES_TELEFONOFIJO +
                " VARCHAR, " + CLIENTES_TELEFONOCELULAR+
                " TINYINT, " +  CLIENTES_L +
                " TINYINT, " + CLIENTES_K +
                " TINYINT, " + CLIENTES_M +
                " TINYINT, " + CLIENTES_J +
                " TINYINT, " + CLIENTES_V +
                " TINYINT, " + CLIENTES_S +
                " VARCHAR, " + CLIENTES_FRECUENCIA +
                " VARCHAR, " + CLIENTES_ORDEN +
                " INTEGER, " + CLIENTES_DESCUENTO +
                " TINYINT, " + CLIENTES_CREDITO +
                " TINYINT, " + CLIENTES_PUEDOFACTURAR +
                " TINYINT, " + CLIENTES_FORMADEPAGO +
                " INTEGER, " + CLIENTES_PLAZO +
                " VARCHAR, " + CLIENTES_COORDENADASLONG +
                " VARCHAR, " + CLIENTES_COORDENADASLAT +" VARCHAR);";
        db.execSQL(sql);

         sql = "CREATE TABLE " + DB_FACTURATOMZA
                + "(" + FACTURATOMZA_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + FACTURATOMZA_CODIGOCLIENTE +
                " VARCHAR, " + FACTURATOMZA_FACNUM +
                " INTEGER, "+ FACTURATOMZA_FECHA +
                " TEXT, " + FACTURATOMZA_Q20 +
                " TINYINT, " + FACTURATOMZA_Q25 +
                " TINYINT, " + FACTURATOMZA_Q35 +
                " TINYINT, " + FACTURATOMZA_Q45 +
                " TINYINT, " + FACTURATOMZA_Q100 +
                " TINYINT, " + FACTURATOMZA_QLTS +
                 " INTEGER, " + FACTURATOMZA_QKGS +
                " INTEGER);";
        db.execSQL(sql);

    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
    * This method is taking two arguments
    * first one is the name that is to be saved
    * second one is the status
    * 0 means the name is synced with the server
    * 1 means the name is not synced with the server
    * */
    public boolean addName(String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    /*
    * This method taking two arguments
    * first one is the id of the name for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * this method is for getting all the unsynced name
    * so that we can sync it with database
    * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
