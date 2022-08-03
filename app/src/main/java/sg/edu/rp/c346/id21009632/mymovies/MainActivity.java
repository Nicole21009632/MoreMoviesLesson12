package sg.edu.rp.c346.id21009632.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etYear, etGenre;
    Button btnShow, btnInsert;
    Spinner spinRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        spinRating = findViewById(R.id.spinRating);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!etTitle.getText().toString().equals("")) && (!etGenre.getText().toString().equals("")) && (!etYear.getText().toString().equals(""))) {
                    String movieTitle = etTitle.getText().toString();
                    String genre = etGenre.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    String rating = spinRating.getSelectedItem().toString();

                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    DBHelper dbh = new DBHelper(MainActivity.this);

                    if (year >= 1888 && year <= currentYear) { // 1888 is the year of the oldest film - google
                        long inserted_id = dbh.insertMovie(movieTitle, genre, year, rating);
                        if (inserted_id != -1) {
                            Toast.makeText(MainActivity.this, "Added " + movieTitle + " to the movie list successfully!", Toast.LENGTH_LONG).show();

                            etTitle.setText("");
                            etGenre.setText("");
                            etYear.setText("");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter a year after 1888 and before the current year", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields in the form correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(i);
            }
        });
    }
}