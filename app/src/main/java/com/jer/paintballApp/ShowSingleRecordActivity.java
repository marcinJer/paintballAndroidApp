package com.jer.paintballApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ShowSingleRecordActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    String HttpURL = "http://jeremy-paintball.000webhostapp.com/FilterOrderData.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView PRICE, GAME, WEAPON, DATE, NUMBEROFPARTICIPANTS;
    Button UpdateButton, DeleteButton;
    String priceHolder, gameHolder, weaponHolder, dateHolder, playersHolder;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        PRICE = (TextView)findViewById(R.id.tvPrice);
        GAME = (TextView)findViewById(R.id.tvGame);
        WEAPON = (TextView)findViewById(R.id.tvWeapon);
        DATE = (TextView)findViewById(R.id.tvDate);
        NUMBEROFPARTICIPANTS = (TextView)findViewById(R.id.tvParticipants);

        UpdateButton = (Button)findViewById(R.id.buttonUpdate);
        DeleteButton = (Button)findViewById(R.id.buttonDelete);

        TempItem = getIntent().getStringExtra("ListViewValue");

        HttpWebCall(TempItem);


        /*UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowSingleRecordActivity.this, UpdateActivity.class);

                intent.putExtra("note_ID", TempItem);
                intent.putExtra("note_name", NameHolder);
                intent.putExtra("note_content", ContentHolder);

                startActivity(intent);

                finish();

            }
        });*/


    }


    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ShowSingleRecordActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                FinalJSonObject = httpResponseMsg ;

                new GetHttpResponse(ShowSingleRecordActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            priceHolder = jsonObject.getString("price");
                            gameHolder = jsonObject.getString("game");
                            weaponHolder = jsonObject.getString("weapon");
                            dateHolder = jsonObject.getString("date");
                            playersHolder = jsonObject.getString("numberOfParticipants");

                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            PRICE.setText(priceHolder);
            GAME.setText(gameHolder);
            WEAPON.setText(weaponHolder);
            DATE.setText(dateHolder);
            NUMBEROFPARTICIPANTS.setText(playersHolder);

        }
    }

}
