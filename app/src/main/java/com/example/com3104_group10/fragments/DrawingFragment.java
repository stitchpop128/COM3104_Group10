package com.example.com3104_group10.fragments;

import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.com3104_group10.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import petrov.kristiyan.colorpicker.ColorPicker;

public class DrawingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DrawView paint;

    // creating objects of type button
    private ImageButton save, color, stroke, undo, eraser;

    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawingFragment newInstance(String param1, String param2) {
        DrawingFragment fragment = new DrawingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DrawingFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Joyce", "no permission");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            Log.d("Joyce", "asking for permission");
//            Snackbar.make(getView(), "Please CLICK SAVE BUTTON AGAIN to save your drawing", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View FView = inflater.inflate(R.layout.fragment_drawing, container, false);

        // getting the reference of the views from their ids
        paint = (DrawView) FView.findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) FView.findViewById(R.id.rangebar);
        undo = (ImageButton) FView.findViewById(R.id.btn_undo);
        save = (ImageButton) FView.findViewById(R.id.btn_save);
        eraser = (ImageButton) FView.findViewById(R.id.btn_eraser);
        color = (ImageButton) FView.findViewById(R.id.btn_color);
        stroke = (ImageButton) FView.findViewById(R.id.btn_stroke);

        // creating a OnClickListener for each button,
        // to perform certain actions

        // the undo button will remove the most
        // recent stroke from the canvas
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        // the save button will save the current
        // canvas which is actually a bitmap
        // in form of PNG, in the storage
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting the bitmap from DrawView class
                Bitmap bmp = paint.save();

                try {
                    String imgPath = saveToStorage(bmp);
                    File imgFile = new File(imgPath);
                    Uri imgUri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".fileprovider", imgFile);

                    // create an intent to send this picture to someone
                    Intent shareIntent = new Intent();
                    // Set the intent’s action to be Intent.ACTION_SEND,so the intent will look
                    // for other applications who can receive the “send” action
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setAction(Intent.ACTION_SEND);
                    // EXTRA_TEXT is the text to go with the image
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my pet drawing created myself");
                    // Intent.EXTRA_STREAM is used to send media contents
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                    // Add flag FLAG_GRANT_READ_URI_PERMISSION :
                    // the new application is granted to decode the URI
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    // Set the content type to image, so the OS will
                    // look for all apps that can receive media type ==
                    // image (whatsapp, wechat, Google Drive, sms….)
                    shareIntent.setType("image/*");
                    startActivity(shareIntent);
                } catch (Exception e) {
                    Log.d("Cyrus", e.toString());
                }
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.setColor(Color.WHITE);
                eraser.setEnabled(false);
                eraser.setBackgroundColor(Color.GRAY);
                color.setBackgroundResource(android.R.drawable.btn_default);
                Snackbar.make(view, "Eraser mode", Snackbar.LENGTH_LONG).show();
            }

        });

        // the color button will allow the user
        // to select the color of his brush
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraser.setEnabled(true);
                color.setBackgroundColor(Color.GRAY);
                eraser.setBackgroundResource(android.R.drawable.btn_default);
                final ColorPicker colorPicker = new ColorPicker(getActivity());
                ArrayList<String> colorHexList = new ArrayList<>(Arrays.asList("#660000", "#663300", "#666600", "#006600", "#003366", "#000066", "#660066", "#000000",
                        "#CCCC00", "#CC6600", "#CCCC00", "#00CC00", "#0066CC", "#0000CC", "#6600CC", "#606060",
                        "#FF3333", "#FF9933", "#FFFF33", "#33FF33", "#3399FF", "#9933FF", "#FF33FF", "#A0A0A0",
                        "#FF9999", "#FFCC99", "#FFFF99", "#99FF99", "#99FFFF", "#9999FF", "#FF99FF", "#FFFFFF"));
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // get the integer value of color
                        // selected from the dialog box and
                        // set it as the stroke color
                        paint.setColor(color);
                    }

                    @Override
                    public void onCancel() {
                        colorPicker.dismissDialog();
                    }
                })
                        // set the number of color columns
                        // you want to show in dialog.
                        .setColumns(8)
                        .setColors(colorHexList)
                        // set a default color selected
                        // in the dialog
//                        .setDialogFullHeight()
//                        .setRoundColorButton(true)
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });
        // the button will toggle the visibility of the RangeBar/RangeSlider
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });

        // set the range of the RangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        // adding a OnChangeListener which will
        // change the stroke width
        // as soon as the user slides the slider
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
            }
        });

        // pass the height and width of the custom view
        // to the init method of the DrawView object
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });

        return FView;
    }

    private String saveToStorage(Bitmap bitmapImage) {
        // Create a path
        // Unseen directory (of VM)
//        ContextWrapper cw = new ContextWrapper(getActivity());
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE); // path to /data/user/0/yourapp/app_imageDir
        // External directory (out of VM, including Internal Shared Storage and SD Card Storage)
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // File name
        Long tsLong = System.currentTimeMillis() / 1000; // Get system current timestamp
        String n = tsLong.toString();
        String fname = "img-" + n + ".png";
        // Create a file
        File mypath = new File(directory, fname);

        // Compress and save bitmap under the mentioned fileName
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Snackbar.make(getView(), "Saved in " + mypath, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        return directory.getAbsolutePath();
        return mypath.toString();
    }
}

