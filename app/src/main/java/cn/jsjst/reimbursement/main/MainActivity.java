package cn.jsjst.reimbursement.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.jsjst.reimbursement.R;
import cn.jsjst.reimbursement.databinding.ActivityMainBinding;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        MainViewModel mainViewModel = new MainViewModel(this);
        mainViewModel.initUI(R.id.content);
        activityMainBinding.setMainModel(mainViewModel);
    }
}
