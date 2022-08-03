package sg.edu.rp.c346.id21009632.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class EditDeleteActivity extends AppCompatActivity {
    TextView tvTitle, tvMovieID, tvyear, tvGenre, tvrating, tvID;
    EditText etTitle, etGenre, etyear;
    Spinner spinRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        tvID = findViewById(R.id.tvID);
        tvMovieID = findViewById(R.id.tvMovieID);

        tvrating = findViewById(R.id.tvrating);
        tvTitle = findViewById(R.id.tvTitle);
        tvyear = findViewById(R.id.tvyear);
        tvGenre = findViewById(R.id.tvGenre);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etyear = findViewById(R.id.etyear);
        spinRating = findViewById(R.id.spinRating);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");
        tvID.setText("Movie ID : " + '\n' + data.getId() + "");

        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etyear.setText(data.getYear() + "");

        if (data.getRating().equals("G")) {
            int position = 0;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("PG")) {
            int position = 1;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("PG13")) {
            int position = 2;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("NC16")) {
            int position = 3;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("M18")) {
            int position = 4;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("R21")) {
            int position = 5;
            spinRating.setSelection(position);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditDeleteActivity.this);

                if ((!etTitle.getText().toString().equals("")) && (!etGenre.getText().toString().equals("")) && (!etyear.getText().toString().equals(""))) {
                    data.setTitle(etTitle.getText().toString());
                    data.setGenre(etGenre.getText().toString());
                    data.setYear(Integer.parseInt(etyear.getText().toString()));
                    data.setRating(spinRating.getSelectedItem().toString());

                    int year = Integer.parseInt(etyear.getText().toString());
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    if (year >= 1888 && year <= currentYear) {
                        dbh.updateMovie(data);
                        dbh.close();
                        finish();

                        Toast.makeText(EditDeleteActivity.this, data.getTitle() + " has been updated!", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(EditDeleteActivity.this, "Please enter a year after 1888 and before the current year", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditDeleteActivity.this, "Please fill in all fields in the form correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditDeleteActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle());
                myBuilder.setCancelable(false);


                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DBHelper dbh = new DBHelper(EditDeleteActivity.this);
                        int id = data.getId();
                        Log.d("Movie id: ", id + "");

                        dbh.deleteMovie(data.getId());
                        Toast.makeText(EditDeleteActivity.this, data.getTitle() + " has been deleted", Toast.LENGTH_LONG).show();

                        finish();
                    }
                });
                myBuilder.setPositiveButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieTitle = data.getTitle();
                String genre = data.getGenre();
                int year = data.getYear();
                String rating = data.getRating().toString();

                String newMovieTite = etTitle.getText().toString();
                String newGenre = etGenre.getText().toString();
                int newYear = Integer.parseInt(etyear.getText().toString());
                String newRating = spinRating.getSelectedItem().toString();

                if ((!movieTitle.equals(newMovieTite)) || (!genre.equals(newGenre)) || (year != newYear) || (!rating.equals(newRating))) {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditDeleteActivity.this);
                    myBuilder.setTitle("Danger");
                    myBuilder.setMessage("Are you sure you want to discard the changes made?");
                    myBuilder.setCancelable(false);

                    myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                            Toast.makeText(EditDeleteActivity.this, "Your changes have been discarded", Toast.LENGTH_LONG).show();

                        }
                    });

                    myBuilder.setPositiveButton("Do not discard", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                } else {
                    finish();
                }
            }
        });
    }
}

