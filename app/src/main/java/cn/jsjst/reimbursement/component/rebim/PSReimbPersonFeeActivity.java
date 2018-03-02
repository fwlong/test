package cn.jsjst.reimbursement.component.rebim;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityPublicServiceReimbPersonFeeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class PSReimbPersonFeeActivity extends BaseActivity {

    ActivityPublicServiceReimbPersonFeeBinding mBinding;
    PSReimbPersonFeeViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_public_service_reimb_person_fee);
        mViewModel = new PSReimbPersonFeeViewModel(this);
        mBinding.setPersonFeeViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startFeeInfoInput(){
        Intent feeIntent = new Intent();
        feeIntent.setClass(this,PSReimbFeeInfoActivity.class);
        startActivityWithCommonAnimation(feeIntent);
    }

}
