package com.example.hemant.retrofitdemogithubreposapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    Button submitBtn;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        nameEditText = (EditText) findViewById(R.id.usernameEditText);
        submitBtn = (Button) findViewById(R.id.submitButton);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchTheRepos(nameEditText.getText().toString());
            }
        });


    }

    public void fetchTheRepos(String username) {
        // Step - 5 Now do the retrofit magic

        // create the builder with the base url and gson converter
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        // do build
        Retrofit retrofit = builder.build();

        // Create client
        GitHubClient client = retrofit.create(GitHubClient.class);

        // Do the method calls on the client with the github "user"
        Call<List<GitHubRepo>> call = client.reposForUser(username);

        // call async way
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                // On getting response simply show on the listview with the adapter
                List<GitHubRepo> repos = response.body();

                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                // On failure do nothing but show the toast with the error message
                Toast.makeText(MainActivity.this, "Error :( Occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
