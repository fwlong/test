package cn.jsjst.reimbursement.component.applylist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.databinding.FragmentApplyListBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ApplyListFragment extends Fragment {

    FragmentApplyListBinding mFragmentApplyListBinding;
    ApplyModel mApplyModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
//        View contentView = inflater.inflate(R.layout.fragment_apply_list,container,false);
        mFragmentApplyListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_apply_list, container, false);
        mApplyModel = new ApplyModel();
        mFragmentApplyListBinding.setApplyModel(mApplyModel);
        TabLayout mTabLayout = mFragmentApplyListBinding.contentTab;
        ViewPager mViewPager = mFragmentApplyListBinding.contentPager;
        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(),getContext()));
        mTabLayout.setupWithViewPager(mViewPager);
        return mFragmentApplyListBinding.getRoot();
    }


}