package myz.graduation_design.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void setNewsImage(Context context,View view, int id, String uri){
        ImageView imageView = null;
        if(view != null) {
            imageView = (ImageView) view.findViewById(id);
            Glide.with(context)
                    .load(uri).into(imageView);

        }

    }
}
