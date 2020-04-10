package com.example.nayatiapp.AddCustomer;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.nayatiapp.APIPackage.ApiClient;
import com.example.nayatiapp.APIPackage.ApiInterface;
import com.example.nayatiapp.DashboardActivity;
import com.example.nayatiapp.R;
import com.example.nayatiapp.TrackingDatang.EditorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditorAddCustActivity extends AppCompatActivity {


    Spinner mcountrycdspinner, mcountryspinner, mTOPcodespinner, mcurrencyspinner, mcatcodespinner;
    EditText mName, mcode, maddress, mcity, mphone, mfax, mEmail, mdisc, mperson, mUser, mtanggal;

    CircleImageView mPicture;
    FloatingActionButton mFabChoosePic;


    Calendar myCalendar = Calendar.getInstance();

    private int mCountrycode = 0;
    public static final int COUNTRYCD1 = 1;
    public static final int COUNTRYCD2 = 2;
    public static final int COUNTRYCD3 = 3;

    private int mCountry = 0;
    public static final int COUNTRY1 = 1;
    public static final int COUNTRY2 = 2;
    public static final int COUNTRY3 = 3;

    private int mTOP = 0;
    public static final int TOPCD1 = 1;
    public static final int TOPCD2 = 2;
    public static final int TOPCD3 = 3;

    private int mCurrency = 0;
    public static final int CURRENCY1 = 1;
    public static final int CURRENCY2 = 2;
    public static final int CURRENCY3 = 3;

    private int mCatcode = 0;
    public static final int CATCODE1 = 1;
    public static final int CATCODE2 = 2;
    public static final int CATCODE3 = 3;


    String name, code, address, city, phone, fax, email, disc, person, user, tanggal, picture;
    int customer_id, Countrycd, Country, TOP, currency, catcode;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_add_cust);

        mName = findViewById(R.id.name);
        mcode = findViewById(R.id.Code);
        maddress = findViewById(R.id.address);
        mcity = findViewById(R.id.city);
        mphone = findViewById(R.id.phone);
        mfax = findViewById(R.id.fax);
        mEmail = findViewById(R.id.email);
        mdisc = findViewById(R.id.maxdisc);
        mperson = findViewById(R.id.person);
        mUser = findViewById(R.id.user);
        mtanggal = findViewById(R.id.tanggal);

        mFabChoosePic = findViewById(R.id.fabChoosePic);
        mPicture = findViewById(R.id.picture);

        mcountrycdspinner = findViewById(R.id.countrycd);
        mcountryspinner = findViewById(R.id.country);
        mTOPcodespinner = findViewById(R.id.topcode);
        mcurrencyspinner = findViewById(R.id.currency);
        mcatcodespinner = findViewById(R.id.catcode);

        mtanggal.setFocusableInTouchMode(false);
        mtanggal.setFocusable(false);

        mtanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorAddCustActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        setupSpinner();
        Intent intent = getIntent();
        customer_id = intent.getIntExtra("customer_id", 0);
        code = intent.getStringExtra("code");
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        Countrycd = intent.getIntExtra("countrycd", 0);
        Country = intent.getIntExtra("country", 0);
        city = intent.getStringExtra("city");
        phone = intent.getStringExtra("phone");
        fax = intent.getStringExtra("fax");
        email = intent.getStringExtra("email");
        disc = intent.getStringExtra("maxdisc");
        person = intent.getStringExtra("person");
        user = intent.getStringExtra("user");
        TOP = intent.getIntExtra("top_code", 0);
        currency = intent.getIntExtra("currency", 0);
        catcode = intent.getIntExtra("catcode", 0);
        tanggal = intent.getStringExtra("tanggal");
        picture = intent.getStringExtra("picture");


        setDataFromIntentExtra();


    }

    private void setDataFromIntentExtra() {

        if (customer_id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + name.toString());

            mcode.setText(code);
            maddress.setText(address);
            mphone.setText(phone);
            mName.setText(name);
            mcity.setText(city);
            mphone.setText(phone);
            mEmail.setText(email);
            mfax.setText(fax);
            mdisc.setText(disc);
            mperson.setText(person);
            mUser.setText(user);
            mtanggal.setText(tanggal);


            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(EditorAddCustActivity.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

            switch (Countrycd) {
                case COUNTRYCD1:
                    mcountrycdspinner.setSelection(1);
                    break;
                case COUNTRYCD2:
                    mcountrycdspinner.setSelection(2);
                    break;
                default:
                    mcountrycdspinner.setSelection(3);
                    break;
            }
            switch (Country) {
                case COUNTRY1:
                    mcountryspinner.setSelection(1);
                    break;
                case COUNTRY2:
                    mcountryspinner.setSelection(2);
                    break;
                default:
                    mcountryspinner.setSelection(3);
                    break;
            }

            switch (TOP) {
                case TOPCD1:
                    mTOPcodespinner.setSelection(1);
                    break;
                case TOPCD2:
                    mTOPcodespinner.setSelection(2);
                    break;
                default:
                    mTOPcodespinner.setSelection(3);
                    break;
            }
            switch (catcode) {
                case CATCODE1:
                    mcatcodespinner.setSelection(1);
                    break;
                case CATCODE2:
                    mcatcodespinner.setSelection(2);
                    break;
                default:
                    mcatcodespinner.setSelection(3);
                    break;
            }
            switch (currency) {
                case CURRENCY1:
                    mcatcodespinner.setSelection(1);
                    break;
                case CURRENCY2:
                    mcatcodespinner.setSelection(2);
                    break;
                default:
                    mcatcodespinner.setSelection(3);
                    break;
            }


        } else {
            getSupportActionBar().setTitle("Add Activity");
        }
    }

    private void setupSpinner(){
        ArrayAdapter countrycdspinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_countrycd_options, android.R.layout.simple_spinner_item);
        countrycdspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mcountrycdspinner.setAdapter(countrycdspinnerAdapter);

        mcountrycdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.ID))) {
                        mCountrycode = COUNTRYCD1;
                    } else if (selection.equals(getString(R.string.DE))) {
                        mCountrycode = COUNTRYCD2;
                    } else {
                        mCountrycode = COUNTRYCD3;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCountrycode = 0;
            }
        });
        ArrayAdapter countryspinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_country_options, android.R.layout.simple_spinner_item);
        countrycdspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mcountryspinner.setAdapter(countryspinnerAdapter);

        mcountryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.country_Id))) {
                        mCountry = COUNTRY1;
                    } else if (selection.equals(getString(R.string.country_DE))) {
                        mCountry = COUNTRY2;
                    } else {
                        mCountry = COUNTRY3;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCountry = 0;
            }
        });
        ArrayAdapter TOPspinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_TOP_options, android.R.layout.simple_spinner_item);
        countrycdspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTOPcodespinner.setAdapter(TOPspinnerAdapter);

        mTOPcodespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.TOP1))) {
                        mTOP = TOPCD1;
                    } else if (selection.equals(getString(R.string.TOP2))) {
                        mTOP = TOPCD2;
                    } else {
                        mTOP = TOPCD3;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTOP = 0;
            }
        });

        ArrayAdapter CurrencyspinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_currency_options, android.R.layout.simple_spinner_item);
        countrycdspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mcurrencyspinner.setAdapter(CurrencyspinnerAdapter);

        mcurrencyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Currency1))) {
                        mCurrency = CURRENCY1;
                    } else if (selection.equals(getString(R.string.Currency2))) {
                        mCurrency = CURRENCY2;
                    } else {
                        mTOP = CURRENCY3;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCurrency = 0;
            }
        });
        ArrayAdapter CatcodespinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_catcode_options, android.R.layout.simple_spinner_item);
        countrycdspinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mcatcodespinner.setAdapter(CatcodespinnerAdapter);

        mcatcodespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mCatcode = CATCODE1;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mCatcode = CATCODE2;
                    } else {
                        mCatcode = CATCODE3;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCountry = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (customer_id == 0){

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);
            action.findItem(R.id.menu_home).setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                action.findItem(R.id.menu_home).setVisible(false);


                return true;
            case R.id.menu_save:
                //Save

                if (customer_id == 0) {

                    if (TextUtils.isEmpty(mName.getText().toString()) ||
                            TextUtils.isEmpty(mcode.getText().toString()) ||
                            TextUtils.isEmpty(maddress.getText().toString()) ||
                            TextUtils.isEmpty(mphone.getText().toString()) ||
                            TextUtils.isEmpty(mcity.getText().toString()) ||
                            TextUtils.isEmpty(mEmail.getText().toString()) ||
                            TextUtils.isEmpty(mfax.getText().toString()) ||
                            TextUtils.isEmpty(mdisc.getText().toString()) ||
                            TextUtils.isEmpty(mperson.getText().toString()) ||
                            TextUtils.isEmpty(mUser.getText().toString()) ||
                            TextUtils.isEmpty(mtanggal.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(EditorAddCustActivity.this);
                        dialog.setMessage("Are You Sure about the data");
                        dialog.setPositiveButton("Save" ,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                postData("insert");
                            }
                        });
                        dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        action.findItem(R.id.menu_edit).setVisible(false);
                        action.findItem(R.id.menu_save).setVisible(true);
                        action.findItem(R.id.menu_delete).setVisible(false);
                        action.findItem(R.id.menu_home).setVisible(true);

                        editMode();
                    }

                } else {

                    updateData("update", customer_id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);
                    action.findItem(R.id.menu_home).setVisible(true);


                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorAddCustActivity.this);
                dialog.setMessage("Delete this Activity?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", customer_id, picture);
                    }
                });
                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;

            case R.id.menu_home:

                Intent i = new Intent(EditorAddCustActivity.this, DashboardActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };

    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mtanggal.setText(sdf.format(myCalendar.getTime()));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        final int REQUEST_IMAGE_CAPTURE = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            bitmap = (Bitmap) extras.get("data");

            mPicture.setImageBitmap(bitmap);

        }
    }

    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String code = mcode.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String address = maddress.getText().toString().trim();
        int countrycd = mCountrycode;
        int country = mCountry;
        String city = mcity.getText().toString().trim();
        String phone = mphone.getText().toString().trim();
        String fax = mfax.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        int top_code = mTOP;
        int currency = mCurrency;
        String maxdisc = mdisc.getText().toString().trim();
        String person = mperson.getText().toString().trim();
        String user = mUser.getText().toString().trim();
        String tanggal = mtanggal.getText().toString().trim();
        int catcode = mCatcode;
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Customer> call = apiInterface.insertCustomer(key,code, name, address, countrycd, country, city, phone, fax, email, top_code, currency, maxdisc, person, user, tanggal,catcode, picture);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                progressDialog.dismiss();

                Log.i(EditorAddCustActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(EditorAddCustActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorAddCustActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int customer_id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String code = mcode.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String address = maddress.getText().toString().trim();
        int countrycd = mCountrycode;
        int country = mCountry;
        String city = mcity.getText().toString().trim();
        String phone = mphone.getText().toString().trim();
        String fax = mfax.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        int top_code = mTOP;
        int currency = mCurrency;
        String maxdisc = mdisc.getText().toString().trim();
        String person = mperson.getText().toString().trim();
        String user = mUser.getText().toString().trim();
        String tanggal = mtanggal.getText().toString().trim();
        int catcode = mCatcode;
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Customer> call = apiInterface.updateCustomer(key, customer_id, code, name,address, countrycd, country, city, phone, fax, email, top_code, currency, maxdisc, person, user, tanggal,catcode, picture);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                progressDialog.dismiss();

                Log.i(EditorAddCustActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorAddCustActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditorAddCustActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorAddCustActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int customer_id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Customer> call = apiInterface.deleteCustomer(key, customer_id, pic);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                progressDialog.dismiss();

                Log.i(EditorAddCustActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorAddCustActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorAddCustActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorAddCustActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("RestrictedApi")
    void readMode(){

        mcode.setFocusableInTouchMode(false);
        mName.setFocusableInTouchMode(false);
        maddress.setFocusableInTouchMode(false);
        mcity.setFocusableInTouchMode(false);
        mphone.setFocusableInTouchMode(false);
        mfax.setFocusableInTouchMode(false);
        mEmail.setFocusableInTouchMode(false);
        mdisc.setFocusableInTouchMode(false);
        mperson.setFocusableInTouchMode(false);
        mUser.setFocusableInTouchMode(false);
        mcode.setFocusable(false);
        mName.setFocusable(false);
        maddress.setFocusable(false);
        mcity.setFocusable(false);
        mphone.setFocusable(false);
        mfax.setFocusable(false);
        mEmail.setFocusable(false);
        mdisc.setFocusable(false);
        mperson.setFocusable(false);
        mUser.setFocusable(false);


        mcountrycdspinner.setEnabled(false);
        mcountryspinner.setEnabled(false);
        mTOPcodespinner.setEnabled(false);
        mcurrencyspinner.setEnabled(false);
        mcatcodespinner.setEnabled(false);
        mtanggal.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("RestrictedApi")
    private void editMode(){

        mcode.setFocusableInTouchMode(true);
        mName.setFocusableInTouchMode(true);
        maddress.setFocusableInTouchMode(true);
        mcity.setFocusableInTouchMode(true);
        mphone.setFocusableInTouchMode(true);
        mfax.setFocusableInTouchMode(true);
        mEmail.setFocusableInTouchMode(true);
        mdisc.setFocusableInTouchMode(true);
        mperson.setFocusableInTouchMode(true);
        mUser.setFocusableInTouchMode(true);

        mcountrycdspinner.setEnabled(true);
        mcountryspinner.setEnabled(true);
        mTOPcodespinner.setEnabled(true);
        mcurrencyspinner.setEnabled(true);
        mcatcodespinner.setEnabled(true);
        mtanggal.setEnabled(true);


        mFabChoosePic.setVisibility(View.VISIBLE);
    }

    public void onBackPressed(){
        Toast.makeText(this, "Selesaikan aktivitas anda", Toast.LENGTH_SHORT).show();
        //jadi yang awalnya klik tombol back maka akan kembali ke activity sebelumnya
        //kali ini ketika tombol back diklik maka
        //hanya muncul Toast
    }






}
