package sg.edu.rp.c346.id21009632.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    Button btnBack;
    ListView lvMovies;
    ArrayList<Movie> alMovie;
    ArrayList<String> alRating;
    ArrayAdapter<String> aaRating;
    CustomAdapter caMovie;
    Spinner spinnerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        btnBack = findViewById(R.id.btnBackToInsertMovie);
        lvMovies = findViewById(R.id.listViewMovie);
        spinnerRating = findViewById(R.id.spinnerRating);

        alRating = new ArrayList<String>();
        aaRating = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, alRating);
        spinnerRating.setAdapter(aaRating);

        alMovie = new ArrayList<Movie>();
        caMovie = new CustomAdapter(this, R.layout.row, alMovie);

        lvMovies.setAdapter(caMovie);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DBHelper dbh = new DBHelper(ShowListActivity.this);
                String rating = spinnerRating.getSelectedItem().toString();

                if (rating != "Show all movies") {
                    alMovie.clear();
                    alMovie.addAll(dbh.getAllMoviesByRating(rating));
                    caMovie.notifyDataSetChanged();

                    alMovie.clear();
                    alMovie.addAll(dbh.getAllMoviesByRating(rating));
                    caMovie.notifyDataSetChanged();

                    Toast.makeText(ShowListActivity.this, "Displaying all songs rated " + rating, Toast.LENGTH_SHORT).show();
                } else {
                    alMovie.clear();
                    alMovie.addAll(dbh.getAllMovies());
                    caMovie.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Movie data = alMovie.get(position);
                Intent i = new Intent(ShowListActivity.this, EditDeleteActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ShowListActivity.this);
        alMovie.clear();
        alMovie.addAll(dbh.getAllMovies());
        caMovie.notifyDataSetChanged();

        alRating.clear();
        alRating.add("Show all movies");
        alRating.addAll(dbh.getAllMoviesByRating());
        aaRating.notifyDataSetChanged();
    }

}