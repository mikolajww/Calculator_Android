package pl.mww.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class AdvancedCalculatorActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calculator);
        textView = findViewById(R.id.calculator_output);
        if (savedInstanceState != null) {
            tokenManager = new TokenManager(savedInstanceState.getStringArrayList("tokenList"));
            textView.setText(tokenManager.constructString());
        } else {
            tokenManager = new TokenManager();
        }
        for (int id : Calculator.advancedButtonIds) {
            initButtonHandler(id, true);
        }
        for (int id : Calculator.basicButtonIds) {
            initButtonHandler(id, false);
        }
        for(int id : Calculator.constantButtonIds) {
            initButtonHandler(id, false);
        }
        initSpecialButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("tokenList", tokenManager.getTokenList());
        super.onSaveInstanceState(outState);
    }

    private void initButtonHandler(int buttonId, boolean appendParen) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(click -> {
            String toAppend = button.getText().toString();
            if(appendParen) {
                toAppend = toAppend + "(";
            }
            tokenManager.append(toAppend);
            textView.setText(tokenManager.constructString());
        });
    }

    private void initSpecialButtons() {
        Button buttonEquals = findViewById(R.id.button_equals);
        buttonEquals.setOnClickListener(click -> {
            String result = Calculator.evaluateExpression(tokenManager.constructString());
            if (result.equalsIgnoreCase("NaN") || result.equalsIgnoreCase("Syntax Error")) {
                Toast.makeText(getApplicationContext(), "Invalid operation", Toast.LENGTH_LONG).show();
                return;
            }
            textView.setText(result);
            tokenManager.clearTokens();
            for (char c : result.toCharArray()) {
                tokenManager.append(Character.toString(c));
            }
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
        Button buttonExp = findViewById(R.id.button_exp);
        buttonExp.setOnClickListener(click -> {
            tokenManager.append("^");
            textView.setText(tokenManager.constructString());
        });
        Button buttonSquare = findViewById(R.id.button_square);
        buttonSquare.setOnClickListener(click -> {
            tokenManager.append("^2");
            textView.setText(tokenManager.constructString());
        });
    }
}
