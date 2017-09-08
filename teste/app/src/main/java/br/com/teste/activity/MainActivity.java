package br.com.teste.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.teste.R;
import br.com.teste.async.RESTAndroidAsync;
import br.com.teste.token.ArmazenaToken;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Main" , "Dentro da main activity") ;


        Button botaoSalvar =(Button) findViewById(R.id.botaoSalvar) ;

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*
                    String token = FirebaseInstanceId.getInstance().getToken();
                    ArmazenaToken.setTokenArmazenado(token);

                    RESTAndroidAsync restAndroidAsync = new RESTAndroidAsync(ArmazenaToken.getTokenArmazenado());
                    restAndroidAsync.execute();
                    */

                }
        });




    }
}
