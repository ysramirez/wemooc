package com.liferay.lms.learningactivity.calificationtype;

import java.util.Locale;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class SpanishCalificationType extends BaseCalificationType{

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
	public String translate(ThemeDisplay themeDisplay, double result) {
		double tmpResult = result/10;
		String label = "-";
		if(tmpResult < 5)
			label = "spanish_ct.insuficiente";
		else if(tmpResult >= 5 && tmpResult < 6)
			label = "spanish_ct.suficiente";
		else if(tmpResult >= 6 && tmpResult < 7)
			label = "spanish_ct.bien";
		else if(tmpResult >= 7 && tmpResult < 9)
			label = "spanish_ct.notable";
		else if(tmpResult >= 9)
			label = "spanish_ct.sobresaliente";
		return LanguageUtil.get(themeDisplay.getLocale(), label);
	}

}
