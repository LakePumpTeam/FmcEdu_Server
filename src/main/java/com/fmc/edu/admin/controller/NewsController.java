package com.fmc.edu.admin.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.controller.BaseController;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yu on 5/28/2015.
 */
@Controller
@RequestMapping("/admin/news")
public class NewsController extends BaseController {
    private static final Logger LOG = Logger.getLogger(NewsController.class);

    @Resource(name = "newsManager")
    private NewsManager mNewsManager;

    @RequestMapping(value = "/publishNews" + GlobalConstant.URL_SUFFIX, method = RequestMethod.POST)
    @ResponseBody
    public String publishNews(HttpServletRequest pRequest,
                              HttpServletResponse pResponse,
                              @RequestParam(value = "newsType", required = true) int newsType,
                              @RequestParam(value = "subject", required = true) String subject,
                              @RequestParam(value = "content", required = true) String content,
                              @RequestParam(value = "imgs", required = false) MultipartFile[] imgs) throws IOException {
        ResponseBean responseBean = new ResponseBean();
        if (StringUtils.isBlank(content)) {
            LOG.debug(">>>>Content is empty.>>>>");
            responseBean.addBusinessMsg("Content is empty.");
            return output(responseBean);
        }

        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser == null && currentUser.isAuthenticated()) {
            LOG.debug("Anonymous user, redirect to login");
            pResponse.sendRedirect("/admin/toLogin");
            return output(responseBean);
        }
        Session session = currentUser.getSession(false);
        if (session == null) {
            LOG.debug("Anonymous user, redirect to login");
            pResponse.sendRedirect("/admin/toLogin");
            return output(responseBean);
        }
        BaseProfile userProfile = (BaseProfile) session.getAttribute(MyAccountManager.CURRENT_SESSION_USER_KEY);
        if (userProfile == null) {
            LOG.debug("User not exists.");
            pResponse.sendRedirect("/admin/toLogin");
            return output(responseBean);
        }

        News news = new News();
        news.setAuthor(userProfile.getId());
        news.setContent(content);
        news.setNewsType(newsType);
        news.setSubject(subject);
        news.setApproved(true);
        TransactionStatus txStatus = ensureTransaction();
        try {
            if (getNewsManager().insertNews(news)) {
                // int newsId = getNewsManager().queryLastInsertNewsTypeNewsIdByAuthor(userProfile.getId(), newsType);
                getNewsManager().saveNewsImage(imgs, String.valueOf(userProfile.getId()), news.getId());
            } else {
                LOG.debug(">>>>Publish news failed.>>>>");
                responseBean.addBusinessMsg("Publish news failed.");
            }
        } catch (Exception e) {
            LOG.error(e);
            txStatus.setRollbackOnly();
            responseBean.addBusinessMsg(e.getMessage());
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    public NewsManager getNewsManager() {
        return mNewsManager;
    }

    public void setNewsManager(NewsManager pNewsManager) {
        mNewsManager = pNewsManager;
    }
}
