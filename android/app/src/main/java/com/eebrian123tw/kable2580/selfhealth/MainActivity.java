package com.eebrian123tw.kable2580.selfhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button shareButton;
    private Button exportImportButton;

    private Button stepButton;
    private Button sleepButton;
    private Button drinkButton;
    private Button usePhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize time zone information
        AndroidThreeTen.init(this);

        shareButton = findViewById(R.id.share_button);
        exportImportButton = findViewById(R.id.export_import_button);
        stepButton = findViewById(R.id.step_button);
        sleepButton = findViewById(R.id.sleep_button);
        drinkButton = findViewById(R.id.drink_button);
        usePhoneButton = findViewById(R.id.use_phone_button);

        shareButton.setOnClickListener(this);
        exportImportButton.setOnClickListener(this);
        stepButton.setOnClickListener(this);
        sleepButton.setOnClickListener(this);
        drinkButton.setOnClickListener(this);
        usePhoneButton.setOnClickListener(this);

        stepButton.setText("1234" + getString(R.string.step_string));
        sleepButton.setText("2.4" + getString(R.string.hour_string));
        drinkButton.setText("3000" + getString(R.string.cc_string));
        usePhoneButton.setText("2.3" + getString(R.string.hour_string));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.step_button:
                startActivity(new Intent(this, DetailDataActivity.class));
                break;
            case R.id.sleep_button:
                startActivity(new Intent(this, DetailDataActivity.class));
                break;
            case R.id.drink_button:
                startActivity(new Intent(this, DetailDataActivity.class));
                break;
            case R.id.use_phone_button:
                startActivity(new Intent(this, DetailDataActivity.class));
                break;
            case R.id.share_button:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "subject");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "body");
                startActivity(Intent.createChooser(sharingIntent, "title"));
                break;

            case R.id.export_import_button:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
