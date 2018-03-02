package cn.jsjst.reimbursement.component.shortcut;

import android.view.View;

import cn.jsjst.reimbursement.component.base.BaseViewModel;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ShortcutViewModel extends BaseViewModel<ShortcutActivity> {

    public ShortcutViewModel(ShortcutActivity context) {
        super(context);
    }

    @Override
    public String getPageTitle() {
        return null;
    }

    @Override
    public boolean showBackIcon() {
        return false;
    }

    @Override
    public boolean showMenuIcon() {
        return false;
    }

    @Override
    public int getMenuIcon() {
        return 0;
    }

    public void onBTApplyClick(View view) {
        mActivity.startTravelApply();
    }

    public void onBTReimbClick(View view) {
        mActivity.startTravelReimb();
    }

    public void onPSApplyClick(View view) {
        mActivity.startOfficialApply();
    }

    public void onPSReimbClick(View view) {
        mActivity.startOfficialReimb();
    }

    public void onScanClick(View view) {

    }

    public void onUploadFileClick(View view) {

    }

    public void onCancelClick(View view) {
        mActivity.finish();
    }
}
