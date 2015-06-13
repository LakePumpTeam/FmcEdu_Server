package com.fmc.edu.web;

import com.fmc.edu.manager.*;
import com.fmc.edu.model.news.*;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.relationship.ProfileSelectionRelationship;
import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Yu on 5/12/2015.
 */
@Service(value = "responseBuilder")
public class ResponseBuilder {
    protected static final String ORIGINAL_IMAGE_PATH_PREFIX = "high";
    protected static final String THUMBNAIL_IMAGE_PATH_PREFIX = "low";
    protected static final String SLIDE_IMAGE_PATH_PREFIX = "slide";
    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    @Resource(name = "newsManager")
    private NewsManager mNewsManager;

    @Resource(name = "taskManager")
    private TaskManager mTaskManager;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    public void buildAuthorizedResponse(ResponseBean pResponseBean, final BaseProfile pProfile) {
        pResponseBean.addData("userId", pProfile.getId());
        pResponseBean.addData("userRole", pProfile.getProfileType());
        pResponseBean.addData("salt", pProfile.getSalt());
        pResponseBean.addData("userName", pProfile.getName());
        List<Map<String, Object>> optionList = new ArrayList<Map<String, Object>>();
        pResponseBean.addData("optionList", optionList);
        if (pProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            ParentProfile parentProfile = getProfileManager().queryParentByPhone(pProfile.getPhone());
            if (parentProfile == null) {
                pResponseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, pProfile.getPhone());
                return;
            }

            List<Student> studentList = getStudentManager().queryStudentByParentId(parentProfile.getId());

            if (!CollectionUtils.isEmpty(studentList)) {
                Map<String, Object> option;
                for (Student student : studentList) {
                    option = new HashMap<String, Object>(4);
                    option.put("optionId", student.getId());
                    option.put("optionName", student.getName());
                    option.put("classId", student.getClassId());
                    option.put("braceletCardNumber", student.getRingPhone());
                    option.put("auditState", student.getParentStudentRelationship().getApproved());
                    optionList.add(option);

                }
            }
            pResponseBean.addData("repayState", parentProfile.isPaid());

            //TODO need to remove bellow logic code.
            pResponseBean.addData("auditState", false);
            if (!CollectionUtils.isEmpty(studentList)) {
                Student student = studentList.get(0);
                pResponseBean.addData("auditState", student.getParentStudentRelationship().getApproved());
                pResponseBean.addData("studentName", student.getName());
                pResponseBean.addData("studentSex", student.isMale());
                pResponseBean.addData("schoolName", student.getSchool().getName());
                pResponseBean.addData("braceletCardNumber", student.getRingPhone());
            }
        } else {
            TeacherProfile teacherProfile = getTeacherManager().queryTeacherById(pProfile.getId());
            List<Map<String, Object>> classes = getTeacherManager().queryClassByTeacherId(pProfile.getId());
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(classes)) {
                Map<String, Object> option;
                for (Map<String, Object> cls : classes) {
                    option = new HashMap<String, Object>(3);
                    option.put("optionId", cls.get("id"));
                    option.put("optionName", getSchoolManager().getClassString(String.valueOf(cls.get("grade")), String.valueOf(cls.get("class"))));
                    option.put("classId", cls.get("id"));
                    optionList.add(option);
                }
            }
            if (teacherProfile == null) {
                return;
            }
            pResponseBean.addData("schoolName", teacherProfile.getSchool().getName());
        }

    }

    public void buildNewsListResponse(ResponseBean pResponseBean, final List<News> pNewsList, BaseProfile pBaseProfile) {
        if (CollectionUtils.isEmpty(pNewsList)) {
            return;
        }
        Map<String, Object> newsMap;
        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>(pNewsList.size());

        for (News news : pNewsList) {
            newsMap = new HashMap<String, Object>(5);
            newsMap.put("newsId", news.getId());
            newsMap.put("subject", news.getSubject());
            newsMap.put("author", news.getAuthor());
            newsMap.put("content", news.getContent());
            newsMap.put("createDate", DateUtils.ConvertDateToString(news.getPublishDate()));
            newsMap.put("type", news.getNewsType());
            if (CollectionUtils.isEmpty(news.getImageUrls())) {
                newsMap.put("imageUrls", Collections.EMPTY_LIST);
            } else {
                newsMap.put("imageUrls", buildImageList(news.getImageUrls()));
            }
            if (news.getNewsType() == NewsType.SCHOOL_BBS) {
                newsMap.put("popular", news.getPopular());
                newsMap.put("participationCount", getNewsManager().queryProfileSelectionRelationshipCount(news.getId()));
            } else {
                newsMap.put("like", news.getLike());
                buildCommentsList(pBaseProfile, news, newsMap);
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
            imgMap.put("origUrl", getImageUrl(ORIGINAL_IMAGE_PATH_PREFIX, img.getImgPath(), img.getImgName()));
            imgMap.put("thumbUrl", getImageUrl(THUMBNAIL_IMAGE_PATH_PREFIX, img.getImgPath(), img.getImgName()));
            imageList.add(imgMap);
        }
        return imageList;
    }

    private String getImageUrl(String size, String path, String fileName) {
        StringBuffer url = new StringBuffer();
        url.append("/")
                .append(size)
                .append("/")
                .append(path)
                .append("/")
                .append(fileName);
        return StringUtils.normalizeUrlNoEndSeparator(url.toString());
    }

    private void buildCommentsList(BaseProfile pBaseProfile, News news, Map<String, Object> newsMap) {
        List<Comments> commentList = getNewsManager().queryCommentsByNewsIdAndProfileId(pBaseProfile.getId(), news.getId());
        if (CollectionUtils.isEmpty(commentList)) {
            newsMap.put("commentCount", 0);
            newsMap.put("commentList", Collections.EMPTY_LIST);
        } else {
            newsMap.put("commentCount", commentList.size());
            newsMap.put("commentList", getCommentMapForNews(commentList));
        }
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
            slideMap.put("imageUrl", getImageUrl(SLIDE_IMAGE_PATH_PREFIX, slide.getImgPath(), slide.getImgName()));
            slideList.add(slideMap);
        }
        pResponseBean.addData("slideList", slideList);
    }

    public void buildNewsDetailResponse(ResponseBean pResponseBean, News pNews, int pCurrentUserId) {
        if (pNews == null) {
            return;
        }
        Map<String, Object> newsMap = new HashMap<String, Object>(5);
        newsMap.put("newsId", pNews.getId());
        newsMap.put("subject", pNews.getSubject());
        newsMap.put("content", pNews.getContent());
        newsMap.put("author", pNews.getAuthor());
        newsMap.put("createDate", DateUtils.ConvertDateToString(pNews.getPublishDate()));
        newsMap.put("imageUrls", getImagePathListOfNews(pNews));
        if (pNews.getNewsType() == NewsType.SCHOOL_BBS) {
            newsMap.put("popular ", pNews.getPopular());
            newsMap.put("participationCount", getNewsManager().queryProfileSelectionRelationshipCount(pNews.getId()));
            buildSelectionForNews(pNews.getId(), newsMap, pCurrentUserId);
        } else {
            List<Comments> commentsList = getNewsManager().queryCommentsByNewsIdAndProfileId(pCurrentUserId, pNews.getId());
            newsMap.put("commentList", getCommentMapForNews(commentsList));
            newsMap.put("like", pNews.getLike());
            newsMap.put("liked", getNewsManager().isLikedNews(pCurrentUserId, pNews.getId()));
        }
        pResponseBean.addData(newsMap);
    }

    private List<String> getImagePathListOfNews(News pNews) {
        if (CollectionUtils.isEmpty(pNews.getImageUrls())) {
            return Collections.EMPTY_LIST;
        }
        List<String> imagePathList = new ArrayList<String>(pNews.getImageUrls().size());
        for (Image img : pNews.getImageUrls()) {
            imagePathList.add(getImageUrl(ORIGINAL_IMAGE_PATH_PREFIX, img.getImgPath(), img.getImgName()));
        }
        return imagePathList;
    }

    private List<Map<String, Object>> getCommentMapForNews(List<Comments> pCommentsList) {
        List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>(pCommentsList.size());
        if (!CollectionUtils.isEmpty(pCommentsList)) {
            Map<String, Object> commentMap;
            for (Comments comments : pCommentsList) {
                commentMap = new HashMap<String, Object>(3);
                commentMap.put("userId", comments.getProfileId());
                BaseProfile baseProfile = getProfileManager().getMyAccountManager().findUserById(String.valueOf(comments.getProfileId()));
                if (baseProfile != null) {
                    commentMap.put("userName", baseProfile.getName());
                } else {
                    commentMap.put("userName", "N/A");
                }
                commentMap.put("comment", comments.getComment());
                commentList.add(commentMap);
            }
        }
        return commentList;
    }

    private void buildSelectionForNews(int pNewsId, Map<String, Object> pNewsMap, int pCurrentUserId) {
        List<Selection> selections = getNewsManager().querySelectionByNewsId(pNewsId);
        if (CollectionUtils.isEmpty(selections)) {
            pNewsMap.put("selections ", Collections.EMPTY_LIST);
            return;
        }
        ProfileSelectionRelationship profileSelectionRelationship = getNewsManager().queryProfileSelectionRelationship(pNewsId, pCurrentUserId);
        List<Map<String, Object>> convertedSelectionList = new ArrayList<Map<String, Object>>(selections.size());
        Map<String, Object> selectionMap;
        boolean isParticipation = false;
        for (Selection selection : selections) {
            selectionMap = new HashMap<String, Object>(3);
            selectionMap.put("selectionId", selection.getId());
            selectionMap.put("selection", selection.getSelection());
            selectionMap.put("sortOrder", selection.getSortOrder());
            boolean isSelected = profileSelectionRelationship != null && profileSelectionRelationship.getSelectionId().equals(selection.getId());
            if (isSelected && !isParticipation) {
                isParticipation = true;
            }
            selectionMap.put("isSelected", isSelected);
            convertedSelectionList.add(selectionMap);
        }
        pNewsMap.put("selections", convertedSelectionList);
        pNewsMap.put("isParticipation", isParticipation);
    }

    public void buildTaskDetail(Task pTask, String pStudentId, ResponseBean pResponseBean) {
        pResponseBean.addData("taskId", pTask.getId());
        pResponseBean.addData("studentId", pStudentId);
        pResponseBean.addData("title", pTask.getTitle());
        pResponseBean.addData("task", pTask.getTask());
        TaskStudentsRelationship taskStudentsRelationship = getTaskManager().queryTaskStudentRelationship(pTask.getId(), Integer.valueOf(pStudentId));
        if (taskStudentsRelationship != null) {
            pResponseBean.addData("status", taskStudentsRelationship.getCompleted() ? 1 : 0);
        }
        pResponseBean.addData("deadline", DateUtils.ConvertDateToString(pTask.getDeadline()));
        Student student = getStudentManager().queryStudentById(Integer.valueOf(pStudentId));
        if (student != null) {
            pResponseBean.addData("studentName", student.getName());
        }
        List<Comments> comments = getNewsManager().queryCommentsByNewsIdAndProfileId(0, pTask.getId());
        List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
        pResponseBean.addData("commentList", commentList);
        if (!CollectionUtils.isEmpty(comments)) {
            for (Comments comment : comments) {
                commentList.add(buildComment(comment, student));
            }
        }
    }

    public Map<String, Object> buildComment(Comments comment, Student student) {
        Map<String, Object> commentMap = new HashMap<String, Object>(7);
        BaseProfile baseProfile = getProfileManager().getMyAccountManager().findUserById(String.valueOf(comment.getProfileId()));
        commentMap.put("commentId", comment.getId());
        commentMap.put("userId", comment.getProfileId());
        commentMap.put("userName", baseProfile.getName());

        if (baseProfile.getProfileType() == ProfileType.PARENT.getValue()) {
            if (student == null) {
                List<Student> students = getStudentManager().queryStudentByParentId(comment.getProfileId());
                if (!CollectionUtils.isEmpty(students)) {
                    student = students.get(0);
                }
            }
            if (student != null) {
                ParentStudentRelationship parentStudentRelationship = getProfileManager().queryParentStudentRelationship(baseProfile.getId(), student.getId());
                if (parentStudentRelationship != null) {
                    commentMap.put("relationship", parentStudentRelationship.getRelationship());
                }
                commentMap.put("sex", student.isMale());
            }
        } else if (baseProfile.getProfileType() == ProfileType.TEACHER.getValue()) {
            commentMap.put("relationship", "师生");
            TeacherProfile teacher = getTeacherManager().queryTeacherById(baseProfile.getId());
            if (teacher != null) {
                commentMap.put("sex", teacher.isMale());
            }
        } else {
            //commentMap.put("sex", 0);
        }
        commentMap.put("comment", comment.getComment());
        commentMap.put("date", DateUtils.ConvertDateToString(comment.getCreationDate()));
        if (baseProfile == null) {
            commentMap.put("userName", baseProfile.getName());
        }
        return commentMap;
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

    public TaskManager getTaskManager() {
        return mTaskManager;
    }

    public void setTaskManager(TaskManager pTaskManager) {
        mTaskManager = pTaskManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }
}
