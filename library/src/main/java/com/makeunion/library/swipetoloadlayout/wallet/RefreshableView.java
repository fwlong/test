/*
 * -------------------------------------------------------------------------------------
 *     Mi-Me Confidential
 *
 *     Copyright (C) 2015 Shanghai Mi-Me Financial Information Service Co., Ltd.
 *     All rights reserved.
 *
 *     No part of this file may be reproduced or transmitted in any form or by any means,
 *     electronic, mechanical, photocopying, recording, or otherwise, without prior
 *     written permission of Shanghai Mi-Me Financial Information Service Co., Ltd.
 *
 * -------------------------------------------------------------------------------------
 */

package com.makeunion.library.swipetoloadlayout.wallet;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 基类
 *
 * @author renjialiang
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public abstract class RefreshableView extends View implements Animatable {

    public RefreshableView(Context context) {
        this(context,null);
    }

    public RefreshableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    abstract void setPercent(float percent);
    abstract void setAnimationDuration(int repeatDuration);
    abstract void setIsAnimation(boolean isAnimation);

    public abstract void setIsBeingDragged(boolean isBeingDragged);

    public abstract void setRefreshColor(int refreshColor);
}
