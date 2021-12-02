package com.example.app_caso1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.app_caso1.interfaces.IDepositos;
import com.example.app_caso1.models.Depositos;

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

public class DepositosActivity extends AppCompatActivity {

    private TextView Tv_list;
    private ProgressBar PBsesion;

    private Float capacidad;

    //creacion de variables para las notificaciones

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositos);

        PBsesion = findViewById(R.id.pb_sesion);
        Tv_list = findViewById(R.id.tv_list);
        getDepositos();

    }

    private void getDepositos() {

        //Se muestra el progress bar
        PBsesion.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.3.12:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        IDepositos iDepositos = retrofit.create(IDepositos.class);

        Call<List<Depositos>> call = iDepositos.getDepositos();

        call.enqueue(new Callback<List<Depositos>>() {
            @Override
            public void onResponse(Call<List<Depositos>> call, Response<List<Depositos>> response) {
                if (!response.isSuccessful()) {
                    //Se apaga el progress bar
                    PBsesion.setVisibility(View.GONE);
                    Tv_list.setText("Código: " + response.code());
                    return;
                }
                //Se apaga el progress bar
                PBsesion.setVisibility(View.GONE);

                List<Depositos> depositosList = response.body();

                for (Depositos deposito : depositosList) {
                    String content = "";
                    capacidad = deposito.getCapacidad();
                    //content += "$id: " + deposito.get$id() + "\n";
                    content += "ID: " + deposito.getId_deposito() + "\n";
                    content += "Depósito: " + deposito.getNom_deposito() + "\n";
                    content += "Capacidad: " + deposito.getCapacidad() + "%" + "\n";
                    content += "Uso: " + deposito.getUso() + "\n";
                    Tv_list.append(content);

                }

                if(capacidad <= 10){

                    createNotificationChannel();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

                    //se agrega la parte del reloj
                    NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().setHintHideIcon(true);

                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("¡Alerta!");
                    builder.setContentText("Presas con 10% de capacidad o menos");
                    builder.setColor(Color.BLUE);
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    builder.setLights(Color.MAGENTA, 1000, 1000);
                    builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
                    builder.setDefaults(Notification.DEFAULT_SOUND);
                    //se vincula con el reloj
                    builder.extend(wearableExtender);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                    notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

                    //al presionar la notificacion te manda a un activityb
                    Intent intent = new Intent(DepositosActivity.this, DepositosActivity.class);

                    PendingIntent pendingIntent = PendingIntent.getActivity(DepositosActivity.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    builder.setContentIntent(pendingIntent);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(NOTIFICACION_ID, builder.build());

                }

                //Se apaga el progress bar
                PBsesion.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Depositos>> call, Throwable t) {
                Tv_list.setText(t.getMessage());
                //Se apaga el progress bar
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

    public void menu (View v)
    {
        startActivity(
                new Intent(
                        DepositosActivity.this,
                        MenuActivity.class
                )
        );
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
