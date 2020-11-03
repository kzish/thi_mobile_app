package retroFitClasses;

import globals.globals;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroClient {

    private static final String ROOT_URL = globals.server_url+"/mobileapi/";


    public RetroClient() {

    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static FileApi getApiService() {
        return getRetroClient().create(FileApi.class);
    }
}
