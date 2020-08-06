package com.rmm.clocksuite.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICountriesAPIService {

    @GET ("all")
    Call<List<CountryData>> getCountries ();
}
