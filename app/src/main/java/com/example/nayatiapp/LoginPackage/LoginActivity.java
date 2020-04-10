package com.example.nayatiapp.LoginPackage;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayatiapp.APIPackage.ApiClient;
import com.example.nayatiapp.APIPackage.ApiInterface;
import com.example.nayatiapp.DashboardActivity;
import com.example.nayatiapp.LoginFragment;
import com.example.nayatiapp.MainActivity2;
import com.example.nayatiapp.R;
import com.example.nayatiapp.TrackingDatang.MainActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText edtemail, edtPassword;
    private Button btnLogin;
    private ProgressDialog pd;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private SessionManager sm;
    private TextView Regist;
    LoginFragment.OnLoginFormActivityListener loginFormActivityListener;

    public interface OnLoginFormActivityListener
    {
        public void performRegister();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Regist = findViewById(R.id.link_regist);

        edtemail =  findViewById(R.id.etEmail);
        edtPassword =  findViewById(R.id.etPassword);

        btnLogin =  findViewById(R.id.btnLogin);
        pd = new ProgressDialog(LoginActivity.this);
        sm = new SessionManager(LoginActivity.this);
        pd.setMessage("loading ...");
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegist();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                ApiInterface Api = ApiClient.getRequestService();
                Call<ResponseApiModel> login = Api.login(edtemail.getText().toString(),edtPassword.getText().toString());
                login.enqueue(new Callback<ResponseApiModel>() {
                    @Override
                    public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                        pd.dismiss();
                        Log.d(TAG, "response : " +response.toString());
                        ResponseApiModel res = response.body();
                        List<UserModel> user = res.getResult();
                        if (res.getKode().equals("1")) {

                            sm.storeLogin(user.get(0).getEmail(), user.get(0).getName(), user.get(0).getId_user());
                            Toast.makeText(LoginActivity.this, "username/password ditemukan" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                            intent.putExtra("id",user.get(0).getIduser());
                            intent.putExtra("email",user.get(0).getEmail());
                            intent.putExtra("name",user.get(0).getName());
                            startActivity(intent);

                        } else
                        {
                            Toast.makeText(LoginActivity.this, "username tidak cocok", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                        pd.dismiss();
                        Log.e(TAG,"error: " + t.getMessage());
                    }
                });
            }
        });

    }

    private void openRegist() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

}
