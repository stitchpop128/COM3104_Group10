package com.example.com3104_group10.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com3104_group10.R;

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

    private EditText et_Weight,et_NumMeals,et_Energy;
    private RadioGroup rg_type;
    private RadioButton rb_dog,rb_cat;
    private RadioGroup rg_Condition;
    private RadioButton rb_notLigated,rb_ligated,rb_overweight,rb_ultraLight,rb_developmental,rb_advanced;
    private Button bt_cal,bt_example;
    private TextView tv_result,tv_DER;

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
        View FrameView = inflater.inflate(R.layout.fragment_calculator, container,false);
        et_Weight=FrameView.findViewById(R.id.et_weight);
        et_NumMeals = FrameView.findViewById(R.id.et_nummeals);
        et_Energy = FrameView.findViewById(R.id.et_fme);
        rg_type = FrameView.findViewById(R.id.rg_type);
        rb_dog = FrameView.findViewById(R.id.rb_dog);
        rb_cat = FrameView.findViewById(R.id.rb_cat);
        rg_Condition = FrameView.findViewById(R.id.rg_condition);
        rb_notLigated = FrameView.findViewById(R.id.rb_notligated);
        rb_overweight = FrameView.findViewById(R.id.rb_overwight);
        rb_ultraLight = FrameView.findViewById(R.id.rb_ultralight);
        rb_advanced = FrameView.findViewById(R.id.rb_advanced);
        bt_cal = FrameView.findViewById(R.id.bt_cal);
        bt_example= FrameView.findViewById(R.id.bt_example);
        tv_DER = FrameView.findViewById(R.id.tv_DER);
        tv_result = FrameView.findViewById(R.id.tv_result);

        bt_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        bt_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double weight = Double.parseDouble(et_Weight.getText().toString())*30+70;
                int type = rb_dog.isChecked() ? 1 : rb_cat.isChecked() ? 2 : -1;
                int Condition = rb_notLigated.isChecked() ? 1 : rb_overweight.isChecked() ? 2 :rb_ultraLight.isChecked() ? 3 :rb_advanced.isChecked() ? 4 :-1;

                double energy = calculateEnergy(type, Condition, weight); //DER

                tv_DER.setText(String.format("Your pet's daily caloric needs are: %.2f calories", energy));

                double staple_food_calories=energy*0.90; //主食熱量
                double x=staple_food_calories*1000/Double.parseDouble(et_Energy.getText().toString());//飼料代謝熱量
                double result=x/Double.parseDouble(et_NumMeals.getText().toString());//每餐所需份量
                tv_result.setText(String.format("Your pet needs: %.2f gram per meal everyday",result));
            }

            public double calculateEnergy(int type, int Condition, double weight) {
                double baseEnergy;
                switch (type) {
                    case 1: //dog
                        switch (Condition) {
                            case 1:
                                baseEnergy = 1.6;
                                break;
                            case 2:
                                baseEnergy = 1.2;
                                break;
                            case 3:
                                baseEnergy = 3;
                                break;
                            case 4:
                                baseEnergy = 1.2;
                                break;
                            default:
                                baseEnergy = 0;
                                break;
                        }
                        break;
                    case 2: //cat
                        switch (Condition) {
                            case 1:
                                baseEnergy = 1.4;
                                break;
                            case 2:
                                baseEnergy = 0.9;
                                break;
                            case 3:
                                baseEnergy = 1.5;
                                break;
                            case 4:
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


        return FrameView;
    }



}