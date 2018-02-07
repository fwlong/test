package cn.jsjst.reimbursement.base;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public abstract class BaseViewModel {

    public Context mContext;

    public ObservableField<Boolean> isEmpty = new ObservableField<>(false);

    public ObservableField<Boolean> isNetError = new ObservableField<>(false);



    public BaseViewModel(Context context){
        this.mContext = context;
    }

    public abstract void refreshView(View view);

    public void showLoading(){}

    public void finishLoading(){}

    /**
     * 周期函数，在activity，fragment里调用
     */

    public void onCreate(){}

    public void onStart(){}

    public void onStop(){}

    public void onDestory(){}

}
