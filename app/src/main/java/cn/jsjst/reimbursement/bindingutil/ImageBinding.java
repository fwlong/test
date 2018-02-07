package cn.jsjst.reimbursement.bindingutil;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import cn.jsjst.reimbursement.GlideApp;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ImageBinding {

    @BindingAdapter({"app:imgUrl","app:placeholder","app:error"})
    public static void loadImage(ImageView imageView,String url, Drawable holderDrawable,Drawable errorDrawable){
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .fitCenter()
                .into(imageView);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView imageView,int resId){
        imageView.setImageResource(resId);
    }

    @BindingAdapter("navi:icon")
    public static void setIcon(ImageView imageView,int resId){
        imageView.setImageResource(resId);
    }

    @BindingAdapter("navi:icon")
    public static void setIcon(ImageView imageView,Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

}
