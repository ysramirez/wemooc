
package com.liferay.lms;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortletRequest;

import com.liferay.lms.model.Module;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

public class moduleValidator {

	public static ArrayList<String> validatemodule(Module module, PortletRequest request) throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Properties props = new Properties();
		ClassLoader classLoader = moduleValidator.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("regexp.properties");
		props.load(is);


	//Field title
		if (Validator.isNull(module.getTitle(request.getLocale(), true))) {
			errors.add("title-required");
		}
	
		

	//Field description
	
	
		if(!validateDescription(props, ParamUtil.getString(request, "description"))){
		    errors.add("error");
		}
		if (Validator.isNull(module.getDescription(request.getLocale(), true))) {
			errors.add("module-description-required");
		}
	//Field order
		
	//Date validation
	if (module.getStartDate().after(module.getEndDate())){
		errors.add("module-startDate-before-endDate");			
	}


		return errors;
	}

	public static boolean validateEditmodule(
		String rowsPerPage, String dateFormat, String datetimeFormat, List errors) {
		boolean valid = true;
		if (Validator.isNull(rowsPerPage)) {
			errors.add("module-rows-per-page-required");
			valid = false;
		} else if (!Validator.isNumber(rowsPerPage)) {
			errors.add("module-rows-per-page-invalid");
			valid = false;
		} else if (Validator.isNull(dateFormat)) {
			errors.add("module-date-format-required");
			valid = false;
		} else if (Validator.isNull(datetimeFormat)) {
			errors.add("module-datetime_format.required");
			valid = false;
		}
		return valid;
	}

	//Field moduleId
	private static boolean validateModuleId(Properties props,String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
		    valid = false;
		}
		return valid;
	}
	//Field title
	private static boolean validateTitle(Properties props,String field) {
		boolean valid = true;
		return valid;
	}
	//Field description
	private static boolean validateDescription(Properties props,String field) {
		boolean valid = true;
		return valid;
	}
	//Field order
	private static boolean validateOrder(Properties props,String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
		    valid = false;
		}
		return valid;
	}
}
