package com.liferay.lms.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.util.AESUtil;
import com.liferay.util.Encryptor;

/**
 * Servlet implementation class SCORMFileServerServlet
 */

public class SCORMZipFileServerServlet extends HttpServlet {

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
	 * Procesa los metodos HTTP GET y POST.<br>
	 * Busca en la ruta que se le ha pedido el comienzo del directorio
	 * "contenidos" y sirve el fichero.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		String mime_type;
		String patharchivo;
		String uri;
		
		
		try {
			User user = PortalUtil.getUser(request);
			
			if (user == null) {
				String userId = null;
				String companyId = null;
				Cookie[] cookies = ((HttpServletRequest) request).getCookies();
				for (Cookie c : cookies) {
					if ("COMPANY_ID".equals(c.getName())) {
						companyId = c.getValue();
					} else if ("ID".equals(c.getName())) {
						userId = hexStringToStringByAscii(c.getValue());
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

			String rutaDatos = SCORMContentLocalServiceUtil.getBaseZipDir();

			// Se comprueba que el usuario tiene permisos para acceder.
			// Damos acceso a todo el mundo al directorio "personalizacion",
			// para permitir mostrar a todos la pantalla de identificacion.
			uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
			uri = uri.substring(uri.indexOf("scormzip/") + "scormzip/".length());
			patharchivo = rutaDatos + "/" + uri;

			String[] params = uri.split("/");
			long groupId = GetterUtil.getLong(params[1]);
			String uuid = params[2];
			SCORMContent scormContent = SCORMContentLocalServiceUtil
					.getSCORMContentByUuidAndGroupId(uuid, groupId);
			
			boolean allowed = false;
			if (user == null) {
				user = UserLocalServiceUtil.getDefaultUser(PortalUtil.getDefaultCompanyId());
			}
			PermissionChecker pc = PermissionCheckerFactoryUtil.create(user);
			allowed = pc.hasPermission(groupId,
					SCORMContent.class.getName(), 
					scormContent.getScormId(),
					ActionKeys.VIEW);
			if (!allowed) {
				AssetEntry scormAsset = AssetEntryLocalServiceUtil.getEntry(SCORMContent.class.getName(), scormContent.getPrimaryKey());
				long scormAssetId = scormAsset.getEntryId();
				int typeId = 9;
				long[] groupIds = user.getGroupIds();
				for (long gId : groupIds) {
					List<LearningActivity> acts = LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(gId, typeId);
					for (LearningActivity act : acts) {
						String entryId = LearningActivityLocalServiceUtil.getExtraContentValue(act.getActId(), "assetEntry");
						if (Validator.isNotNull(entryId) && Long.valueOf(entryId) == scormAssetId) {
							allowed = pc.hasPermission(gId, LearningActivity.class.getName(), act.getActId(), ActionKeys.VIEW);
							if (allowed) {
								break;
							}
						}
					}
					if (allowed) {
						break;
					}
				}
				
			}
			if (allowed) {

				
				File archivo = new File(patharchivo);

				// Si el archivo existe y no es un directorio se sirve. Si no,
				// no se hace nada.
				if (archivo.exists() && archivo.isFile()) {

					// El content type siempre antes del printwriter
					mime_type = MimeTypesUtil.getContentType(archivo);

					response.setContentType(mime_type);
					response.addHeader("Content-Type", mime_type);
					
					java.io.OutputStream out = response.getOutputStream();

					FileInputStream fis = new FileInputStream(patharchivo);
					
					if (scormContent.isCiphered()) {
						BufferedInputStream is = new BufferedInputStream(fis);
						BufferedOutputStream os = new BufferedOutputStream(out);
						AESUtil.encrypt(is, os, getPassword(user));
					} else {
						byte[] buffer = new byte[512];
						int i = 0;
	
						while (fis.available() > 0) {
							i = fis.read(buffer);
							if (i == 512) {
								out.write(buffer);
							} else {
								out.write(buffer, 0, i);
							}
						}
	
						out.flush();
						out.close();
					}
					fis.close();
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (Exception e) {
			System.out
					.println("Error en el processRequest() de ServidorArchivos: "
							+ e.getMessage());
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Procesa el metodo HTTP GET.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Procesa el metodo HTTP POST.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}
	
	private String getPassword(User user) throws SystemException, NoSuchAlgorithmException {
		return md5Sum(user.getUserUuid().replace("-", "").getBytes());
	}
	
	public String md5Sum(byte[] convertme) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("MD5"); 
	    return byteArray2Hex(md.digest(convertme));
	}

	private String byteArray2Hex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash) {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
