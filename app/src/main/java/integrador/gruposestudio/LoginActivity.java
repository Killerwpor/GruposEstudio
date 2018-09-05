package integrador.gruposestudio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import bolts.Task;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SignInButton botonGoogle=findViewById(R.id.botonGoogle);
        LoginButton botonFacebook=findViewById(R.id.botonFacebook);
        botonFacebook.setReadPermissions("email", "public_profile");
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

     botonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });







        botonGoogle.setOnClickListener(new View.OnClickListener() { //solo para el esqueleto
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void debug(){
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        //Si es satisfactorio el login con Facebook entra por aqui
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                            intent.putExtra("Usuario",user);
                            startActivityForResult(intent, 0);


                        } else { //Si NO es satisfactorio el login con Facebook entra por aqui
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "signInWithCredential:failure");
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }




                });
    }

}
