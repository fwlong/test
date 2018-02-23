package cn.jsjst.reimbursement.component.applylist;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class ItemModelView {

    public final static int TYPE_APPLY = 0x1;
    public final static int TYPE_APPROVAL = 0x2;

    private int itemType = TYPE_APPLY;

    public final ObservableField<String> imgUrl = new ObservableField<>();

    public final ObservableField<String> applyName = new ObservableField<>();

    public final ObservableField<String> applyNo = new ObservableField<>();

    public final ObservableField<String> applyDepat = new ObservableField<>();

    public final ObservableInt applyStatusInt = new ObservableInt();

    public final ObservableField<String> applyStatus = new ObservableField<>();

    public final ObservableField<String> applyTime = new ObservableField<>();

    public final ObservableField<String> applyType = new ObservableField<>();

    public final ObservableInt applyTypeInt = new ObservableInt();


    public ItemModelView() {
    }

    public ItemModelView(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void onItemClick(View view) {
       //TODO - 跳转详情页面
    }
}
