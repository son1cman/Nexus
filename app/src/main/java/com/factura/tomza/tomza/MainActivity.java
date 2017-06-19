package com.factura.tomza.tomza;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.Manifest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.content.DialogInterface;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.telephony.SmsManager;
import android.content.BroadcastReceiver;
import android.widget.Toast;
import android.content.IntentFilter;
import android.content.Context;

import android.view.inputmethod.EditorInfo;
import android.view.View.OnKeyListener;
import android.view.View;
import android.view.KeyEvent;

import 	android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.text.DateFormat;
import java.util.Date;

import static java.lang.Math.round;


/**
 * The only activity in this sample. Displays UI widgets for requesting and removing location
 * updates, and for the batched location updates that are reported.
 *
 * Location updates requested through this activity continue even when the activity is not in the
 * foreground. Note: apps running on "O" devices (regardless of targetSdkVersion) may receive
 * updates less frequently than the interval specified in the {@link LocationRequest} when the app
 * is no longer in the foreground.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    /******* Temporary Database *********/
    String[] _razonDB = {"PULPERIA CARACOL","PULPERIA KIKE","SUPER SANTA TERE","SUPER LA FAMILIA","PULPERIA LIMONCITO","PULPERIA LOS GEMELOS","PULPERIA DIVINO NIÑO","PULPERIA EL PERLA","PULPERIA CHIQUITITA","PULPERIA PAPILLOS","LACTEOS DE ORIENTE","PULPERIA EL BUITRE","DISTRIBUIDORA L Y M","PANADERIA LA TRINIDADA","SUPER LA SUIZA","VERDULERIA M. JOSE","CANASTA LA SUIZA","FERRETERIA VELCA","PULPERIA EL CIPRES","PULPEERIA EL HIGUERON","PULPERIA LA MIGUELEÑA","SUPER LA CENTRAL","SUPER LAS ORQUIDEAS","PULPERIA GAUDY","PULPERIA SAN CRISTOBAL","PULPERIA AGUILA REAL","VALLE GRANO DE ORO","PULPERIA BEBATA","SAN MARTIN","SUPER LA PLAZA","FERRETERIA ANGEL","PULPERIA ADONAY","KEVIN VARGAS","PULPERIA SITIO MATA","SUPER BARATO","SUPER LA AMISTAD","MINI SUPER SALAS","DISTRIBUIDOR A Y D","SUPER AQUIARES","ESTER SOJO","CARNICERIA LA FAVORITA","DISTRIBUIDORA L Y M","PULPERIA BENDICION DE DIOS","WORK ROL","PULPERIA EL JIRON","PULPERIA SAN MARTIN","PANADERIA EL CANTANO","CANASTA JUAN VIÑAS","SUPER JUAN VIÑAS","ABASTECEDOR TIERRA POETA","SUPER LUCRECIA","FERRETERIA CHINCHILLA","SUPER TUIZ","PULPERIA EL PUEBLO","PULPERIA PERALTA","AGRO DEL NORTE","SUPER CANADA","SUPER PAGUE MENOS","COMERCIALIZADORA BELCA","SUPER SAIMERSON","SUPER JABILLOS","MARIA JOSE","SUPER SANTA CRUZ","PULPERIA RAYO AZUL","PULPERIA LA CASITA","MINI SUPER MARIA","SANTA LUCIA"};
    String[] _direccionDB = {"GRANO DE ORO 600NORTE DE LA PLAZA","75 ESTE DEL TRANSITO DE TURRIALBA","FRENTE AL PARQUE","CARRETERA HACIA PERALTA","DE LA ESCUELA DE CALLE DEL CAS 300 ESTE","DE LA ESCUELA DEL CAS 700 OESTE","600 ESTE DE LA ESCUELA EL CAS","BARRIOS LOS SAUSES","BARRIO LOS SAUCES","BARRIO LOS SAUCES","BARRIO EL ORIENTE SANTA ROSA","BARRIO EL ORIENTE SANTA ROSA","PEJIBALLE 300 SUROESTE DE LA ESCUELA","DIAGONAL AL PARQUE LA SUIZA","DE LA RURAL50 OESTE","CENTRO LA SUIZ","FRENTE AL BANCO NACIONEAL DE SUIZA","FRENTE A PLAZA DE DEPORTES DE LA SUIZA","FRENTE A PLAZA LAS COLONIAS","BARRIO 100 MANZANAS 200 SUR DE LA ESCUELA","BARRIO 100 MANZANAS 800 NORTE DE LA ESCULA","FRENTE AL CENTRO EDUCATIVO TAYUDIC","DE LA ESCULA DE TAYITIC 2 KM SUR","DE LA ESCUELA 75 NORTE","BAJOS DE PACUARE FRENTE A IGLESIA","BAJOS DE PACUARE SOBRE CARRETERA","GRANO DE ORO CENTRO","GRANO DE ORO QUETZAL ESCUELA 250SUR","GRANO DE ORO 300 ESTE","FRENTE A PLAZA DE DEPORTES DE GRANO DE ORO","DE LA CONCHA DEPORTIVA 50 ESTE GRANO DE ORO","GRANO DE ORO 200 NORTE","TERMINAL DE TRANSTUSA","DE LA ESCUELA DE SITIO MATA 300 ESTE","500 OESTE DE LA ESCUELA","CENTRO DE LLANOS PAVONES","100 SUR DE LA ESCUELA DE JOBILLOS","TURRIALBA CENTRO","DEL PARQUE DE AQUIARES 50 NORTE","DE COOPEANDE 50 NORTE","PEJIBALLE EL HUMO CENTRO","DE LA IGLESIA PEJIBAYE 400 SUR","PEJIBALLE ATIRRO 700 OESTE","DEL PARQUE DE TURRIALNA 200 NORTE","SANTA ELENA 50 NORTE DE FOTOS MARTINEZ","JUAN VILLAS 400 NORTE DE LA ESCUELA","JUAN VIÑAS LOS ALPES ESCUELA 100 ESTE","JUAN VIÑAS 50 OESTE","JUAN VIÑAS DE LA IGLESIA 50 NORTE","FRENTE A LA ESCUELA DE GUAYABO","SANTA CRUZ CENTRO","DEL CENTRO DE SANTA CRUZ KM AL NORTE","DIAGONAL A LA IGLESIA DEL TUIS","CANADA LASUIZA DE LA ESCUELA 50M OESTE","PERALTA CENTRO DE LA ESCUELA 25 ESTE","CAPELLADES DIAGONAL A LA IGLESIA","BARRIO CANADA 150 OESTE DE ESCUELA","DE LA ESCUELA DE LA LIRA 400 NORTE","CENTRO DE TURRIALBA DIAGONAL A LA PLAZA","TURIALBA CENTRO","DE LA ESCULEA DE PAVONES 300 NORTE","300 NORTE DE LA CRUZ ROJA LA SUIZA","SANTA CRUZ CENNTRO DE LA IGLESIA 200 NORTE","DEL MONUMENTO GUAYABO 5 KM NORTE","300 NORESTE DE LA IGLESIA DEL CARMEN DE SANTA CRUZ","DEL BAR ARC NOE 800 MT MANO DERACHA","DEL BAR ARC NOE 800 MT MANO DERACHA"};
    String[] _telefonoDB = {"8337-3517","2556-6621","2559-1020","8635-5975","8346,7764","8350-8612","2285-1700","8659-0449","2245-1709","2245-1861","2529-2047","2578-0187","8989-7322","2531-1281","2531-1919","8745-3214","2531-1084","6138-6734","8841-4225","7102-3673","8357-2837","8846-3392","","2554-8345","","","","8472-0883","2532-2083","","","","8950-3754","2539-1670","2538-1122","8648-7436","2538-1514","2556-1834","2556-2298","8790-7444","2531-3102","2272-9150","2272-2313","2274-4747","8753-7194","","2532-1020","2532-2010","8767-1688","2221-2733","6103-1234","8320-7649","2292-4583","2531-1101","2559-0915","","2531-1530","2577-1940","2574-2630","2577-1034","2538-1005","8488-6045","","7156-2072","8770-8047","8971-2653",""};
    Integer[] _descuentoDB = {0,425,425,0,0,0,0,0,0,0,0,0,240,0,649,0,240,200,649,0,0,649,0,0,0,0,0,0,0,0,0,0,649,0,649,649,200,300,649,-1,240,240,0,300,0,0,0,240,649,425,0,200,0,200,0,0,425,649,200,425,300,649,240,0,0,240,240};
    Integer[] _creditDB = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};



    /********************************************************************************************/




    private boolean _do = false;
    private String _SelectedOptionStr = "";
    private int _SelectedOption = 0;


    private DatabaseHelper db;
    //Bluetooth variables
    // will show the statuses like bluetooth open, close or data sent
    TextView myLabel;
    Button sendButton;
    // will enable user to enter any text to be printed
    EditText myTextbox;
    EditText myUserName;
    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;



    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    /********************************************************************************************/


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL = 5000; // Every 60 seconds. miliseconds

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    private static final long FASTEST_UPDATE_INTERVAL = 2500; // Every 30 seconds

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = 30000;//UPDATE_INTERVAL * 5; // Every 5 minutes.

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;

    // UI Widgets.
    private Button mRequestUpdatesButton;
    private Button mRemoveUpdatesButton;
    private Button mRequestButton;
    private TextView mLocationUpdatesResultView;
    private EditText mTextView;
    /*********************************************************
     * Facturacion
     */
    RadioButton m_i20, m_i25, m_i35,m_i45,m_i100,m_lts,m_kgs,m_i10,m_i40,m_i60;
    Button m_add,m_substract;
    String _codigocliente = "0";
    String _client = "Libreria Hellen";
    String _ruta = "1";
    String _coords = "9.872890,-83.944350";
    String _date;
    double _totalfac = 0;
    String _Quantities;
    int _facnum = 10001;
    String _credit = "c";
    String _desc = "y";

    int Q10,Q20,Q25,Q35,Q40,Q45,Q60,Q100,Qlts,Qkgs;

    double _precioCilindro10 = 4000;
    double _precioCilindro20 = 4000;
    double _precioCilindro25 = 5799;//6967
    double _precioCilindro35 = 6000;
    double _precioCilindro40 = 2000;
    double _precioCilindro45 = 13000;
    double _precioCilindro60 = 4000;
    double _precioCilindro100 = _precioCilindro25 * 4;


    double _descCilindro25 = 0;
    double _descCilindro100 = _descCilindro25 * 4;

    double _descCilindro10 = round(_descCilindro25/25) * 10;
    double _descCilindro20 = round(_descCilindro25/25) * 20;
    double _descCilindro35 = round(_descCilindro25/25) * 35;
    double _descCilindro40 = round(_descCilindro25/25) * 40;
    double _descCilindro45 = round(_descCilindro25/25) * 45;
    double _descCilindro60 = round(_descCilindro25/25) * 60;




    double _precioLts = 221.4;
    double _precioKgs = _precioLts * 11.67;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
