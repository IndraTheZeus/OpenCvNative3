package com.example.opencvnative3;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = "MyTag";
    JavaCameraView javaCameraView;
    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS :
                    javaCameraView.enableView();
                    Log.d(TAG,"callback:OPENCv Loaded successfully ");
                    break;

                default :
                    Log.d(TAG,"callback:could not load opencv");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        javaCameraView = (JavaCameraView) findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(View.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        setContentView(R.layout.activity_main);
    }

    public void onResume(){
        Log.d(TAG,"in on resume");
        super.onResume();

        if(!OpenCVLoader.initDebug()){
            boolean success = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,mLoaderCallBack);

            if(success){
                Log.d(TAG,"async init succeeded");
            }else{
                Log.d(TAG,"async init failed");
            }
        }else{
            Log.d(TAG,"OpenCV Library found inside package.Using it!");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }
}
