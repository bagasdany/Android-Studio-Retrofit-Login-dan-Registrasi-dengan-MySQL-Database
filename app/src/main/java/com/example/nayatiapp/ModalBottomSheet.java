package com.example.nayatiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    private ActionListener mActionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_content, container, false);

        Button btnA = v.findViewById(R.id.buttonA);
        Button btnB = v.findViewById(R.id.buttonB);

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionListener!=null){
                    mActionListener.onButtonClick(R.id.buttonA);
                }
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionListener!=null){
                    mActionListener.onButtonClick(R.id.buttonB);
                }
            }
        });

        return v;

    }

    public void setmActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    interface ActionListener{
        void onButtonClick(int id);
    }
}
