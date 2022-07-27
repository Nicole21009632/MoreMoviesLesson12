package sg.edu.rp.c346.id21009632.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    Button btnPG13Movies;
    ListView lvMovies;
    ArrayList<Movie> alMovie;
    CustomAdapter caMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        btnPG13Movies = findViewById(R.id.btnPG13Movies);
        lvMovies = findViewById(R.id.listViewMovie);

        alMovie = new ArrayList<Movie>();
        caMovie = new CustomAdapter(this, R.layout.row, alMovie);

        lvMovies.setAdapter(caMovie);

        btnPG13Movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowListActivity.this);
                alMovie.clear();
                alMovie.addAll(dbh.getAllPG13Movies());
                caMovie.notifyDataSetChanged();
                Toast.makeText(ShowListActivity.this, "Displaying all PG 13 Movies", Toast.LENGTH_LONG).show();
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
    }
}