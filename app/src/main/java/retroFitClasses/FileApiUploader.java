package retroFitClasses;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileApiUploader {
    @Multipart
    @POST("sync_file_uploades.aspx")
    Call<Respond> uploadFile(
            @Part MultipartBody.Part file,
            @Part("upload_data") RequestBody upload_data
    );


}
