package cn.jsjst.reimbursement.component.events;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class EventApplyFinishMessage {
    public final static int TYPE_TRAVEL = 1;
    public final static int TYPE_OFFICIAL = 2;

    private int applyType = TYPE_TRAVEL;

    public EventApplyFinishMessage(int applyType) {
        this.applyType = applyType;
    }

    public boolean isTravelApply(){
        return applyType == TYPE_TRAVEL;
    }
}
