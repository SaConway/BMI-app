package com.example.sagee.bmiapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IBW extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ibw, container, false);
        Button calcButton = (Button) view.findViewById(R.id.button_compute);
        calcButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonComputeHandler();
            }
        });

        return view;
    }

    private void buttonComputeHandler()
    {
        EditText heightEditText = (EditText)getView().findViewById(R.id.editText_height);
        TextView resultTextView = (TextView)getView().findViewById(R.id.textView_result);
        RadioGroup radioGroup = (RadioGroup)getView().findViewById(R.id.radio_group);

        // check if user didn't enter height
        if (heightEditText.getText().length() == 0)
        {
            Toast.makeText(getActivity(), "Please insert your height", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightEditText.getText().toString());
        double weight = 0;

        int selected_id = radioGroup.getCheckedRadioButtonId();
        RadioButton m_radio_b = (RadioButton)getView().findViewById(selected_id);

        // check if it's a male
        if (m_radio_b.getText().toString().equals("Male"))
            weight = 48 + (height - 152.4) * 1.1;

        // or a female
        else if (m_radio_b.getText().toString().equals("Female"))
            weight = 45 + (height - 152.4) * 0.9;

        weight = Math.round(weight * 10);
        weight = weight / 10;
        resultTextView.setText("IBW = " + Double.toString(weight));
    }
}