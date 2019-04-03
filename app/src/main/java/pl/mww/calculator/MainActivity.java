package pl.mww.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch modeSwitch;
    private Button exitButton;
    private Button basicButton;
    private Button advancedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppDarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();

        modeSwitch = findViewById(R.id.modeSwitch);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            modeSwitch.setChecked(true);
        }
        modeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                restart();
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                restart();
            }
        });
    }

    private void initButtons() {
        exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(click -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        basicButton = findViewById(R.id.basic);
        basicButton.setOnClickListener(click -> {
            Intent intent = new Intent(this, BasicCalculatorActivity.class);
            startActivity(intent);
        });

        advancedButton = findViewById(R.id.advanced);
        advancedButton.setOnClickListener(click -> {
            Intent intent = new Intent(this, AdvancedCalculatorActivity.class);
            startActivity(intent);
        });
    }

    private void restart() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
