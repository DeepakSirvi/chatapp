package com.chat.app.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestContextHolder {

	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

	/**
	 * This method this used to set HttpServletRequest for ThreadLocal This is
	 * commonly done in web applications to make the request object available
	 * throughout the execution of a particular thread without having to pass it
	 * around explicitly.
	 * Set from security filter
	 * @param request
	 */
	public static void setRequest(HttpServletRequest request) {
		requestHolder.set(request);
	}

	/**
	 * This method retrieves the HttpServletRequest object from the ThreadLocal
	 * variable requestHolder.
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	/**
	 * This method removes the HttpServletRequest object from the ThreadLocal
	 * variable requestHolder, typically called after the request has been processed
	 * to avoid memory leaks.
	 */
	public static void clear() {
		requestHolder.remove();
	}

}
