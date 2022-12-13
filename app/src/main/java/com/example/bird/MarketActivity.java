package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MarketActivity extends AppCompatActivity {

    private TextView valueBtc, valueLtc, valueEth, valueDoge;
    private static DecimalFormat df = new DecimalFormat("#.##");
    private ImageView btnhome;
    HomeActivity homeActivity = new HomeActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        valueBtc = findViewById(R.id.valueBtc);
        valueEth = findViewById(R.id.valueEth);
        valueLtc = findViewById(R.id.valueLtc);
        valueDoge = findViewById(R.id.valueDoge);
        btnhome = findViewById(R.id.btnhomet);

        getCurrency();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClassExtend(btnhome);
                getIntent(HomeActivity.class);

            }
        });
    }

    public void getClassExtend(ImageView imageView){
        homeActivity.getAnimation(imageView);
    }

    public void getIntent(Class name){
        Intent intent = new Intent(getApplicationContext(), name);
        startActivity(intent);
    }

    public void getCurrency(){
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        JSONObject quote = jsonObject.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");

                        String priceFormat = NumberFormat.getCurrencyInstance(Locale.US).format(price);
                        if(name.equals("Bitcoin")){
                            valueBtc.setText(priceFormat);
                        }

                        if(name.equals("Ethereum")){
                            valueEth.setText(priceFormat);
                        }

                        if(name.equals("Litecoin")){
                            valueLtc.setText(priceFormat);
                        }

                        if(name.equals("Dogecoin")){
                            valueDoge.setText(priceFormat);
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(MarketActivity.this, "Fail to extract json data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MarketActivity.this, "Fail to get the data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","88862da3-3606-4132-8355-485f1690282d");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}