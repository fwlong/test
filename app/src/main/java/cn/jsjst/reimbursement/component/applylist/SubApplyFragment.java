package cn.jsjst.reimbursement.component.applylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragment;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class SubApplyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_apply_approval_sub,container,false);
        return contentView;
    }
}
