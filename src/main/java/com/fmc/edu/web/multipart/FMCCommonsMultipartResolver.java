package com.fmc.edu.web.multipart;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.crypto.impl.ReplacementBase64EncryptService;
import com.fmc.edu.util.URLUtils;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Yu on 6/4/2015.
 */
public class FMCCommonsMultipartResolver extends CommonsMultipartResolver {


	private static final Logger LOG = Logger.getLogger(FMCCommonsMultipartResolver.class);
	private ReplacementBase64EncryptService mBase64EncryptService;
	private boolean mEnabled;

	private boolean mInitialized = false;
	private String[] mDisablePrefixArray;

	public FMCCommonsMultipartResolver() {
		super();
		mBase64EncryptService = new ReplacementBase64EncryptService();
		LOG.debug("============== Initialized FMCCommonsMultipartResolver ==============");
	}

	@Override
	protected MultipartParsingResult parseRequest(final HttpServletRequest pRequest) throws MultipartException {
		String encoding = determineEncoding(pRequest);
		FileUpload fileUpload = prepareFileUpload(encoding);
		if (!mInitialized) {
			Initialize(pRequest);
		}
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(pRequest);
			for (String disablePrefix : getDisablePrefixArray()) {
				if (URLUtils.PrefixURLMatch(pRequest.getRequestURI(), disablePrefix)) {
					super.parseFileItems(fileItems, encoding);
				}
			}
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet pRequest", ex);
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

	private void Initialize(final HttpServletRequest pRequest) {
		ServletContext sc = pRequest.getServletContext();
		String context = pRequest.getServletContext().getContextPath();
		// check web context configuration
		Boolean enableWebConfigCtx = (Boolean) sc.getAttribute("enableWebConfigContext");
		if (enableWebConfigCtx != null && enableWebConfigCtx.booleanValue()) {
			// set context configured manually in web.properties
			context = WebConfig.getFMCWebContext();
			LOG.debug("Override context from web.properties: " + context);
		}
		LOG.debug("Get final context: " + context);
		// disable decode url parameters prefix URL set
		String[] prefixArray = (String[]) sc.getAttribute("disableDecodeURLPrefixArray");
		mDisablePrefixArray = new String[prefixArray.length];
		for (int i = 0; i < prefixArray.length; i++) {
			mDisablePrefixArray[i] = (context + prefixArray[i]).replace("//", "/");
		}
		LOG.debug("Set disable decode URL prefix: " + Arrays.toString(mDisablePrefixArray));
	}


	public boolean isEnabled() {
		return mEnabled;
	}

	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
	}

	public String[] getDisablePrefixArray() {
		return mDisablePrefixArray;
	}

	public void setDisablePrefixArray(final String[] pDisablePrefixArray) {
		mDisablePrefixArray = pDisablePrefixArray;
	}
}
