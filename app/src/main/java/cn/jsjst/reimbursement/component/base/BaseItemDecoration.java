package cn.jsjst.reimbursement.component.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
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
public class BaseItemDecoration extends RecyclerView.ItemDecoration {

    private int mDecorationHeight;

    public BaseItemDecoration(Context context) {
        mDecorationHeight = context.getResources().getDimensionPixelOffset(R.dimen.custom_recycler_decoration_height);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDecorationHeight;
    }
}
