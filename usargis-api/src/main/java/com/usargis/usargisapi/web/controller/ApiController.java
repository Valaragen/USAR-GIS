package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.util.Constant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@RequestMapping(Constant.USARGIS_PATH + Constant.API_PATH + Constant.V1_PATH)
interface ApiController {
}
