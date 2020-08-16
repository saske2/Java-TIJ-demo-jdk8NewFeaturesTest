package cd.demo.hystrixTests.simulationOfNics;

import com.atguigu.bean.Person;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: cd.demo.hystrixTests.simulationOfNics.ncisOutBoundExcutor.class
 * @UserAndTime: saske2-2020/8/15-13:20
 * @Descript: xxxxx
 * @Reference: {@link}
 **/
public class ncisOutBoundExcutor {

    public ncisOutBoundExcutor() {
    }

    public static CdA0991A002OutboundOutvo outboundwithShardingkey(FullSimuAuthEntity fullSimuAuthEntity) throws InterruptedException {
        System.out.println("outboundwithShardingkey_beginSleep");
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("outboundwithShardingkey_finishSleep");
        CdA0991A002OutboundOutvo cdA0991A002OutboundOutvo = new CdA0991A002OutboundOutvo();
        cdA0991A002OutboundOutvo.setCrcrdCardno(fullSimuAuthEntity.getCrcrdCardno());
        cdA0991A002OutboundOutvo.setCtrlMsrMtdcd("pass");
        return cdA0991A002OutboundOutvo;
    }

    public static CdA0991A002OutboundOutvo outboundInvoke(FullSimuAuthEntity fullSimuAuthEntity) throws InterruptedException {
        System.out.println("outboundInvoke_beginSleep");
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("outboundInvoke_finishSleep");

        CdA0991A002OutboundOutvo cdA0991A002OutboundOutvo = new CdA0991A002OutboundOutvo();
        cdA0991A002OutboundOutvo.setCrcrdCardno(fullSimuAuthEntity.getCrcrdCardno());
        cdA0991A002OutboundOutvo.setCtrlMsrMtdcd("pass");
        return cdA0991A002OutboundOutvo;
    }

    public static CdA0991A002OutboundOutvo outboundInvokeError(FullSimuAuthEntity fullSimuAuthEntity) throws InterruptedException {
        System.out.println("outboundInvokeError_beginSleep");
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("outboundInvokeError_finishSleep");
        throw new MyNRuntimeException("MyNExceptions_outboundInvokeError");
    }
}
