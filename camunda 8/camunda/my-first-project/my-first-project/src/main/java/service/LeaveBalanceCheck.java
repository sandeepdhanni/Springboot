package service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("LeaveBalanceCheck")
@Component(value = "approve")
public class LeaveBalanceCheck implements JavaDelegate {
    public static final int LEAVE_BALANCE=5;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @SuppressWarnings("unused")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if(LEAVE_BALANCE>0){
            logger.info("Employee has leave balance..");
        }else{
            logger.info("Employee don't have any leave balance...");
        }
    }
}
