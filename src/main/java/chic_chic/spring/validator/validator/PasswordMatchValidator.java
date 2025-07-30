package chic_chic.spring.validator.validator;


import chic_chic.spring.validator.annotation.PasswordMatch;
import chic_chic.spring.web.dto.MemberRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, MemberRequestDTO.JoinDto> {

    @Override
    public boolean isValid(MemberRequestDTO.JoinDto dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getPasswordConfirm() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호와 비밀번호 확인은 필수입니다.")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
            return false;
        }

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
                    .addPropertyNode("passwordConfirm") // 이 필드에 오류 표시
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
