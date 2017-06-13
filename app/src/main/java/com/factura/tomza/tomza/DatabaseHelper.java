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
    public static final String DB_TOMZA = "TOMZADB";

    public static final String TABLE_CLIENTES = "CLIENTES";
    public static final String TABLE_FACTURATOMZA = "FACTURATOMZA";
    public static final String TABLE_FACTURACILZA = "FACTURACILZA";
    public static final String TABLE_GENERAL = "GENERAL";

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

    public static final String FACTURATOMZA_ID = "id";
    public static final String FACTURATOMZA_FACNUM = "facnum";
    public static final String FACTURATOMZA_CODIGOCLIENTE = "codigocliente";
    public static final String FACTURATOMZA_STATUS = "status";
    public static final String FACTURATOMZA_Q20 = "q20";
    public static final String FACTURATOMZA_Q25 = "q25";
    public static final String FACTURATOMZA_Q35 = "q35";
    public static final String FACTURATOMZA_Q45 = "q45";
    public static final String FACTURATOMZA_Q100 = "q100";
    public static final String FACTURATOMZA_QLTS = "qlts";
    public static final String FACTURATOMZA_QKGS = "qkgs";
    public static final String FACTURATOMZA_FECHA = "fecha";

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
    public static final String GENERAL_PRECIO20 = "";
    public static final String GENERAL_PRECIO25 = "";
    public static final String GENERAL_PRECIO35 = "";
    public static final String GENERAL_PRECIO45 = "";
    public static final String GENERAL_PRECIO100 = "";
    public static final String GENERAL_PRECIOLTS = "";
    public static final String GENERAL_PRECIOCIL20 = "";
    public static final String GENERAL_PRECIOCIL25 = "";
    public static final String GENERAL_PRECIOCIL35 = "";
    public static final String GENERAL_PRECIOCIL45 = "";
    public static final String GENERAL_PRECIOCIL100 = "";
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
        super(context, DB_TOMZA, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_CLIENTES
                + "(" + CLIENTES_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLIENTES_RUTA +
                " VARCHAR, " + CLIENTES_CODIGO + " INTEGER, " +
                 CLIENTES_FECHACREACION + " TEXT, " +
                                 CLIENTES_CEDI +
                " VARCHAR, " + CLIENTES_NOMBRE +
                " VARCHAR, " + CLIENTES_RAZONSOCIAL +
                " VARCHAR, " + CLIENTES_TIPODEDOCUMENTO +
                " VARCHAR, " + CLIENTES_DOCUMENTO +
                " VARCHAR, " + CLIENTES_DIRECCION +
                " VARCHAR, " + CLIENTES_TELEFONOFIJO +
                " VARCHAR, " + CLIENTES_TELEFONOCELULAR +
                " VARCHAR, " + CLIENTES_L +
                " TINYINT, " + CLIENTES_K +
                " TINYINT, " + CLIENTES_M +
                " TINYINT, " + CLIENTES_J +
                " TINYINT, " + CLIENTES_V +
                " TINYINT, " + CLIENTES_S +
                " TINYINT, " + CLIENTES_FRECUENCIA +
                " INTEGER, " + CLIENTES_ORDEN +
                " INTEGER, " + CLIENTES_DESCUENTO +
                " DOUBLE, " + CLIENTES_CREDITO +
                " TINYINT, " + CLIENTES_PUEDOFACTURAR +
                " TINYINT, " + CLIENTES_FORMADEPAGO +
                " INTEGER, " + CLIENTES_PLAZO +
                " VARCHAR, " + CLIENTES_COORDENADASLONG +
                " VARCHAR, " + CLIENTES_COORDENADASLAT +" VARCHAR);";
        db.execSQL(sql);

         sql = "CREATE TABLE " + TABLE_FACTURATOMZA
                + "(" + FACTURATOMZA_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + FACTURATOMZA_CODIGOCLIENTE +
                " VARCHAR, " + FACTURATOMZA_FACNUM +
                " INTEGER, "+ FACTURATOMZA_FECHA +
                 " TEXT, "+ FACTURATOMZA_STATUS +
                " INTEGER, " + FACTURATOMZA_Q20 +
                " TINYINT, " + FACTURATOMZA_Q25 +
                " TINYINT, " + FACTURATOMZA_Q35 +
                " TINYINT, " + FACTURATOMZA_Q45 +
                " TINYINT, " + FACTURATOMZA_Q100 +
                " TINYINT, " + FACTURATOMZA_QLTS +
                 " INTEGER, " + FACTURATOMZA_QKGS +
                " INTEGER);";
        db.execSQL(sql);


        sql = "CREATE TABLE " + TABLE_GENERAL
                + "(" + GENERAL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + GENERAL_LASTFACNUM +
                " VARCHAR, " + GENERAL_FECHA +
                " TEXT, "+  GENERAL_PRECIO20 +
                " INTEGER, " + GENERAL_PRECIO25 +
                " INTEGER, " + GENERAL_PRECIO35 +
                " INTEGER, " + GENERAL_PRECIO45 +
                " INTEGER, " + GENERAL_PRECIO100 +
                " INTEGER, " + GENERAL_PRECIOLTS +
                " DOUBLE," + GENERAL_PRECIOCIL20 +
                " INTEGER, " + GENERAL_PRECIOCIL20 +
                " INTEGER, " + GENERAL_PRECIOCIL25 +
                " INTEGER, " + GENERAL_PRECIOCIL35 +
                " INTEGER, " + GENERAL_PRECIOCIL45 +
                " INTEGER, " + GENERAL_PRECIOCIL100 +
                " INTEGER, " + GENERAL_RUTA +
                " INTEGER, " + GENERAL_SUBCANAL +
                " VARCHAR(20)," + GENERAL_CANAL + " VARCHAR);";
        db.execSQL(sql);


    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }


    public boolean addFactura(Integer CODIGOCLIENTE, String FACNUM , String FECHA, int Q20,
                              int Q25,int Q35,int Q45,int Q100,int QLTS,int QKGS) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FACTURATOMZA_CODIGOCLIENTE, CODIGOCLIENTE);
        contentValues.put(FACTURATOMZA_FACNUM, FACNUM);
        contentValues.put(FACTURATOMZA_FECHA, FECHA);
        contentValues.put(FACTURATOMZA_STATUS, 1);
        contentValues.put(FACTURATOMZA_Q20, Q20);
        contentValues.put(FACTURATOMZA_Q25, Q25);
        contentValues.put(FACTURATOMZA_Q35, Q35);
        contentValues.put(FACTURATOMZA_Q45, Q45);
        contentValues.put(FACTURATOMZA_Q100, Q100);
        contentValues.put(FACTURATOMZA_QLTS, QLTS);
        contentValues.put(FACTURATOMZA_QKGS, QKGS);


        db.insert(TABLE_FACTURATOMZA, null, contentValues);
        db.close();
        return true;
    }

    public boolean addGeneral(String LASTFACNUM,String FECHAG, String PRECIO20,int PRECIO25
                             ,int PRECIO35,int PRECIO45,int PRECIO100, double PRECIOLTS, int PRECIOCIL20, int PRECIOCIL25, int PRECIOCIL35
                             ,int PRECIOCIL45, int PRECIOCIL100, int RUTA, String SUBCANAL,String CANAL){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(GENERAL_LASTFACNUM, LASTFACNUM);
        contentValues.put(GENERAL_FECHA, FECHAG);
        contentValues.put(GENERAL_PRECIO20, PRECIO20);
        contentValues.put(GENERAL_PRECIO25, PRECIO25);
        contentValues.put(GENERAL_PRECIO35, PRECIO35);
        contentValues.put(GENERAL_PRECIO45, PRECIO45);
        contentValues.put(GENERAL_PRECIO100, PRECIO100);
        contentValues.put(GENERAL_PRECIOLTS, PRECIOLTS);
        contentValues.put(GENERAL_PRECIOCIL20, PRECIOCIL20);
        contentValues.put(GENERAL_PRECIOCIL25, PRECIOCIL25);
        contentValues.put(GENERAL_PRECIOCIL35, PRECIOCIL35);
        contentValues.put(GENERAL_PRECIOCIL45, PRECIOCIL45);
        contentValues.put(GENERAL_PRECIOCIL100, PRECIOCIL100);
        contentValues.put(GENERAL_RUTA, RUTA);
        contentValues.put(GENERAL_SUBCANAL, SUBCANAL);
        contentValues.put(GENERAL_CANAL, CANAL);


        db.insert(TABLE_GENERAL, null, contentValues);
        db.close();


        return true;
    }

    public boolean addCliente(int RUTA, int CODIGO, String FECHACREACION, String CEDI, String NOMBRE, String RAZONSOCIAL, String TIPOD,String DOC,
                              String DIR,String TELF,String TELC, int L, int K, int M,int J,int V, int S,int FREQ, int ORD, double DESC,int CREDITO
                              ,int PUEDOFACTURAR,int FORMADEPAGO, int PLAZO, String LAT, String LONG){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CLIENTES_RUTA, RUTA);
        contentValues.put(CLIENTES_CODIGO, CODIGO);
        contentValues.put(CLIENTES_FECHACREACION, FECHACREACION);
        contentValues.put(CLIENTES_CEDI, CEDI);
        contentValues.put(CLIENTES_NOMBRE, NOMBRE);
        contentValues.put(CLIENTES_RAZONSOCIAL, RAZONSOCIAL);
        contentValues.put(CLIENTES_TIPODEDOCUMENTO, TIPOD);
        contentValues.put(CLIENTES_DOCUMENTO, DOC);
        contentValues.put(CLIENTES_DIRECCION, DIR);
        contentValues.put(CLIENTES_TELEFONOFIJO, TELF);
        contentValues.put(CLIENTES_TELEFONOCELULAR, TELC);
        contentValues.put(CLIENTES_L,L);
        contentValues.put(CLIENTES_K,K);
        contentValues.put(CLIENTES_M,M);
        contentValues.put(CLIENTES_J,J);
        contentValues.put(CLIENTES_V,V);
        contentValues.put(CLIENTES_S,S);
        contentValues.put(CLIENTES_FRECUENCIA,FREQ);
        contentValues.put(CLIENTES_ORDEN,ORD);
        contentValues.put(CLIENTES_DESCUENTO,DESC);
        contentValues.put(CLIENTES_CREDITO,CREDITO);
        contentValues.put(CLIENTES_PUEDOFACTURAR,PUEDOFACTURAR);
        contentValues.put(CLIENTES_FORMADEPAGO,FORMADEPAGO);
        contentValues.put(CLIENTES_PLAZO,PLAZO);
        contentValues.put(CLIENTES_COORDENADASLONG,LONG);
        contentValues.put(CLIENTES_COORDENADASLAT,LAT);

        db.insert(TABLE_CLIENTES, null, contentValues);
        db.close();
        return true;

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

    /*º
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

    public boolean anularFactura(String Numerodefactura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FACTURATOMZA_STATUS, 0);
        db.update(TABLE_FACTURATOMZA, contentValues, FACTURATOMZA_FACNUM + "=" + Numerodefactura, null);
        db.close();
        return true;
    }
    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getFacturas() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_FACTURATOMZA + " ORDER BY " + FACTURATOMZA_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getGeneral() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_GENERAL + " ORDER BY " + GENERAL_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getCliente() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CLIENTES + " ORDER BY " + CLIENTES_ID + " ASC;";
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
