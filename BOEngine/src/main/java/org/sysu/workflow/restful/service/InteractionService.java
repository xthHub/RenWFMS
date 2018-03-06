/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.workflow.restful.service;

import org.sysu.renCommon.enums.LogLevelType;
import org.sysu.workflow.SCXMLExecutionContext;
import org.sysu.workflow.SCXMLIOProcessor;
import org.sysu.workflow.env.MultiStateMachineDispatcher;
import org.sysu.workflow.instanceTree.InstanceManager;
import org.sysu.workflow.instanceTree.RInstanceTree;
import org.sysu.workflow.instanceTree.RTreeNode;
import org.sysu.workflow.model.extend.MessageMode;
import org.sysu.workflow.utility.LogUtil;
import org.sysu.workflow.utility.SerializationUtil;

import java.util.HashMap;

/**
 * Author: Rinkako
 * Date  : 2018/3/1
 * Usage : Handle state-machine interaction request outside BO Engine
 */
public class InteractionService {

    /**
     * Handle received callback event and dispatch it to destination BO tree node.
     * @param rtid process rtid (required)
     * @param bo from which BO (required)
     * @param on which callback scene (required)
     * @param event event send to engine (required)
     * @param payload event send to engine
     */
    public static void DispatchCallback(String rtid, String bo, String on, String event, String payload) {
        try {
            HashMap payloadMap = payload != null ? SerializationUtil.JsonDeserialization(payload, HashMap.class) : new HashMap();
            RInstanceTree instanceTree = InstanceManager.GetInstanceTree(rtid);
            if (instanceTree == null) {
                LogUtil.Log(String.format("Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but tree not exist, ignored.",
                        bo, on, event, payload), InteractionService.class.getName(), LogLevelType.WARNING, rtid);
                return;
            }
            RTreeNode mainBONode = instanceTree.Root;
            if (mainBONode == null) {
                LogUtil.Log(String.format("Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but main BO not exist, ignored.",
                        bo, on, event, payload), InteractionService.class.getName(), LogLevelType.WARNING, rtid);
                return;
            }
            LogUtil.Log(String.format("Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s )",
                    bo, on, event, payload), InteractionService.class.getName(), LogLevelType.INFO, rtid);
            MultiStateMachineDispatcher dispatcher = (MultiStateMachineDispatcher) mainBONode.getExect().getEventDispatcher();
            SCXMLExecutionContext ctx = mainBONode.getExect();
            dispatcher.send(ctx.Rtid, ctx.Tid, "", MessageMode.UNICAST, bo, "",
                    SCXMLIOProcessor.DEFAULT_EVENT_PROCESSOR, event, payloadMap, "", 0);
        }
        catch (Exception ex) {
            LogUtil.Log(String.format("Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but exception occurred, %s",
                bo, on, event, payload, ex), InteractionService.class.getName(), LogLevelType.ERROR, rtid);
            throw ex;  // rethrow to controller
        }
    }
}
