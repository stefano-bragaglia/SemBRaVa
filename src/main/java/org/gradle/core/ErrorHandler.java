/**
 * 
 */
package org.gradle.core;

import java.util.List;

import org.gradle.core.flexi.Base;
import org.gradle.core.flexi.Paraphernalia;

/**
 * @author stefano
 *
 */
public class ErrorHandler extends Handler {

	private static ErrorHandler instance = null;

	public static ErrorHandler get() {
		if (null == instance)
			instance = new ErrorHandler();
		return instance;
	}

	private ErrorHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gradle.Handler#execute(int, java.lang.String[],
	 * org.gradle.core.Paraphernalia, java.util.List)
	 */
	@Override
	protected void execute(int id, String[] tokens, Base base, Paraphernalia paraphernalia, List<String> errors) {
		errors.add("Sentence #" + id + ": not a valid SBVR statement");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gradle.Handler#match(java.lang.String[])
	 */
	@Override
	protected boolean match(String[] tokens) {
		return null != tokens && tokens.length > 0;
	}

}
