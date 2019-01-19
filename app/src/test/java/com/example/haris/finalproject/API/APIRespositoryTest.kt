package com.example.haris.finalproject.API

import org.junit.Test
import org.mockito.Mockito

class APIRespositoryTest{
    @Test
    fun testDoRequest(){
        val apiRepository = Mockito.mock(ApiRespository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=Arsenal_vs_Chelsea"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

}