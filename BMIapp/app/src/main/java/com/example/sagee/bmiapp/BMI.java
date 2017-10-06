package com.example.sagee.bmiapp;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI extends Fragment
{
    // private variables
    private static double mWeight, mHeight, mBMIresult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        Button calcButton = (Button) view.findViewById(R.id.buttonCalculate);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonComputeHandler();
            }
        });

        return view;
    }

    private void buttonComputeHandler()
    {
        EditText editTextHeight = (EditText) getView().findViewById(R.id.editTextHeightBMI);
        EditText editTextWeight = (EditText) getView().findViewById(R.id.editTextWeightBMI);
        TextView resultTxt = (TextView) getView().findViewById(R.id.textViewBMIresult);
        TextView statusTxt = (TextView) getView().findViewById(R.id.textViewBMIstatus);

        // user didn't enter height
        if (editTextHeight.getText().length() == 0)
        {
            Toast.makeText(getActivity(), "Please insert your height", Toast.LENGTH_SHORT).show();
            return;
        }

        // user didn't enter weight
        if (editTextWeight.getText().length() == 0)
        {
            Toast.makeText(getActivity(), "Please insert your weight", Toast.LENGTH_SHORT).show();
            return;
        }

        mHeight = Double.parseDouble(editTextHeight.getText().toString());
        mWeight = Double.parseDouble(editTextWeight.getText().toString());

        // calculate bmi result
        mHeight /= 100;
        mBMIresult = (mWeight) / (mHeight * mHeight);
        mBMIresult = mBMIresult * 10;
        mBMIresult = Math.round(mBMIresult);
        mBMIresult = mBMIresult / 10;

        resultTxt.setText("BMI = " + Double.toString(mBMIresult));

        if (mBMIresult <= 18.5) statusTxt.setText("Underweight");
        else if (mBMIresult > 18.5 && mBMIresult < 24.9) statusTxt.setText("Normal weight");
        else if (mBMIresult >= 25 && mBMIresult < 29.9) statusTxt.setText("Overweight");
        else if (mBMIresult >= 30) statusTxt.setText("Obesity");

        alertDialogHandler();
    }

    private void alertDialogHandler()
    {
        // ask user about saving data
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage("Would you like to save your data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int id) {
                        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                        DataBaseHelper.getInstance(getActivity()).insertData(timeStamp, mWeight, mHeight, mBMIresult);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();

        // set text color of AlertDialog answers to red
        Button buttonPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAlertDialogText));
        Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAlertDialogText));
    }
}