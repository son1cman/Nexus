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
import 	android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.text.DateFormat;
import java.util.Date;


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

    /********************************************************************************************/
    //Bluetooth variables
    // will show the statuses like bluetooth open, close or data sent
    TextView myLabel;
    Button sendButton;
    // will enable user to enter any text to be printed
    EditText myTextbox;
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
    private TextView mTextView;
    /*********************************************************
     * Facturacion
     */
    RadioButton m_i20, m_i25, m_i35,m_i45,m_i100,m_lts,m_kgs;
    Button m_add,m_substract;
    String _client = "Libreria Hellen";
    String _ruta = "60";
    String _coords = "9.872890,-83.944350";
    String _date;
    double _totalfac = 0;
    String _Quantities;
    int _facnum = 10001;
    String _credit = "c";
    String _desc = "y";

    int Q20,Q25,Q35,Q45,Q100,Qlts,Qkgs;
    double _precioCilindro20 = 4000;
    double _precioCilindro25 = 5799;
    double _precioCilindro35 = 6000;
    double _precioCilindro45 = 13000;
    double _descCilindro25 = 240;
    double _descCilindro100 = 960;
    double _precioCilindro100 = _precioCilindro25 * 4;
    double _precioLts = 221.4;
    double _precioKgs = _precioLts * 11.67;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*************************************************************************************************
 * * Facturacion Radio Buttons
 */
        Q20 = Q25 = Q35 = Q45 = Q100 = Qlts = Qkgs = 0;

        m_i20 = (RadioButton) findViewById(R.id.i20);m_i25 = (RadioButton) findViewById(R.id.i25);
        m_i35 = (RadioButton) findViewById(R.id.i35);m_i45 = (RadioButton) findViewById(R.id.i45);
        m_i100 = (RadioButton) findViewById(R.id.i100);
        m_lts = (RadioButton) findViewById(R.id.ilts);m_kgs = (RadioButton) findViewById(R.id.ikgs);

        m_add = (Button) findViewById(R.id.btn_add); m_substract = (Button) findViewById(R.id.btn_substract);

        m_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (_SelectedOption ){
                    case 0:
                        Q25++;
                        m_i25.setText("25 lbs x "+Q25+"  Unids.");
                        break;
                    case 1:
                        Q20++;
                        m_i20.setText("20 lbs x "+Q20+"  Unids.");
                        break;

                    case 2:
                        Q35++;
                        m_i35.setText("35 lbs x "+Q35+"  Unids.");
                        break;
                    case 3:
                        Q45++;
                        m_i45.setText("45 lbs x "+Q45+"  Unids.");
                        break;
                    case 4:
                        Q100++;
                        m_i100.setText("100 lbs x "+Q100+"  Unids.");
                        break;
                    case 5:
                        Qlts++;
                        m_lts.setText("Granel lts x "+Qlts);
                        break;
                    case 6:
                        Qkgs++;
                        m_kgs.setText("Granel kgs x "+Qkgs);
                        break;
                    default:
                        break;
                }

            }
        });

        m_substract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (_SelectedOption ){
                    case 0:
                        Q25--;
                        if(Q25 < 0) Q25 = 0;
                        m_i25.setText("25 lbs x "+Q25+"  Unids.");
                        break;
                    case 1:
                        Q20--;
                        if(Q20 < 0) Q20 = 0;
                        m_i20.setText("20 lbs x "+Q20+"  Unids.");
                        break;
                    case 2:
                        Q35--;
                        if(Q35 < 0) Q35 = 0;
                        m_i35.setText("35 lbs x "+Q35+"  Unids.");
                        break;
                    case 3:
                        Q45--;
                        if(Q45 < 0) Q45 = 0;
                        m_i45.setText("45 lbs x "+Q45+"  Unids.");
                        break;
                    case 4:
                        Q100--;
                        if(Q100 < 0) Q100 = 0;
                        m_i100.setText("100 lbs x "+Q100+"  Unids.");
                        break;
                    case 5:
                        Qlts--;
                        if(Qlts < 0) Qlts = 0;
                        m_lts.setText("Granel lts x "+Qlts);
                        break;
                    case 6:
                        Qkgs--;
                        if(Qkgs < 0) Qkgs = 0;
                        m_kgs.setText("Granel kgs x "+Qkgs);
                        break;
                    default:
                        break;
                }
            }
        });


        m_i20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 1;
                m_i20.setChecked(true);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);
            }
        });
        m_i25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 0;
                m_i20.setChecked(false);m_i25.setChecked(true);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);
            }
        });
        m_i35.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 2;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(true);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);
            }
        });
        m_i45.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 3;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(true);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(false);
            }
        });
        m_i100.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 4;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(true);m_lts.setChecked(false);
                m_kgs.setChecked(false);
            }
        });
        m_lts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 5;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(true);
                m_kgs.setChecked(false);
            }
        });
        m_kgs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _SelectedOption = 6;
                m_i20.setChecked(false);m_i25.setChecked(false);m_i35.setChecked(false);
                m_i45.setChecked(false);m_i100.setChecked(false);m_lts.setChecked(false);
                m_kgs.setChecked(true);
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




        try {
            sendButton = (Button) findViewById(R.id.send);
            //Button openButton = (Button) findViewById(R.id.btnsync);


            // send data typed by the user to be printed
            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
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




        } catch(Exception e) {


            e.printStackTrace();
        }



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

    private String _SelectedOptionStr = "";
    private int _SelectedOption = 0;

    String fac_detail,desc_detail;
    boolean _facok = true;
    // this will send text data to be printed by the bluetooth printer
    void sendData() throws IOException {
        //No eliminar tal vez sirva para los litros
        /*
        mTextView = (TextView) findViewById(R.id.txtWebsite);
        int Result_25 = (Integer.parseInt(mTextView.getText().toString()) * 5799);
        int Result_lts = (Integer.parseInt(mTextView.getText().toString()) * 222);
        */
        float _fd25 = (float)(Q25 * _descCilindro25);
        float _fd100 = (float)(Q100 * _descCilindro100);
        float _f =  (float)(Q25 * _precioCilindro25);float _f0 = (float)(Q20 * _precioCilindro20);float _f2 = (float)(Q35 * _precioCilindro35);
        float _f3 = (float)(Q45 * _precioCilindro45);float _f4 = (float)(Q100 * _precioCilindro100);float _f5 = (float)(Qlts * _precioLts);
        float _f6 = (float)(Qkgs * _precioKgs);
        fac_detail = "";
        desc_detail = "";
        _Quantities = String.valueOf(Q20)+","+String.valueOf(Q25)+","+String.valueOf(Q35)+","+String.valueOf(Q45)+","+String.valueOf(Q100)+","+String.valueOf(Qlts)+","+String.valueOf(Qkgs);
        _totalfac = _f+_f0+_f2+_f3+_f4+_f5+_f6;

        if (Q20 > 0){
            fac_detail += "    "+String.valueOf(Q20)+ "    "+ String.valueOf(_precioCilindro20)  +"       Cilndro 20   "+ String.format("%.2f", _f0);
            fac_detail += "\n";

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
        }
        if (Q45 > 0){
            fac_detail += "    "+String.valueOf(Q45)+ "    "+ String.valueOf(_precioCilindro45)  +"       Cilndro 45   "+ String.format("%.2f", _f3);
            fac_detail += "\n";
        }
        if (Q100 > 0){
            fac_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_precioCilindro100)  +"       Cilndro 100   "+ String.format("%.2f", _f4);
            fac_detail += "\n";
            if (_desc == "y"){
                desc_detail += "    "+String.valueOf(Q100)+ "    "+ String.valueOf(_descCilindro100)  +"      100 Lbs   "+  String.format("%.2f", _fd25);
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
            if(_credit == "c")
                msg += "   CONTADO ";
            else
                msg += "   CREDITO ";
            msg += "\n";
            msg += "   Razon Social:"+ _client;
            msg += "\n";
            msg += "   Direccion:  6 CALLE ORIENTE, CARTAGO         ";
            msg += "\n";
            msg += "   Telefono:  2605-6879  Codigo Cliente: C0125  ";
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

            if(_credit == "c" || _desc == "y"){
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
                msg += "                   NOTA DE CREDITO              ";
                msg += "\n";
                msg += "   Fecha:"+_date.toString();
                msg += "\n";
                msg += "   Codigo de cliente: C8907";
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
                msg += "    CREDITO(CRC):             "+String.format("%.2f", _fd25+_fd100)+"   ";
                msg += "\n";
                msg += "    TOTAL A PAGAR(CRC):       "+String.format("%.2f",_totalfac - (_fd25+_fd100))+"   ";
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
                        Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
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
                        mTextView.setText("Response is: " + response.toString());

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

