package cd.demo.hystrixTests.simulationOfNics;

/**
 * @ClassName: cd.demo.hystrixTests.simulationOfNics.OutboundOutvo.class
 * @UserAndTime: saske2-2020/8/15-14:42
 * @Descript: xxxxx
 * @Reference: {@link}
 **/
public class CdA0991A002OutboundOutvo {
    public String ctrlMsrMtdcd;
    public String crcrdCardno;
    public String sysEvtTraceId;
    public String crcrdarId;

    public CdA0991A002OutboundOutvo() {
    }

    public String getCtrlMsrMtdcd() {
        return ctrlMsrMtdcd;
    }

    public void setCtrlMsrMtdcd(String ctrlMsrMtdcd) {
        this.ctrlMsrMtdcd = ctrlMsrMtdcd;
    }

    public String getCrcrdCardno() {
        return crcrdCardno;
    }

    public void setCrcrdCardno(String crcrdCardno) {
        this.crcrdCardno = crcrdCardno;
    }

    public String getSysEvtTraceId() {
        return sysEvtTraceId;
    }

    public void setSysEvtTraceId(String sysEvtTraceId) {
        this.sysEvtTraceId = sysEvtTraceId;
    }

    public String getCrcrdarId() {
        return crcrdarId;
    }

    public void setCrcrdarId(String crcrdarId) {
        this.crcrdarId = crcrdarId;
    }

    @Override
    public String toString() {
        return "CdA0991A002OutboundOutvo{" +
                "ctrlMsrMtdcd='" + ctrlMsrMtdcd + '\'' +
                ", crcrdCardno='" + crcrdCardno + '\'' +
                ", sysEvtTraceId='" + sysEvtTraceId + '\'' +
                ", crcrdarId='" + crcrdarId + '\'' +
                '}';
    }
}
