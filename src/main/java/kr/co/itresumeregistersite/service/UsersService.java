package kr.co.itresumeregistersite.service;

import kr.co.itresumeregistersite.domain.dto.usersDto.*;
import kr.co.itresumeregistersite.domain.entity.Users;
import kr.co.itresumeregistersite.domain.exception.UsersException;
import kr.co.itresumeregistersite.domain.exception.UsersExceptionType;
import kr.co.itresumeregistersite.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Security;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private UsersRepository usersRepository;


    // TODO Spring Security(PasswordEncoder)에 대해 공부
    // 회원가입
    @Transactional
    public void signUp(SignUpDto signUpDto) throws Exception{
        Users users = Users.builder()
                .identity(signUpDto.getIdentity())
                .password(signUpDto.getPassword())
                .name(signUpDto.getName())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .birth(signUpDto.getBirth())
                .address(signUpDto.getAddress())
                .gender(signUpDto.getGender())
                .build();

        // 아이디 중복 검사
        if (usersRepository.findByIdentity(signUpDto.getIdentity()).isPresent()) {
            throw new UsersException(UsersExceptionType.ALREADY_EXIST_USERSIDENTITY);
        }

        usersRepository.save(users);
    }



    // TODO 일치하는 회원정보가 없을 경우 예외 처리
    // 회원정보 조회
    public ReadDto getUser(String identity) throws Exception {
        Users users = usersRepository.findByIdentity(identity)
                .orElseThrow(() -> new UsersException(UsersExceptionType.NOT_FOUND_USERS));


    }

    // 회원정보 수정
    @Transactional
    public void updateUser(UsersUpdateDto usersUpdateDto) throws Exception {
        Optional<Users> users = usersRepository.findByIdentity(usersUpdateDto.getIdentity());

        // TODO 하나로 묶을 수 있는 방법(update 메소드로 전부 다 수정이 가능하도록 할지 아님 지금처럼 할지)
        users.get().updateEmail(usersUpdateDto.getEmail());
        users.get().updatePhone(usersUpdateDto.getPhone());
        users.get().updateAddress(usersUpdateDto.getAddress());

        if (usersUpdateDto.getEmail().isEmpty()) {
            throw new UsersException(UsersExceptionType.NOT_EXIST_EMAIL);
        }
        else if (usersUpdateDto.getPhone().isEmpty()) {
            throw new UsersException(UsersExceptionType.NOT_EXIST_PHONE);
        }
        else if (usersUpdateDto.getAddress().isEmpty()) {
            throw new UsersException(UsersExceptionType.NOT_EXIST_ADDRESS);
        }

        usersRepository.save(users.get());
    }

    // TODO 입력한 비밀번호가 틀렸을 경우 예외 처리
    // 회원 비밀번호 수정
    @Transactional
    public void updatePassword(UsersPasswordDto usersPasswordDto) throws Exception {
        Users users = usersRepository.findByIdentity(usersPasswordDto.getIdentity())
                .orElseThrow(() -> new UsersException(UsersExceptionType.NOT_FOUND_USERS));

        if (usersPasswordDto.getPassword().equals(usersPasswordDto.getChangePassword())) {
            throw new UsersException(UsersExceptionType.WRONG_PASSWORD);
        }

        usersRepository.save(users);
    }

    // 회원탈퇴
    @Transactional
    public void delete(DeleteDto deleteDto) {
        Optional<Users> users = usersRepository.findByIdentity(deleteDto.getIdentity());
        usersRepository.delete(users.get());
    }
}
