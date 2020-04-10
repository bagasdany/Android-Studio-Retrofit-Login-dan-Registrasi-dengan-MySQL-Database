package com.example.nayatiapp.TrackingPulang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.example.nayatiapp.DashboardActivity;
import com.example.nayatiapp.LoginPackage.SessionManager;
import com.example.nayatiapp.R;
import com.example.nayatiapp.TrackingDatang.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class PulangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterPulang adapter;
    private List<Pulang> pulangList;
    ApiInterface apiInterface;
    AdapterPulang.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    SessionManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulang);
        getSupportActionBar().setTitle("Validasi Akhir Kegiatan");
        sm = new SessionManager(PulangActivity.this);

        HashMap<String, String> map = sm.getDetailLogin();
        getSupportActionBar().setTitle("Wellcome"+ "\t" + map.get(sm.KEY_name));


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterPulang.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(PulangActivity.this, EditorPulangActivity.class);
                intent.putExtra("id", pulangList.get(position).getId());
                intent.putExtra("nameP", pulangList.get(position).getNameP());
                intent.putExtra("catatanP", pulangList.get(position).getCatatanP());
                intent.putExtra("lokasiP", pulangList.get(position).getLokasiP());
                intent.putExtra("genderP", pulangList.get(position).getGenderP());
                intent.putExtra("pictureP", pulangList.get(position).getPictureP());
                intent.putExtra("tanggalP", pulangList.get(position).getTanggalP());
                startActivity(intent);

            }

            @Override
            public void onLoveClick(View view, int position) {

                final int id = pulangList.get(position).getId();
                final Boolean love = pulangList.get(position).getLoveP();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    pulangList.get(position).setLoveP(false);
                    updateLovePulang("update_love", id, false);
                    adapter.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    pulangList.get(position).setLoveP(true);
                    updateLovePulang("update_love", id, true);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PulangActivity.this, EditorPulangActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);

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

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent i = new Intent(PulangActivity.this, DashboardActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPets(){

        HashMap<String, String> map = sm.getDetailLogin();
        Call<List<Pulang>> call = apiInterface.getPetsPulang(Integer.parseInt(map.get(sm.KEY_id)));
        call.enqueue(new Callback<List<Pulang>>() {
            @Override
            public void onResponse(Call<List<Pulang>> call, Response<List<Pulang>> response) {
                progressBar.setVisibility(View.GONE);
                pulangList = response.body();
                Log.i(PulangActivity.class.getSimpleName(), response.body().toString());
                adapter = new AdapterPulang(pulangList, PulangActivity.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pulang>> call, Throwable t) {
                Toast.makeText(PulangActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLovePulang(final String key, final int id, final Boolean love){

        Call<Pulang> call = apiInterface.updateLovePulang(key, id, love);

        call.enqueue(new Callback<Pulang>() {
            @Override
            public void onResponse(Call<Pulang> call, Response<Pulang> response) {

                Log.i(PulangActivity.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(PulangActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PulangActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pulang> call, Throwable t) {
                Toast.makeText(PulangActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPets();
    }
}
