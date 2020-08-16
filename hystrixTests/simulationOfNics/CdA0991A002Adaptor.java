package cd.demo.hystrixTests.simulationOfNics;

import com.atguigu.bean.Person;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @ClassName: cd.demo.hystrixTests.simulationOfNics.CdA0991A002Adaptor.class
 * @UserAndTime: saske2-2020/8/15-14:37
 * @Descript: xxxxx
 * @Reference: {@link}
 **/
public class CdA0991A002Adaptor implements Consumer<FullSimuAuthEntity> {

    @Override
    public void accept(FullSimuAuthEntity fullSimuAuthEntity) {
        CdA0991A002Command cdA0991A002Command = new CdA0991A002Command(fullSimuAuthEntity);
        Future<CdA0991A002OutboundOutvo> future = null;
        try {
//            cdA0991A002Command.execute();
            future = cdA0991A002Command.queue();
        }catch (Exception e){
            throw e;
        }
        System.out.println("CdA0991A002Adaptor_accept_over");
        fullSimuAuthEntity.setAsynOutboundFuture(future);
    }
}