/*************************************************************************************************
 * * Facturacion Radio Buttons
 */
        Q10 = Q40 = Q60 = Q20 = Q25 = Q35 = Q45 = Q100 = Qlts = Qkgs = 0;

        m_i20 = (RadioButton) findViewById(R.id.i20); m_i25 = (RadioButton) findViewById(R.id.i25);
        m_i35 = (RadioButton) findViewById(R.id.i35); m_i45 = (RadioButton) findViewById(R.id.i45);
        m_i100= (RadioButton) findViewById(R.id.i100); m_i60 = (RadioButton) findViewById(R.id.i60);
        m_i10 = (RadioButton) findViewById(R.id.i10); m_i40 = (RadioButton) findViewById(R.id.i40);
        m_lts = (RadioButton) findViewById(R.id.ilts);m_kgs = (RadioButton) findViewById(R.id.ikgs);

        m_add = (Button) findViewById(R.id.btn_add); m_substract = (Button) findViewById(R.id.btn_substract);
        mTextView = (EditText) findViewById(R.id.txtWebsite);
        mTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEND) { //IME_ACTION_DONE para cerrar editor
                    //m_i25.setText("25 lbs x "+mTextView.getText().toString()+"  Unids.");
                    switch (_SelectedOption ){
                        case 0:
                            Q25 = Integer.valueOf(mTextView.getText().toString());
                            m_i25.setText("25 lbs x "+mTextView.getText().toString()+"  Unids.");
                            break;
                        case 1:
                            Q20= Integer.valueOf(mTextView.getText().toString());
                            m_i20.setText("20 lbs x "+mTextView.getText().toString()+"  Unids.");
                            break;

                        case 2:
                            Q35= Integer.valueOf(mTextView.getText().toString());
                            m_i35.setText("35 lbs x "+mTextView.getText().toString()+"  Unids.");
                            break;
                        case 3:
                            Q45= Integer.valueOf(mTextView.getText().toString());
                            m_i45.setText("45 lbs x "+mTextView.getText().toString()+"  Unids.");
                            break;
                        case 4:
                            Q100= Integer.valueOf(mTextView.getText().toString());
                            m_i100.setText("100 lbs x "+mTextView.getText().toString()+"  Unids.");
                            break;
                        case 5:
                            Qlts= Integer.valueOf(mTextView.getText().toString());
                            m_lts.setText("Granel lts x "+mTextView.getText().toString());
                            break;
                        case 6:
                            Qkgs= Integer.valueOf(mTextView.getText().toString());
                            m_kgs.setText("Granel kgs x "+mTextView.getText().toString());
                            break;
                        case 7:
                            Q10 = Integer.valueOf(mTextView.getText().toString());
                            m_i10.setText("10 lbs x "+mTextView.getText().toString() +"  Unids.");
                            break;
                        case 8:
                            Q40 = Integer.valueOf(mTextView.getText().toString());
                            m_i40.setText("40 lbs x "+mTextView.getText().toString() +"  Unids.");
                            break;
                        case 9:
                            Q60 = Integer.valueOf(mTextView.getText().toString());
                            m_i60.setText("60 lbs x "+mTextView.getText().toString() +"  Unids.");
                            break;
                        default:
                            break;
                    }
                    //progressButton.performClick();
                    return true;
                }

                return false;
            }
        });

        m_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    myUserName = (EditText)findViewById(R.id.txtUserName); //Boton menos
        m_substract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        m_i10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 7;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(true);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_i40.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 8;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(true);
                m_i60.setChecked(false);
            }
        });
        m_i60.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 9;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(true);
            }
        });

        m_i20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 1;
                m_i20.setChecked(true);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_i25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 0;
                m_i20.setChecked(false);m_i25.setChecked(true);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_i35.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 2;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(true);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_i45.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 3;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(true);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_i100.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 4;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(true);m_lts.setChecked(false);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_lts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 5;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(true);
                m_kgs.setChecked(false);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });
        m_kgs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 6;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(true);m_i10.setChecked(false);m_i40.setChecked(false);
                m_i60.setChecked(false);
            }
        });






