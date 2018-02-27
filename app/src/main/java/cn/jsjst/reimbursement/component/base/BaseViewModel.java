package cn.jsjst.reimbursement.component.base;

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
public class BaseViewModel<T extends BaseActivity> {

    public T mActivity;

    public ObservableField<Boolean> isEmpty = new ObservableField<>(false);

    public ObservableField<Boolean> isNetError = new ObservableField<>(false);



    public BaseViewModel(T context){
        mActivity = context;
    }


    /**
     * 周期函数，在activity，fragment里调用
     */

    public void onCreate(){}

    public void onStart(){}

    public void onStop(){}

    public void onDestory(){}

}
