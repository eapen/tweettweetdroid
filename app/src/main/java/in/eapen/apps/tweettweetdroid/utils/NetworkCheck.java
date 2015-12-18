package in.eapen.apps.tweettweetdroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by geapen on 12/17/15.
 */
public class NetworkCheck {

    Context ctx;
    public NetworkCheck(Context ctx) {
        this.ctx = ctx;
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
