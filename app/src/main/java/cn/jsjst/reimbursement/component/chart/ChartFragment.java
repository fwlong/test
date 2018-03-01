package cn.jsjst.reimbursement.component.chart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragment;
import cn.jsjst.reimbursement.databinding.FragmentChartBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ChartFragment extends BaseFragment {

    FragmentChartBinding mBinding;
    ChartViewModel mChartViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_chart,container,false);
        mChartViewModel = new ChartViewModel(this);
        mBinding.setChartViewModel(mChartViewModel);
        return mBinding.getRoot();
    }
}
