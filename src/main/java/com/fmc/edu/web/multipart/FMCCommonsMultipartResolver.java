package com.fmc.edu.web.multipart;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 6/4/2015.
 */
public class FMCCommonsMultipartResolver extends CommonsMultipartResolver {

	protected static final String PREFIX_ADMIN = "/admin";

	private static final Logger LOG = Logger.getLogger(FMCCommonsMultipartResolver.class);
	private ReplacementBase64EncryptService mBase64EncryptService;
	private boolean mEnabled;

	public FMCCommonsMultipartResolver() {
		super();
		mBase64EncryptService = new ReplacementBase64EncryptService();
		LOG.debug("============== Initialized FMCCommonsMultipartResolver ==============");
	}

	@Override
	protected MultipartParsingResult parseRequest(final HttpServletRequest request) throws MultipartException {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			if (request.getRequestURI().startsWith(PREFIX_ADMIN)) {
				return super.parseFileItems(fileItems, encoding);
			}
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}

	@Override
	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
		MultipartParsingResult multipartParsingResult = super.parseFileItems(fileItems, encoding);
		if (!isEnabled()) {
			return multipartParsingResult;
		}
		if (multipartParsingResult == null || CollectionUtils.isEmpty(multipartParsingResult.getMultipartParameters())) {
			return multipartParsingResult;
		}
		Map<String, String[]> multipartParameters = multipartParsingResult.getMultipartParameters();
		Map<String, String[]> encodedMultipartParameters = new HashMap<String, String[]>(multipartParameters.size());
		Iterator<Map.Entry<String, String[]>> iterator = multipartParameters.entrySet().iterator();
		Map.Entry<String, String[]> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			String[] encodedValues = entry.getValue();
			if (encodedValues == null || encodedValues.length == 0) {
				encodedMultipartParameters.put(entry.getKey(), encodedValues);
				continue;
			}

			String[] decodedValues = new String[encodedValues.length];
			String decodedValue;
			for (int i = 0; i < encodedValues.length; i++) {
				decodedValue = encodedValues[i];
				LOG.debug(String.format("FMCCommonsMultipartResolver - Obtain Parameter:%s = %s", entry.getKey(), decodedValue));
				if (WebConfig.isEncodeBase64InputParam() && ReplacementBase64EncryptService.isBase64(encodedValues[i])) {
					decodedValue = mBase64EncryptService.decrypt(encodedValues[i]);
				}
				decodedValues[i] = decodedValue;
				LOG.debug(String.format("FMCCommonsMultipartResolver - Obtain Parameter:%s = %s", entry.getKey(), decodedValue));
			}
			encodedMultipartParameters.put(entry.getKey(), decodedValues);
		}

		return new MultipartParsingResult(multipartParsingResult.getMultipartFiles(), encodedMultipartParameters, multipartParsingResult.getMultipartParameterContentTypes());
	}

	public boolean isEnabled() {
		return mEnabled;
	}

	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
	}
}
