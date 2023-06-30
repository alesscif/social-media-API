package com.cooksys.socialmedia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResourceExistsException extends RuntimeException {

    private static final long serialVersionUID = 4941218089603524914L;

    private String message;

}