/*************************************************************************************************
 * * Bluetooht
 */
        mRequestUpdatesButton = (Button) findViewById(R.id.request_updates_button);
        mRemoveUpdatesButton = (Button) findViewById(R.id.remove_updates_button);
        mLocationUpdatesResultView = (TextView) findViewById(R.id.location_updates_result);
        mRequestButton = (Button) findViewById(R.id.btnsync);

        // Check if the user revoked runtime permissions.
        if (!checkPermissions()) {
            requestPermissions();
        }

        buildGoogleApiClient();





            sendButton = (Button) findViewById(R.id.send);
            //Button openButton = (Button) findViewById(R.id.btnsync);


            // send data typed by the user to be printed
            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    _do=true;
                                    try {
                                        try {
                                            sendData();

                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    } catch(Exception e) {


                                        e.printStackTrace();
                                    }

                                    //saveNameToServer("0,"+editTextName.getText().toString()+","+LAT+","+LONG+","+_supervisor);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    _do=false;
                                    break;
                            }
                        }
                    };
                    calcularFactura();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Desglose de factura: " + "\n" + fac_detail + "\n Desglose Descuento" + desc_detail )
                            .setPositiveButton("Imprimir", dialogClickListener)
                            .setNegativeButton("Cancelar", dialogClickListener).show();
                    if(_do){


                    }
                }
            });
            /*
            openButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        findBT();
                        openBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });*/








    }

    /*************************************************************************************************************/
    //Bluetooth Functions
    // this will find a bluetooth printer device
    void findBT() {
        //myLabel.setText("Buscando....");
        sendButton.setText("Buscando");
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                sendButton.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Ask for user permission if bluetooth is not on
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {


                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("PTP-III")) {
                        mmDevice = device;
                        break;

                    }
                }
            }

            //myLabel.setText("Bluetooth device found.");

        }catch(Exception e){
            sendButton.setText(e.getMessage());
            //sendButton.setText("Imprimir");
            e.printStackTrace();
        }
    }
    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

             sendButton.setText("Imprimir");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * after opening a connection to bluetooth printer device,
 * we have to listen and check if a data were sent to be printed.
 */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    String fac_detail,desc_detail;
    boolean _facok = true;

    void calcularFactura(){
        int codigocliente = Integer.parseInt(myUserName.getText().toString());
        _codigocliente = myUserName.getText().toString();
        _client = _razonDB[codigocliente];
        if(_descuentoDB[codigocliente] != 0){
            _desc = "y";
            if(_descuentoDB[codigocliente] == -1) {
                if((Q25 + (Q100 * 4)) < 10)
                    _descCilindro25 = 1975;
                if((Q25 + (Q100 * 4)) > 9 && (Q25 + (Q100 * 4)) < 21)
                    _descCilindro25 = 1754;
                if((Q25 + (Q100 * 4)) > 19 && (Q25 + (Q100 * 4)) < 31)
                    _descCilindro25 = 1858;
                if((Q25 + (Q100 * 4)) >= 30 && (Q25 + (Q100 * 4)) < 41)
                    _descCilindro25 = 1898;
                if((Q25 + (Q100 * 4)) >= 40 && (Q25 + (Q100 * 4)) < 51)
                    _descCilindro25 = 1908;
                if((Q25 + (Q100 * 4)) >= 50 && (Q25 + (Q100 * 4)) < 61)
                    _descCilindro25 = 1923;
                if((Q25 + (Q100 * 4)) >= 60)
                    _descCilindro25 = 1933;

            }else {

                _descCilindro25 = Integer.parseInt(_descuentoDB[codigocliente].toString());
                _descCilindro100 = _descCilindro100 * 4;
            }
        }else{
            _desc = "n";
        }
        if(_creditDB[codigocliente] != 0){
            _credit = "y";
        }else{
            _credit = "n";
        }



        float _fd25 = (float)(Q25 * _descCilindro25);
        float _fd100 = (float)(Q100 * _descCilindro100);
        float _fd10 = (float)(Q10 * _descCilindro10);
        float _fd20 = (float)(Q20 * _descCilindro20);
        float _fd35 = (float)(Q35 * _descCilindro35);
        float _fd40 = (float)(Q40 * _descCilindro40);
        float _fd45 = (float)(Q45 * _descCilindro45);
        float _fd60 = (float)(Q60 * _descCilindro60);


        float _f =  (float)(Q25 * _precioCilindro25);float _f0 = (float)(Q20 * _precioCilindro20);float _f2 = (float)(Q35 * _precioCilindro35);
        float _f3 = (float)(Q45 * _precioCilindro45);float _f4 = (float)(Q100 * _precioCilindro100);float _f5 = (float)(Qlts * _precioLts);
        float _f6 = (float)(Qkgs * _precioKgs); float _f7 = (float)(Q10 * _precioCilindro10); float _f8 = (float)(Q40 * _precioCilindro40); float _f9 = (float)(Q60 * _precioCilindro60);



        fac_detail = "";
        desc_detail = "";
        _Quantities = String.valueOf(Q20)+","+String.valueOf(Q25)+","+String.valueOf(Q35)+","+String.valueOf(Q45)+","+String.valueOf(Q100)+","+String.valueOf(Qlts)+","+String.valueOf(Qkgs)+","+String.valueOf(Q10)+","+String.valueOf(Q40)+","+String.valueOf(Q60);
        _totalfac = _f+_f0+_f2+_f3+_f4+_f5+_f6+_f7+_f8+_f9;

        if (Q10 > 0){
            fac_detail += "    "+String.valueOf(Q10)+ "    "+ String.valueOf(_precioCilindro20)  +"       Cilndro 10   "+ String.format("%.2f", _f7);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q10)+ "    "+ String.valueOf(_descCilindro10)  +"       10 Lbs   "+  String.format("%.2f", _fd10);
                desc_detail += "\n";
            }
        }

        if (Q20 > 0){
            fac_detail += "    "+String.valueOf(Q20)+ "    "+ String.valueOf(_precioCilindro20)  +"       Cilndro 20   "+ String.format("%.2f", _f0);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q20)+ "    "+ String.valueOf(_descCilindro20)  +"       20 Lbs   "+  String.format("%.2f", _fd20);
                desc_detail += "\n";
            }
        }
        if (Q25 > 0){
            fac_detail += "    "+String.valueOf(Q25)+ "    "+ String.valueOf(_precioCilindro25)  +"       Cilndro 25   "+  String.format("%.2f", _f);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q25)+ "    "+ String.valueOf(_descCilindro25)  +"       25 Lbs   "+  String.format("%.2f", _fd25);
                desc_detail += "\n";
            }
        }
        if (Q35 > 0){
            fac_detail += "    "+String.valueOf(Q35)+ "    "+ String.valueOf(_precioCilindro35)  +"       Cilndro 35   "+ String.format("%.2f", _f2);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q35)+ "    "+ String.valueOf(_descCilindro35)  +"       35 Lbs   "+  String.format("%.2f", _fd35);
                desc_detail += "\n";
            }
        }

        if (Q40 > 0){
            fac_detail += "    "+String.valueOf(Q40)+ "    "+ String.valueOf(_precioCilindro40)  +"       Cilndro 40   "+ String.format("%.2f", _f8);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q40)+ "    "+ String.valueOf(_descCilindro40)  +"       40 Lbs   "+  String.format("%.2f", _fd40);
                desc_detail += "\n";
            }

        }
        if (Q45 > 0){
            fac_detail += "    "+String.valueOf(Q45)+ "    "+ String.valueOf(_precioCilindro45)  +"       Cilndro 45   "+ String.format("%.2f", _f3);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q45)+ "    "+ String.valueOf(_descCilindro45)  +"       20 Lbs   "+  String.format("%.2f", _fd45);
                desc_detail += "\n";
            }
        }
        if (Q60 > 0){
            fac_detail += "    "+String.valueOf(Q60)+ "    "+ String.valueOf(_precioCilindro60)  +"       Cilndro 60   "+ String.format("%.2f", _f9);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q60)+ "    "+ String.valueOf(_descCilindro60)  +"       60 Lbs   "+  String.format("%.2f", _fd60);
                desc_detail += "\n";
            }

        }
        if (Q100 > 0){
            fac_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_precioCilindro100)  +"       Cilndro 100   "+ String.format("%.2f", _f4);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_descCilindro100)  +"      100 Lbs   "+  String.format("%.2f", _fd100);
                desc_detail += "\n";
            }
        }
        if (Qlts > 0){
            fac_detail += "    "+String.valueOf(Qlts)+ "  "+ String.format("%.2f", _precioLts)  +"     Granel Lts   "+ String.format("%.2f", _f5
            );
            fac_detail += "\n";
        }
        if (Qkgs > 0){
            fac_detail += "    "+String.valueOf(Qkgs)+ "  "+ String.format("%.2f", _precioKgs)  +"     Granel Kgs   "+ String.format("%.2f", _f6);
            fac_detail += "\n";
        }

    }
    // this will send text data to be printed by the bluetooth printer


    void sendData() throws IOException {
        //No eliminar tal vez sirva para los litros
        /*
        mTextView = (TextView) findViewById(R.id.txtWebsite);
        int Result_25 = (Integer.parseInt(mTextView.getText().toString()) * 5799);
        int Result_lts = (Integer.parseInt(mTextView.getText().toString()) * 222);
        */

        int codigocliente = Integer.parseInt(myUserName.getText().toString());
         _codigocliente = myUserName.getText().toString();
        _client = _razonDB[codigocliente];
        if(_descuentoDB[codigocliente] != 0){
            _desc = "y";
            if(_descuentoDB[codigocliente] == -1) {
                if((Q25 + (Q100 * 4)) < 10)
                    _descCilindro25 = 1975;
                if((Q25 + (Q100 * 4)) > 9 && (Q25 + (Q100 * 4)) < 21)
                    _descCilindro25 = 1754;
                if((Q25 + (Q100 * 4)) > 19 && (Q25 + (Q100 * 4)) < 31)
                    _descCilindro25 = 1858;
                if((Q25 + (Q100 * 4)) >= 30 && (Q25 + (Q100 * 4)) < 41)
                    _descCilindro25 = 1898;
                if((Q25 + (Q100 * 4)) >= 40 && (Q25 + (Q100 * 4)) < 51)
                    _descCilindro25 = 1908;
                if((Q25 + (Q100 * 4)) >= 50 && (Q25 + (Q100 * 4)) < 61)
                    _descCilindro25 = 1923;
                if((Q25 + (Q100 * 4)) >= 60)
                    _descCilindro25 = 1933;

            }else {

                _descCilindro25 = Integer.parseInt(_descuentoDB[codigocliente].toString());
                _descCilindro100 = _descCilindro100 * 4;
            }
        }else{
            _desc = "n";
        }
        if(_creditDB[codigocliente] != 0){
            _credit = "y";
        }else{
            _credit = "n";
        }



        float _fd25 = (float)(Q25 * _descCilindro25);
        float _fd100 = (float)(Q100 * _descCilindro100);
        float _fd10 = (float)(Q10 * _descCilindro10);
        float _fd20 = (float)(Q20 * _descCilindro20);
        float _fd35 = (float)(Q35 * _descCilindro35);
        float _fd40 = (float)(Q40 * _descCilindro40);
        float _fd45 = (float)(Q45 * _descCilindro45);
        float _fd60 = (float)(Q60 * _descCilindro60);


        float _f =  (float)(Q25 * _precioCilindro25);float _f0 = (float)(Q20 * _precioCilindro20);float _f2 = (float)(Q35 * _precioCilindro35);
        float _f3 = (float)(Q45 * _precioCilindro45);float _f4 = (float)(Q100 * _precioCilindro100);float _f5 = (float)(Qlts * _precioLts);
        float _f6 = (float)(Qkgs * _precioKgs); float _f7 = (float)(Q10 * _precioCilindro10); float _f8 = (float)(Q40 * _precioCilindro40); float _f9 = (float)(Q60 * _precioCilindro60);



        fac_detail = "";
        desc_detail = "";
        _Quantities = String.valueOf(Q20)+","+String.valueOf(Q25)+","+String.valueOf(Q35)+","+String.valueOf(Q45)+","+String.valueOf(Q100)+","+String.valueOf(Qlts)+","+String.valueOf(Qkgs)+","+String.valueOf(Q10)+","+String.valueOf(Q40)+","+String.valueOf(Q60);
        _totalfac = _f+_f0+_f2+_f3+_f4+_f5+_f6+_f7+_f8+_f9;

        if (Q10 > 0){
            fac_detail += "    "+String.valueOf(Q10)+ "    "+ String.valueOf(_precioCilindro20)  +"       Cilndro 10   "+ String.format("%.2f", _f7);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q10)+ "    "+ String.valueOf(_descCilindro10)  +"       10 Lbs   "+  String.format("%.2f", _fd10);
                desc_detail += "\n";
            }
        }

        if (Q20 > 0){
            fac_detail += "    "+String.valueOf(Q20)+ "    "+ String.valueOf(_precioCilindro20)  +"       Cilndro 20   "+ String.format("%.2f", _f0);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q20)+ "    "+ String.valueOf(_descCilindro20)  +"       20 Lbs   "+  String.format("%.2f", _fd20);
                desc_detail += "\n";
            }
        }
        if (Q25 > 0){
            fac_detail += "    "+String.valueOf(Q25)+ "    "+ String.valueOf(_precioCilindro25)  +"       Cilndro 25   "+  String.format("%.2f", _f);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q25)+ "    "+ String.valueOf(_descCilindro25)  +"       25 Lbs   "+  String.format("%.2f", _fd25);
                desc_detail += "\n";
            }
        }
        if (Q35 > 0){
            fac_detail += "    "+String.valueOf(Q35)+ "    "+ String.valueOf(_precioCilindro35)  +"       Cilndro 35   "+ String.format("%.2f", _f2);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q35)+ "    "+ String.valueOf(_descCilindro35)  +"       35 Lbs   "+  String.format("%.2f", _fd35);
                desc_detail += "\n";
            }
        }

        if (Q40 > 0){
            fac_detail += "    "+String.valueOf(Q40)+ "    "+ String.valueOf(_precioCilindro40)  +"       Cilndro 40   "+ String.format("%.2f", _f8);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q40)+ "    "+ String.valueOf(_descCilindro40)  +"       40 Lbs   "+  String.format("%.2f", _fd40);
                desc_detail += "\n";
            }

        }
        if (Q45 > 0){
            fac_detail += "    "+String.valueOf(Q45)+ "    "+ String.valueOf(_precioCilindro45)  +"       Cilndro 45   "+ String.format("%.2f", _f3);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q45)+ "    "+ String.valueOf(_descCilindro45)  +"       20 Lbs   "+  String.format("%.2f", _fd45);
                desc_detail += "\n";
            }
        }
        if (Q60 > 0){
            fac_detail += "    "+String.valueOf(Q60)+ "    "+ String.valueOf(_precioCilindro60)  +"       Cilndro 60   "+ String.format("%.2f", _f9);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q60)+ "    "+ String.valueOf(_descCilindro60)  +"       60 Lbs   "+  String.format("%.2f", _fd60);
                desc_detail += "\n";
            }

        }
        if (Q100 > 0){
            fac_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_precioCilindro100)  +"       Cilndro 100   "+ String.format("%.2f", _f4);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_descCilindro100)  +"      100 Lbs   "+  String.format("%.2f", _fd100);
                desc_detail += "\n";
            }
        }
        if (Qlts > 0){
            fac_detail += "    "+String.valueOf(Qlts)+ "  "+ String.format("%.2f", _precioLts)  +"     Granel Lts   "+ String.format("%.2f", _f5
            );
            fac_detail += "\n";
        }
        if (Qkgs > 0){
            fac_detail += "    "+String.valueOf(Qkgs)+ "  "+ String.format("%.2f", _precioKgs)  +"     Granel Kgs   "+ String.format("%.2f", _f6);
            fac_detail += "\n";
        }

        _facok = false;
        _date = DateFormat.getDateTimeInstance().format(new Date());
        try {

            // the text typed by the user
            String msg = "\n";// myTextbox.getText().toString();
            msg += "           Gas Tomza de Costa Rica S.A.         ";
            msg += "\n";
            msg += "              Ced. Jur. 3-101-349880            ";
            msg += "\n";
            msg += "          La lima de Cartago, Costa Rica        ";
            msg += "\n";
            msg += "              Telefono:(506)2201-6000           ";
            msg += "\n";
            msg += "                www.gastomza.co.cr            ";
            msg += "\n";
            msg += "            servicioalcliente.cr@tomza.com      ";
            msg += "\n";

            msg += "\n";
            msg += "   Factura: " +String.valueOf(_facnum);
            msg += "\n";
            msg += "   Rutero: "+_ruta ;
            msg += "\n";
            msg += "   Fecha:"+_date;
            msg += "\n";
            if(_credit == "y")
                msg += "   CREDITO ";
            else
                msg += "   CONTADO ";
            msg += "\n";
            msg += "   Razon Social:"+ _client;
            msg += "\n";
            msg += "   Direccion:  " + _direccionDB[codigocliente];
            msg += "\n";
            msg += "   Telefono:" +_telefonoDB[codigocliente] + "Codigo Cliente:" +  _codigocliente;
            msg += "\n";
            msg += "\n";
            msg += "   CANT.  P.UNITARIO    PRODUCTO    TOTAL    ";
            msg += "\n";
            msg += "   -------------------------------------------- ";
            msg += "\n";
            /*if(_SelectedOption == 0)
                msg += "     "+ mTextView.getText().toString()  +"              Cilind. 25 lbs      "+ String.valueOf(Result_25) +"    ";
            else
                msg += "     "+ mTextView.getText().toString()  +"                 GLP lts          "+ String.valueOf(Result_lts) +"    ";
*/
            if(fac_detail != "" )
                msg += fac_detail;
            //msg += "\n";
            //msg += "     275                Granel GLP      61050   ";
            msg += "\n";
            msg += "   -------------------------------------------- ";
            msg += "\n";
            msg += "    TOTAL(CRC):               "+String.format("%.2f", _totalfac)+"   ";

            msg += "\n";

            msg += "     Sello de garantia en www.tomza.co.cr    ";
            msg += "     AUTORIZADO MEDIANTE EL OFICIO NUMERO    ";
            msg += "\n";
            msg += "    03-0001-2003 del 6 de feb de D.G.T.D   ";
            msg += "\n";

            if(_desc == "y"){//if(_credit == "y" || _desc == "y"){
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "           Gas Tomza de Costa Rica S.A.         ";
                msg += "\n";
                msg += "              Ced. Jur. 3-101-349880            ";
                msg += "\n";
                msg += "          La lima de Cartago, Costa Rica        ";
                msg += "\n";
                msg += "              Telefono:(506)2201-6000           ";
                msg += "\n";
                msg += "                www.gastomza.co.cr            ";
                msg += "\n";
                msg += "            servicioalcliente.cr@tomza.com      ";
                msg += "\n";
                msg += "                    DESCUENTO                   ";
                msg += "\n";
                msg += "   Fecha:"+_date.toString();
                msg += "\n";
                msg += "   Codigo de cliente:"+  _codigocliente;
                msg += "\n";
                msg += "   Cliente: " + _client.toString();
                msg += "\n";
                msg += "   Asignado a factura No: " + String.valueOf(_facnum);
                msg += "\n";
                msg += "   CANT.  DESC.    PRODUCTO    TOTAL    ";
                msg += "\n";
                msg += "   -------------------------------------------- ";
                msg += desc_detail;
                msg += "\n";
                msg += "    DESCUENTO(CRC):           "+String.format("%.2f", _fd25+_fd100+_fd10+_fd20+_fd35+_fd40+_fd45+_fd60)+"   ";
                msg += "\n";
                msg += "    TOTAL A PAGAR(CRC):       "+String.format("%.2f",_totalfac - _fd25+_fd100+_fd10+_fd20+_fd35+_fd40+_fd45+_fd60)+"   ";
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "\n";
                msg += "       Firma:_______________________    ";
                msg += "\n";




            }//end is _credit






            mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            myLabel.setText("Data sent."); _facok = true;


        } catch (Exception e) {

            e.printStackTrace();
        }
        //if(_facok)
        SendToServer(String.valueOf(_facnum),_client,_coords,_date,_Quantities,String.format("%.2f", _totalfac),_credit,_ruta);
        //ta dando error :(
        //db.addFactura(Integer.getInteger(_client),String.valueOf(_facnum),_date,Q20,Q25,Q35,Q45,Q100,Qlts,Qkgs);
        //SubirCierreDelDiaHTTP();
    }

    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void recieveSMS(String smsCode){
        //cODIGO de la central que solicita
        switch (smsCode){

            case "A0": //Solicitar facturas
                break;
            case "A1": //Solicitar GPS_S
                break;



            default:
                break;
        }

    }
    private void UploadFactura(String CodeFAC,String ClientCode,String Quantities,String Hora){
        String phoneNumber = "60159990";
        String smsBody = "21,"+CodeFAC+","+ClientCode+","+Quantities+","+Hora;


        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



// For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Factura enviada con exito", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Enviado a tomza.co.cr", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Reintentando...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

// Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);


        /****************************************************************/



    }
    private void UploadFacturas(String[] CodeFAC,String[] ClientCode,String[] Quantities,String[] Hora){
        String phoneNumber = "60159990";
        String smsBody = "21,"+CodeFAC[0]+","+ClientCode[0]+","+Quantities[0]+","+Hora[0]
                        +","+CodeFAC[1]+","+ClientCode[1]+","+Quantities[1]+","+Hora[1]
                        +","+CodeFAC[2]+","+ClientCode[2]+","+Quantities[2]+","+Hora[2]
                        +","+CodeFAC[3]+","+ClientCode[3]+","+Quantities[3]+","+Hora[3];


        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



// For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Factura enviada con exito", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Enviado a tomza.co.cr", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Reintentando...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

// Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);


        /****************************************************************/

    }
    private void UploadGPS(String hour, String Latitud , String Longitud) {
        String phoneNumber = "60159990";
        String smsBody = "22,"+hour+","+Latitud+","+Longitud;


        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



// For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Factura Procesada", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Enviado a tomza.co.cr", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Reintentando...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

// Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);


        /****************************************************************/



    }
    private void UploadGPS_S(String[] hour, String[] Latitud , String[] Longitud){
        String phoneNumber = "60159990";
        String smsBody = "23,"+hour[0]+","+Latitud[0]+","+Longitud[0]
                +","+hour[1]+","+Latitud[1]+","+Longitud[1]
                +","+hour[2]+","+Latitud[2]+","+Longitud[2]
                +","+hour[3]+","+Latitud[3]+","+Longitud[3]
                +","+hour[4]+","+Latitud[4]+","+Longitud[4]
                +","+hour[5]+","+Latitud[5]+","+Longitud[5]
                +","+hour[6]+","+Latitud[6]+","+Longitud[6];


        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



// For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Factura Procesada", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Enviado a tomza.co.cr", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Reintentando...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

// Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);


        /****************************************************************/


    }
    private void CargarPreciosClientesySistemaHTTP(){
        //Busca en el servidor cambios en el precio, rutas, clientes (altas bajas) y demas confirguraciones

    }
    private void SubirCierreDelDiaHTTP(){
        //Sube lsitado de GPS, nuevos clientes, Fotos de los nuevos clientes, facturas y automaticamente busca por carga de precios


        String url ="http://192.168.2.201/tomza/test.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: " + response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



        CargarPreciosClientesySistemaHTTP();

    }


    private void RecieveToken(String Token){
        switch (Token){
            case "F0": //Anular Factura
                break;
            case "F1": //Reimprimir Factura
                break;
            case "F2": //Autorizar otra factura a cliente (ver caso tiendas con el descuento en dos facturas)
                break;

            case "R0": //Cambiar ruta para facturar cliente
                break;
            case "CILZA":
                //cambiamos la pantalla para que pueda facturar venta de cilindros
                break;


            default:
                break;
        }

    }




    private void SendToServer(String CodeFAC, String Code,String Coordinates,String Date, String Quantities, String Total,String Iscredit,String Ruta){
        String phoneNumber = "60159990";
        String smsBody = "21,"+Coordinates+","+Code+","+CodeFAC+","+Date+","+Quantities+","+Total+","+Iscredit+","+Ruta;


        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



// For when the SMS has been sent
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Factura Procesada", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Enviado a tomza.co.cr", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Reintentando...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

// Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);


        /****************************************************************/
    }


    public void requestWebService(View view){



        SendToServer(String.valueOf(_facnum),_client,_coords,_date,_Quantities,String.format("%.2f", _totalfac),_credit,_ruta);



        String url ="http://192.168.2.201/tomza/test.php";



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: " + response.toString());
                        //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);




    }
    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        if(mmDevice == null) {
            try {
                findBT();
                openBT();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        updateButtonsState(Utils.getRequestingLocationUpdates(this));
        mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
    }

    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        // Note: apps running on "O" devices (regardless of targetSdkVersion) may receive updates
        // less frequently than this interval when the app is no longer in the foreground.
        mLocationRequest.setInterval(UPDATE_INTERVAL);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Sets the maximum time when batched location updates are delivered. Updates may be
        // delivered sooner than this interval.
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    /**
     * Builds {@link GoogleApiClient}, enabling automatic lifecycle management using
     * {@link GoogleApiClient.Builder#enableAutoManage(android.support.v4.app.FragmentActivity,
     * int, GoogleApiClient.OnConnectionFailedListener)}. I.e., GoogleApiClient connects in
     * {@link AppCompatActivity#onStart}, or if onStart() has already happened, it connects
     * immediately, and disconnects automatically in {@link AppCompatActivity#onStop}.
     */
    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
    }


    private PendingIntent getPendingIntent() {
        // Note: for apps targeting API level 25 ("Nougat") or lower, either
        // PendingIntent.getService() or PendingIntent.getBroadcast() may be used when requesting
        // location updates. For apps targeting API level O, only
        // PendingIntent.getBroadcast() should be used. This is due to the limits placed on services
        // started in the background in "O".

        // TODO(developer): uncomment to use PendingIntent.getService().
//        Intent intent = new Intent(this, LocationUpdatesIntentService.class);
//        intent.setAction(LocationUpdatesIntentService.ACTION_PROCESS_UPDATES);
//        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnectionSuspended(int i) {
        final String text = "Connection suspended";
        Log.w(TAG, text + ": Error code: " + i);
        showSnackbar("Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        final String text = "Exception while connecting to Google Play services";
        Log.w(TAG, text + ": " + connectionResult.getErrorMessage());
        showSnackbar(text);
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {

        View container = findViewById(R.id.activity_main);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting
                // GoogleApiClient.
                buildGoogleApiClient();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Utils.KEY_LOCATION_UPDATES_RESULT)) {
            mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
        } else if (s.equals(Utils.KEY_LOCATION_UPDATES_REQUESTED)) {
            updateButtonsState(Utils.getRequestingLocationUpdates(this));
        }
    }

    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    public void requestLocationUpdates(View view) {
        try {
            Log.i(TAG, "Starting location updates");
            Utils.setRequestingLocationUpdates(this, true);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            Utils.setRequestingLocationUpdates(this, false);
            e.printStackTrace();
        }
    }

    /**
     * Handles the Remove Updates button, and requests removal of location updates.
     */
    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                getPendingIntent());
    }

    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private void updateButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            mRequestUpdatesButton.setEnabled(false);
            mRemoveUpdatesButton.setEnabled(true);
        } else {
            mRequestUpdatesButton.setEnabled(true);
            mRemoveUpdatesButton.setEnabled(false);
        }
    }
}

