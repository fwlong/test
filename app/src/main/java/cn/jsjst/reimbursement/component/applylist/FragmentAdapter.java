package cn.jsjst.reimbursement.component.applylist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.jsjst.reimbursement.R;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final static List<Fragment> fragments = new ArrayList<>();
    static {
        fragments.add(new SubApplyFragment());
        fragments.add(new SubApprovalFragment());
    }
    private final Context mContext;

    public FragmentAdapter(FragmentManager fm,Context context){
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
            return mContext.getString(R.string.title_my_apply_list);
            case 1:
                return mContext.getString(R.string.title_my_approval_list);
            default:
                return "";
        }
    }
}
