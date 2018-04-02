package com.example.hemant.retrofitdemogithubreposapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hemant on 3/24/18.
 */

// Step - 3 Create this interface with methods - Methods annotated with Retrofit annotations

public interface GitHubClient {

    // Makes GET request to the URL inside with dynamic user parameter

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
