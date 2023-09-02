package com.open6.taleadventure.data.remote.service

import com.open6.taleadventure.data.remote.model.userinfo.RequestSetAgeDto
import com.open6.taleadventure.data.remote.model.userinfo.ResponseSetAgeDto
import com.open6.taleadventure.data.remote.model.wrapper.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.PATCH

interface SetUserInfoService {
    @PATCH("member/set/age")
    suspend fun setAge(@Body request: RequestSetAgeDto): ResponseWrapper<ResponseSetAgeDto>
}