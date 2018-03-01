package cn.jsjst.reimbursement.component.apply;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityBusinessTravelApplyTypeFeeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BTApplyTypeFeeActivity extends BaseActivity {

    ActivityBusinessTravelApplyTypeFeeBinding mBinding;
    BTApplyTypeFeeViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_travel_apply_type_fee);
        mViewModel = new BTApplyTypeFeeViewModel(this);
        mBinding.setViewModel(mViewModel);
    }
}
