package request.fileRequest;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aaron Wang on 2016/11/14.
 */
public class FileDownloadHelper {

    public interface DownloadListener {
        void onDownloadStart();
        void onDownloading(int progress);
        void onDownloadComplete();
        void onError(Exception e);
    }

    public static void downLoadFile(Observable<ResponseBody> observable, final File file) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        Log.e("call", Thread.currentThread().getName());
                        FileOutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                            inputStream = responseBody.byteStream();
                            byte[] bytes = new byte[1024];
                            int len ;
                            while ((len = inputStream.read(bytes)) != -1) {
                                outputStream.write(bytes,0,len);
                            }
                            return file;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return null;
                    }
                });
    }

    public static void downLoadFile(Observable<ResponseBody> observable, final File file, final DownloadListener listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        FileOutputStream out = null;
                        InputStream in = null;
                        try {
                            out = new FileOutputStream(file);
                            in = responseBody.byteStream();
                            long totalLength = in.available();
                            long progress = 0L;
                            byte[] bytes = new byte[1024];
                            int len;
                            onStart(listener);
                            while ((len = in.read(bytes)) != -1) {
                                out.write(bytes, 0, len);
                                progress += len;
                                onDownloading(listener, (int) (progress*100/totalLength));
                            }
                            onComplete(listener);
                            return file;
                        } catch (IOException e) {
                            onError(listener,e);
                        }finally {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    onError(listener,e);
                                }
                            }
                            if (in != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    onError(listener,e);
                                }
                            }
                        }
                        return null;
                    }
                }).subscribe();
    }

    private static void onStart(DownloadListener listener) {
        if (listener != null) {
            listener.onDownloadStart();
        }
    }

    private static void onDownloading(DownloadListener listener, int progress) {
        if (listener != null) {
            listener.onDownloading(progress);
        }
    }

    private static void onComplete(DownloadListener listener) {
        if (listener != null) {
            listener.onDownloadComplete();
        }
    }

    private static void onError(DownloadListener listener, Exception e) {
        listener.onError(e);
    }
}
