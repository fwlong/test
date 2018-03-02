package cn.jsjst.reimbursement.component.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.component.base.FragmentHelper;
import cn.jsjst.reimbursement.component.base.NavModel;
import cn.jsjst.reimbursement.component.shortcut.ShortcutActivity;
import cn.jsjst.reimbursement.databinding.ActivityMainBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MainActivity extends BaseActivity {

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
                        case NavModel.SHORTCUT_TAB:
                            startShortcut();
                            break;
                    default:
                        break;
                }
            }
        }));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void switchFragment(String currentTab, String toTab) {
        FragmentHelper.switchFragment(this, R.id.content, currentTab, toTab);
        mCurrentTab = toTab;
    }

    private void startShortcut(){
        Intent shortcutIntent = new Intent();
        shortcutIntent.setClass(this, ShortcutActivity.class);
        startActivity(shortcutIntent);
        overridePendingTransition(R.anim.shortcut_activity_enter_in,R.anim.shortcut_common_activity_exit_out);
    }




}
