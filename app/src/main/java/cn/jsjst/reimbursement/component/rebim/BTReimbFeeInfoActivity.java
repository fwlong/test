package cn.jsjst.reimbursement.component.rebim;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityBusinessTravelReimbFeeInfoBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTReimbFeeInfoActivity extends BaseActivity {

    ActivityBusinessTravelReimbFeeInfoBinding mBinding;
    BTReimbFeeInfoViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_travel_reimb_fee_info);
        mViewModel = new BTReimbFeeInfoViewModel(this);
        mBinding.setFeeInfoViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startUploadFile(){
        //TODO 测试，先打开time，addr 页面
        Intent timeAddrIntent = new Intent();
        timeAddrIntent.setClass(this,BTReimbTimeAddrActivity.class);
        startActivityWithCommonAnimation(timeAddrIntent);
    }

}
