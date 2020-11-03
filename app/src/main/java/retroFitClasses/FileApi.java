package retroFitClasses;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileApi {
    @Multipart
    @POST("sync_attachments.aspx")
    Call<Respond> uploadAttachment(
                              @Part MultipartBody.Part file,
                              @Part("filename") RequestBody filename,
                              @Part("android_client_id") RequestBody android_client_id,
                              @Part("fileType") RequestBody fileType
                    );


}
