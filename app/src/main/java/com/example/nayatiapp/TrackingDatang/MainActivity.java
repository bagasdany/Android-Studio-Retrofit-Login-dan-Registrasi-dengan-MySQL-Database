package com.example.nayatiapp.TrackingDatang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.nayatiapp.APIPackage.ApiClient;
import com.example.nayatiapp.APIPackage.ApiInterface;
import com.example.nayatiapp.LoginPackage.InfoActivity;
import com.example.nayatiapp.LoginPackage.SessionManager;
import com.example.nayatiapp.TrackingPulang.PulangActivity;
import com.example.nayatiapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Track> trackList;
    ApiInterface apiInterface;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    LocationManager locationManager;

    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sm = new SessionManager(MainActivity.this);

        HashMap<String, String> map = sm.getDetailLogin();
        getSupportActionBar().setTitle("Wellcome"+ "\t" + map.get(sm.KEY_name));


        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("id", trackList.get(position).getId());
                intent.putExtra("user", trackList.get(position).getUser());
                intent.putExtra("name", trackList.get(position).getName());
                intent.putExtra("catatan", trackList.get(position).getCatatan());
                intent.putExtra("lokasi", trackList.get(position).getLokasi());
                intent.putExtra("gender", trackList.get(position).getGender());
                intent.putExtra("picture", trackList.get(position).getPicture());
                intent.putExtra("tanggal", trackList.get(position).getTanggal());
                startActivity(intent);

            }

            @Override
            public void onLoveClick(View view, int position) {

                final int id = trackList.get(position).getId();
                final Boolean love = trackList.get(position).getLove();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    trackList.get(position).setLove(false);
                    updateLove("update_love", id, false);
                    adapter.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    trackList.get(position).setLove(true);
                    updateLove("update_love", id, true);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, EditorActivity.class));

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())

        );
        searchView.setQueryHint("Search Activity");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapter.getFilter().getClass();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }


        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_pulang) {
            Intent i = new Intent(MainActivity.this, PulangActivity.class);
            startActivity(i);
            Toast.makeText(this, "Anda menginputkan aktivitas pulang", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPets(){
        HashMap<String, String> map = sm.getDetailLogin();
        Call<List<Track>> call = apiInterface.getPets(Integer.parseInt(map.get(sm.KEY_id)));
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse  (Call<List<Track>> call, Response<List<Track>> response) {
                progressBar.setVisibility(View.GONE);
                trackList = response.body();
                Log.i(MainActivity.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(trackList, MainActivity.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLove(final String key, final int id, final Boolean love){

        Call<Track> call = apiInterface.updateLove(key, id, love);

        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {

                Log.i(MainActivity.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPets();
    }
}
