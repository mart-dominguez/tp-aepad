package isi.aepad.tp.batch;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.listener.JobListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named("InfoJobListener")
public class InfoJobListener implements JobListener {
	
    private static final Logger logger = Logger.getLogger(InfoJobListener.class.getName());

    @Override
    public void beforeJob() throws Exception {
        logger.log(Level.INFO, "The job is starting");
    }
 
    @Override
    public void afterJob() throws Exception { 
        logger.log(Level.INFO, "The job is ending");
    }
}