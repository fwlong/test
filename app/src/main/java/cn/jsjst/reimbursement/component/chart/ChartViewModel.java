package cn.jsjst.reimbursement.component.chart;

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
public class ChartViewModel extends BaseFragmentViewModel<ChartFragment> {

    public ChartViewModel(ChartFragment context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return mFragment.getString(R.string.title_page_chart);
    }

    @Override
    public boolean showBackIcon() {
        return false;
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
