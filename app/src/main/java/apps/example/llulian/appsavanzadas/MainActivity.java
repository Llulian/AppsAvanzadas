package apps.example.llulian.appsavanzadas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    // El callbackManager es usado para enrutar las llamadas de vuelta al Facebook SDK y a los respectivos callbacks registrados
    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se inicializa el SDK antes de ejecutar cualquiera otra operación
        // Especialmente si se están usando elementos en la interfaz de usuario, como el botón de login
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

        setContentView(R.layout.activity_main);

        // Objeto usado para mostrar la publicidad en el banner
        AdView mAdView = (AdView) findViewById(R.id.adView);

        // Objeto para hacer la petición de la publicidad del banner
        AdRequest adRequest = new AdRequest.Builder().build();
        // Método para cargar la publicidad en el banner
        mAdView.loadAd(adRequest);

        // Se instancia el callbackManager para manejar las distintas respuestas del login de Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);

        // Para responder al resultado del login es necesario registrar un callback, ya sea con un LoginManager o un LoginButton
        // Si se registra el callback con un LoginButton, no será necesario registrar al mismo en el LoginManager
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "¡Inicio de sesión exitoso!", Toast.LENGTH_LONG).show();            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "¡Inicio de sesión cancelado!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(MainActivity.this, "¡Inicio de sesión NO exitoso!", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Al método onActivityResult es usado para pasarle el resultado del  login al LoginManager
    // Este es un proceso que se realiza vía el callbackManager que se observa en el cuerpo del método
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
