package com.liferay.lms.learningactivity.calificationtype;

import java.util.Locale;

public class SpanishCalificationType extends CeroToTenCalificationType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public long getTypeId() {
		return 2;
	}

	@Override
	public String getName() {
		return "spanish_ct";
	}

	@Override
	public String getTitle(Locale locale) {
		return "spanish_ct.title";
	}

	@Override
	public String getDescription(Locale locale) {
		return "spanish_ct.description";
	}

	@Override
	public String translate(double result) {
		double tmpResult = Double.parseDouble(super.translate(result));
		if(tmpResult < 5)
			return "spanish_ct.insuficiente";
		else if(tmpResult >= 5 && tmpResult < 6)
			return "spanish_ct.suficiente";
		else if(tmpResult >= 6 && tmpResult < 7)
			return "spanish_ct.bien";
		else if(tmpResult >= 7 && tmpResult < 9)
			return "spanish_ct.notable";
		else if(tmpResult >= 9)
			return "spanish_ct.sobresaliente";
		return "-";
	}

}
