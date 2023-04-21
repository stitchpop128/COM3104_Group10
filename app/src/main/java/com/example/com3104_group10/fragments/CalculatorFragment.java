package com.example.com3104_group10.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com3104_group10.CustomAdapter;
import com.example.com3104_group10.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText et_Weight, et_NumMeals, et_Energy, et_Name;
    private RadioGroup rg_type;
    private RadioButton rb_dog, rb_cat;
    private RadioGroup rg_Condition;
    private RadioButton rb_notLigated, rb_overweight, rb_ultraLight, rb_advanced;
    private Button bt_cal, bt_example, bt_his;
    private TextView tv_result, tv_DER;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View FrameView = inflater.inflate(R.layout.fragment_calculator, container, false);
        et_Weight = FrameView.findViewById(R.id.et_weight);
        et_NumMeals = FrameView.findViewById(R.id.et_nummeals);
        et_Energy = FrameView.findViewById(R.id.et_fme);
        et_Name = FrameView.findViewById(R.id.et_name);
        rg_type = FrameView.findViewById(R.id.rg_type);
        rb_dog = FrameView.findViewById(R.id.rb_dog);
        rb_cat = FrameView.findViewById(R.id.rb_cat);
        rg_Condition = FrameView.findViewById(R.id.rg_condition);
        rb_notLigated = FrameView.findViewById(R.id.rb_notligated);
        rb_overweight = FrameView.findViewById(R.id.rb_overwight);
        rb_ultraLight = FrameView.findViewById(R.id.rb_ultralight);
        rb_advanced = FrameView.findViewById(R.id.rb_advanced);
        bt_cal = FrameView.findViewById(R.id.bt_cal);
        bt_example = FrameView.findViewById(R.id.bt_example);
        bt_his = FrameView.findViewById(R.id.bt_his);
        tv_DER = FrameView.findViewById(R.id.tv_DER);
        tv_result = FrameView.findViewById(R.id.tv_result);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        rg_type.check(R.id.rb_dog);
        rg_Condition.check(R.id.rb_notligated);

        bt_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ImageDialog = new AlertDialog.Builder(et_NumMeals.getContext());
//                ImageDialog.setTitle("Title");

                ImageView image = new ImageView(et_NumMeals.getContext());
                image.setImageResource(R.drawable.example);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                int imageWidth = (int) (et_NumMeals.getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
                int originalImgWidth = image.getDrawable().getIntrinsicWidth();
                double percentageChange = (imageWidth - originalImgWidth) / originalImgWidth;
                int imageHeight;
                int originalImgHeight = image.getDrawable().getIntrinsicHeight();
                if (percentageChange > 0) {
                    imageHeight = (int) ((1 + percentageChange) * originalImgHeight);
                } else {
                    imageHeight = (int) ((percentageChange) * originalImgHeight);
                }
                image.setMinimumWidth(imageWidth);
                image.setMinimumHeight(imageHeight);
                ImageDialog.setView(image);

                ImageDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                ImageDialog.show();
            }
        });

        bt_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double weight = Double.parseDouble(et_Weight.getText().toString()) * 30 + 70;
                    int type = rb_dog.isChecked() ? 1 : rb_cat.isChecked() ? 2 : -1;
                    int Condition = rb_notLigated.isChecked() ? 1 : rb_overweight.isChecked() ? 2 : rb_ultraLight.isChecked() ? 3 : rb_advanced.isChecked() ? 4 : -1;
//                    String TType = rb_dog.isChecked() ? "Dog" : rb_cat.isChecked() ? "Cat" : "Not Provide";
//                    String TCondition = rb_notLigated.isChecked() ? "Not Ligated" : rb_overweight.isChecked() ? "Overweight" : rb_ultraLight.isChecked() ? "Ultra Light" : rb_advanced.isChecked() ? "Advanced" : "Not Provide";

                    double energy = calculateEnergy(type, Condition, weight); //DER

                    tv_DER.setText(String.format("Your pet's daily caloric needs are: %.2f calories", energy));

                    double staple_food_calories = energy * 0.90; //主食熱量
                    double x = staple_food_calories * 1000 / Double.parseDouble(et_Energy.getText().toString());//飼料代謝熱量
                    double result = x / Double.parseDouble(et_NumMeals.getText().toString());//每餐所需份量
                    tv_result.setText(String.format("Your pet needs: %.2f gram per meal everyday", result));
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(et_Name.getText().toString(), "Name: " + et_Name.getText() + "  " + "Needs: " + String.format("%.2f", x) + " gram per day");
                    editor.apply();
//                    String dataString = sharedPref.getString("OMG", "");
//                    if (!dataString.isEmpty()) {
//                        List<String> data = new ArrayList<>(Arrays.asList(dataString.split(":")));
//                        tv_test1.setText(data.get(0) + data.get(1) + data.get(2));
//
//                    }

                } catch (java.lang.NumberFormatException e) {
//                    ...
                }
            }

            public double calculateEnergy(int type, int Condition, double weight) {
                double baseEnergy;
                switch (type) {
                    case 1: //dog
                        switch (Condition) {
                            case 1: //rb_notLigated
                                baseEnergy = 1.6;
                                break;
                            case 2: //rb_overweight
                                baseEnergy = 1.2;
                                break;
                            case 3: //rb_ultraLight
                                baseEnergy = 3;
                                break;
                            case 4: //rb_advanced
                                baseEnergy = 1.2;
                                break;
                            default:
                                baseEnergy = 0;
                                break;
                        }
                        break;
                    case 2: //cat
                        switch (Condition) {
                            case 1: //rb_notLigated
                                baseEnergy = 1.4;
                                break;
                            case 2: //rb_overweight
                                baseEnergy = 0.9;
                                break;
                            case 3: //rb_ultraLight
                                baseEnergy = 1.5;
                                break;
                            case 4: //rb_ultraLight
                                baseEnergy = 1.4;
                                break;
                            default:
                                baseEnergy = 0;
                                break;
                        }
                        break;
                    default:
                        baseEnergy = 0;
                        break;
                }
                return baseEnergy * weight;
            }
        });

        bt_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder historyDialog = new AlertDialog.Builder(getContext());
                historyDialog.setTitle("Recent Searching History");

                Map<String, ?> keys = sharedPref.getAll();
                String[] items = new String[keys.size()];
                int cnt = 0;
                for (Map.Entry<String, ?> entry : keys.entrySet()) {
                    entry.getKey();
                    items[cnt] = entry.getValue().toString();
                    cnt++;
                }

                ListView listView = new ListView(getContext());
                int itemHeight = 400;
                CustomAdapter adapter = new CustomAdapter(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(items), itemHeight);
                listView.setAdapter(adapter);
                historyDialog.setView(listView);

//                historyDialog.setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                    }
//                });

                historyDialog.setPositiveButton("Clear History", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        sharedPref.edit().clear().commit();
                    }
                });

                historyDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                historyDialog.show();
            }
        });

        return FrameView;
    }
}