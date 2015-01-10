package com.charlito.getJson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;


public class JsonGetter {
	
	private int tMaximo = 0;
	private final int  TIEMPO_INTERVALO = 500;
	private int tTranscurrido = TIEMPO_INTERVALO;
	private InterfaceCharlitoJsonGetter clasePadre;
	private String urlJson;
	
	private boolean isLoaderCompleted = false;
	private boolean errorOnJson = false;
	private String jsonFile = "";
	
	//no se hara validacion..  no es para wueyes esta clase.
	public JsonGetter(InterfaceCharlitoJsonGetter clasePadre,String urlJson,int tMaximo) {
		this.tMaximo = tMaximo;
		this.clasePadre=clasePadre;
		this.urlJson = urlJson;
		if (tMaximo<1000 || tMaximo >7000) {clasePadre.resultadoJsonGetter(null,5); return;} 
		new CharlieAsyncTask().execute();
		checandoGetCall();
	}
	
	private void checandoGetCall() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isLoaderCompleted){ //entra si no se ha completado
					log("entrando a getcall.....  ");
					tTranscurrido+=TIEMPO_INTERVALO;
					if(tTranscurrido<tMaximo) {checandoGetCall(); return;}
					else {clasePadre.resultadoJsonGetter(null,3); return;}
				}
				else{
					if (errorOnJson){clasePadre.resultadoJsonGetter(null,2); return;}
					clasePadre.resultadoJsonGetter(jsonFile,0); return;
				}
			}	
		}, TIEMPO_INTERVALO);
	}
	
private class CharlieAsyncTask extends AsyncTask<Void, Integer, Void> {
		
        @Override
        protected Void doInBackground(Void... params) {
            
            String url= urlJson;
            log("url a consultar : "+url);
            BufferedReader in = null;
            //String data = null;
            try{
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet request = new HttpGet();
                URI website = new URI(url);
                request.setURI(website);
                HttpResponse response = httpclient.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                	
                jsonFile = in.readLine();
                log("lonjitud del json:"+jsonFile.length());
                log("lectura exitosa");
                errorOnJson = false;
                isLoaderCompleted = true;
                
            }catch(Exception e){
                log("error en la puta lectura del json: "+e.getMessage());
                errorOnJson = true;
                isLoaderCompleted = true;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @SuppressWarnings("unused")
		protected void onPostExecute(Long result) {
            log("el hilo de asyncTask a terminado1...!!!!!!");
        }
    }
	
	private void log(String cad){
		Log.d("charlie", cad);
	}
	

}
