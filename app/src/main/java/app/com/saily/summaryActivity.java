package app.com.saily;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saily on 15-06-2018.
 */

public class summaryActivity extends AppCompatActivity {


    final Context context = this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);



        ImageView imageView=findViewById(R.id.profilePicture);
        TextView etHeight=(TextView) findViewById(R.id.sHeight);
        TextView etDate=(TextView) findViewById(R.id.sDOB);
        TextView name=(TextView) findViewById(R.id.textFullName);
        TextView gender=(TextView) findViewById(R.id.sGender);
        TextView email=(TextView) findViewById(R.id.sEmail);
        TextView interest=(TextView) findViewById(R.id.sInterest);
        TextView age=(TextView) findViewById(R.id.sAge);
        TextView race=(TextView) findViewById(R.id.sRace);
        TextView religion=(TextView) findViewById(R.id.sReligion);
        Button button=(Button)findViewById(R.id.register_button);

        final Bundle extras=getIntent().getExtras();


        etHeight.setText(extras.getString("HEIGHT"));
        etDate.setText(extras.getString("DATE"));
        name.setText(extras.getString("FULLNAME"));
        gender.setText(extras.getString("GENDER"));
        race.setText(extras.getString("RACE"));
        age.setText("18-"+extras.getString("AGE"));
        religion.setText(extras.getString("RELIGION"));
        email.setText(extras.getString("EMAIL"));
        interest.setText(extras.getString("INTEREST"));
        byte[] byteArray= extras.getByteArray("IMAGE");


        //profile picture image
        if(byteArray!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);}


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //public JSONObject HttpRequestJSON;
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            public void onClick(View v) {
                JSONObject HttpRequestJSON = new JSONObject();
                JSONObject HttpresponseJSON ;
                String  status="";
                try {


                  //getting the values from previous pages
                    HttpRequestJSON.put("height", extras.getString("HEIGHT"));
                    HttpRequestJSON.put("date",extras.getString("DATE"));
                    HttpRequestJSON.put("name",extras.getString("FULLNAME"));
                    HttpRequestJSON.put("gender",extras.getString("DATE"));
                    HttpRequestJSON.put("race",extras.getString("DATE"));
                    HttpRequestJSON.put("age",extras.getString("DATE"));
                    HttpRequestJSON.put("religion",extras.getString("DATE"));
                    HttpRequestJSON.put("email",extras.getString("DATE"));
                    HttpRequestJSON.put("interest",extras.getString("INTEREST"));
                    HttpRequestJSON.put("image",extras.getByteArray("IMAGE"));
                }
                catch ( JSONException err ) {
                    err.printStackTrace();
                }


                //CODE TO GIVE HTTP REQUEST

                Router process = new Router();
                try {

                    HttpresponseJSON = process.execute(HttpRequestJSON).get();
                    status=HttpresponseJSON.get("status").toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                //Alert for Successful Registration
                if (!status.isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setTitle("Success ");
                    alertDialogBuilder
                            .setMessage("Registration Successful!")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
//
//        intent.putExtra("FULLNAME",extras.getString("FULLNAME"));
//        intent.putExtra("FIRSTNAME", extras.getString("FIRSTNAME"));
        });
    }
}