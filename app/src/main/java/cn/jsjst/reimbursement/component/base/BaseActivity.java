package cn.jsjst.reimbursement.component.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.jsjst.reimbursement.R;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getColor(R.color.color_white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void refreshView(View view){}

    public void loadMoreView(View view){}

    public void showLoading(){}

    public void finishLoading(){}

    public void startActivityWithCommonAnimation(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.common_enter_in,R.anim.common_alpha_exit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.common_exit_out);
    }

}


