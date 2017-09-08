package br.com.teste;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import br.com.teste.activity.MainActivity;

/**
 * Created by lprates on 01/09/2017.
 */

public class CDCMessasingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("FROM", "From: " + remoteMessage.getFrom());
        Log.d("TO", "To: " + remoteMessage.getTo() );
        Log.d("MESSAGE_TYPE", "Message Type: " + remoteMessage.getMessageType() );


        if (remoteMessage.getData().size() > 0) {
            Log.d("MESSAGE_PAYLOAD", "Message data payload: " + remoteMessage.getData());

            Map<String,String> map = remoteMessage.getData() ;
            Log.i("MENSAGEM_RECEBIDA: " , map.get("message")  ) ;

        }


        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification =  remoteMessage.getNotification();
            mostrarNotificacao(notification);
        }

    }

    public void mostrarNotificacao(RemoteMessage.Notification notification) {
        String titulo = notification.getTitle();
        String mensagem = notification.getBody();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Notification notificacao = builder
                .setSound(defaultSoundUri)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao);

    }




}