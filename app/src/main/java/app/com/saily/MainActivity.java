package app.com.saily;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.util.Patterns.EMAIL_ADDRESS;

/**
 * Created by saily on 15-06-2018.
 */
public class MainActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;

    //alert box
    final Context context = this;


    String createEmail;
    String createPassword;
    String fullName;
    String zipcode;
    byte[] byteArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText etFullName = (EditText) findViewById(R.id.etFullName);
        final EditText etCreateEmail = (EditText) findViewById(R.id.etCreateEmail);
        final EditText etCreatePassword = (EditText) findViewById(R.id.etCreatePassword);
        final EditText etZipcode = (EditText) findViewById(R.id.etZipcode);
        final Button signUpNextButton = (Button) findViewById(R.id.signUpNextButton);
        final ImageView profilePicture = (ImageView) findViewById(R.id.profilePicture);


        //profile picture selection
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        signUpNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            public void onClick(View v) {

                createEmail = etCreateEmail.getText().toString();
                createPassword = etCreatePassword.getText().toString();
                fullName = etFullName.getText().toString();
                zipcode = etZipcode.getText().toString();


                //validation for fullname
                Pattern pattern = Pattern.compile("\\s");
                Matcher matcher = pattern.matcher(fullName);

                //validation for password
                Pattern password_pattern;
                Matcher matcher1;
                String pattern_pwd = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
                password_pattern = Pattern.compile(pattern_pwd);
                matcher1 = password_pattern.matcher(createPassword);

                // validation for zipcode
                Pattern zip_pattern;
                Matcher matcher2;
                String pattern_zip = "^[0-9]{5}(-[0-9]{4})?$";
                zip_pattern = Pattern.compile(pattern_zip);
                matcher2 = zip_pattern.matcher(zipcode);

                if (!matcher.find() && !fullName.isEmpty()) {    //if fullname is not entered and field is not empty

                    etFullName.setError("Enter First & Last Name");
                }

                else if (fullName.isEmpty()) {     //if fullname is empty

                    etFullName.setError("Enter Name");

                }

                else if (createEmail.isEmpty()) {      //if email address is empty

                    etCreateEmail.setError("Enter email");
                }

                else if (!(EMAIL_ADDRESS.matcher(createEmail)).matches()) {  //Validation for email & if there is Invalid email address

                    etCreateEmail.setError("Enter a valid email address");
                }

                else if (createPassword.isEmpty()) {     //Password is Empty

                    etCreatePassword.setError("Enter Password");
                }

                else if (!matcher1.find() && !createPassword.isEmpty()) {      //Not satisfying conditions for password

                    etCreatePassword.setError("Password must be at least 8 characters and contain at least 1 number and letter");
                }

                else if (zipcode.equals("#####")) {    //if zipcode field is Empty

                    etZipcode.setError("Enter Zipcode");
                }

                else if (!matcher2.find() && !zipcode.equals("#####")) {     //Incorrect zipcode format

                    etZipcode.setError("Enter correct Zipcode");
                }
                else
                    {
                    // separating fullname
//                    String[] FN = fullName.split(" ");


                  //sending details to next page
                    Intent intent = new Intent(MainActivity.this, detailActivity.class);

                    intent.putExtra("FULLNAME", fullName);
                    intent.putExtra("EMAIL", createEmail);
                    intent.putExtra("PASSWORD", createPassword);
                    intent.putExtra("ZIPCODE", zipcode);
                    intent.putExtra("IMAGE",byteArray);

                    MainActivity.this.startActivity(intent);

                }
            }

        });
    }


      //cancelling profile picture and set default
    public void clear(View v){
        byteArray=null;
     ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
        imageView.setImageResource(R.drawable.avatar);
     }

      //profile picture
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();

            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }

    }
}
