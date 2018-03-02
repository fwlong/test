package cn.jsjst.reimbursement.component.rebim;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityBusinessTravelReimbAddressTimeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTReimbTimeAddrActivity extends BaseActivity {

    ActivityBusinessTravelReimbAddressTimeBinding mBinding;
    BTReimbTimeAddrViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_travel_reimb_address_time);
        mViewModel = new BTReimbTimeAddrViewModel(this);
        mBinding.setTimeAddrViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startFeeItemInput(){
        Intent feeItemIntent = new Intent();
        feeItemIntent.setClass(this,BTReimbFeeItemActivity.class);
        startActivityWithCommonAnimation(feeItemIntent);
    }
}
