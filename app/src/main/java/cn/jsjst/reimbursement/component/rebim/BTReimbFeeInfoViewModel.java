package cn.jsjst.reimbursement.component.rebim;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.component.base.BaseViewModel;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTReimbFeeInfoViewModel extends BaseViewModel {

    public BTReimbFeeInfoViewModel(BaseActivity context) {
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
}
