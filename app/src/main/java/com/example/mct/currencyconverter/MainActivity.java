package com.example.mct.currencyconverter;

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
 // entered currencies must be entered in upper case :)
public class MainActivity extends AppCompatActivity {

    TextView textView,textView2,textView3;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        textView = (TextView) findViewById( R.id.textView );
        textView2 = (TextView) findViewById( R.id.textView2 );
        textView3 = (TextView) findViewById( R.id.textView3 );
        editText =  (EditText) findViewById( R.id.editText );


    }
    public void getRates(View view){
        DownloadDate downloadData = new DownloadDate();

        try {
            String url = "http://api.fixer.io/latest?base=";
            String chosenBase = editText.getText().toString();

            downloadData.execute(url+chosenBase);

        } catch ( Exception e){
            e.printStackTrace();
        }
    }


    private class DownloadDate extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            try {

                JSONObject jsonObject = new JSONObject( s );
                String base = jsonObject.getString( "base" );
                System.out.println(base);
                String date = jsonObject.getString( "date" );
                System.out.println(date);
                String rates = jsonObject.getString( "rates" );
                System.out.println(rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String chf = jsonObject1.getString("CHF");
                System.out.println(chf);
                String czk = jsonObject1.getString("CZK");
                System.out.println(czk);
                String tl = jsonObject1.getString("TRY");

                textView.setText("CHF: "+chf);
                textView2.setText("CZK: "+czk);
                textView3.setText("TRY: "+tl);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL( params[0] );
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader( inputStream );

                int data = inputStreamReader.read();

                while (data>0){

                    char  character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return  result;

            } catch (Exception e){
                e.printStackTrace();

                return null;
            }
        }
    }
}
