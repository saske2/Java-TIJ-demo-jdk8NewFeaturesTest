package cd.demo.hystrixTests.simulationOfNics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.Future;

/**
 * @ClassName: cd.demo.hystrixTests.simulationOfNics.FullSimuAuthEntity.class
 * @UserAndTime: saske2-2020/8/16-11:57
 * @Descript: xxxxx
 * @Reference: {@link}
 **/
public class FullSimuAuthEntity implements Serializable {
    private String systxcode;
    private BigDecimal authTxAmt;
    private String accentryDate;
    private String crcrdCardno;

    private Future asynOutboundFuture;

    public String getSystxcode() {
        return systxcode;
    }

    public void setSystxcode(String systxcode) {
        this.systxcode = systxcode;
    }

    public BigDecimal getAuthTxAmt() {
        return authTxAmt;
    }

    public void setAuthTxAmt(BigDecimal authTxAmt) {
        this.authTxAmt = authTxAmt;
    }

    public String getAccentryDate() {
        return accentryDate;
    }

    public void setAccentryDate(String accentryDate) {
        this.accentryDate = accentryDate;
    }

    public String getCrcrdCardno() {
        return crcrdCardno;
    }

    public void setCrcrdCardno(String crcrdCardno) {
        this.crcrdCardno = crcrdCardno;
    }

    public Future getAsynOutboundFuture() {
        return asynOutboundFuture;
    }

    public void setAsynOutboundFuture(Future asynOutboundFuture) {
        this.asynOutboundFuture = asynOutboundFuture;
    }
}
