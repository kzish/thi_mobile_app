package retroFitClasses;

import globals.globals;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroClientUploader {

    private static final String ROOT_URL = globals.server_url+"/mobileUploadService/";


    public RetroClientUploader() {

    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static FileApiUploader getApiService() {
        return getRetroClient().create(FileApiUploader.class);
    }
}
