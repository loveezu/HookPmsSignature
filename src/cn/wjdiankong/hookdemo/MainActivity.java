package cn.wjdiankong.hookdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaoenai.app.ServiceManagerWraper;

public class MainActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ServiceManagerWraper.hookPMS(newBase);
    }
    
    private void getSignature() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo.signatures != null) {
            	Log.i("jw", "sig:"+packageInfo.signatures[0].hashCode());
            }
        } catch (Exception e2) {
        } 
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	getSignature();
            }
        });
        
    }
    
}
