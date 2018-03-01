package cn.jsjst.reimbursement.component.rebim;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityPublicServiceReimbFeeInfoBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class PSReimbFeeInfoActivity extends BaseActivity {

    ActivityPublicServiceReimbFeeInfoBinding mBinding;
    PSReimbFeeInfoViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_public_service_reimb_fee_info);
        mViewModel = new PSReimbFeeInfoViewModel(this);
        mBinding.setFeeInfoViewModel(mViewModel);
    }
}
