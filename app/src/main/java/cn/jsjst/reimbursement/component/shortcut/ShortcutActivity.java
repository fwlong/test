package cn.jsjst.reimbursement.component.shortcut;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.apply.BTApplyBaseInfoActivity;
import cn.jsjst.reimbursement.component.apply.PSApplyBaseInfoActivity;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.component.rebim.BTReimbBaseInfoActivity;
import cn.jsjst.reimbursement.component.rebim.PSReimbBaseInfoActivity;
import cn.jsjst.reimbursement.databinding.ActivityShortcutBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ShortcutActivity extends BaseActivity {

    ActivityShortcutBinding mBinding;
    ShortcutViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shortcut);
        mViewModel = new ShortcutViewModel(this);
        mBinding.setShortcutViewModel(mViewModel);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.shortcut_activity_exit_out);
    }

    public void startTravelApply(){
        Intent travelInfoIntent = new Intent();
        travelInfoIntent.setClass(this, BTApplyBaseInfoActivity.class);
        startActivityWithCommonAnimation(travelInfoIntent);
    }

    public void startTravelReimb(){
        Intent travelReimbIntent = new Intent();
        travelReimbIntent.setClass(this, BTReimbBaseInfoActivity.class);
        startActivityWithCommonAnimation(travelReimbIntent);
    }

    public void startOfficialApply(){
        Intent officialApplyIntent = new Intent();
        officialApplyIntent.setClass(this, PSApplyBaseInfoActivity.class);
        startActivityWithCommonAnimation(officialApplyIntent);
    }

    public void startOfficialReimb(){
        Intent officialReimbIntent = new Intent();
        officialReimbIntent.setClass(this, PSReimbBaseInfoActivity.class);
        startActivityWithCommonAnimation(officialReimbIntent);

    }

    public void startScan(){

    }

    public void startUploadFile(){

    }
}
