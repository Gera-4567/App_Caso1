package com.example.app_caso1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_caso1.interfaces.IUsuarios;
import com.example.app_caso1.models.Usuarios;
import java.security.MessageDigest;

import java.security.cert.CertificateException;

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

public class RegistroActivity extends AppCompatActivity {

    private EditText Et_nom_usuario, Et_ap1, Et_ap2, Et_email, Et_pass;
    private Button Btn_registro;
    private ProgressBar PBsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar las vistas
        Et_nom_usuario = findViewById(R.id.et_nom_usu);
        Et_ap1 = findViewById(R.id.et_ap1);
        Et_ap2 = findViewById(R.id.et_ap2);
        Et_email = findViewById(R.id.et_corr_usu);
        Et_pass = findViewById(R.id.et_pass_usu);
        Btn_registro = findViewById(R.id.btn_registro);
        PBsesion = findViewById(R.id.pb_sesion);

        // Agregando un OnClick a el botón
        Btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validar campos vacios
                if(Et_nom_usuario.getText().toString().isEmpty() || Et_ap1.getText().toString().isEmpty() ||
                Et_ap2.getText().toString().isEmpty()  || Et_email.getText().toString().isEmpty() || Et_pass.getText().toString().isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                EnvUser(Et_nom_usuario.getText().toString(), Et_pass.getText().toString(), Et_ap1.getText().toString(), Et_ap2.getText().toString(),
                        Et_email.getText().toString());

            }

        });

    }

    private void EnvUser(String nom_usuario, String pass_usuario, String Ap1_usuario, String Ap2_usuario, String email ){
        //Se muestra el progress bar
        PBsesion.setVisibility(View.VISIBLE);

        //Se crea la llamada a la API mediante Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.3.12:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        // Se instancia la interfaz
        IUsuarios iUsuarios = retrofit.create(IUsuarios.class);

        // Se pasan los datos de los edit text a el modelo
        Usuarios usuarios = new Usuarios(nom_usuario, pass_usuario, Ap1_usuario, Ap2_usuario, email);

        //Llamando el método para mandar el post al modelo
        Call<Usuarios> call = iUsuarios.createPost(usuarios);

        // Se ejecuta el método
        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                // Toast para indicar al usuario que se registro correctamente
                Toast.makeText(RegistroActivity.this, "Se agrego el usuario correctamente", Toast.LENGTH_SHORT).show();

                // Se deja de mostrar el progress bar
                PBsesion.setVisibility(View.GONE);

                // Se envian textos vacios para ambos edit text
                Et_nom_usuario.setText("");
                Et_ap1.setText("");
                Et_ap2.setText("");
                Et_email.setText("");
                Et_pass.setText("");

                // Se obtiene un response del cuerpo y se pasa de nuestro modelo clase
                Usuarios responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Hubo un error al cargar los datos", Toast.LENGTH_SHORT).show();
                PBsesion.setVisibility(View.GONE);
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

    public void login (View v)
    {
        startActivity(
                new Intent(
                        RegistroActivity.this,
                        MainActivity.class
                )
        );
    }
}