package request.fileRequest;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Aaron Wang on 2016/11/15.
 */
public class FileUploadHelper {

    public static RequestBody getRequestBodyString(String desc) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), desc);
    }

    public static RequestBody getRequestBodyFile(File file) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }

    public static MultipartBody.Part getMuMultipartBodyPart(File file) {
        return MultipartBody.Part.create(getRequestBodyFile(file));
    }
}
