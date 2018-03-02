package cn.jsjst.reimbursement.component.events;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class EventReimbFinishMessage {

    public final static int TYPE_TRAVEL = 0x1;

    public final static int TYPE_OFFICIAL = 0x2;

    private int typeReimb = TYPE_TRAVEL;

    public EventReimbFinishMessage(int typeReimb) {
        this.typeReimb = typeReimb;
    }

    public boolean isTravelReimb(){
        return typeReimb == TYPE_TRAVEL;
    }
}
