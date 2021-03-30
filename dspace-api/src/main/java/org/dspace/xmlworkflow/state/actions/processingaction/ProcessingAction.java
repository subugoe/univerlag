/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.xmlworkflow.state.actions.processingaction;

import org.dspace.core.Context;
import org.dspace.xmlworkflow.state.actions.Action;
import org.dspace.xmlworkflow.storedcomponents.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * A class that that extends the action to support the common
 * isAuthorized method
 *
 * @author Bram De Schouwer (bram.deschouwer at dot com)
 * @author Kevin Van de Velde (kevin at atmire dot com)
 * @author Ben Bosman (ben at atmire dot com)
 * @author Mark Diggory (markd at atmire dot com)
 */
public abstract class ProcessingAction extends Action {
    private static final Logger log = Logger.getLogger(ProcessingAction.class);

    @Override
    public boolean isAuthorized(Context context, HttpServletRequest request, XmlWorkflowItem wfi) throws SQLException {
        ClaimedTask task = null;
	log.debug("Checking Authorization with WFI: " + wfi.getID() + " User: " + context.getCurrentUser().getID());
        if(context.getCurrentUser() != null)
            task = ClaimedTask.findByWorkflowIdAndEPerson(context, wfi.getID(), context.getCurrentUser().getID());
  
        //Check if we have claimed the current task
	log.debug("Checking  if we have claimed the current task...");
        return task != null &&
                task.getWorkflowID().equals(getParent().getStep().getWorkflow().getID()) &&
                task.getStepID().equals(getParent().getStep().getId()) &&
                task.getActionID().equals(getParent().getId());
    }
}
