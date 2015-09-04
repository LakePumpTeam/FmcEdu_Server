package com.fmc.edu.manager;

import com.fmc.edu.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yu on 6/6/2015.
 */
@Component("resourceManager")
public class ResourceManager {

	public static final String VALIDATION_USER_USER_ID_EMPTY = "validation.user.user.id.empty";

	public static final String VALIDATION_USER_USER_ID_ERROR = "validation.user.user.id.error";

	public static final String VALIDATION_LOCATION_PROVINCE_ID_EMPTY = "validation.location.province.id.empty";

	public static final String VALIDATION_LOCATION_PROVINCE_ID_ERROR = "validation.location.province.id.error";

	public static final String VALIDATION_NEWS_ID_ERROR = "validation.news.id.error";

	public static final String VALIDATION_NEWS_PROVINCE_TYPE_ERROR = "validation.news.province.type.error";

	public static final String VALIDATION_USER_PHONE_ERROR = "validation.user.phone.error";

	public static final String VALIDATION_USER_PHONE_EXIST = "validation.user.phone.exist";

	public static final String VALIDATION_USER_PHONE_EMPTY = "validation.user.phone.empty";

	public static final String VALIDATION_USER_SALT_EMPTY = "validation.user.salt.empty";

	public static final String VALIDATION_USER_OLD_PASSWORD_ERROR = "validation.user.old.password.error";

	public static final String VALIDATION_USER_NEW_PASSWORD_EMPTY = "validation.user.new.password.empty";

	public static final String VALIDATION_USER_BAIDU_CHANNEL_ID_ERROR = "validation.user.baidu.channel.id.error";

	public static final String VALIDATION_USER_BAIDU_USER_ID_ERROR = "validation.user.baidu.user.id.error";

	public static final String VALIDATION_USER_DEVICE_TYPE_ERROR = "validation.user.device.type.error";

	public static final String VALIDATION_NEWS_CONTENT_EMPTY = "validation.news.content.empty";

	public static final String VALIDATION_USER_PASSWORD_EMPTY = "validation.user.password.empty";

	public static final String VALIDATION_NEWS_SELECTION_EMPTY = "validation.news.selection.empty";

	public static final String VALIDATION_IDENTITY_CODE_EMPTY = "validation.identity.code.empty";

	public static final String VALIDATION_USER_PARENT_ID_ERROR = "validation.user.parent.id.error";

	public static final String VALIDATION_USER_TEACHER_ID_ERROR = "validation.user.teacher.id.error";

	public static final String VALIDATION_SCHOOL_CLASS_ID_ERROR = "validation.school.class.id.error";

	public static final String VALIDATION_APP_DEVICE_TYPE_EMPTY = "app.device.type.empty";

	public static final String ERROR_NOT_FIND_USER = "profile.error.not.find.user";

	public static final String ERROR_RESET_PASSWORD_FAILED = "profile.error.reset.password.failed";

	public static final String ERROR_ACCOUNT_UNAVAILABLE = "profile.error.account.unavailable";

	public static final String ERROR_PASSWORD_ERROR = "profile.error.password.error";

	public static final String ERROR_NEWS_DUPLICATION_PARTICIPATION_ERROR = "news.error.duplication.participation.error";

	public static final String ERROR_NEWS_PARTICIPATION_FAILED = "news.error.participation.failed";

	public static final String ERROR_USER_TYPE_UNKNOWN = "profile.error.type.unknown";

	public static final String ERROR_NEWS_DELETE_TASK_FAILED = "news.error.delete.task.failed";

	public static final String ERROR_NEWS_OBTAIN_TASK_DETAIL_FAILED = "news.error.obtain.task.detail.failed";

	public static final String ERROR_NEWS_ADD_COMMENT_FAILED = "news.error.add.comment.failed";

	public static final String ERROR_NEWS_DELETE_COMMENT_FAILED = "news.error.delete.comment.failed";

	public static final String ERROR_NEWS_MODIFY_TASK_FAILED = "news.error.modify.task.failed";

	public static final String ERROR_NEWS_SUBMIT_TASK_FAILED = "news.error.submit.task.failed";

	public static final String ERROR_IDENTITY_CODE_INVALID = "identity.error.identity.code.invalid";

	public static final String ERROR_IDENTITY_CODE_EXPIRED = "identity.error.identity.code.expired";

	public static final String ERROR_COMMON_OPERATION_FAILED = "common.error.operation.failed";

	public static final String ERROR_POST_CLASS_NEWS_FAILED = "news.error.post.class.news.failed";

	public static final String ERROR_NEWS_DISABLE_NEWS_FAILED = "news.error.disable.news";

	public static final String ERROR_LOCATION_CITY_ID_ERROR = "location.error.city.id.error";

	public static final String ERROR_LOCATION_SCHOOL_ID_ERROR = "school.error.school.id.error";

	public static final String ERROR_LOCATION_CLASS_ID_ERROR = "school.error.class.id.error";

	public static final String ERROR_TEACHER_UNKNOWN = "school.error.teacher.unknown";

	public static final String ERROR_NOT_FOND_CLASS = "school.error.class.not.found";

	public static final String ERROR_APP_UNKNOWN_DEVICE_TYPE = "app.device.type.unknown";


	public static final String BAIDU_PUSH_MESSAGE_NOTIFY_NORTH_DELTA = "baidu.push.message.notify.north.delta";

	public static final String BAIDU_PUSH_MESSAGE_SENT_CHILD = "baidu.push.message.sent.child";

	public static final String BAIDU_PUSH_MESSAGE_NORTH_DELTA = "baidu.push.message.north.delta";

	public static final String BAIDU_PUSH_MESSAGE_WARNING_USING_LOST_CARD = "baidu.push.message.warning.using.lost.card";

	public static final String BAIDU_PUSH_MESSAGE_STUDENT_TO_SCHOOL = "baidu.push.message.student.to.school";

	public static final String BAIDU_PUSH_MESSAGE_STUDENT_LEFT_SCHOOL = "baidu.push.message.student.left.school";

	public String getMessage(HttpServletRequest request, String pKey, Object[] pArgs, String pDefaultMessage) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(pKey, pArgs, pDefaultMessage);
	}

	public String getMessage(HttpServletRequest request, String pKey, Object[] pArgs) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(pKey, pArgs, StringUtils.EMPTY);
	}

	public String getMessage(HttpServletRequest request, String pKey) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(pKey);
	}
}
