package com.xiaoenai.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

/**
 * Created by jiangwei1-g on 2016/9/7.
 */
public class PmsHookBinderInvocationHandler implements InvocationHandler{

    private Object base;
    
    //应用正确的签名信息
    private final static String SIGN = "308201fb30820164a003020102020450349ae9300d06092a864886f70d01010505003042310e300c060355040613054368696e61310b30090603550408130247443111300f060355040713085368656e7a68656e3110300e060355040313074d5a4420496e63301e170d3132303832323038343030395a170d3337303831363038343030395a3042310e300c060355040613054368696e61310b30090603550408130247443111300f060355040713085368656e7a68656e3110300e060355040313074d5a4420496e6330819f300d06092a864886f70d010101050003818d003081890281810081a4e2f99f5737c1ec6da872108b3252073554f38091c5c5789cfba846d3c75ef9943c9efaef850ca08ea9c92f5128826ca562b827c075f8c55f3f2b6a27b351e8808ac572ada973bd4628b89c587b6d8bff9ccc7554994f254c67be9c18b7a8014e534cec68d535ef6b83dd1ae5dcd387ae567f6ac885b11e9ecf2813655f8b0203010001300d06092a864886f70d01010505000381810024ea2f8d5ecb409eb52f5aa67a191afd026771533d8c7cdd8cf072c02e054e754935fea19c72fd0b9053aeffece8c68bee3c01cfe7aa65c928fd7fec9077e420cf1f3347a80dcdd4f400701b6c7a23ec28e2d0cadb22d9e281730b36214c1d726b6e2fd6b3bf7f087cfeb41f557179fa5915227701613dd1e2cb854cddf8dddf";

    public PmsHookBinderInvocationHandler(Object base) {
        try {
            this.base = base;
        } catch (Exception e) {
            Log.d("jw", "error:"+Log.getStackTraceString(e));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	Log.i("jw", "pms method:"+method.getName());
        if("getPackageInfo".equals(method.getName())){
            Integer flag = (Integer)args[1];
            if(flag == PackageManager.GET_SIGNATURES){
            	Signature sign = new Signature(SIGN);
            	PackageInfo info = (PackageInfo) method.invoke(base, args);
            	Log.i("jw", "old sign:"+info.signatures[0].hashCode());
            	info.signatures[0] = sign;
            	return info;
            }
        }
        return method.invoke(base, args);
    }

}
