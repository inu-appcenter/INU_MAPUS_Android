package delilah.personal.inumapus.network;

import android.app.Application;

import delilah.personal.inumapus.StaticData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController extends Application {
    private static final String mainURL = StaticData.mainURL;
    private ApiService apiService;
    public static NetworkController networkController = new NetworkController();

    public NetworkController() {
        apiService = new Retrofit.Builder()
                .baseUrl(mainURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public static NetworkController getInstance(){
        return networkController;
    }

    public ApiService getApiService(){
        return apiService;
    }
}