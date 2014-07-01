package com.liferay.lms.views;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.UserCompetence;

public class CompetenceView {
	final static SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");

	private Competence competence = null;
	private UserCompetence userCompetence = null;
	
	
	public CompetenceView(Competence competence, UserCompetence userCompetence) {
		super();
		this.competence = competence;
		this.userCompetence = userCompetence;
	}
	
	public String getTitle(Locale locale) {
		return competence.getTitle(locale);
	}
    public boolean getGenerateCertificate()
    {
    	return competence.getGenerateCertificate();
    }
	public String getFormatDate() {
		if(userCompetence.getCompDate()==null)
			return "";
		return sdf.format(userCompetence.getCompDate());
	}
	
	public Long getCompetenceId(){
		return competence.getCompetenceId();
	}
}
