package com.fmc.edu.web;

import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.StudentManager;
import com.fmc.edu.manager.TeacherManager;
import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.news.Image;
import com.fmc.edu.model.news.News;
import com.fmc.edu.model.news.Slide;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "responseBuilder")
public class ResponseBuilder {
    protected static final String ORIGINAL_IMAGE_PATH_PREFIX = "/high/";
    protected static final String THUMBNAIL_IMAGE_PATH_PREFIX = "/low/";
    protected static final String SLIDE_IMAGE_PATH_PREFIX = "/slide/";
    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    @Resource(name = "newsManager")
    private NewsManager mNewsManager;

    public void buildAuthorizedResponse(ResponseBean pResponseBean, final BaseProfile pProfile) {
        pResponseBean.addData("userId", pProfile.getId());
        pResponseBean.addData("userRole", pProfile.getProfileType());
        pResponseBean.addData("salt", pProfile.getSalt());

        if (pProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            ParentProfile parentProfile = getProfileManager().queryParentByPhone(pProfile.getPhone());
            if (parentProfile == null) {
                return;
            }
            List<Student> studentList = getStudentManager().queryStudentByParentId(parentProfile.getId());
            if (!CollectionUtils.isEmpty(studentList)) {
                Student student = studentList.get(0);
                pResponseBean.addData("auditState", student.getParentStudentRelationship().getApproved());
                pResponseBean.addData("userCardNum", student.getRingPhone());
                pResponseBean.addData("studentName", student.getName());
                pResponseBean.addData("studentSex", student.isMale());
                pResponseBean.addData("schoolName", student.getSchool().getName());
                pResponseBean.addData("repayState", parentProfile.isPaid());
            }
        } else {
            TeacherProfile teacherProfile = getTeacherManager().queryTeacherById(pProfile.getId());
            if (teacherProfile == null) {
                return;
            }
            pResponseBean.addData("schoolName", teacherProfile.getSchool().getName());
        }

    }

    public void buildNewsListResponse(ResponseBean pResponseBean, final List<News> pNewsList) {
        if (CollectionUtils.isEmpty(pNewsList)) {
            return;
        }
        Map<String, Object> newsMap;
        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>(pNewsList.size());

        for (News news : pNewsList) {
            newsMap = new HashMap<String, Object>(5);
            newsMap.put("newsId", news.getId());
            newsMap.put("subject", news.getSubject());
            newsMap.put("content", news.getContent());
            newsMap.put("like", news.getLike());
            newsMap.put("createDate", DateUtils.ConvertDateToString(news.getPublishDate()));
            if (CollectionUtils.isEmpty(news.getImageUrls())) {
                newsMap.put("imageUrls", Collections.EMPTY_LIST);
            } else {
                newsMap.put("imageUrls", buildImageList(news.getImageUrls()));
            }
            newsList.add(newsMap);
        }
        pResponseBean.addData("newsList", newsList);
    }

    private List<Map<String, String>> buildImageList(List<Image> pImageList) {
        List<Map<String, String>> imageList = new ArrayList<Map<String, String>>(pImageList.size());
        Map<String, String> imgMap;
        for (Image img : pImageList) {
            imgMap = new HashMap<String, String>(2);
            imgMap.put("origUrl", ORIGINAL_IMAGE_PATH_PREFIX + img.getImgPath() + "/" + img.getImgName());
            imgMap.put("thumbUrl", THUMBNAIL_IMAGE_PATH_PREFIX + img.getImgPath() + "/" + img.getImgName());
            imageList.add(imgMap);
        }
        return imageList;
    }

    public void buildSlideListResponse(ResponseBean pResponseBean, final List<Slide> pSlideList) {
        if (CollectionUtils.isEmpty(pSlideList)) {
            return;
        }
        List<Map<String, Object>> slideList = new ArrayList<Map<String, Object>>(pSlideList.size());
        Map<String, Object> slideMap;
        for (Slide slide : pSlideList) {
            slideMap = new HashMap<String, Object>(3);
            slideMap.put("newsId", slide.getNewsId());
            slideMap.put("order", slide.getOrder());
            slideMap.put("imageUrl", SLIDE_IMAGE_PATH_PREFIX + slide.getImgPath() + "/" + slide.getImgName());
            slideList.add(slideMap);
        }
        pResponseBean.addData("slideList", slideList);
    }

    public void buildNewsDetailResponse(ResponseBean pResponseBean, News pNews, List<Comments> pCommentsList, int pCurrentUserId) {
        if (pNews == null) {
            return;
        }
        Map<String, Object> newsMap = new HashMap<String, Object>(5);
        newsMap.put("newsId", pNews.getId());
        newsMap.put("subject", pNews.getSubject());
        newsMap.put("content", pNews.getContent());
        newsMap.put("imageUrl", "");
        newsMap.put("like", pNews.getLike());
        newsMap.put("liked", getNewsManager().isLikedNews(pCurrentUserId, pNews.getId()));
        newsMap.put("createDate", DateUtils.ConvertDateToString(pNews.getPublishDate()));
        List<Map<String, Object>> commnetList = new ArrayList<Map<String, Object>>(pCommentsList.size());
        newsMap.put("commentList", commnetList);
        if (!CollectionUtils.isEmpty(pCommentsList)) {
            Map<String, Object> commentMap;
            for (Comments comments : pCommentsList) {
                commentMap = new HashMap<String, Object>(3);
                commentMap.put("userId", comments.getProfileId());
                BaseProfile baseProfile = getProfileManager().getMyAccountManager().findUser(String.valueOf(comments.getProfileId()));
                if (baseProfile != null) {
                    commentMap.put("userName", baseProfile.getName());
                }
                commentMap.put("comment", comments.getComment());
                commnetList.add(commentMap);
            }
        }
        pResponseBean.addData(newsMap);
    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(TeacherManager pTeacherManager) {
        mTeacherManager = pTeacherManager;
    }

    public NewsManager getNewsManager() {
        return mNewsManager;
    }

    public void setNewsManager(NewsManager pNewsManager) {
        mNewsManager = pNewsManager;
    }
}
