package com.example.form;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, ageEditText, emailEditText, phoneEditText, passwordEditText;
    private Spinner deptSpinner;
    private String name, age, email, phone, dept, password;
    private Button submit;

    // Define regex patterns
    private Pattern namePattern = Pattern.compile("^[a-zA-Z ._]+$"); // Name pattern
    private Pattern agePattern = Pattern.compile("^(1[0-9]|[1-9][0-9]|[1-9])$"); // Age pattern (1-120)
    private Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$"); // Email pattern
    private Pattern phonePattern = Pattern.compile("^[0-9]{11}$"); // Phone number pattern (11 digits)
    private Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&#]{8,}$"); // Password pattern (8 characters, 1 upper, 1 lower, 1 digit, 1 special)

    LinearLayout inputLayout, outputLayout;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEditText = findViewById(R.id.name);
        ageEditText = findViewById(R.id.age);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.num);
        passwordEditText = findViewById(R.id.pass); // Add this line for password
        deptSpinner = findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);
        inputLayout = findViewById(R.id.inputLayout);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);

        String[] items = new String[]{"Select Facial Type", "Herbal Facial", "Deep Cleansing", "Anti-Aging Facial", "Hydrating Facial", "Brightening Facial", "Sensitive Skin Facial", "Spa Facial", "Chemical Peel Facial"};
        deptSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items));
        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = deptSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                age = ageEditText.getText().toString();
                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                password = passwordEditText.getText().toString();

                // Validation logic
                if (name.isEmpty()) {
                    nameEditText.setError("Empty!!");
                    nameEditText.requestFocus();
                } else if (!namePattern.matcher(name).matches()) {
                    nameEditText.setError("Name can only contain alphabets");
                    nameEditText.requestFocus();
                } else if (age.isEmpty() || !agePattern.matcher(age).matches()) {
                    ageEditText.setError("Enter a valid age (1-120)");
                    ageEditText.requestFocus();
                } else if (email.isEmpty() || !emailPattern.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid email");
                    emailEditText.requestFocus();
                } else if (phone.isEmpty() || !phonePattern.matcher(phone).matches()) {
                    phoneEditText.setError("Enter a valid phone number (11 digits)");
                    phoneEditText.requestFocus();
                } else if (password.isEmpty() || !passwordPattern.matcher(password).matches()) {
                    passwordEditText.setError("Password must be at least 8 characters long, include uppercase, lowercase, number, and special character");
                    passwordEditText.requestFocus();
                } else if (Objects.equals(dept, "Select facial type")) {
                    Toast.makeText(getApplicationContext(), "Please Select Facial Type", Toast.LENGTH_SHORT).show();
                } else {
                    inputLayout.setVisibility(View.GONE);
                    outputLayout.setVisibility(View.VISIBLE);
                    String s = "Name: " + name  + "\nAge: " + age  + "\nEmail: " + email  + "\nMobile Number: " +phone  + "\nSelected Facial Type: " + dept;
                    outputText.setText(s);
                }
            }
        });
    }
}
