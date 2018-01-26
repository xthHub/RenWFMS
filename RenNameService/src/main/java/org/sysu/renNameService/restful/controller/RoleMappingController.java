/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.renNameService.restful.controller;
import org.springframework.web.bind.annotation.*;
import org.sysu.renNameService.NSScheduler;
import org.sysu.renNameService.restful.dto.ReturnModel;
import org.sysu.renNameService.restful.dto.ReturnModelHelper;
import org.sysu.renNameService.restful.dto.StatusCode;
import org.sysu.renNameService.transaction.NameServiceTransaction;
import org.sysu.renNameService.transaction.TransactionCreator;
import org.sysu.renNameService.transaction.TransactionType;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2018/1/19
 * Usage : Handle requests about business role and worker mappings.
 */
@RestController
@RequestMapping("/rolemap")
public class RoleMappingController {
    /**
     * Get worker's id by his business role.
     * @param rtid process rtid
     * @param brole business role name
     * @return response package
     */
    @RequestMapping(value = "/getWorkerByBRole", produces = {"application/json", "application/xml"})
    @ResponseBody
    public ReturnModel GetWorkerByBusinessRole(@RequestParam(value="rtid", required = false)String rtid,
                                               @RequestParam(value="brole", required = false)String brole) {
        ReturnModel rnModel = new ReturnModel();
        try {
            // miss params
            List<String> missingParams = new ArrayList<>();
            if (rtid == null) missingParams.add("rtid");
            if (brole == null) missingParams.add("brole");
            if (missingParams.size() > 0) {
                rnModel = ReturnModelHelper.MissingParametersResponse(missingParams);
                return rnModel;
            }
            // logic
            HashMap<String, String> args = new HashMap<>();
            args.put("rtid", rtid);
            args.put("brole", brole);
            NameServiceTransaction t = TransactionCreator.Create(TransactionType.BusinessRoleMapping, "getWorkerByBRole", args);
            String jsonifyResult = (String) RoleMappingController.scheduler.Schedule(t);
            // return
            ReturnModelHelper.WrapResponse(rnModel, StatusCode.OK, jsonifyResult);
        } catch (Exception e) {
            rnModel = ReturnModelHelper.ExceptionResponse(e.getClass().getName());
        }
        return rnModel;
    }

    /**
     * Get business role by the worker's id.
     * @param rtid process rtid
     * @param gid worker global id
     * @return response package
     */
    @RequestMapping(value = "/getBRoleByWorker", produces = {"application/json", "application/xml"})
    @ResponseBody
    public ReturnModel GetBusinessRoleByGlobalId(@RequestParam(value="rtid", required = false)String rtid,
                                                 @RequestParam(value="gid", required = false)String gid) {
        ReturnModel rnModel = new ReturnModel();
        try {
            // miss params
            List<String> missingParams = new ArrayList<>();
            if (rtid == null) missingParams.add("rtid");
            if (gid == null) missingParams.add("gid");
            if (missingParams.size() > 0) {
                rnModel = ReturnModelHelper.MissingParametersResponse(missingParams);
                return rnModel;
            }
            // logic
            HashMap<String, String> args = new HashMap<>();
            args.put("rtid", rtid);
            args.put("gid", gid);
            NameServiceTransaction t = TransactionCreator.Create(TransactionType.BusinessRoleMapping, "getBRoleByWorker", args);
            String jsonifyResult = (String) RoleMappingController.scheduler.Schedule(t);
            // return
            ReturnModelHelper.WrapResponse(rnModel, StatusCode.OK, jsonifyResult);
        } catch (Exception e) {
            rnModel = ReturnModelHelper.ExceptionResponse(e.getClass().getName());
        }
        return rnModel;
    }


