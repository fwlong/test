package cn.jsjst.reimbursement.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.base.FragmentHelper;
import cn.jsjst.reimbursement.base.NavModel;
import cn.jsjst.reimbursement.databinding.ActivityMainBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MainActivity extends AppCompatActivity {

    private String mCurrentTab = FragmentHelper.TAG_HOME;
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = new MainViewModel(this);
        initUI();
        mMainBinding.setMainModel(mMainViewModel);
    }

    private void initUI() {
        switchFragment(null, FragmentHelper.TAG_HOME);
        mMainBinding.setNavModel(new NavModel(new NavModel.OnNaviBottomClickListener() {
            @Override
            public void onNaviBottomClick(int type) {
                switch (type) {
                    case NavModel.HOME_TAB:
                        switchFragment(mCurrentTab, FragmentHelper.TAG_HOME);
                        break;
                    case NavModel.APPLY_LIST_TAB:
                        switchFragment(mCurrentTab, FragmentHelper.TAG_APPLY_LIST);
                        break;
                    case NavModel.CHART_TAB:
                        switchFragment(mCurrentTab, FragmentHelper.TAG_CHART);
                        break;
                    case NavModel.MINE_TAB:
                        switchFragment(mCurrentTab, FragmentHelper.TAG_MINE);
                        break;
                    default:
                        break;
                }
            }
        }));
    }

    private void switchFragment(String currentTab, String toTab) {
        FragmentHelper.switchFragment(this, R.id.content, currentTab, toTab);
        mCurrentTab = toTab;
    }


}
