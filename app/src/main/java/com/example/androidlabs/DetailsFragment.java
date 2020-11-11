package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle dataFromActivity = getArguments();
        String Message = dataFromActivity.getString("Message", "");
        long ID = dataFromActivity.getLong("id");
        boolean isSend = dataFromActivity.getBoolean("isSend", false);
        View result = inflater.inflate(R.layout.fragment_details, container, false);
        TextView tv1 = result.findViewById(R.id.Message_Here);
        TextView tv2 = result.findViewById(R.id.ID);
        CheckBox c1 = result.findViewById(R.id.CB);
        Button b1 = result.findViewById(R.id.btn);
        tv1.setText(Message);
        tv2.setText(String.valueOf(ID));
        c1.setChecked(isSend);
        b1.setOnClickListener(click ->{
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        return result;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        parentActivity = (AppCompatActivity)context;
    }
}