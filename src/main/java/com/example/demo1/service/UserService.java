package com.example.demo1.service;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.exception.DuplicateUserException;
import com.example.demo1.exception.UserNotFoundException;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.SeatClass;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.TicketRepo;
import com.example.demo1.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo1.constants.CommonConstants.ASC;
import static com.example.demo1.constants.CommonConstants.LIKE;
import static com.example.demo1.constants.MessageConstants.*;

@Slf4j
@Service
public class UserService {

    private final UserRepo userRepo;
    private final Mapper mapper;
    private final TicketRepo ticketRepo;

    public UserService(UserRepo userRepo, Mapper mapper, TicketRepo ticketRepo) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.ticketRepo = ticketRepo;
    }

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

        return userRepo.findAll(spec, pageable).map(UserAssembler::toUserDto);
    }


/*Adding users without validating the duplicates

    public ApiResponse<UserResponseDto> addUser(UserRequestDto userRequestDto) {
        User_Profile userProfile = userRepo.save(Mapper.toUserEntity(userRequestDto));
        UserResponseDto userResponseDto = Mapper.toUserDto(userProfile);
        return new ApiResponse<>(ADDED,userResponseDto);
    }*/


    public UserResponseDto getUserById(Long id) {

        return UserAssembler.toUserDto(userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(NOTFOUND)));
    }

    public UserResponseDto addUser(UserRequestDto dto) {
        Optional<UserProfile> existing = userRepo.findByEmailAndPhone(dto.getEmail(), dto.getPhone());
        if (existing.isPresent()) {
            throw new DuplicateUserException(FOUND);
        }
        return UserAssembler.toUserDto(userRepo.save(mapper.toUserEntity(dto)));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        UserProfile user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(NOTFOUND));
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return UserAssembler.toUserDto(userRepo.save(user));
    }

    public String deleteUser(Long id) {
        if(!userRepo.existsById(id)) {
            throw new UserNotFoundException(NOTFOUND);
        }
        else {
            userRepo.deleteById(id);
            return DELETED;
        }
    }

    public UserResponseDto getUserByTicketId(Long ticketId) {
        UserProfile user = userRepo.findUserProfileByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserAssembler.toUserDto(user);
    }


    public List<UserResponseDto> findByGender(String gender) {
        return userRepo.findByGenderIgnoreCase(gender);
    }

    public List<UserProfile> getUserByTravelClass(SeatClass travelClass) {
        return userRepo.findByTravelClass(travelClass);
    }

    public List<UserProfile> getUserByFare(String fare) {
        return userRepo.findByFare(fare);
    }
}











