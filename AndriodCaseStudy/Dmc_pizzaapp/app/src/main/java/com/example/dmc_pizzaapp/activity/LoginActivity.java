package com.example.dmc_pizzaapp.activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.dmc_pizzaapp.MainActivity;
import com.example.dmc_pizzaapp.R;

import com.example.dmc_pizzaapp.entity.Users;
import com.example.dmc_pizzaapp.utils.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private static final String tag ="dddd";
    TextInputEditText editUsernameLogin,editPasswordLogin;
    CheckBox chkrememberme;
    Button btnLogin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Mapping();

        if(getSharedPreferences("Login_status",MODE_PRIVATE).getBoolean("login",false)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users users = new Users();
                users.setEmail(editUsernameLogin.getText().toString());
                users.setPassword(editPasswordLogin.getText().toString());
                Log.e("aaa",users.toString());
                RetrofitClient.getInstance().getApi().loginUser(users).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        // Log.d("aaa", ""+response.body().getAsJsonObject().get("data").getAsJsonObject().toString());
                        JsonObject login = response.body().getAsJsonObject().get("data").getAsJsonObject();
//                        JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                        if(login.get("success").getAsInt() == 1){
                            JsonObject jsonObject = login.get("data").getAsJsonObject();
                            getSharedPreferences("mobileStore",MODE_PRIVATE).edit()
                                    .putInt("uid",jsonObject.get("id").getAsInt()).apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void Mapping() {

        editUsernameLogin = findViewById(R.id.editUsernameLogin);
        editPasswordLogin = findViewById(R.id.editPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        chkrememberme = findViewById(R.id.chkrememberme);


    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    public void login(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}