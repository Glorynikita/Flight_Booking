package com.example.demo1.service;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.exception.DuplicateUserException;
import com.example.demo1.exception.UserNotFoundException;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.SeatClass;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.UserProfileRepo;
import com.example.demo1.translator.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.example.demo1.constants.CommonConstants.ASC;
import static com.example.demo1.constants.CommonConstants.LIKE;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepo userProfileRepo;
    private final Mapper mapper;
    private final BCryptPasswordEncoder PasswordEncoder;
    private final Translator translator;

    public Page<UserResponseDto> getUsers(String filter, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(ASC) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        log.info("Fetching users from database...");
        Specification<UserProfile> spec = (root, query, cb) -> {
            if (filter != null && !filter.isEmpty()) {
                String like = LIKE + filter.toLowerCase() + LIKE;
                return cb.or(
                        cb.like(cb.lower(root.get(UserProfile.Fields.name)), like),
                        cb.like(cb.lower(root.get(UserProfile.Fields.email)), like),
                        cb.like(cb.lower(root.get(UserProfile.Fields.gender)), like)
                );
            }
            return cb.conjunction();
        };

        return userProfileRepo.findAll(spec, pageable).map(UserAssembler::toUserDto);
    }


/*Adding users without validating the duplicates

    public ApiResponse<UserResponseDto> addUser(UserRequestDto userRequestDto) {
        User_Profile userProfile = userRepo.save(Mapper.toUserEntity(userRequestDto));
        UserResponseDto userResponseDto = Mapper.toUserDto(userProfile);
        return new ApiResponse<>(ADDED,userResponseDto);
    }*/


    public UserResponseDto getUserById(Long id, Locale locale) {
        return UserAssembler.toUserDto(userProfileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(translator.toLocale("user.not.found", locale))));
    }

    public UserResponseDto addUser(UserRequestDto dto, Locale locale) {
        Optional<UserProfile> existing = userProfileRepo.findByEmailAndPhone(dto.getEmail(), dto.getPhone());
        if (existing.isPresent()) {
            throw new DuplicateUserException(translator.toLocale("user.found", locale));
        }
        UserProfile userEntity = mapper.toUserEntity(dto);
        userEntity.setPassword(PasswordEncoder.encode(dto.getPassword()));
        return UserAssembler.toUserDto(userProfileRepo.save(userEntity));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto, Locale locale) {
        UserProfile user = userProfileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(translator.toLocale("user.not.found", locale)));
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return UserAssembler.toUserDto(userProfileRepo.save(user));
    }

    public String deleteUser(Long id, Locale locale) {
        if(!userProfileRepo.existsById(id)) {
            throw new UserNotFoundException(translator.toLocale("user.not.found", locale));
        }
        else {
            userProfileRepo.deleteById(id);
            return translator.toLocale("deleted", locale);
        }
    }

    public UserResponseDto getUserByTicketId(Long ticketId, Locale locale) {
        UserProfile user = userProfileRepo.findUserProfileByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException(translator.toLocale("user.not.found", locale)));
        return UserAssembler.toUserDto(user);
    }

    public List<UserResponseDto> findByGender(String gender) {
        return userProfileRepo.findByGenderIgnoreCase(gender);
    }

    public List<UserProfile> getUserByTravelClass(SeatClass travelClass) {
        return userProfileRepo.findByTravelClass(travelClass);
    }

    public List<UserProfile> getUserByFare(String fare) {
        return userProfileRepo.findByFare(fare);
    }
}











