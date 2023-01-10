package kr.co.itresumeregistersite.service;

import kr.co.itresumeregistersite.domain.dto.userDto.DeleteDto;
import kr.co.itresumeregistersite.domain.dto.userDto.ReadDto;
import kr.co.itresumeregistersite.domain.dto.userDto.SignUpDto;
import kr.co.itresumeregistersite.domain.dto.userDto.UpdateDto;
import kr.co.itresumeregistersite.domain.entity.User;
import kr.co.itresumeregistersite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private UserRepository userRepository;

    // TODO 회원가입
    public void signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .identity(signUpDto.getIdentity())
                .password(signUpDto.getPassword())
                .name(signUpDto.getName())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .birth(signUpDto.getBirth())
                .address(signUpDto.getAddress())
                .gender(signUpDto.getGender())
                .build();

        userRepository.save(user);
    }

    // TODO 회원정보 조회
    public ReadDto getUser(String identity) {
        Optional<User> user = userRepository.findById(identity);
        ReadDto readDto = ReadDto.builder()
                .identity(user.get().getIdentity())
                .build();

        return readDto;
    }

    // TODO 회원정보 수정
    public void updateUser(UpdateDto updateDto) {
        Optional<User> user = userRepository.findById(updateDto.getIdentity());
        user.get().update(updateDto.getIdentity(), updateDto.getName(), updateDto.getPhone(), updateDto.getEmail(), updateDto.getBirth(), updateDto.getAddress(), updateDto.getGender());

        userRepository.save(user.get());
    }

    // TODO 회원탈퇴
    public void deleteUser(DeleteDto deleteDto) {
        Optional<User> user = userRepository.findById(deleteDto.getIdentity());
        userRepository.delete(user.get());
    }
}
