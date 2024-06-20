package uv.fei.langroup.utilidades;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    public AuthInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = SesionSingleton.getInstance().getToken();

        if (token == null) {
            return chain.proceed(originalRequest);
        }

        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        Response response = chain.proceed(newRequest);

        if (response.header("Set-Authorization") != null) {
            String newToken = response.header("Set-Authorization");
            SesionSingleton.getInstance().setToken(newToken);
        }

        return response;
    }
}
