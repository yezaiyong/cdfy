/**
 * 
 */
package com.bsco.framework.web.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author jack.li
 * 
 */
public class StatusExposingServletResponse extends HttpServletResponseWrapper {

	private int status = SC_OK;

	public StatusExposingServletResponse(HttpServletResponse response)
			throws IOException {
		super(response);
	}

	public int getStatus() {
		return status;
	}

	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
		status = sc;
	}

	public void sendError(int sc) throws IOException {
		super.sendError(sc);
		status = sc;
	}

	public void sendRedirect(String location) throws IOException {
		super.sendRedirect(location);
		status = SC_MOVED_TEMPORARILY;
	}

	public void setStatus(int sc) {
		super.setStatus(sc);
		status = sc;
	}

	public void setStatus(int sc, String sm) {
		super.setStatus(sc, sm);
		status = sc;
	}

}
