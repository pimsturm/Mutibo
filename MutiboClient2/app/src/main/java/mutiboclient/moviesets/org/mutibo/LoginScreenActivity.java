package mutiboclient.moviesets.org.mutibo;

import java.util.Collection;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mutiboclient.moviesets.org.service.MutiboSyncService;
import mutiboclient.moviesets.org.service.UserSvc;
import mutiboclient.moviesets.org.service.UserSvcApi;

/**
 * 
 * This application uses ButterKnife. AndroidStudio has better support for
 * ButterKnife than Eclipse, but Eclipse was used for consistency with the other
 * courses in the series. If you have trouble getting the login button to work,
 * please follow these directions to enable annotation processing for this
 * Eclipse project:
 * 
 * http://jakewharton.github.io/butterknife/ide-eclipse.html
 * 
 */
public class LoginScreenActivity extends Activity {
    private static final String TAG = "Loginscreen";
    private String enteredUser;
    private String enteredPassword;

	@InjectView(R.id.userName)
	protected EditText userName_;

	@InjectView(R.id.password)
	protected EditText password_;

	@InjectView(R.id.server)
	protected EditText server_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		ButterKnife.inject(this);
	}

	@OnClick(R.id.loginButton)
	public void login() {
        enteredUser = userName_.getText().toString();
        enteredPassword = password_.getText().toString();
        final String server = server_.getText().toString();

        Log.d(TAG, "server: " + server);
        Log.d(TAG, "user: " + enteredUser);
        Log.d(TAG, "password: " + enteredPassword);

        SharedPreferences sharedPref = LoginScreenActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", enteredUser);
        editor.putInt("highscore", 10);
        editor.commit();

        Intent intent = new Intent(getBaseContext(), MutiboSyncService.class);
        intent.setData(Uri.parse(server));
        intent.putExtra("user", enteredUser);
        intent.putExtra("password", enteredPassword);
        startService(intent);

        // Return to main activity
        this.finish();
    }


}
