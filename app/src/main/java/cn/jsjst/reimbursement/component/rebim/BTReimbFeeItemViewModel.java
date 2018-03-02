package cn.jsjst.reimbursement.component.rebim;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseViewModel;
import cn.jsjst.reimbursement.component.events.EventAddItemFinishMessage;
import cn.jsjst.reimbursement.component.events.EventReimbFinishMessage;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTReimbFeeItemViewModel extends BaseViewModel<BTReimbFeeItemActivity> {

    public BTReimbFeeItemViewModel(
            BTReimbFeeItemActivity context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return mActivity.getString(R.string.title_page_business_reimb);
    }

    @Override
    public boolean showBackIcon() {
        return true;
    }

    @Override
    public boolean showMenuIcon() {
        return false;
    }

    @Override
    public int getMenuIcon() {
        return 0;
    }

    public void registEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unregistEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(EventAddItemFinishMessage event) {
        mActivity.finish();
    }

    public void sendFinishEvent(){
        EventBus.getDefault().post(new EventAddItemFinishMessage());
    }

    public void onNextClick(View view){
        mActivity.inputComplete();
    }
}
