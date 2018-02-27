package cn.jsjst.reimbursement.component.base;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BaseFragmentViewModel<T extends Fragment> {

    public T mFragment;

    public ObservableField<Boolean> isEmpty = new ObservableField<>(false);

    public ObservableField<Boolean> isNetError = new ObservableField<>(false);


    public BaseFragmentViewModel(T context) {
        mFragment = context;
    }

    /**
     * 周期函数，在activity，fragment里调用
     */

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestory() {
    }
}
