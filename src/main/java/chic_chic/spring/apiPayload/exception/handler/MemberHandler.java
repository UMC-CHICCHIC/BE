package chic_chic.spring.apiPayload.exception.handler;


import chic_chic.spring.apiPayload.code.BaseErrorCode;
import chic_chic.spring.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}