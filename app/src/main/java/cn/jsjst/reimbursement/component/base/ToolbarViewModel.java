package cn.jsjst.reimbursement.component.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import cn.jsjst.reimbursement.R;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ToolbarViewModel {

    private ToolbarEventListener mListener;

    public ToolbarViewModel() {
    }

    public ToolbarViewModel(ToolbarEventListener listener) {
        mListener = listener;
    }

    public ObservableField<String> title = new ObservableField<>();
    public ObservableBoolean showBackIcon = new ObservableBoolean();
    public ObservableBoolean showRightMenuIcon = new ObservableBoolean();
    public ObservableInt menuIconId = new ObservableInt(R.drawable.menu_qr_code);

    public void setEventListener(ToolbarEventListener listener){
        mListener = listener;
    }

    public void onBackEvent(View view){
        if(mListener!=null){
            mListener.onBackEvent(view);
        }
    }

    public void onRightMenuEvent(View view){
        if(mListener!=null){
            mListener.onRightMenuEvent(view);
        }
    }




}
