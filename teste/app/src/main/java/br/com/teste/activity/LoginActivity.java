package br.com.teste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.teste.R;
import br.com.teste.async.RESTAndroidAsync;
import br.com.teste.model.Usuario;
import br.com.teste.token.ArmazenaToken;

/**
 * Created by lprates on 07/09/2017.
 */

public class LoginActivity extends AppCompatActivity {


    Usuario usuario = null ;
    EditText editEmail = null ;
    EditText editPassword = null ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editEmail  =  (EditText) findViewById(R.id.email);
        editPassword  =  (EditText) findViewById(R.id.password);
        Button botaoLogin = (Button)  findViewById(R.id.botaoLogin) ;


        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String token = FirebaseInstanceId.getInstance().getToken();

                usuario = new Usuario();
                usuario.setId(1);
                usuario.setEmail(editEmail.getText().toString());
                usuario.setToken(token);

                //ArmazenaToken.setTokenArmazenado(token);

                RESTAndroidAsync restAndroidAsync = new RESTAndroidAsync(usuario);
                restAndroidAsync.execute();


                Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}
