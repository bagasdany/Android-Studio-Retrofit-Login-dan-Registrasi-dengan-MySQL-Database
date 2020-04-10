package com.example.nayatiapp.AddCustomer;

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

public class CustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterCustomer adapter;
    private List<Customer> customerList;
    ApiInterface apiInterface;
    AdapterCustomer.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        sm = new SessionManager(CustomerActivity.this);

        HashMap<String, String> map = sm.getDetailLogin();
        getSupportActionBar().setTitle("Hello"+ "\t" + map.get(sm.KEY_name));


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterCustomer.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(CustomerActivity.this, EditorAddCustActivity.class);
                intent.putExtra("customer_id", customerList.get(position).getCustomer_id());
                intent.putExtra("code", customerList.get(position).getCode());
                intent.putExtra("name", customerList.get(position).getName());
                intent.putExtra("address", customerList.get(position).getAddress());
                intent.putExtra("countrycd", customerList.get(position).getCountrycd());
                intent.putExtra("country", customerList.get(position).getCountry());
                intent.putExtra("city", customerList.get(position).getCity());
                intent.putExtra("phone", customerList.get(position).getPhone());
                intent.putExtra("fax", customerList.get(position).getFax());
                intent.putExtra("email", customerList.get(position).getEmail());
                intent.putExtra("top_code", customerList.get(position).getTop_code());
                intent.putExtra("currency", customerList.get(position).getCurrency());
                intent.putExtra("maxdisc", customerList.get(position).getMaxdisc());
                intent.putExtra("person", customerList.get(position).getPerson());
                intent.putExtra("user", customerList.get(position).getUser());
                intent.putExtra("tanggal", customerList.get(position).getTanggal());
                intent.putExtra("catcode", customerList.get(position).getCatcode());
                intent.putExtra("picture", customerList.get(position).getPicture());
                startActivity(intent);

            }

            @Override
            public void onLoveClick(View view, int position) {

                final int id = customerList.get(position).getCustomer_id();
                final Boolean love = customerList.get(position).getLove();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    customerList.get(position).setLove(false);
                    updateLove("update_love", id, false);
                    adapter.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    customerList.get(position).setLove(true);
                    updateLove("update_love", id, true);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, EditorAddCustActivity.class));
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back_cust, menu);

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

        if (id == R.id.action_pulang) {
            Intent i = new Intent(CustomerActivity.this, DashboardActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPetsCust(){

        Call<List<Customer>> call = apiInterface.getPetsCust();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse  (Call<List<Customer>> call, Response<List<Customer>> response) {
                progressBar.setVisibility(View.GONE);
                customerList = response.body();
                Log.i(CustomerActivity.class.getSimpleName(), response.body().toString());
                adapter = new AdapterCustomer(customerList, CustomerActivity.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLove(final String key, final int customer_id, final Boolean love){

        Call<Customer> call = apiInterface.updateLoveCustomer(key, customer_id, love);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                Log.i(CustomerActivity.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(CustomerActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPetsCust();
    }
}
