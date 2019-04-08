package pl.mww.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class BasicCalculatorActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppDarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);
        textView = findViewById(R.id.calculator_output);
        for (int id : Calculator.basicButtonIds) {
            initButtonHandler(id);
        }
        initSpecialButtons();
        if(savedInstanceState != null) {
            tokenManager = new TokenManager(savedInstanceState.getStringArrayList("tokenList"));
            textView.setText(tokenManager.constructString());
        }
        else {
            tokenManager = new TokenManager();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("tokenList", tokenManager.getTokenList());
        super.onSaveInstanceState(outState);
    }

    private void initButtonHandler(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(click -> {
            tokenManager.append(button.getText().toString());
            textView.setText(tokenManager.constructString());
        });
    }

    private void initSpecialButtons() {
        Button buttonEquals = findViewById(R.id.button_equals);
        buttonEquals.setOnClickListener(click -> {
            String result = Calculator.evaluateExpression(tokenManager.constructString());
            if(result.equalsIgnoreCase("NaN")) {
                Toast.makeText(getApplicationContext(), "Invalid operation", Toast.LENGTH_LONG).show();
                return;
            }
            if(result.equalsIgnoreCase("Syntax Error")) {
                Toast.makeText(getApplicationContext(), "Syntax Error", Toast.LENGTH_LONG).show();
                return;
            }
            textView.setText(result);
            tokenManager.clearTokens();
            /*for(char c: result.toCharArray()) {
                tokenManager.append(Character.toString(c));
            }*/
            tokenManager.append(result);
        });
        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(click -> {
            tokenManager.removeToken();
            textView.setText(tokenManager.constructString());
        });
        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(click -> {
            tokenManager.clearTokens();
            textView.setText(tokenManager.constructString());
        });
    }


}
