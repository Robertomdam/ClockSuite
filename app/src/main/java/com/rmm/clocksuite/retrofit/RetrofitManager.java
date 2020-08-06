package com.rmm.clocksuite.retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    static {
        mInstance = new RetrofitManager();
    }

    private RetrofitManager () {
        mDataCollectors = new ArrayList<>();

        mRetrofitCountries = new Retrofit.Builder()
                .baseUrl (RETROFIT_API_BASE_URL_COUNTRIES)
                .addConverterFactory(GsonConverterFactory.create())
                .build ();

        mCountriesAPIService = mRetrofitCountries.create (ICountriesAPIService.class);
    }

    private static RetrofitManager mInstance;
    public  static RetrofitManager getInstance () { return mInstance; }

    private final String RETROFIT_API_BASE_URL_COUNTRIES = "https://restcountries.eu/rest/v2/";
    private Retrofit mRetrofitCountries;

    private ArrayList<IRetrofitManagerDataCollector> mDataCollectors;
    private ICountriesAPIService mCountriesAPIService;

    public void addDataCollector (IRetrofitManagerDataCollector dataCollector) { mDataCollectors.add (dataCollector); }

    public void getAllCountries () {

        Call<List<CountryData>> callCountries = mCountriesAPIService.getCountries();
        callCountries.enqueue(new Callback<List<CountryData>>() {

            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                ArrayList<CountryData> countries = new ArrayList<> (response.body());

                for (IRetrofitManagerDataCollector dataCollector : mDataCollectors) {
                    dataCollector.onListCountriesReceived (countries);
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {

                for (IRetrofitManagerDataCollector dataCollector : mDataCollectors) {
                    dataCollector.onListCountriesFailed();
                }
            }
        });
    }

    public interface IRetrofitManagerDataCollector {
        void onListCountriesReceived (ArrayList<CountryData> countries);
        void onListCountriesFailed ();
    }
}
