package es.elpesetero;

import android.os.Bundle;
import android.view.Menu;
import org.apache.cordova.*;
import es.elpesetero.R;

public class ElPesetero extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setBooleanProperty("keepRunning", false);
        super.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_el_pesetero, menu);
        return true;
    }
}
