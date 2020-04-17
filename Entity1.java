package cd.work.demo.beanOperate;

import java.io.Serializable;
import java.math.BigDecimal;

public class Entity1 implements Serializable {
    public String crcrdCardno;
    public BigDecimal amt;
    public String getCrcrdCardno() {
        return crcrdCardno;
    }
    public void setCrcrdCardno(String crcrdCardno) {
        this.crcrdCardno = crcrdCardno;
    }
    public BigDecimal getAmt() {
        return amt;
    }
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }
    @Override
    public String toString() {
        int leng1;
        if("".equals(crcrdCardno)||crcrdCardno==null)
            leng1=0;
        else leng1=crcrdCardno.length();
        return "Entity1{" +
                "crcrdCardno='" + crcrdCardno + '\'' +"crcrdCardno_length:"+leng1+
                ", amt=" + amt +
                '}';
    }
}