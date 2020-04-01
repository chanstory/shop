package com.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.common.SingleResult;
import com.myapp.service.LoginService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"1. Login"})
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "http://localhost:3000")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private final LoginService loginService;
    private final ResponseService responseService;
    
    /**
	 * 로그인
	 * 
	 * @param String id
	 * @param String password
	 * @return SingleResult<Map<String, String>>
	 */
    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다")
    @GetMapping(value = "/login")
    public SingleResult login(@ApiParam(value = "id", required = true) @RequestParam String id,
                                      			   @ApiParam(value = "password", required = true) @RequestParam String password,
                                      			   HttpServletResponse response) {
 
    	//Map<String, String> jwtTokens = loginService.login(id, password, response);
    	loginService.login(id, password, response);
        //로그인이 성공하면 jwt token을 발급
        return responseService.getSingleResult("");
    }
    
    /**
   	 * 리프레쉬 토큰 로그인
   	 * 
   	 * @param X-AUTH-TOKEN
   	 * @param HttpServletRequest request
   	 * @return SingleResult<Map<String, String>>
   	 */
    @ApiOperation(value = "리프레쉬 토큰 로그인", notes = "리프레쉬 토큰으로 로그인을 한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-REFRESH-TOKEN", value = "refresh_token", required = true, dataType = "String", paramType = "header") })
    @GetMapping(value = "/login/refresh")
    public SingleResult<Map<String, String>> refreshLogin(HttpServletRequest request) {
    	Map<String, String> jwtToken = loginService.refreshLogin(request);
    	
    	//로그인이 성공하면 jwt access token을 발급
        return responseService.getSingleResult(jwtToken);
	}
       
    /**
	 * 로그아웃
	 * 
	 * @param 
	 * @return CommonResult
	 */
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token", required = true, dataType = "String", paramType = "header"),
    					 @ApiImplicitParam(name = "X-AUTH-REFRESH-TOKEN", value = "refresh_token", required = true, dataType = "String", paramType = "header")})
    @GetMapping(value = "/logout")
    public CommonResult logout(HttpServletRequest request) {
 
    	loginService.logout(request);
 
        return responseService.getSuccessResult();
    }
    
    
}
