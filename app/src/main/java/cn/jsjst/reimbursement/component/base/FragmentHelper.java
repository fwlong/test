package cn.jsjst.reimbursement.component.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import cn.jsjst.reimbursement.component.applylist.ApplyListFragment;
import cn.jsjst.reimbursement.component.chart.ChartFragment;
import cn.jsjst.reimbursement.component.home.HomeFragment;
import cn.jsjst.reimbursement.component.mine.MineFragment;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class FragmentHelper {

    public final static String TAG_HOME = "home";
    public final static String TAG_MINE = "mine";
    public final static String TAG_CHART = "chart";
    public final static String TAG_APPLY_LIST = "apply_list";

    public static Fragment getFragmentByTag(AppCompatActivity activity, String tag) {
        if (activity == null || tag == null) {
            return null;
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment target = fragmentManager.findFragmentByTag(tag);
        switch (tag) {
            case TAG_HOME:
                return target == null ? new HomeFragment() : (HomeFragment) target;
            case TAG_APPLY_LIST:
                return target == null ? new ApplyListFragment() : (ApplyListFragment) target;
            case TAG_CHART:
                return target == null ? new ChartFragment() : (ChartFragment) target;
            case TAG_MINE:
                return target == null ? new MineFragment() : (MineFragment) target;
            default:
                return null;
        }
    }

    public static void switchFragment(AppCompatActivity activity, int contentId, String from, String to) {
        if (to == null || to.equals(from)) {
            return;
        }
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment toFrag = getFragmentByTag(activity, to);
        if (from != null) {
            Fragment fromFrag = getFragmentByTag(activity, from);
            if (fromFrag != null && fromFrag.isAdded() ) {
                transaction.hide(fromFrag);
            }
        }
        if(toFrag!=null){
            if(toFrag.isAdded()){
                transaction.show(toFrag);
            }else{
                transaction.add(contentId,toFrag,to);
            }
        }
        transaction.commit();
    }
}
