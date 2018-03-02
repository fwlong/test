package cn.jsjst.reimbursement.component.apply;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityPublicServiceApplyBaseInfoBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class PSApplyBaseInfoActivity extends BaseActivity {

    ActivityPublicServiceApplyBaseInfoBinding mBinding;
    PSApplyBaseInfoViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_public_service_apply_base_info);
        mViewModel = new PSApplyBaseInfoViewModel(this);
        mBinding.setViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startFeeInput(){
        Intent feeInputIntent = new Intent();
        feeInputIntent.setClass(this,PSApplyFeeActivity.class);
        startActivityWithCommonAnimation(feeInputIntent);
    }
}
