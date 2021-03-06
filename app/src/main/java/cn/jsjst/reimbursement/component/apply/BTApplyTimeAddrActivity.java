package cn.jsjst.reimbursement.component.apply;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityBusinessTravelApplyAddressTimeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTApplyTimeAddrActivity extends BaseActivity {

    ActivityBusinessTravelApplyAddressTimeBinding mBinding;
    BTApplyTimeAddrViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_travel_apply_address_time);
        mViewModel = new BTApplyTimeAddrViewModel(this);
        mBinding.setViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startTypeFeeInput(){
        Intent typeFeeIntent = new Intent();
        typeFeeIntent.setClass(this,BTApplyTypeFeeActivity.class);
        startActivityWithCommonAnimation(typeFeeIntent);
    }
}
