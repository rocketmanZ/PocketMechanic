package com.example.PocketMechanic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Activity2 extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView email, jsontext;
    Button signOutBtn, carBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        email = findViewById(R.id.email);
        signOutBtn = findViewById(R.id.signout);
        carBtn = findViewById(R.id.check);
        TextView make = (TextView) findViewById(R.id.make);
        TextView model = (TextView) findViewById(R.id.model);
        TextView year = (TextView) findViewById(R.id.year);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            email.setText(personEmail);
        }
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        carBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(Activity2.this);
                String url = MessageFormat.format("https://api.nhtsa.gov/recalls/recallsByVehicle?make={0}&model={1}&modelYear={2}", make.getText().toString(),model.getText().toString(), year.getText().toString());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(Activity2.this, response.toString(), Toast.LENGTH_SHORT).show();
                        //Snackbar.make(view, response.toString(), Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();


                        JSONObject summaryinfo = null;
                        String summary = null;
                        try {
                            summary = response.getString("summary");

                        } catch (JSONException e) {
                            Toast.makeText(Activity2.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Activity2.this, summary.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();


                                } else if (error instanceof AuthFailureError) {
                                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NetworkError) {
                                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                                }
                    }
                });
//                {
//                    @Override //use with api's that need keys
//                public Map getHeaders () throws AuthFailureError {
//                    HashMap headers = new HashMap();
//                    headers.put("content-type", "application/json");
//                    headers.put("authorization", "MzgyN2JmNDItN2YzNy00ZDA0LWE2MzktYmYyNmUwODM2ZjRl");
//                    headers.put("partner-token", "03f89adfc76247cf89fbcf24d120ca42");
//                    return headers;
//                }
//                };

                queue.add(request);

//
//                RequestQueue queue = Volley.newRequestQueue(Activity2.this);
//                String url = MessageFormat.format("https://api.nhtsa.gov/recalls/recallsByVehicle?make={0}&model={1}&modelYear={2}", make.getText().toString(),model.getText().toString(), year.getText().toString());
//                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response) {
//                                // response
//                                Toast.makeText(Activity2.this, response.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO Auto-generated method stub
//                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
//
//                                } else if (error instanceof AuthFailureError) {
//                                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
//                                } else if (error instanceof ServerError) {
//                                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
//                                } else if (error instanceof NetworkError) {
//                                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
//                                } else if (error instanceof ParseError) {
//                                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        }
//                ) ;
////                {
////                    @Override
////                    public Map<String, String> getHeaders() throws AuthFailureError {
////                        Map<String, String>  params = new HashMap<String, String>();
////                        params.put("undefinedaccept", "application/json");
////                        params.put("authorization", "Basic MzgyN2JmNDItN2YzNy00ZDA0LWE2MzktYmYyNmUwODM2ZjRl");
////                        params.put("partner-token", "03f89adfc76247cf89fbcf24d120ca42");
////
////                        return params;
////                    }
////                };
//                queue.add(getRequest);


            }
        }));
    }
    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
                startActivity(new Intent(Activity2.this,MainActivity.class));
            }
        });
    }
}