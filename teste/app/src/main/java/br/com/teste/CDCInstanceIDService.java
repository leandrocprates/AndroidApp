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