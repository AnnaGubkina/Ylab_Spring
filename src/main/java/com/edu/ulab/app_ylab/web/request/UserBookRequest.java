package com.edu.ulab.app_ylab.web.request;

import lombok.Data;

import java.util.List;

@Data
public class UserBookRequest {

    private UserRequest userRequest;
    private List<BookRequest> bookRequests;
}
