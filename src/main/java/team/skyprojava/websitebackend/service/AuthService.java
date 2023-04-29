package team.skyprojava.websitebackend.service;

import team.skyprojava.websitebackend.dto.RegisterReqDto;
import team.skyprojava.websitebackend.dto.Role;

public interface AuthService {
    void login(String userName, String password);
    void register(RegisterReqDto registerReqDto);
}
