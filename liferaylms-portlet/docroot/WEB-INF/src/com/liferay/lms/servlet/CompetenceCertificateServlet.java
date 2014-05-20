package com.liferay.lms.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.Key;
import java.sql.SQLException;
import java.text.Format;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Servlet implementation class CompetenceCertificateServlet
 */

public class CompetenceCertificateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String hexStringToStringByAscii(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			String oneHexa = hexString.substring(i * 2, i * 2 + 2);
			bytes[i] = Byte.parseByte(oneHexa, 16);
		}
		try {
			return new String(bytes, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompetenceCertificateServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			User user = PortalUtil.getUser(request);
		
		
		if (user == null) {
			String userId = null;
			String companyId = null;
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			if (Validator.isNotNull(cookies)) {
				for (Cookie c : cookies) {
					if ("COMPANY_ID".equals(c.getName())) {
						companyId = c.getValue();
					} else if ("ID".equals(c.getName())) {
						userId = hexStringToStringByAscii(c.getValue());
					}
				}
			}

			if (userId != null && companyId != null) {
				try {
					Company company = CompanyLocalServiceUtil
							.getCompany(Long.parseLong(companyId));
					Key key = company.getKeyObj();

					String userIdPlain = Encryptor.decrypt(key, userId);

					user = UserLocalServiceUtil.getUser(Long
							.valueOf(userIdPlain));

					// Now you can set the liferayUser into a thread local
					// for later use or
					// something like that.

				} catch (Exception pException) {
					throw new RuntimeException(pException);
				}
			}
		}
		PermissionChecker permissionChecker=PermissionCheckerFactoryUtil.create(user);
		long userId=ParamUtil.getLong(request, "userId");
		long competenceId=ParamUtil.getLong(request, "competenceId");
		Competence competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
		UserCompetence ucomp=UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(userId, competenceId);
		
		if(user.getUserId()==userId&& ucomp!=null)
		{
			response.setContentType("application/pdf");
			OutputStream ouputStream=response.getOutputStream();
			Document documento = new Document(PageSize.A4.rotate());
			PdfWriter writer=PdfWriter.getInstance(documento, ouputStream);
			writer.setPageEvent(new PDFBackground("http://telefonica.telefonica/img/es/be_more_tv_05.jpg"));
			documento.open();
			Paragraph userName=new Paragraph(user.getFullName(),
	                FontFactory.getFont("arial",   // fuente
	                20,                            // tamaño
	                Font.BOLD));
			
			userName.setAlignment(Element.ALIGN_CENTER);
			userName.setSpacingAfter(10);
			documento.add(userName);
			Paragraph competenceText=new Paragraph(LanguageUtil.get(user.getLocale(), "competence.text"),
	                FontFactory.getFont("arial",   // fuente
	                18,                            // tamaño
	                Font.BOLD));
			competenceText.setAlignment(Element.ALIGN_CENTER);
			documento.add(competenceText);
			
			Paragraph competenceName=new Paragraph(competence.getTitle(user.getLocale(), true),
	                FontFactory.getFont("arial",   // fuente
	                26,                            // tamaño
	                Font.BOLD));
			competenceName.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(competenceName);
			Date compDate=ucomp.getCompDate();
			Format dateFormatDate = FastDateFormatFactoryUtil.getDate(user.getLocale(), user.getTimeZone());
			String dateString=dateFormatDate.format(compDate);
			Paragraph datecompetence=new Paragraph(dateString,
	                FontFactory.getFont("arial",   // fuente
	                14,                            // tamaño
	                Font.ITALIC));
			datecompetence.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(datecompetence);
			Paragraph UUID=new Paragraph(ucomp.getUuid(),
	                FontFactory.getFont("arial",   // fuente
	                10,                            // tamaño
	                Font.NORMAL));
			UUID.setAlignment(Element.ALIGN_RIGHT);
			UUID.setSpacingBefore(60);
			
			documento.add(UUID);
			documento.close();
			
		}
		
		}
		catch (PortalException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 catch (SystemException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	catch(SQLException ex)
	{
		ex.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	}
	class PDFBackground extends PdfPageEventHelper 
	{
		String image;
        public PDFBackground(String imageURL)
        {
        	this.image=imageURL;
        }
	    @Override
		public
	           void onEndPage(PdfWriter writer, Document document) {
	        Image background;
			try {
				background = Image.getInstance(image);
			
	        // This scales the image to the page,
	        // use the image's width & height if you don't want to scale.
	        float width = document.getPageSize().getWidth();
	        float height = document.getPageSize().getHeight();
	        writer.getDirectContentUnder()
	                .addImage(background, width, 0, 0, height, 0, 0);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	}
}
