package com.nsf.appfreiinscricoes.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nsf.appfreiinscricoes.R;
import com.nsf.appfreiinscricoes.telas.Splash;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FreiFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String CHANNEL_ID = "1";
    BroadcastReceiver mReceiver;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sTitulo = "";
        String sMensagem = "";
        String sActivity = "";

        sTitulo = remoteMessage.getData().get("title");
        sMensagem = remoteMessage.getData().get("body");
        sActivity = remoteMessage.getData().get("activity");

        if (Ultil.recuperaDadosUsuario(this).getDsCpf().length() != 0 )
        {
            exibirNotificacao(sTitulo, sMensagem, sActivity);
        }
    }

    private void exibirNotificacao(String strTituloNotificacao, String strMensagem, String sActivity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, Splash.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pushActivity", sActivity);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {0, 100, 1000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_push_notification)
                .setContentTitle(strTituloNotificacao)
                .setContentText(strMensagem)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(strMensagem))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(Ultil.retornaIdNotificacao(this), builder.build());
        Ultil.exibiuNotificacao(this);
    }
}
