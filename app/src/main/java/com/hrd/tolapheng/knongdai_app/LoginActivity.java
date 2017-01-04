package com.hrd.tolapheng.knongdai_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hrd.tolapheng.knongdai_app.model.User;
import com.hrd.tolapheng.knongdai_app.response.UserResponse;
import com.hrd.tolapheng.knongdai_app.service.ServiceGenerator;
import com.hrd.tolapheng.knongdai_app.service.UserService;
import com.hrd.tolapheng.knongdai_app.util.Session;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    Toolbar lToolbar;

    Button btnLoginWithKA, btLoginWithKAProcess , custom_login_facebook_button;

    EditText etEmail , etPassword;

    boolean flag = false;

    ScrollView scrollView;

    /**
     *Facebook
     **/
    // Facebook App ID
    private static String FACEBOOK_APP_ID = "1457762170931373";

    private LoginButton loginButton;
    private CallbackManager callbackManager;



    // Google

    //Signin button
    private SignInButton signInButton;

    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    private Button custom_login_google_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);



        lToolbar = (Toolbar) findViewById(R.id.toolbar);

        scrollView = (ScrollView) findViewById(R.id.loginScreenScrollView);

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);

        btnLoginWithKA =(Button) findViewById(R.id.btnLoginWithKA);
        btnLoginWithKA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout one = (LinearLayout) findViewById(R.id.blockLoginWithKA);
                if(flag == true){
                    one.setVisibility(View.INVISIBLE);
                    scrollView.pageScroll(View.FOCUS_UP);
                    flag = false;
                }else{
                    one.setVisibility(View.VISIBLE);
                    scrollView.pageScroll(View.FOCUS_DOWN);
                    flag = true;
                }


            }
        });

        btLoginWithKAProcess = (Button) findViewById(R.id.btLoginWithKAProcess);
        btLoginWithKAProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setEmail(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());

                login(user);

            }
        });

        loginButton = (LoginButton) findViewById(R.id.facebookLoginButton);
        loginButton.setReadPermissions("email", "public_profile");

        custom_login_facebook_button = (Button) findViewById(R.id.custom_login_facebook_button);
        custom_login_facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("FB" , "FB");

                loginButton.performClick();
                loginButton.setPressed(true);
                loginButton.invalidate();;
                loginButton.registerCallback(callbackManager , callback);
                loginButton.setPressed(false);
                loginButton.invalidate();
            }
        });


        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.googleLoginButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Setting onclick listener to signing button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST" , "TEST");
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        custom_login_google_button = (Button) findViewById(R.id.custom_login_google_button);
        custom_login_google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Login with facebook callback
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Google
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }


    void login(User user){

        UserService userService = ServiceGenerator.createService(UserService.class);

        Call<UserResponse> loginResponseCall = userService.loginService(user);
        loginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                Log.d("S" , response.body().getMessage());

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(response.body().getUser());
                Log.d("MainActivity" , json );

                if(response.body().isStatus() == false){
                    // Login failed
                    Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }else{
                    // Login success
                    Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();

                    Log.d("USER" , response.body().getUser().getUsername());

                    Session.saveUserSession(getApplicationContext(), response.body().getUser());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("MainActivity" , "onFailure  " + t.getMessage()  );
            }
        });

    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            Log.d("Facebook" , "onSuccess");

            GraphRequest request = GraphRequest.newMeRequest
                    (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                String facebookID = object.getString("id");
                                String email = object.getString("email");
                                String username = object.getString("name");
                                String gender = object.getString("gender");
                                String userImageUrl = "https://graph.facebook.com/" + facebookID + "/picture?type=large";

                                Log.d("Facebook" , "email " + email);
                                Log.d("Facebook" , "username " +username);
                                Log.d("Facebook" , "gender " +gender);
                                Log.d("Facebook" , "userImageUrl " +"https://graph.facebook.com/" + facebookID + "/picture?type=large");

                                User user = new User();
                                user.setUsername(username);
                                user.setEmail(email);
                                user.setGender(gender);
                                user.setUserImageUrl(userImageUrl);
                                user.setSocialId(facebookID);
                                user.setScoialType("2"); // 1 = Google , 2 = Facebook , 3 = Twitter
                                user.setSignUpWith("2"); // 0 = WEB , 1 = iOS , 2 = AOS


                                loginRegisterWithSocial(user);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large){url}");
            request.setParameters(parameters);
            request.executeAsync();

        }

        @Override
        public void onCancel() {
            Log.d("Facebook" , "onCancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("Facebook" , "onError " +  error.getMessage());
        }

    };

    void loginRegisterWithSocial(User user){

        UserService userService = ServiceGenerator.createService(UserService.class);

        Call<UserResponse> loginResponseCall = userService.loginWithSocialService(user);
        loginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

               // Log.d("S" , response.body().getMessage());

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(response);
                Log.d("MainActivity" , json );

                if(response.body().isStatus() == false){
                    // Login failed
                    Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }else{
                    // Login success
                    Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();

                    Log.d("USER" , response.body().getUser().getUsername());

                    Session.saveUserSession(getApplicationContext(), response.body().getUser());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("MainActivity" , "onFailure  " + t.getMessage()  );
            }
        });

    }


    // Google

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            try{
                Log.d("GOOGLE id" , acct.getId());
                Log.d("GOOGLE username" , acct.getDisplayName());
                Log.d("GOOGLE email" , acct.getEmail());
                Log.d("GOOGLE Photo" , acct.getPhotoUrl().toString());


                User user = new User();
                user.setUsername(acct.getDisplayName());
                user.setEmail(acct.getEmail());
                user.setUserImageUrl(acct.getPhotoUrl().toString());
                user.setSocialId(acct.getId());
                user.setScoialType("1"); // 1 = Google , 2 = Facebook , 3 = Twitter
                user.setSignUpWith("2"); // 0 = WEB , 1 = iOS , 2 = AOS

                loginRegisterWithSocial(user);

            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

}
