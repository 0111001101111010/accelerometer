package com.example.accelerometer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class MyActivity extends Activity implements SensorEventListener {
    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;

    private SensorManager mSensorManager;
    public String[] name = {"x_move", "y_move", "z_move"};
    public String current = null; //movement
    public String last 	  = null; // movement
    public ArrayList<String> history = new ArrayList<String>();
    public String[] gestureKey = {"knock", "shakeX", "shakeY"};
    public String gesture = "initalized";// two movements = one gesture 
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tvX= (TextView)findViewById(R.id.x_axis);
        TextView tvY= (TextView)findViewById(R.id.y_axis);
        TextView tvZ= (TextView)findViewById(R.id.z_axis);
        TextView movement= (TextView)findViewById(R.id.editText1);
        ImageView iv = (ImageView)findViewById(R.id.image);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            tvX.setText("0.0");
            tvY.setText("0.0");
            tvZ.setText("0.0");
            movement.setText(gesture);//if nothing in the beginning then nothing
            mInitialized = true;
        } else {
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);
            if (deltaX < NOISE) deltaX = (float)0.0;
            if (deltaY < NOISE) deltaY = (float)0.0;
            if (deltaZ < NOISE) deltaZ = (float)0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            tvX.setText(Float.toString(deltaX));
            tvY.setText(Float.toString(deltaY));
            tvZ.setText(Float.toString(deltaZ));
            iv.setVisibility(View.VISIBLE);
            /**
             * Do movement calculation of current gesture
             * 
             */
            /*
            if ((deltaX >deltaY)){//||(mLastX >mLastZ)){
            	//this is a x-plane shake
            	current =gestureKey[1];
                movement.setText(current);
            }
            else if ((mLastY >mLastX)||(mLastY >mLastZ)){
            	//this is a y-plane shake
            	current =gestureKey[2];
            }
            else if ((mLastZ >mLastX)||(mLastZ >mLastY)){
            	//this is a knock 
            	current =gestureKey[0];
            }*/
            /***
             * The X,Y,Z
             * compare lastGesture to Current gesture if correct set 
             * public String[] gesture = {"knock", "shakeX", "shakeY"};
             */
            /*
            if (current ==last){
            	gesture = current;
            }*
            
            movement.setText(gesture);
            /** set the arrows**/
            if (deltaX > deltaY) {
                iv.setImageResource(R.drawable.shaker_fig_1);
                current = gestureKey[1];//   
                
            } else if (deltaY > deltaX) {
                iv.setImageResource(R.drawable.shaker_fig_2);
                current = gestureKey[2];//     
            } else {
                iv.setVisibility(View.INVISIBLE);
                current =gestureKey[0];//   
            }
            movement.setText(current);//   
            history.add(current) ;

            for (String motion : history){
                Log.i("Member name: ", motion);
            }
        }
    }
}
