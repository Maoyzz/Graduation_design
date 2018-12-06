package myz.graduation_design.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myz.graduation_design.R;

/**
 * Created by 10246 on 2018/4/4.
 */

public  class StringUtils {

    public static String BaseUrl="http://172.20.10.2:8080";

    public static Boolean IsEmpty(String s){
        if(s == null || s.equals("")){
            return true;
        }
        return false;
    }

    public static Boolean isEmptySpace(String string){
        if (string == null){
            return true;
        }else{
            string = replaceBlank(string);
            if (string.equals("")){
                return  true;
            }else{
                return false;
            }
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getFormatString(Resources resources, int res, String string){
        return String.format(resources.getString(res), string);
    }

    public static String getFormatString(Resources resources, int res, String string, String string2){
        return String.format(resources.getString(res), string, string2);
    }

    public static String getFormatString(Resources resources, int res, String string, String string2,String string3){
        return String.format(resources.getString(res), string, string2,string3);
    }

    public static String formatImgUrl(String url){
        if (url == null || url.equals("")){
            return "";
        }else{
            return url.replace("\\", "/");
        }
    }

    public static String Webservice(String title,String string){
//           string.replace()
        return "";
    }

    public static int getNewTextId(Context mContext, int id){
        return mContext.getResources().getIdentifier("tv_new" + id, "id" , mContext.getPackageName());
    }

    public static void setNewsText(View view, int id,String content){
        TextView textView = null;
        if(view != null) {
            textView = (TextView) view.findViewById(id);
            textView.setText(content);
        }

    }

}
