/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.renResourcing.context;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sysu.renResourcing.basic.enums.AgentReentrantType;
import org.sysu.renResourcing.basic.enums.WorkerType;
import org.sysu.renResourcing.context.steady.RenRsparticipantEntity;
import org.sysu.renResourcing.utility.HibernateUtil;
import org.sysu.renResourcing.utility.LogUtil;

import java.io.Serializable;

/**
 * Author: Rinkako
 * Date  : 2018/2/4
 * Usage : Task context is an encapsulation of RenRsparticipant in a
 *         convenient way for resourcing service.
 */
public class ParticipantContext implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Worker global id.
     */
    private String workerId;

    /**
     * User-friendly resource name.
     */
    private String displayName;

    /**
     * Worker type enum.
     */
    private WorkerType workerType;

    /**
     * Agent type enum, only valid when worker type is Agent.
     */
    private AgentReentrantType agentType;

    /**
     * Get a participant context by its global id.
     * @param workerId worker global id
     * @return Participant resourcing context, null if exception occurred or assertion error
     */
    public static ParticipantContext GetContext(String rtid, String workerId) {
        Session session = HibernateUtil.GetLocalThreadSession();
        Transaction transaction = session.beginTransaction();
        boolean cmtFlag = false;
        try {
            RenRsparticipantEntity rre = session.get(RenRsparticipantEntity.class, workerId);
            assert rre != null;
            transaction.commit();
            cmtFlag = true;
            return ParticipantContext.GenerateParticipantContext(rre);
        }
        catch (Exception ex) {
            if (!cmtFlag) {
                transaction.rollback();
            }
            LogUtil.Log("When json serialization exception occurred, transaction rollback. " + ex,
                    TaskContext.class.getName(), LogUtil.LogLevelType.ERROR, rtid);
            return null;
        }
    }

    /**
     * Get worker global id.
     * @return id string
     */
    public String getWorkerId() {
        return this.workerId;
    }

    /**
     * Get a user-friendly display name.
     * @return name string
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Get the worker resource type.
     * @return WorkerType enum
     */
    public WorkerType getWorkerType() {
        return this.workerType;
    }

    /**
     * Get the agent type, valid only worker type is Agent.
     * @return AgentReentrantType enum
     */
    public AgentReentrantType getAgentType() {
        return this.agentType;
    }

    /**
     * Generate a participant context by a steady entity.
     * @param rsparticipantEntity RS participant entity
     * @return equivalent task context.
     */
    private static ParticipantContext GenerateParticipantContext(RenRsparticipantEntity rsparticipantEntity) {
        assert rsparticipantEntity != null;
        ParticipantContext context = new ParticipantContext(rsparticipantEntity.getWorkerid(),
                WorkerType.values()[rsparticipantEntity.getType()]);
        context.agentType = AgentReentrantType.values()[rsparticipantEntity.getReentrantType()];
        context.displayName = rsparticipantEntity.getDisplayname();
        return context;
    }

    /**
     * Create a new participant context.
     * Private constructor for preventing create context without using `{@code ParticipantContext.GetContext}`.
     * @param workerId worker global id
     * @param type worker type enum
     */
    private ParticipantContext(String workerId, WorkerType type) {
        this.workerId = workerId;
        this.workerType = type;
    }

}