package chic_chic.spring.apiPayload.exception.handler;

import chic_chic.spring.apiPayload.code.BaseErrorCode;
import chic_chic.spring.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}