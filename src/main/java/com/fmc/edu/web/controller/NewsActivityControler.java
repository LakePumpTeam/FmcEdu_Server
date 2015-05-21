package com.fmc.edu.web.controller;

import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.NewsType;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yu on 2015/5/21.
 */
@Controller
@RequestMapping("/news")
public class NewsActivityControler extends BaseController {
    @Resource(name = "responseBuilder")
    private ResponseBuilder mResponseBuilder;

    @Resource(name = "newsManager")
    private NewsManager mNewsManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @RequestMapping("/requestNewsList")
    @ResponseBody
    public String requestNewsList(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean();
        try {
            Pagination pagination = buildPagination(pRequest);
            String userIdStr = decodeInput(pRequest.getParameter("userId"));
            String typeStr = decodeInput(pRequest.getParameter("type"));
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg("User id 不合法.");
                return output(responseBean);
            }
            if (!StringUtils.isNumeric(typeStr)) {
                responseBean.addBusinessMsg("新闻类型错误:" + typeStr);
                return output(responseBean);
            }

            BaseProfile currentUser = getMyAccountManager().findUserById(userIdStr);
            if (currentUser == null) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
                return output(responseBean);
            }

            int newType = Integer.valueOf(typeStr);
            List<News> newsList = getNewsManager().queryNewsListByNewType(pagination, newType);
            responseBean.addData(JSONOutputConstant.IS_LAST_PAGE, RepositoryUtils.isLastPageFlag(newsList, pagination.getPageSize()));

            if (CollectionUtils.isEmpty(newsList)) {
                return output(responseBean);
            }
            if (newType == NewsType.SCHOOL_DYNAMICS_ACTIVITY.getValue() || newType == NewsType.SCHOOL_DYNAMICS_NEWS.getValue() || newType == NewsType.SCHOOL_DYNAMICS_NOTIFY.getValue()) {
                currentUser.setLastSCId(getNewsManager().queryNewsMaxIdByNewsType(newType));
            } else if (newType == NewsType.CLASS_DYNAMICS.getValue()) {
                currentUser.setLastCLId(getNewsManager().queryNewsMaxIdByNewsType(newType));
            } else if (newType == NewsType.PARENTING_CLASS.getValue()) {
                currentUser.setLastPCId(getNewsManager().queryNewsMaxIdByNewsType(newType));
            }
            //TODO update base profile
            //getMyAccountManager().
            getResponseBuilder().buildNewsListResponse(responseBean, newsList);

        } catch (Exception e) {
            responseBean.addErrorMsg(e);
        }
        return output(responseBean);
    }

    public NewsManager getNewsManager() {
        return mNewsManager;
    }

    public void setNewsManager(NewsManager pNewsManager) {
        mNewsManager = pNewsManager;
    }

    public ResponseBuilder getResponseBuilder() {
        return mResponseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
        mResponseBuilder = pResponseBuilder;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }
}
