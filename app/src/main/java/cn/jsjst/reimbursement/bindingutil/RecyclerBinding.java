package cn.jsjst.reimbursement.bindingutil;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.jsjst.reimbursement.adapter.CustomAdapter;

/**
 * RecyclerView 在布局文件中使用的自定义属性值
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class RecyclerBinding {

    @BindingAdapter("app:data")
    public static void setRecyclerInfos(RecyclerView recyclerView , List datas){
        CustomAdapter mAdapter = (CustomAdapter) recyclerView.getAdapter();
        mAdapter.setData(datas);
    }
}
