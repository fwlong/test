package cn.jsjst.reimbursement.component.base;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public abstract class BaseFragmentViewModel<T extends Fragment> {

    public T mFragment;

    public final ToolbarViewModel mToolbarViewModel = new ToolbarViewModel();

    public ObservableField<Boolean> isEmpty = new ObservableField<>(false);

    public ObservableField<Boolean> isNetError = new ObservableField<>(false);


    public BaseFragmentViewModel(T context) {
        mFragment = context;
        initToolbar();
    }


    private void initToolbar(){
        mToolbarViewModel.setEventListener(new ToolbarEventListener() {
            @Override
            public void onBackEvent(View view) {
                onLeftIconClick();
            }

            @Override
            public void onRightMenuEvent(View view) {
                onRightIconClick(view);
            }
        });
        mToolbarViewModel.title.set(getPageTitle());
        mToolbarViewModel.showBackIcon.set(showBackIcon());
        if(getMenuIcon()<=0){
            mToolbarViewModel.showRightMenuIcon.set(false);
        }else{
            mToolbarViewModel.showRightMenuIcon.set(showMenuIcon());
            mToolbarViewModel.menuIconId.set(getMenuIcon());
        }
    }

    /**
     * 周期函数，在activity，fragment里调用
     */

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestory() {
    }

    /**
     * 标题栏设置
     */

    public abstract String getPageTitle();

    public abstract boolean showBackIcon();

    public abstract boolean showMenuIcon();

    public abstract int getMenuIcon();

    public void onLeftIconClick() {
        if(mFragment.isAdded()){
            mFragment.getActivity().onBackPressed();
        }
    }

    public void onRightIconClick(View view) {
    }
}
