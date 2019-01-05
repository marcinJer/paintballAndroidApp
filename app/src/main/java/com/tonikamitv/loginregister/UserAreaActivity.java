package com.tonikamitv.loginregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class UserAreaActivity extends AppCompatActivity {

    TextView tvWelcomeMsg;
    Button addOrderBtn;

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

        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHowToDialog();
            }
        });
    }

    private void openHowToDialog() {
        new AlertDialog.Builder(this).setTitle("Jak zarezerwować").setMessage("Po tej wiadomości otrzymasz listę wyborów twojej rezerwacji:" +
                " mapę, rodzaj broni, liczbę uczestników oraz datę.").setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openGameSelectionDialog();
                    }
                }).show();
    }

    private void openGameSelectionDialog() {
        new AlertDialog.Builder(this).setTitle("Wybierz grę").setItems(R.array.games, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String gameType;
                if (which == 0){
                    gameType = "Dwa zespoły";
                }
                else if (which == 1){
                    gameType = "Zdobądź flagę";
                }
                else if (which == 2){
                    gameType = "Pojedynek rewolwerowców";
                }
                else if (which == 3) {
                    gameType = "Dwie flagi";
                }
                else
                    gameType = "Utrzymaj VIPa przy życiu";

                openPlayerSelectionDialog(gameType);
            }
        }).show();
    }

    final CharSequence[] numberOFParticipantsArray = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private void openPlayerSelectionDialog(final String gameType) {
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
                openWeaponsSelectionDialog(gameType, number);
            }
        }).show();

    }

    protected void openWeaponsSelectionDialog(final String gameType, final String number) {
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
                openDatePickerSelectionDialog(gameType, number, weaponType);
            }
        }).show();
    }

    private void openDatePickerSelectionDialog(final String gameType, final String number, final String weaponType) {
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
                openTimePickerPickerSelectionDialog(gameType, number, weaponType, currentDateString);
            }
        });
        builder.show();
    }

    private void openTimePickerPickerSelectionDialog(final String gameType, final String number, final String weaponType, final String currentDateString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TimePicker picker = new TimePicker(this);

        builder.setTitle("Wybierz godzinę");
        builder.setView(picker);
        builder.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String date = currentDateString + " " + picker.getCurrentHour() + ":" + picker.getCurrentMinute();
                //addEvent(200, gameType, weaponType, number, date);
            }
        });
        builder.show();
    }

    private void openEditGameSelectionDialog() {
        new AlertDialog.Builder(this).setTitle("Wybierz grę").setItems(R.array.games, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String gameType;
                if (which == 0){
                    gameType = "Dwa zespoły";
                }
                else if (which == 1){
                    gameType = "Zdobądź flagę";
                }
                else if (which == 2){
                    gameType = "Pojedynek rewolwerowców";
                }
                else if (which == 3) {
                    gameType = "Dwie flagi";
                }
                else
                    gameType = "Utrzymaj VIPa przy życiu";

                openEditPlayerSelectionDialog(gameType);
            }
        }).show();
    }

    private void openEditPlayerSelectionDialog(final String gameType) {
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
                openEditWeaponsSelectionDialog(gameType, number);
            }
        }).show();

    }

    protected void openEditWeaponsSelectionDialog(final String gameType, final String number) {
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
                openEditDatePickerSelectionDialog(gameType, number, weaponType);
            }
        }).show();
    }

    private void openEditDatePickerSelectionDialog(final String gameType, final String number, final String weaponType) {
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
                openEditTimePickerPickerSelectionDialog(gameType, number, weaponType, currentDateString);
            }
        });
        builder.show();
    }

    private void openEditTimePickerPickerSelectionDialog(final String gameType, final String number, final String weaponType, final String currentDateString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TimePicker picker = new TimePicker(this);

        builder.setTitle("Wybierz godzinę");
        builder.setView(picker);
        builder.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String date = currentDateString + " " + picker.getCurrentHour() + ":" + picker.getCurrentMinute();

                //editEvent(200, gameType, weaponType, number, date);

            }
        });
        builder.show();
    }

    /*private void addEvent(int i, String gameType, String weaponType, String number, String date) {

    }*/
}
