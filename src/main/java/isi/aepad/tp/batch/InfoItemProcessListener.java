package isi.aepad.tp.batch;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.listener.ItemProcessListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent	   
@Named("InfoItemProcessListeners")
public class InfoItemProcessListener implements ItemProcessListener {
    private static final Logger logger = Logger.getLogger(InfoItemProcessListener.class.getName());

    @Override
    public void beforeProcess(Object o) throws Exception {
        logger.log(Level.INFO, "beforeProcess "+o.toString());
    }

	@Override
	public void afterProcess(Object item, Object result) throws Exception {
        logger.log(Level.INFO, "afterProcess "+result.toString());
		
	}

	@Override
	public void onProcessError(Object item, Exception ex) throws Exception {
        logger.log(Level.INFO, "onProcessError "+item+ " ex "+ex.getMessage());		
	}
}
