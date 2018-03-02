package cn.jsjst.reimbursement.component.apply;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseActivity;
import cn.jsjst.reimbursement.databinding.ActivityPublicServiceApplyFeeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class PSApplyFeeActivity extends BaseActivity {

    ActivityPublicServiceApplyFeeBinding mBinding;
    PSApplyFeeViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_public_service_apply_fee);
        mViewModel = new PSApplyFeeViewModel(this);
        mBinding.setViewModel(mViewModel);
        mViewModel.registEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.unregistEventBus();
    }

    public void startUploadFile(){
        //TODO 测试，先finish流程
        mViewModel.sendFinishEvent();

    }
}
