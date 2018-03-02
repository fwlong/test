package cn.jsjst.reimbursement.component.base;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public abstract class BaseViewModel<T extends BaseActivity> {

    public T mActivity;

    public final ToolbarViewModel mToolbarViewModel = new ToolbarViewModel();

    public ObservableField<Boolean> isEmpty = new ObservableField<>(false);

    public ObservableField<Boolean> isNetError = new ObservableField<>(false);


    public BaseViewModel(T context) {
        mActivity = context;
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
        mToolbarViewModel.showRightMenuIcon.set(showMenuIcon());
        if(getMenuIcon()<=0){
            mToolbarViewModel.showRightMenuIcon.set(false);
        }else{
            mToolbarViewModel.menuIconId.set(getMenuIcon());
        }
    }

    public abstract String getPageTitle();

    public abstract boolean showBackIcon();

    public abstract boolean showMenuIcon();

    public abstract int getMenuIcon();

    public void onLeftIconClick() {
        mActivity.onBackPressed();
    }

    public void onRightIconClick(View view) {
    }


}
