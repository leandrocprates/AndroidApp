package br.com.teste.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import br.com.teste.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lprates on 07/09/2017.
 */

public class RESTAndroidAsync  extends AsyncTask<Object,Object,String> {


    Context context ;
    String token ;
    Usuario usuario ;


    public RESTAndroidAsync(Usuario usuario){
        this.usuario = usuario ;
    }

    public RESTAndroidAsync(String token){
        this.token = token ;
    }


    public RESTAndroidAsync(Context context){
        this.context = context ;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("RESPOSTA" , s) ;
    }


    @Override
    protected String doInBackground(Object... params) {

        String  resposta = "" ;

        try{

            //Method GET
            /*
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    //.url("http://10.0.2.2:8080/RESTAndroid/hello/"+this.token)
                    .url("http://192.168.0.184:8080/RESTAndroid/hello/"+ usuario.getToken() )
                    .build();

            Response response =   client.newCall(request).execute() ;
            resposta = response.body().toString() ;
            */

            //Method POST

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            Gson gson = new Gson();
            String stringJson = gson.toJson(usuario);

            RequestBody requestBody = RequestBody.create(mediaType , stringJson);

            Request.Builder requestBuilder = new Request.Builder()
                                        .url("http://192.168.0.184:8080/RESTAndroid/token");
            requestBuilder.post(requestBody) ;

            Request request = requestBuilder.build() ;

            Response response = client.newCall(request).execute();

            resposta = response.body().toString();

            response.close();

        }catch(Exception ex){
            Log.e("Erro REQUISICAO:" , ex.toString()) ;
        }

        return resposta;
    }
}
