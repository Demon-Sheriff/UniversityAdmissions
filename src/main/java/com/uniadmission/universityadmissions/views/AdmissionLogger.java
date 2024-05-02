package com.uniadmission.universityadmissions.views;

public class AdmissionLogger {

	public static void log(String message) {
		System.out.println("LOG: " + message);
	}

	public static void logError(String message) {
		System.out.println("ERROR: " + message);
	}

	public static void logWarning(String message) {
		System.out.println("WARNING: " + message);
	}

	public static void logInfo(String message) {
		System.out.println("INFO: " + message);
	}

	public static void logDebug(String message) {
		System.out.println("DEBUG: " + message);
	}

	public static void logSuccess(String message) {
		System.out.println("SUCCESS: " + message);
	}

	public static void logFailure(String message) {
		System.out.println("FAILURE: " + message);
	}

}
