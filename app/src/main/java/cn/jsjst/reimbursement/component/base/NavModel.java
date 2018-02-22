package cn.jsjst.reimbursement.component.base;

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
public class NavModel {

    public final static int HOME_TAB = 0x1;
    public final static int APPLY_LIST_TAB = 0x2;
    public final static int CHART_TAB = 0x3;
    public final static int MINE_TAB = 0x4;
    public final static int SHORTCUT_TAB = 0x5;


    public ObservableField<Boolean> isHomeChecked = new ObservableField<>(true);
    public ObservableField<Boolean> isApplyListChecked = new ObservableField<>(false);
    public ObservableField<Boolean> isChartChecked = new ObservableField<>(false);
    public ObservableField<Boolean> isMineChecked = new ObservableField<>(false);

    private OnNaviBottomClickListener mClickListener;

    public NavModel(){

    }

    public NavModel(OnNaviBottomClickListener mListener) {
        this.mClickListener = mListener;
    }

    public void setOnNaviBottomClickListener(OnNaviBottomClickListener mListener) {
        this.mClickListener = mListener;
    }

    public void onHomeTabClick(View view) {
        setCheckedState(true, false, false, false);
        if (mClickListener != null) {
            mClickListener.onNaviBottomClick(HOME_TAB);
        }
    }

    public void onApplyListTabClick(View view) {
        setCheckedState(false, true, false, false);
        if (mClickListener != null) {
            mClickListener.onNaviBottomClick(APPLY_LIST_TAB);
        }
    }

    public void onChartTabClick(View view) {
        setCheckedState(false, false, true, false);
        if (mClickListener != null) {
            mClickListener.onNaviBottomClick(CHART_TAB);
        }
    }

    public void onMineTabClick(View view) {
        setCheckedState(false, false, false, true);
        if (mClickListener != null) {
            mClickListener.onNaviBottomClick(MINE_TAB);
        }
    }

    public void onShortCutClick(View view) {
        if (mClickListener != null) {
            mClickListener.onNaviBottomClick(SHORTCUT_TAB);
        }
    }

    public static interface OnNaviBottomClickListener {
        public void onNaviBottomClick(int type);
    }

    private void setCheckedState(boolean isHome, boolean isApplyList,
            boolean isChart, boolean isMine) {
        isHomeChecked.set(isHome);
        isApplyListChecked.set(isApplyList);
        isChartChecked.set(isChart);
        isMineChecked.set(isMine);
    }

}
