package com.example.app_caso1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_caso1.interfaces.ILogin;
import com.example.app_caso1.models.Login;
import com.example.app_caso1.models.Presas;
import com.example.app_caso1.models.Tanques;
import com.example.app_caso1.models.Usuarios;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText Et_email;
    private EditText Et_password;
    private Button Btn_login;
    private ProgressBar PBsesion;

   // SharedPreferences pref;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las vistas
        Et_email = findViewById(R.id.et_corr_usu);
        Et_password = findViewById(R.id.et_pass_usu);
        Btn_login = findViewById(R.id.btn_login);
        PBsesion = findViewById(R.id.pb_sesion);

       // pref = getSharedPreferences("user_details", MODE_PRIVATE);

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Se muestra el progress bar
                    PBsesion.setVisibility(View.VISIBLE);

                    email = Et_email.getText().toString();
                    password = Et_password.getText().toString();

                    /*
                if(email.isEmpty() || password.isEmpty()) {
                    PBsesion.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                     */

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://192.168.3.12:45455/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getUnsafeOkHttpClient())
                            .build();
                    ILogin iLogin = retrofit.create(ILogin.class);

                    Call<Login> call = iLogin.find(email);

                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {

                            //Se muestra el progress bar
                            PBsesion.setVisibility(View.GONE);

                            Login login = response.body();

                            if (login == null) {
                                PBsesion.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this.getApplicationContext(), "Correo Electrónico o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                            else if (!login.getEmail().equalsIgnoreCase(MainActivity.this.email) || !login.getPass_usuario().equalsIgnoreCase(MainActivity.this.password)) {
                                Toast.makeText(MainActivity.this.getApplicationContext(), "Correo Electrónico o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                PBsesion.setVisibility(View.GONE);
                            }
                            else {

                                startActivity(new Intent(
                                        MainActivity.this,
                                        MenuActivity.class
                                ));

                                }

                            }

                            @Override
                            public void onFailure (Call < Login > call, Throwable t){
                                Toast.makeText(MainActivity.this, "Hubo un error al cargar los datos, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                PBsesion.setVisibility(View.GONE);
                            }

                    });
            }
        });

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registro (View v)
    {
        startActivity(
                new Intent(
                        MainActivity.this,
                        RegistroActivity.class
                )
        );
    }
}