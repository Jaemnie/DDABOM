package com.example.team.perser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface useAPI {
    @POST("graphql")
    Call<LicenseSearchResponse> getLicenseSearchList(@Body LicenseSearchRequest request);
}