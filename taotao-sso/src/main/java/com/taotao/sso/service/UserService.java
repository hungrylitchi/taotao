package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

	TaotaoResult checkData(String content, Integer type);

	TaotaoResult createUser(TbUser user);

	TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password);

	TaotaoResult getUserByToken(String token);


}
