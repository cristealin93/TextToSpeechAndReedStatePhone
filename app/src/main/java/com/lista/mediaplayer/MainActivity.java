package com.lista.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech ttsEngine;
    private Button buttonSpeak;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSpeak = (Button) findViewById(R.id.button1);
        editText = (EditText) findViewById(R.id.editText1);


        // TextToSpeech
        ttsEngine = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int lang = ttsEngine.setLanguage(Locale.ENGLISH);
                }
            }
        });

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speech = ttsEngine.speak(editText.getText().toString(), ttsEngine.QUEUE_FLUSH, null);
            }
        });
// Function to check and request permission

            // Checking if permission is not granted
            if (ContextCompat.checkSelfPermission(MainActivity.this,  Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_PHONE_STATE }, 1);
            }
            else {
                Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            }



        //Get the instance of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String IMEINumber = tm.getDeviceSoftwareVersion();
        String subscriberID=tm.getLine1Number();
        String SIMSerialNumber=tm.getSimOperatorName();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();

        //Get the phone type
        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }

        //getting information if phone is in roaming
        boolean isRoaming=tm.isNetworkRoaming();

        String info="Phone Details:\n";
        info+="\n IMEI Number:"+IMEINumber;
        info+="\n SubscriberID:"+subscriberID;
        info+="\n Sim Serial Number:"+SIMSerialNumber;
        info+="\n Network Country ISO:"+networkCountryISO;
        info+="\n SIM Country ISO:"+SIMCountryISO;
        info+="\n Software Version:"+softwareVersion;
        info+="\n Voice Mail Number:"+voiceMailNumber;
        info+="\n Phone Network Type:"+strphoneType;
        info+="\n In Roaming? :"+isRoaming;

        editText.setText(info);//displaying the information in the textView
    }
}





