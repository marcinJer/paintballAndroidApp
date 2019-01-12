package com.jer.paintballApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ShowSingleRecordActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    String HttpURL = "http://jeremy-paintball.000webhostapp.com/FilterOrderData.php";
    String HttpURLUpdate = "http://jeremy-paintball.000webhostapp.com/UpdateOrder.php";

    String finalResult;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    String ParseResult;
    HashMap<String, String> ResultHash = new HashMap<>();
    String FinalJSonObject;
    TextView PRICE, GAME, WEAPON, DATE, NUMBEROFPARTICIPANTS;
    Button UpdateButton, DeleteButton;
    String priceHolder, gameHolder, weaponHolder, dateHolder, playersHolder;
    String TempItem;
    String idHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);


        PRICE = (TextView) findViewById(R.id.tvPrice);
        GAME = (TextView) findViewById(R.id.tvGame);
        WEAPON = (TextView) findViewById(R.id.tvWeapon);
        DATE = (TextView) findViewById(R.id.tvDate);
        NUMBEROFPARTICIPANTS = (TextView) findViewById(R.id.tvParticipants);

        UpdateButton = (Button) findViewById(R.id.buttonUpdate);
        DeleteButton = (Button) findViewById(R.id.buttonDelete);

        TempItem = getIntent().getStringExtra("ListViewValue");
        idHolder = getIntent().getStringExtra("id");

        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openEditGameSelectionDialog(TempItem);
            }
        });


    }


    public void HttpWebCall(final String PreviousListViewClickedItem) {

        class HttpWebCallFunction extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ShowSingleRecordActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                FinalJSonObject = httpResponseMsg;

                new GetHttpResponse(ShowSingleRecordActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id", params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                if (FinalJSonObject != null) {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);

                            priceHolder = jsonObject.getString("price");
                            gameHolder = jsonObject.getString("game");
                            weaponHolder = jsonObject.getString("weapon");
                            dateHolder = jsonObject.getString("date");
                            playersHolder = jsonObject.getString("numberOfParticipants");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            PRICE.setText(priceHolder);
            GAME.setText(gameHolder);
            WEAPON.setText(weaponHolder);
            DATE.setText(dateHolder);
            NUMBEROFPARTICIPANTS.setText(playersHolder);

        }
    }


    public void orderUpdate(final String idHolder, final String gameType, final String number, final String weaponType, final String date) {

        class orderRecordUpdateClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ShowSingleRecordActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(ShowSingleRecordActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                HttpWebCall(TempItem);

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("id", params[0]);

                hashMap.put("game", params[1]);

                hashMap.put("numberOfParticipants", params[2]);

                hashMap.put("weapon", params[3]);

                hashMap.put("date", params[4]);


                finalResult = httpParse.postRequest(hashMap, HttpURLUpdate);

                return finalResult;
            }
        }

        orderRecordUpdateClass orderRecordUpdateClass = new orderRecordUpdateClass();

        orderRecordUpdateClass.execute(idHolder, gameType, number, weaponType, date);
    }

    final CharSequence[] numberOFParticipantsArray = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private void openEditGameSelectionDialog(final String idHolder) {
        new AlertDialog.Builder(this).setTitle("Wybierz grę").setItems(R.array.games, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String gameType;
                if (which == 0) {
                    gameType = "Dwa zespoły";
                } else if (which == 1) {
                    gameType = "Zdobądź flagę";
                } else if (which == 2) {
                    gameType = "Pojedynek rewolwerowców";
                } else if (which == 3) {
                    gameType = "Dwie flagi";
                } else
                    gameType = "Utrzymaj VIPa przy życiu";

                openEditPlayerSelectionDialog(idHolder, gameType);
            }
        }).show();
    }

    private void openEditPlayerSelectionDialog(final String idHolder, final String gameType) {
        new AlertDialog.Builder(this).setTitle("Wybierz liczbę uczestników").setItems(numberOFParticipantsArray, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String number;
                if (which == 0)
                    number = "2";
                else if (which == 1)
                    number = "3";
                else if (which == 2)
                    number = "4";
                else if (which == 3)
                    number = "5";
                else if (which == 4)
                    number = "6";
                else if (which == 5)
                    number = "7";
                else if (which == 6)
                    number = "8";
                else if (which == 7)
                    number = "9";
                else
                    number = "10";
                openEditWeaponsSelectionDialog(idHolder, gameType, number);
            }
        }).show();

    }

    protected void openEditWeaponsSelectionDialog(final String idHolder, final String gameType, final String number) {
        new AlertDialog.Builder(this).setTitle("Wybierz broń dla zawodników").setItems(R.array.weapons, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String weaponType;
                if (which == 0)
                    weaponType = "Pompka";
                else if (which == 1)
                    weaponType = "Pneumatyczna";
                else if (which == 2)
                    weaponType = "Elektro-pneumatyczna";
                else
                    weaponType = "Elektroniczna";
                openEditDatePickerSelectionDialog(idHolder, gameType, number, weaponType);
            }
        }).show();
    }

    private void openEditDatePickerSelectionDialog(final String idHolder, final String gameType, final String number, final String weaponType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);

        builder.setTitle("Wybierz datę");
        builder.setView(picker);
        builder.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int month = picker.getMonth() + 1;
                String currentDateString = String.valueOf(picker.getDayOfMonth() + "." + month + "." + picker.getYear());
                openEditTimePickerPickerSelectionDialog(idHolder, gameType, number, weaponType, currentDateString);
            }
        });
        builder.show();
    }

    private void openEditTimePickerPickerSelectionDialog(final String idHolder, final String gameType, final String number, final String weaponType, final String currentDateString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TimePicker picker = new TimePicker(this);

        builder.setTitle("Wybierz godzinę");
        builder.setView(picker);
        builder.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String date = currentDateString + " " + picker.getCurrentHour() + ":" + picker.getCurrentMinute();
                orderUpdate(idHolder, gameType, number, weaponType, date);
            }
        });
        builder.show();
    }

}
