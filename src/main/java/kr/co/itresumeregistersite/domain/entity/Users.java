package kr.co.itresumeregistersite.domain.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "tbl_users")
public class Users {

    @Id @GeneratedValue
    @Column(name = "users_id")
    private Long usersId;   // primary Key

    @Column(length = 20, nullable = false, unique = true)
    private String identity;    // 아이디

    @Column(length = 20, nullable = false)
    private String password;    // 비밀번호

    @Column(length = 20, nullable = false)
    private String checkPassword;   // 비밀번호 확인

    @Column(length = 20, nullable = false)
    private String name;    // 이름

    @Column(length = 20, nullable = false, unique = true)
    private String phone;   // 전화번호

    @Column(length = 30, nullable = false, unique = true)
    private String email;   // 이메일

    @Column(length = 20, nullable = false)
    private String birth;   // 생일

    @Column(length = 30, nullable = false)
    private String address; // 주소

    @Column(length = 10, nullable = false)
    private String gender;  // 성별



    // == 정보 수정 == //

    // 회원 비밀번호 수정
    public void updatePassword(String password) {
        this.password = password;
    }

    // 회원정보 수정
    public void update(String email, String phone, String address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // 비밀번호 변경, 회원탈퇴 시 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }
}
