package cn.jsjst.reimbursement.main;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import cn.jsjst.reimbursement.base.BaseViewModel;
import cn.jsjst.reimbursement.base.FragmentHelper;
import cn.jsjst.reimbursement.base.NavModel;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MainViewModel extends BaseViewModel {

    private String mCurrentTab = FragmentHelper.TAG_HOME;
    private final MainActivity mActivity;
    public NavModel mNavModel = new NavModel();


    public MainViewModel(Context context) {
        super(context);
        mActivity = (MainActivity)context;
    }


    public void initUI(final int contentId) {
        FragmentHelper.switchFragment(mActivity, contentId, null,
                FragmentHelper.TAG_HOME);
        mNavModel.setOnNaviBottomClickListener(new NavModel.OnNaviBottomClickListener() {
            @Override
            public void onNaviBottomClick(int type) {
                switch (type) {
                    case NavModel.HOME_TAB:
                        FragmentHelper.switchFragment(mActivity,contentId,mCurrentTab,FragmentHelper.TAG_HOME);
                        mCurrentTab = FragmentHelper.TAG_HOME;
                        break;
                    case NavModel.APPLY_LIST_TAB:
                        FragmentHelper.switchFragment(mActivity,contentId,mCurrentTab,FragmentHelper.TAG_APPLY_LIST);
                        mCurrentTab = FragmentHelper.TAG_APPLY_LIST;
                        break;
                    case NavModel.CHART_TAB:
                        FragmentHelper.switchFragment(mActivity,contentId,mCurrentTab,FragmentHelper.TAG_CHART);
                        mCurrentTab = FragmentHelper.TAG_CHART;
                        break;
                    case NavModel.MINE_TAB:
                        FragmentHelper.switchFragment(mActivity,contentId,mCurrentTab,FragmentHelper.TAG_MINE);
                        mCurrentTab = FragmentHelper.TAG_MINE;
                        break;
                    case NavModel.SHORTCUT_TAB:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void refreshView(View view) {

    }


}