    /**
     * Register a mapping to RoleMap Service.
     * @param rtid process rtid
     * @param organGid organization global id
     * @param dataVersion organization data version
     * @param isolationType organization data isolation type
     * @param map map descriptor
     * @return response package
     */
    @RequestMapping(value = "/register", produces = {"application/json", "application/xml"})
    @ResponseBody
    public ReturnModel RegisterRoleMapService(@RequestParam(value="rtid", required = false)String rtid,
                                              @RequestParam(value="organgid", required = false)String organGid,
                                              @RequestParam(value="dataversion", required = false)String dataVersion,
                                              @RequestParam(value="isolationtype", required = false)String isolationType,
                                              @RequestParam(value="map", required = false)String map) {
        ReturnModel rnModel = new ReturnModel();
        try {
            // miss params
            List<String> missingParams = new ArrayList<>();
            if (rtid == null) missingParams.add("rtid");
            if (organGid == null) missingParams.add("organgid");
            if (dataVersion == null) missingParams.add("dataversion");
            if (isolationType == null) missingParams.add("isolationtype");
            if (map == null) missingParams.add("map");
            if (missingParams.size() > 0) {
                rnModel = ReturnModelHelper.MissingParametersResponse(missingParams);
                return rnModel;
            }
            // logic
            HashMap<String, String> args = new HashMap<>();
            args.put("rtid", rtid);
            args.put("organGid", organGid);
            args.put("dataVersion", dataVersion);
            args.put("isolationType", isolationType);
            args.put("map", map);
            NameServiceTransaction t = TransactionCreator.Create(TransactionType.BusinessRoleMapping, "register", args);
            String jsonifyResult = (String) RoleMappingController.scheduler.Schedule(t);
            // return
            ReturnModelHelper.WrapResponse(rnModel, StatusCode.OK, jsonifyResult);
        } catch (Exception e) {
            rnModel = ReturnModelHelper.ExceptionResponse(e.getClass().getName());
        }
        return rnModel;
    }

    /**
     * Finish a process and delete cache.
     * @param rtid process rtid
     * @return response package
     */
    @RequestMapping(value = "/fin", produces = {"application/json", "application/xml"})
    @ResponseBody
    @Transactional
    public ReturnModel FinishRoleMapService(@RequestParam(value="rtid", required = false)String rtid) {
        ReturnModel rnModel = new ReturnModel();
        try {
            // miss params
            List<String> missingParams = new ArrayList<>();
            if (rtid == null) missingParams.add("rtid");
            if (missingParams.size() > 0) {
                rnModel = ReturnModelHelper.MissingParametersResponse(missingParams);
                return rnModel;
            }
            // logic
            HashMap<String, String> args = new HashMap<>();
            args.put("rtid", rtid);
            NameServiceTransaction t = TransactionCreator.Create(TransactionType.BusinessRoleMapping, "fin", args);
            String jsonifyResult = (String) RoleMappingController.scheduler.Schedule(t);
            // return
            ReturnModelHelper.WrapResponse(rnModel, StatusCode.OK, jsonifyResult);
        } catch (Exception e) {
            rnModel = ReturnModelHelper.ExceptionResponse(e.getClass().getName());
        }
        return rnModel;
    }

    /**
     * Get all resources involved in a process.
     * @param rtid process rtid
     * @return response package
     */
    @RequestMapping(value = "/getInvolved", produces = {"application/json", "application/xml"})
    @ResponseBody
    @Transactional
    public ReturnModel GetInvolvedResource(@RequestParam(value="rtid", required = false)String rtid) {
        ReturnModel rnModel = new ReturnModel();
        try {
            // miss params
            List<String> missingParams = new ArrayList<>();
            if (rtid == null) missingParams.add("rtid");
            if (missingParams.size() > 0) {
                rnModel = ReturnModelHelper.MissingParametersResponse(missingParams);
                return rnModel;
            }
            // logic
            HashMap<String, String> args = new HashMap<>();
            args.put("rtid", rtid);
            NameServiceTransaction t = TransactionCreator.Create(TransactionType.BusinessRoleMapping, "getInvolved", args);
            String jsonifyResult = (String) RoleMappingController.scheduler.Schedule(t);
            // return
            ReturnModelHelper.WrapResponse(rnModel, StatusCode.OK, jsonifyResult);
        } catch (Exception e) {
            rnModel = ReturnModelHelper.ExceptionResponse(e.getClass().getName());
        }
        return rnModel;
    }

    /**
     * Transaction scheduler.
     */
    private static NSScheduler scheduler = NSScheduler.GetInstance();
}
