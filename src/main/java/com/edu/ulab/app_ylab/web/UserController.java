package com.edu.ulab.app_ylab.web;

import com.edu.ulab.app_ylab.facade.UserDataFacade;
import com.edu.ulab.app_ylab.web.constant.WebConstant;
import com.edu.ulab.app_ylab.web.request.UserBookRequest;
import com.edu.ulab.app_ylab.web.response.UserBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static com.edu.ulab.app_ylab.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app_ylab.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserDataFacade userDataFacade;

    public UserController(UserDataFacade userDataFacade) {
        this.userDataFacade = userDataFacade;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse createUserWithBooks(@RequestBody @Valid UserBookRequest request,
                                                @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }

    @PutMapping(value = "/update/{userId}")
    @Operation(summary = "Update user with books.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse updateUserWithBooks(@RequestBody UserBookRequest request, @PathVariable Long userId,
                                                @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.updateUserWithBooks(request, userId);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Get user with books.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse getUserWithBooks(@PathVariable Long userId,
                                             @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.getUserWithBooks(userId);
        log.info("Response with user and his books: {}", response);
        return response;
    }


    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Delete user with books.")
    public void deleteUserWithBooks(@PathVariable Long userId,
                                    @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        log.info("Delete user and his books:  userId {}", userId);
        userDataFacade.deleteUserWithBooks(userId);
    }
}
