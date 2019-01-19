package com.jer.paintballApp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jer.paintballApp.R;
import com.jer.paintballApp.httpClasses.HttpParse;

import java.util.HashMap;

public class UserAreaActivity extends AppCompatActivity {

    TextView tvWelcomeMsg;
    Button addOrderBtn, showOrders;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    String finalResult;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://jeremy-paintball.000webhostapp.com/AddOrder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        String message = "Witaj " + name + " " + surname;
        tvWelcomeMsg.setText(message);

        addOrderBtn = (Button) findViewById(R.id.addOrderBtn);
        showOrders = (Button) findViewById(R.id.buttonShow);

        final String userId = intent.getStringExtra("id");

        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHowToDialog(userId);
            }
        });

        showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(UserAreaActivity.this, ShowAllOrdersActivity.class);
                intent.putExtra("id", userId.toString());
                UserAreaActivity.this.startActivity(intent);

            }
        });
    }

    private void openHowToDialog(final String userId) {
        new AlertDialog.Builder(this).setTitle("Jak zarezerwować").setMessage("Po tej wiadomości otrzymasz listę wyborów twojej rezerwacji:" +
                " mapę, rodzaj broni, liczbę uczestników oraz datę.").setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openGameSelectionDialog(userId);
                    }
                }).show();
    }

    private void openGameSelectionDialog(final String userId) {
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

                openPlayerSelectionDialog(userId, gameType);
            }
        }).show();
    }

    final CharSequence[] numberOFParticipantsArray = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private void openPlayerSelectionDialog(final String userId, final String gameType) {
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
                openWeaponsSelectionDialog(userId, gameType, number);
            }
        }).show();

    }

    protected void openWeaponsSelectionDialog(final String userId, final String gameType, final String number) {
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
                openDatePickerSelectionDialog(userId, gameType, number, weaponType);
            }
        }).show();
    }

    private void openDatePickerSelectionDialog(final String userId, final String gameType, final String number, final String weaponType) {
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
                openTimePickerSelectionDialog(userId, gameType, number, weaponType, currentDateString);
            }
        });
        builder.show();
    }

    private void openTimePickerSelectionDialog(final String userId, final String gameType, final String number, final String weaponType, final String currentDateString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TimePicker picker = new TimePicker(this);

        builder.setTitle("Wybierz godzinę");
        builder.setView(picker);
        builder.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String date = currentDateString + " " + picker.getCurrentHour() + ":" + picker.getCurrentMinute();
                addOrder(userId, "200", gameType, weaponType, date, number);
            }
        });
        builder.show();
    }

    public void addOrder(final String userId, final String price, final String game, final String weapon, final String date, final String numberOfParticipants) {

        class OrderRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UserAreaActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UserAreaActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id", params[0]);

                hashMap.put("price", params[1]);

                hashMap.put("game", params[2]);

                hashMap.put("weapon", params[3]);

                hashMap.put("date", params[4]);

                hashMap.put("numberOfParticipants", params[5]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        OrderRegistrationClass OrderRegistrationClass = new OrderRegistrationClass();

        OrderRegistrationClass.execute(userId, price, game, weapon, date, numberOfParticipants);
    }
}
