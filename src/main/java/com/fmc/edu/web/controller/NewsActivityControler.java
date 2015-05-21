package com.fmc.edu.web.controller;

import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.News;
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
import java.io.IOException;
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

    @RequestMapping("/requestNewsList")
    @ResponseBody
    public String requestNewsList(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean();
        try {
            Pagination pagination = buildPagination(pRequest);
            String userIdStr = decodeInput(pRequest.getParameter("userId"));
            String typeStr = decodeInput(pRequest.getParameter("type"));
            if (!RepositoryUtils.idIsValid(userIdStr)) {
                responseBean.addBusinessMsg("");
                return output(responseBean);
            }
            if (!StringUtils.isNumeric(typeStr)) {
                responseBean.addBusinessMsg("新闻类型错误:" + typeStr);
                return output(responseBean);
            }
            int newType = Integer.valueOf(typeStr);
            List<News> newsList = getNewsManager().queryNewsListByNewType(pagination, newType);
            responseBean.addData(JSONOutputConstant.IS_LAST_PAGE, RepositoryUtils.isLastPageFlag(newsList, pagination.getPageSize()));

            if (CollectionUtils.isEmpty(newsList)) {
                return output(responseBean);
            }
            getResponseBuilder().buildNewsListResponse(responseBean, newsList);

            int userId = Integer.valueOf(userIdStr);
            //TODO mark

        } catch (IOException e) {
            responseBean.addErrorMsg(e);
        }
        return responseBean.toString();
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
}
