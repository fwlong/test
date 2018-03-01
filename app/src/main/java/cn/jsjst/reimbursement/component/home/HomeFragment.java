package cn.jsjst.reimbursement.component.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragment;
import cn.jsjst.reimbursement.component.rebim.BTReimbFeeInfoActivity;
import cn.jsjst.reimbursement.component.rebim.PSReimbBaseInfoActivity;
import cn.jsjst.reimbursement.component.rebim.PSReimbFeeInfoActivity;
import cn.jsjst.reimbursement.component.rebim.PSReimbPersonFeeActivity;
import cn.jsjst.reimbursement.component.rebim.PSReimbPersonFeeViewModel;
import cn.jsjst.reimbursement.databinding.FragmentHomeBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class HomeFragment extends BaseFragment {

    FragmentHomeBinding mBinding;
    HomeViewModel mHomeViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        mHomeViewModel = new HomeViewModel(this);
        mBinding.setHomeViewModel(mHomeViewModel);
        return mBinding.getRoot();
    }

    /**
     * 测试用的
     */
    public void startNextActivity(){
        Intent a = new Intent();
        a.setClass(getContext(), PSReimbPersonFeeActivity.class);
        startActivity(a);
    }
}
