package szydlowskiptr.com.epz.activity;

import android.app.Application;

/**
 * Created by Piotr Szydlowski on 06.04.2023
 */
public class GlobalProperties extends Application {

    private String defaultStore = "3";

    public String getDefaultStore() {
        return defaultStore;
    }
}
