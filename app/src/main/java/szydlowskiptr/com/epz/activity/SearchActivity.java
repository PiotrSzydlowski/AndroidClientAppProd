package szydlowskiptr.com.epz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import szydlowskiptr.com.epz.R;

public class SearchActivity extends AppCompatActivity {
    View searchLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchLabel = findViewById(R.id.search_view_on_search);

    }
}