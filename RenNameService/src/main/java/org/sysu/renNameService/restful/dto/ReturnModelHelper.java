/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.renNameService.restful.dto;

import org.sysu.renNameService.GlobalContext;
import org.sysu.renNameService.utility.LogUtil;
import org.sysu.renNameService.utility.TimestampUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2018/1/26
 * Usage : Helper methods for ReturnModel construction.
 */
public class ReturnModelHelper {
    /**
     * Warp success response to a ReturnModel
     * @param rnModel return model package to be updated
     * @param code status code enum
     * @param retStr execution return data
     */
    public static void StandardResponse(ReturnModel rnModel, StatusCode code, String retStr) {
        rnModel.setCode(code);
        rnModel.setNs(String.format("%s %s", TimestampUtil.GetTimeStamp(), GlobalContext.NAME_SERVICE_GLOBAL_ID));
        ReturnElement returnElement = new ReturnElement();
        returnElement.setData(retStr);
        rnModel.setReturnElement(returnElement);
    }

    /**
     * Router exception handler.
     * @param exception exception descriptor
     * @return response package
     */
    public static void ExceptionResponse(ReturnModel rnModel, String exception) {
        rnModel.setCode(StatusCode.Exception);
        rnModel.setNs(String.format("%s %s", TimestampUtil.GetTimeStamp(), GlobalContext.NAME_SERVICE_GLOBAL_ID));
        ReturnElement returnElement = new ReturnElement();
        returnElement.setMessage(exception);
        rnModel.setReturnElement(returnElement);
    }

    /**
     * Router unauthorized service request handler.
     * @param token unauthorized token
     * @return response package
     */
    public static ReturnModel UnauthorizedResponse(String token) {
        ReturnModel rnModel = new ReturnModel();
        rnModel.setCode(StatusCode.Unauthorized);
        rnModel.setNs(String.format("%s %s", TimestampUtil.GetTimeStamp(), GlobalContext.NAME_SERVICE_GLOBAL_ID));
        ReturnElement returnElement = new ReturnElement();
        returnElement.setMessage(token);
        rnModel.setReturnElement(returnElement);
        LogUtil.Log(String.format("Unauthorized service request (token:%s)", token),
                ReturnModelHelper.class.getName(), LogUtil.LogLevelType.UNAUTHORIZED, "");
        return rnModel;
    }

    /**
     * Router request parameter missing handler.
     * @param params missing parameter list
     * @return response package
     */
    public static ReturnModel MissingParametersResponse(List<String> params) {
        ReturnModel rnModel = new ReturnModel();
        rnModel.setCode(StatusCode.Fail);
        rnModel.setNs(String.format("%s %s", TimestampUtil.GetTimeStamp(), GlobalContext.NAME_SERVICE_GLOBAL_ID));
        ReturnElement returnElement = new ReturnElement();
        StringBuilder sb = new StringBuilder();
        sb.append("miss required parameters:");
        for (String s : params) {
            sb.append(s).append(" ");
        }
        returnElement.setMessage(sb.toString());
        rnModel.setReturnElement(returnElement);
        return rnModel;
    }
}