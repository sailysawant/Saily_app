package app.com.saily;


import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by saily on 15-06-2018.
 */

public class detailActivity extends AppCompatActivity {

    TextView textAge;
    int progress=18;
    String date="";
    String gender="";
    String race="";
    String religion="";
    String age="";
    String height="";
    String interest=null;

    final Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final EditText etHeight=(EditText)findViewById(R.id.etHeight);
        final EditText etDate=(EditText)findViewById(R.id.etDate);
        final Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        final Spinner raceSpinner = (Spinner) findViewById(R.id.raceSpinner);
        final Spinner religionSpinner = (Spinner) findViewById(R.id.religionSpinner);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final CheckBox male = (CheckBox) findViewById(R.id.checkBoxM);
        final CheckBox female = (CheckBox) findViewById(R.id.checkBoxF);
        textAge=(TextView)findViewById(R.id.textAge);
        final Button reviewButton = (Button) findViewById(R.id.review_button);



        //seekBar
        seekBar.setMax(100);
        seekBar.setProgress(progress);

        textAge.setText(""+progress);
        textAge.setTextSize(progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress=i;
                textAge.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progress<18){
                    Toast.makeText(context, "Age should not be less than 18 years!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //gender array
        ArrayAdapter<CharSequence> genderArrayAdapter= ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderArrayAdapter);

        genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });


        //race array
        ArrayAdapter<CharSequence> raceArrayAdapter= ArrayAdapter.createFromResource(this, R.array.race_array, android.R.layout.simple_spinner_item);
        raceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(raceArrayAdapter);

        raceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });


        //religion array
        ArrayAdapter<CharSequence> religionArrayAdapter= ArrayAdapter.createFromResource(this, R.array.religion_array, android.R.layout.simple_spinner_item);
        religionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religionSpinner.setAdapter(religionArrayAdapter);

        genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //public JSONObject HttpRequestJSON;
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            public void onClick(View v) {




                height = etHeight.getText().toString();
                date = etDate.getText().toString();
                gender=genderSpinner.getSelectedItem().toString();
                race=raceSpinner.getSelectedItem().toString();
                religion=religionSpinner.getSelectedItem().toString();
                age=textAge.getText().toString();
                if(male.isChecked()){
                    interest="male";
                }
                if(female.isChecked()){
                    interest="female";
                }


                //date validation
                Pattern date_pattern;
                Matcher matcher2;
                String pattern_birth = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
                date_pattern = Pattern.compile(pattern_birth);
                matcher2 = date_pattern.matcher(date);

                if (date.isEmpty()) {    //if DOB field is Empty

                    etDate.setError("Birthdate format:mm/dd/yyyy");



                }else if (!matcher2.find() && !date.isEmpty()) {     //Incorrect DOB format

                    etDate.setError("Birthdate format:mm/dd/yyyy");
                }


                else if (height.isEmpty()) {    //if height field is Empty

                    etHeight.setError("Enter Height");
                }

                else if(interest==null){    //if interest in gender field is Empty

                    TextView gender=findViewById(R.id.textGender);
                    gender.setError("Select Gender");
                }

                else{
                    final Bundle extras=getIntent().getExtras();
                    Intent intent = new Intent(detailActivity.this, summaryActivity.class);

                    intent.putExtra("HEIGHT", height);
                    intent.putExtra("DATE", date);
                    intent.putExtra("GENDER", gender);
                    intent.putExtra("RACE", race);
                    intent.putExtra("RELIGION", religion);
                    intent.putExtra("AGE", age);
                    intent.putExtra("INTEREST", interest);
                    intent.putExtra("FULLNAME", extras.getString("FULLNAME"));
                    intent.putExtra("EMAIL", extras.getString("EMAIL"));
                    intent.putExtra("PASSWORD", extras.getString("PASSWORD"));
                    intent.putExtra("ZIPCODE", extras.getString("ZIPCODE"));
                    intent.putExtra("IMAGE",extras.getByteArray("IMAGE"));


                    detailActivity.this.startActivity(intent);
                }

            }
        });
    }
}
