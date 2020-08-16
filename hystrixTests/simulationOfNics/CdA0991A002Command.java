package cd.demo.hystrixTests.simulationOfNics;

import com.atguigu.bean.Car;
import com.atguigu.bean.Person;
import com.netflix.hystrix.*;

/**
 * @ClassName: cd.demo.hystrixTests.simulationOfNics.CdA0991A002Command.class
 * @UserAndTime: saske2-2020/8/15-14:41
 * @Descript: xxxxx
 * @Reference: {@link}
 **/
public class CdA0991A002Command extends HystrixCommand<CdA0991A002OutboundOutvo> {

    private FullSimuAuthEntity fullSimuAuthEntity;

    public CdA0991A002Command(FullSimuAuthEntity fullSimuAuthEntity) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("outcallService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("A0991A002"))
                //这里的线程池策略看看是以SYS_TX_CODE为一个组起1个还是以A0991A002作为一个线程池资源起10个
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(fullSimuAuthEntity.getSystxcode()))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1))
                .andCommandPropertiesDefaults(
                                                HystrixCommandProperties.Setter().withFallbackEnabled(false)
//                                                HystrixCommandProperties.Setter().withFallbackEnabled(true)
                                                .withExecutionTimeoutInMilliseconds(10000)
                                                 //熔断机制并没有开启
                                                .withCircuitBreakerEnabled(false)
                                                .withCircuitBreakerErrorThresholdPercentage(60)
                                                .withCircuitBreakerSleepWindowInMilliseconds(3000)));
        this.fullSimuAuthEntity = fullSimuAuthEntity;
    }

    @Override
    protected CdA0991A002OutboundOutvo run() {
        FullSimuAuthEntity f1 = generatePerson(fullSimuAuthEntity);
//        CdA0991A002OutboundOutvo outvo = ncisOutBoundExcutor.outboundInvoke(f1);
        CdA0991A002OutboundOutvo outvo = null;
        try {
            outvo = ncisOutBoundExcutor.outboundInvokeError(f1);
        } catch (InterruptedException e) {
            throw new RuntimeException("MyNExceptions_outboundInvokeError2");
        }
        return outvo;
    }

    @Override
    protected CdA0991A002OutboundOutvo getFallback() {
        throw new RuntimeException("this is fallback error");
//        CdA0991A002OutboundOutvo n = new CdA0991A002OutboundOutvo();
//        n.setCtrlMsrMtdcd("pass");
//        return n;
    }

    private FullSimuAuthEntity generatePerson(FullSimuAuthEntity fullSimuAuthEntity){
        FullSimuAuthEntity p1 = new FullSimuAuthEntity();
        p1.setAccentryDate(fullSimuAuthEntity.getAccentryDate());
        p1.setAuthTxAmt(fullSimuAuthEntity.getAuthTxAmt());
        p1.setCrcrdCardno(fullSimuAuthEntity.getCrcrdCardno());
        p1.setSystxcode(fullSimuAuthEntity.getSystxcode());
        return p1;
    }
}
