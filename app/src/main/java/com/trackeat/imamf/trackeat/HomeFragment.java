package com.trackeat.imamf.trackeat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class HomeFragment extends android.support.v4.app.Fragment {
    private Button bt_scan;
    private CameraHelper camHelper;
    private ConstraintLayout layout_pBar;
    private View viewDimScreen;
    private AVLoadingIndicatorView aviLoadingIndicatorView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_home, null);

        bt_scan = inflate.findViewById(R.id.bt_scan);

//        layout_pBar = inflate.findViewById(R.id.layout_pBar);
//        layout_pBar.setVisibility(View.GONE);

        //initializin IBM Visual Recognition camera helper
        camHelper = new CameraHelper(getActivity());

        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camHelper.dispatchTakePictureIntent();
            }
        });

        viewDimScreen = inflate.findViewById(R.id.viewDimScreen);
        viewDimScreen.setVisibility(View.GONE);
        aviLoadingIndicatorView = inflate.findViewById(R.id.aviLoadingIndicatorView);

        return inflate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //organic scan result
            if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
                //get image path after captured
                final File photoFile = camHelper.getFile(resultCode);
                imgRecogntion(photoFile);
            }
        }
    }

    //Object detection using IBM Watson Visual Recognition
    public void imgRecogntion(final File file) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        layout_pBar.setVisibility(View.VISIBLE);
                        startLoadingIndicator();
                    }
                });
                IamOptions options = new IamOptions.Builder()
                        .apiKey(getResources().getString(R.string.api_key))
                        .build();

                VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", options);

                ClassifyOptions classifyOptions = null;
                try {
                    classifyOptions = new ClassifyOptions.Builder()
                            .imagesFile(file)
                            .classifierIds(Arrays.asList("DefaultCustomModel_1303267125"))
                            .build();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();

                Log.d("nasgor", result.toString());

//                productName = result.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getClassName();
//                if (productName.equalsIgnoreCase("Apel")){
//                    imgSrc = "https://doktersehat.com/wp-content/uploads/2014/05/apel.jpg";
//                }
//                else if (productName.equalsIgnoreCase("Kol")){
//                    imgSrc = "https://blue.kumparan.com/kumpar/image/upload/fl_progressive,fl_lossy,c_fill,q_auto:best,w_640/v1521360707/raxkgzgrt44iiebd0krw.jpg";
//                }


                if (result.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getClassName() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stopLoadingIndicator();
                        }
                    });
                    Intent i = new Intent(getActivity(), FoodDetailActivity.class);
                    i.putExtra("productName", result.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getClassName());
                    //i.putExtra("imgSrc", imgSrc);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Mohon maaf makanan belum dikenali", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startLoadingIndicator() {
        viewDimScreen.setVisibility(View.VISIBLE);
        aviLoadingIndicatorView.smoothToShow();
    }

    private void stopLoadingIndicator() {
        viewDimScreen.setVisibility(View.GONE);
        aviLoadingIndicatorView.smoothToHide();
    }
}
