package com.wd.winddots;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIStore {
    @GET("conversations")
    Call<String> getMessage(@Query("fromId") String fromId);
}
