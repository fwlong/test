package cn.jsjst.reimbursement.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.databinding.ActivityTestBinding;

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
        ActivityTestBinding atb = DataBindingUtil.setContentView(this, R.layout.activity_test);
        atb.setBaseModel(new BaseViewModel(this) {
            @Override
            public void showLoading() {
                super.showLoading();
            }

            @Override
            public void finishLoading() {
                super.finishLoading();
            }

            @Override
            public void onCreate() {
                super.onCreate();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onStop() {
                super.onStop();
            }

            @Override
            public void onDestory() {
                super.onDestory();
            }
        });
    }
}


