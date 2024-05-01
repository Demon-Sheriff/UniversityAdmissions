package com.uniadmission.universityadmissions.exceptions.applicantExceptions;

import com.uniadmission.universityadmissions.exceptions.admissionExceptions.NoAdmissionFoundException;

public class NoApplicantFoundException extends RuntimeException{

    public NoApplicantFoundException(String message) {
        super(message);
    }

}
