package cn.jsjst.reimbursement.component.applylist;

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
public class ApplyModel extends BaseFragmentViewModel<ApplyListFragment> {

    public ApplyModel(ApplyListFragment context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return mFragment.getString(R.string.tile_page_apply);
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
        return R.drawable.ic_search;
    }

    @Override
    public void onRightIconClick(View view) {
        super.onRightIconClick(view);
        if (mFragment.isAdded()) {
            Toast.makeText(mFragment.getContext(), "search action ", Toast.LENGTH_LONG).show();
        }
    }
}
