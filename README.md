# AndroidApp - Modelo de Integração com o Firebase Cloud Message 



- **Aplicativo android integrado com o FCM Google para receber mensagens e notificações do servidor  (FCM).**


Para Criar um Projeto do FCM no Firebase seguir o tutorial da  [Caelum](http://blog.alura.com.br/integrando-app-android-com-o-firebase-cloud-messaging/) que tem uma otima explicação de como faze-lo. 


1. Arquivo **AndroidManifest.xml** , onde será declaradas as Classes **CDCMessasingService.java** que recebe notificações do Servidor FCM e a classe **CDCInstanceIDService.java** que gera o token necessario para o envio das mensagens.  


```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="br.com.teste">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">


        <activity android:name=".activity.MainActivity">
        </activity>

        <activity
            android:name=".activity.LoginActivity"
            android:label="@string/title_activity_login">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>


        <!-- Trata mensagem que vem do Firebase como Notificacao -->
        <service android:name=".CDCMessasingService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <!-- Gera token do FireBase Cloud Message -->
        <service android:name=".CDCInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>

    </application>

</manifest>

``` 

2. A classe **LoginActivity.java** possui uma tela de login e assim que for clicado o botao Login será gerado um token , que é o token que devemos armazenar no servidor para enviar-mos mensagem de notificação 

```
Linha 46 - String token = FirebaseInstanceId.getInstance().getToken();
``` 



Após o token ser gerado ele será enviado para o servico que foi definido no projeto [RESTAndroid](https://github.com/leandrocprates/RESTAndroid) . Copie o projeto e execute ele e verá que a chamada sendo feita. 


```
  Linha 55 - RESTAndroidAsync restAndroidAsync = new RESTAndroidAsync(usuario);
             restAndroidAsync.execute();


             Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
             startActivity(intent);

```

3. A Classe **CDCInstanceIDService.java** pode ser utilizada para gerar o token . 

```

package br.com.teste;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.teste.token.ArmazenaToken;

/**
 * Created by lprates on 01/09/2017.
 */

public class CDCInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token da App", token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

        ArmazenaToken.setTokenArmazenado(token);

//        RESTAndroidAsync restAndroidAsync = new RESTAndroidAsync(token) ;
//        restAndroidAsync.execute();
    }


}
```
4. Classe CDCMessasingService.java responsavel por receber mensagens de Notificação .


```

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

```


