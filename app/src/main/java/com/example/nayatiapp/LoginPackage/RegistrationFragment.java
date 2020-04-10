package com.example.nayatiapp.LoginPackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nayatiapp.MainActivity2;
import com.example.nayatiapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText Email,Password,Nama;
    private Button BnRegister;
    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        Nama= view.findViewById(R.id.txt_nama);
        Email = view.findViewById(R.id.txt_email);
        Password = view.findViewById(R.id.txt_password);
        BnRegister = view.findViewById(R.id.btn_register);

        BnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
        return view;
    }

    public void performRegistration()
    {
        String name = Nama.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        Call<User> call = MainActivity2.apiInterface.performRegistration(name,email,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok"))
                {
                    MainActivity2.prefConfig.displayToast("Registration Succes...");
                }
                else if (response.body().getResponse().equals("exist"))
                {
                    MainActivity2.prefConfig.displayToast("User Already Exist...");
                }
                else if (response.body().getResponse().equals("error"))
                {
                    MainActivity2.prefConfig.displayToast("Something Went Wrong...");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        Nama.setText("");
        Email.setText("");
        Password.setText("");
    }
}
