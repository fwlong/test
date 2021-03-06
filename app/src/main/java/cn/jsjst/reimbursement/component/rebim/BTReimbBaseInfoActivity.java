package cn.jsjst.reimbursement.component.rebim;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityBusinessTravelReimbInfoBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTReimbBaseInfoActivity extends BaseActivity {

    ActivityBusinessTravelReimbInfoBinding mBinding;
    BTReimbBaseInfoViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_travel_reimb_info);
        mViewModel = new BTReimbBaseInfoViewModel(this);
        mBinding.setBaseInfoViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startFeeInput(){
        Intent feeIntent = new Intent();
        feeIntent.setClass(this,BTReimbFeeInfoActivity.class);
        startActivityWithCommonAnimation(feeIntent);
    }

}
