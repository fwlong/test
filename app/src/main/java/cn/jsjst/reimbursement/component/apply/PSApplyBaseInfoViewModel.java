package cn.jsjst.reimbursement.component.apply;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseViewModel;

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
}
