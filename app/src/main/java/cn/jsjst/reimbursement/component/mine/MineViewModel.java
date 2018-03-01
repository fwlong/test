package cn.jsjst.reimbursement.component.mine;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.component.base.BaseFragmentViewModel;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MineViewModel extends BaseFragmentViewModel<MineFragment> {

    public ObservableField<String> userName = new ObservableField<>("test name");
    public ObservableField<String> userDepart = new ObservableField<>("test depart");
    public ObservableField<String> userDuties = new ObservableField<>("test duties");

    public MineViewModel(MineFragment context) {
        super(context);
    }


    @Override
    public String getPageTitle() {
        return mFragment.getString(R.string.title_page_mine);
    }

    @Override
    public boolean showBackIcon() {
        return false;
    }

    @Override
    public boolean showMenuIcon() {
        return true;
    }

    @Override
    public int getMenuIcon() {
        return R.drawable.ic_add_alert;
    }


    @Override
    public void onRightIconClick(View view) {
        super.onRightIconClick(view);
        if (mFragment.isAdded()) {
            Toast.makeText(mFragment.getContext(),"right menu click",Toast.LENGTH_LONG).show();
        }
    }

    public void settingClick(View view){
        if(mFragment.isAdded()){
            Toast.makeText(mFragment.getContext(),"setting click",Toast.LENGTH_LONG).show();
        }
    }

    public void logoutClick(View view){
        if(mFragment.isAdded()){
            Toast.makeText(mFragment.getContext(),"logout click",Toast.LENGTH_LONG).show();
        }
    }
}
