package cn.jsjst.reimbursement.component.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragment;
import cn.jsjst.reimbursement.databinding.FragmentMineBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MineFragment extends BaseFragment {

    FragmentMineBinding mBinding;
    MineViewModel mMineViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
//        View contentView = inflater.inflate(R.layout.fragment_mine,container,false);
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine,container,false);
        mMineViewModel = new MineViewModel(this);
        mBinding.setMineViewModel(mMineViewModel);
        return mBinding.getRoot();
    }
}
