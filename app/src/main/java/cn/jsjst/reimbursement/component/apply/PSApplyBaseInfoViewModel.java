package cn.jsjst.reimbursement.component.apply;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseViewModel;
import cn.jsjst.reimbursement.component.events.EventApplyFinishMessage;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class PSApplyBaseInfoViewModel extends BaseViewModel<PSApplyBaseInfoActivity> {

    public PSApplyBaseInfoViewModel(
            PSApplyBaseInfoActivity context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return mActivity.getString(R.string.title_page_public_service_apply);
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
    public void onSubscribe(EventApplyFinishMessage event) {
        if (!event.isTravelApply()) {
            mActivity.finish();
        }
    }

    public void onNextClick(View view){
        mActivity.startFeeInput();
    }

}
