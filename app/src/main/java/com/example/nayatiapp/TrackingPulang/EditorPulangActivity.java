package com.example.nayatiapp.TrackingPulang;

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
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.nayatiapp.APIPackage.ApiClient;
import com.example.nayatiapp.APIPackage.ApiInterface;
import com.example.nayatiapp.DashboardActivity;
import com.example.nayatiapp.LoginPackage.SessionManager;
import com.example.nayatiapp.R;
import com.example.nayatiapp.TrackingDatang.EditorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditorPulangActivity extends AppCompatActivity {

    SessionManager sm;

    TextView mUser;

    private Spinner mGenderSpinner;
    private EditText mName, mCatatan, mLokasi, mTanggal;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();

    private int mGender = 0;
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    private String nameP, catatanP, lokasiP, pictureP, tanggalP;
    private int id, genderP;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_pulang);

        mUser = findViewById(R.id.user);
        mName = findViewById(R.id.name);
        mCatatan = findViewById(R.id.catatan);
        mLokasi = findViewById(R.id.lokasi);
        mTanggal = findViewById(R.id.tanggal);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);

        mGenderSpinner = findViewById(R.id.gender);
        mTanggal = findViewById(R.id.tanggal);

        mTanggal.setFocusableInTouchMode(false);
        mTanggal.setFocusable(false);

        mTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorPulangActivity.this, date, myCalendar
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

        disableEditText(mName, mCatatan, mLokasi, mTanggal);
        disableSpinner(mGenderSpinner);

        setupSpinner();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        nameP = intent.getStringExtra("nameP");
        catatanP = intent.getStringExtra("catatanP");
        lokasiP = intent.getStringExtra("lokasiP");
        tanggalP = intent.getStringExtra("tanggalP");
        pictureP = intent.getStringExtra("pictureP");
        genderP = intent.getIntExtra("genderP", 0);

        setDataFromIntentExtra();

        sm = new SessionManager(EditorPulangActivity.this);
        HashMap<String, String> map = sm.getDetailLogin();
        mUser.setText(map.get(sm.KEY_name));
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + nameP.toString());

            mName.setText(nameP);
            mCatatan.setText(catatanP);
            mLokasi.setText(lokasiP);
            mTanggal.setText(tanggalP);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(EditorPulangActivity.this)
                    .load(pictureP)
                    .apply(requestOptions)
                    .into(mPicture);

            switch (genderP) {
                case GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }

        } else {
            getSupportActionBar().setTitle("Add Activity");
        }
    }

    private void setupSpinner(){
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else {
                        mGender = GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){

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

                if (id == 0) {


                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditorPulangActivity.this);
                    dialog.setMessage("Are you sure about the data");
                    dialog.setPositiveButton("Save" ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            postData("insert");
                            Intent i = new Intent(EditorPulangActivity.this, DashboardActivity.class);
                            startActivity(i);
                            Toast.makeText(EditorPulangActivity.this, "Anda Telah Selesai Melakukan Pencatatan Aktivitas", Toast.LENGTH_SHORT).show();
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



                } else {

                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);
                    action.findItem(R.id.menu_home).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorPulangActivity.this);
                dialog.setMessage("Delete this Activity?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id, pictureP);
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

                Intent i = new Intent(EditorPulangActivity.this, DashboardActivity.class);
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

        mTanggal.setText(sdf.format(myCalendar.getTime()));
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
        HashMap<String, String> map = sm.getDetailLogin();
        int id_user = Integer.parseInt(map.get(sm.KEY_id));
        String userP = map.get(sm.KEY_name);
        String nameP = mName.getText().toString().trim();
        String catatanP = mCatatan.getText().toString().trim();
        String lokasiP = mLokasi.getText().toString().trim();
        int genderP = mGender;
        String tanggalP = mTanggal.getText().toString().trim();
        String pictureP = null;
        if (bitmap == null) {
            pictureP = "";
        } else {
            pictureP = getStringImage(bitmap);
        }


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pulang> call = apiInterface.insertTrackPulang(key, id_user, userP, nameP, catatanP, lokasiP, genderP, tanggalP, pictureP);

        call.enqueue(new Callback<Pulang>() {
            @Override
            public void onResponse(Call<Pulang> call, Response<Pulang> response) {

                progressDialog.dismiss();

                Log.i(EditorPulangActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(EditorPulangActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pulang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorPulangActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        HashMap<String, String> map = sm.getDetailLogin();
        int id_user = Integer.parseInt(map.get(sm.KEY_id));
        String userP = map.get(sm.KEY_name);
        String nameP = mName.getText().toString().trim();
        String catatanP = mCatatan.getText().toString().trim();
        String lokasiP = mLokasi.getText().toString().trim();
        int genderP = mGender;
        String tanggalP = mTanggal.getText().toString().trim();
        String pictureP = null;
        if (bitmap == null) {
            pictureP = "";
        } else {
            pictureP = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pulang> call;
        call = apiInterface.updateTrackPulang(key, id, id_user,  userP,  nameP, catatanP, lokasiP, genderP, tanggalP, pictureP);

        call.enqueue(new Callback<Pulang>() {
            @Override
            public void onResponse(Call<Pulang> call, Response<Pulang> response) {

                progressDialog.dismiss();

                Log.i(EditorPulangActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorPulangActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditorPulangActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pulang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorPulangActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pulang> call = apiInterface.deleteTrackPulang(key, id, pic);

        call.enqueue(new Callback<Pulang>() {
            @Override
            public void onResponse(Call<Pulang> call, Response<Pulang> response) {

                progressDialog.dismiss();

                Log.i(EditorPulangActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorPulangActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorPulangActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pulang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorPulangActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("RestrictedApi")
    void readMode(){

        mName.setFocusableInTouchMode(false);
        mCatatan.setFocusableInTouchMode(false);
        mLokasi.setFocusableInTouchMode(false);
        mName.setFocusable(false);
        mCatatan.setFocusable(false);
        mLokasi.setFocusable(false);

        mGenderSpinner.setEnabled(false);
        mTanggal.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("RestrictedApi")
    private void editMode(){

        mName.setFocusableInTouchMode(true);
        mCatatan.setFocusableInTouchMode(true);
        mLokasi.setFocusableInTouchMode(true);

        mGenderSpinner.setEnabled(true);
        mTanggal.setEnabled(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

    public void onBackPressed(){
        Toast.makeText(this, "Selesaikan aktivitas anda", Toast.LENGTH_SHORT).show();
        //jadi yang awalnya klik tombol back maka akan kembali ke activity sebelumnya
        //kali ini ketika tombol back diklik maka
        //hanya muncul Toast
    }

    private void disableEditText(EditText mName, EditText mCatatan, EditText mLokasi, EditText mTanggal) {
        mName.setFocusable(false);
        mName.setEnabled(false);
        mName.setCursorVisible(false);
        mName.setKeyListener(null);
        mName.setBackgroundColor(Color.TRANSPARENT);
        mCatatan.setFocusable(false);
        mCatatan.setEnabled(false);
        mCatatan.setCursorVisible(false);
        mCatatan.setKeyListener(null);
        mCatatan.setBackgroundColor(Color.TRANSPARENT);
        mLokasi.setFocusable(false);
        mLokasi.setEnabled(false);
        mLokasi.setCursorVisible(false);
        mLokasi.setKeyListener(null);
        mLokasi.setBackgroundColor(Color.TRANSPARENT);
        mTanggal.setFocusable(false);
        mTanggal.setEnabled(false);
        mTanggal.setCursorVisible(false);
        mTanggal.setKeyListener(null);
        mTanggal.setBackgroundColor(Color.TRANSPARENT);
    }
    private void disableSpinner(Spinner mGenderSpinner){
        mGenderSpinner.setFocusable(false);
        mGenderSpinner.setEnabled(false);
        mGenderSpinner.setBackgroundColor(Color.TRANSPARENT);
    }

}
