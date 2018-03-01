package cn.jsjst.reimbursement.component.home;

import android.view.View;
import android.widget.Toast;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragmentViewModel;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class HomeViewModel extends BaseFragmentViewModel<HomeFragment> {

    public HomeViewModel(HomeFragment context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return mFragment.getString(R.string.title_page_home);
    }

    @Override
    public boolean showBackIcon() {
        return false;
    }

    @Override
    public boolean showMenuIcon() {
        return true;
    }

    @Override
    public int getMenuIcon() {
        return R.drawable.menu_qr_code;
    }

    @Override
    public void onRightIconClick(View view) {
        super.onRightIconClick(view);
        if (mFragment.isAdded()) {
           mFragment.startNextActivity();
        }
    }
}
