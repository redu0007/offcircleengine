package com.ditrans.offcircleengine;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ditrans.offcirclev9engine.ShakeRecognizer;

public class MainActivity extends AppCompatActivity implements ShakeRecognizer.OnShakeListener{

    private final String Tag = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ShakeRecognizer.create(this, this)) {
            float fSensibility = 3.0f;
            int iShakeNumber = 3;
            int iInterval = 200;
            ShakeRecognizer.updateConfiguration(fSensibility, iShakeNumber,iInterval);
            Log.e(Tag, "ShakeRecognizer created");
        } else {
            Log.e(Tag, "Error - ShakeRecognizer creation failed");
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if (ShakeRecognizer.start()) {
            Log.e(Tag, "ShakeRecognizer started");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void OnShake() {
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibe.vibrate(100);
        }
        new AlertDialog.Builder(MainActivity.this)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage("Shooken!")
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShakeRecognizer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShakeRecognizer.destroy();
    }
}
