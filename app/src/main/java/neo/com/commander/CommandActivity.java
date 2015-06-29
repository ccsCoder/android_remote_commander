package neo.com.commander;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class CommandActivity extends ActionBarActivity {
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize Firebase Context
        this.initFirebase();

        setContentView(R.layout.activity_command);
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        System.out.println("Connecting to neo's firebase...");
        this.firebaseRef = new Firebase("https://fiery-fire-4989.firebaseio.com/");
        System.out.println("Connected to Firebase!" + this.firebaseRef);
    }


    /**
     * Method to write to firebase.
     * @param commandText
     */
    private void writeToFirebase(final String commandText) {

        this.firebaseRef.child("whatToExecute").setValue(commandText, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError ferror, Firebase firebase) {
                if (ferror != null) {
                    System.out.println("OOPS!! Couldn't write to Firebase! " + ferror.getMessage());
                } else {
                    System.out.println("Write Successful.");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_command, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doExecute(View view) {
        final TextView textBox = (TextView) findViewById(R.id.editText);
        String command = textBox.getText().toString();
        if(command==null || command.length()==0) {
            Toast.makeText(getApplicationContext(), "Enter a Command", Toast.LENGTH_SHORT).show();
            return;
        }

        //we have something to work with.
        Toast.makeText(getApplicationContext(),"You Entered :"+command, Toast.LENGTH_SHORT).show();


    }
}
