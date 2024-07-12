package org.aba.web.scheduling;

import org.aba.web.manager.ApplicationManager;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;


/**
 * {@inheritDoc}
 */
@Component("cronConfigReloadCheck")
@Scope("singleton")
@Transactional(readOnly = true)
public class CronConfigReloadCheckImpl implements CronConfigReloadCheck, Serializable
{
    private static final long serialVersionUID = -7580586674736587430L;

    @Autowired
    private ApplicationManager applicationManager;

    @Override
    @Scheduled(cron = "0 */1 05-20 * * *")
    @Async
    public void runScheduled()
    {
//        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "CronConfigReloadCheckImpl running on: "
//                + CommonUtils.getDateAndTimeLongFormatted(applicationManager.getApplDate()));

        Date lastDateOnDatabase = null;

    }
}