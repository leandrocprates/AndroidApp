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



