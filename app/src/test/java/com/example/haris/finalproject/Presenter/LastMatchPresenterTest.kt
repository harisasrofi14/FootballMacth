package com.example.haris.finalproject.Presenter

import com.example.haris.finalproject.API.ApiRespository
import com.example.haris.finalproject.API.TheSportBDApi
import com.example.haris.finalproject.Interface.PrevMatchView
import com.example.haris.finalproject.model.EventItem
import com.example.haris.finalproject.model.EventResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest{
    @Mock
    val gson = Gson()
    @Mock
    val apiRespository = ApiRespository()
    @Mock
    lateinit var view:PrevMatchView

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view,apiRespository,gson)

    }

    @Test
    fun testGetPastEvent() {
        val events: MutableList<EventItem> = mutableListOf()
        val response = EventResponse(events)
        val idLeague = "4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRespository
                        .doRequest(TheSportBDApi.getPastLeague(idLeague)).await(),
                    EventResponse::class.java
                )
            ).thenReturn(response)


            presenter.getPastEvent(idLeague)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventListPrev(events)
            Mockito.verify(view).hideLoading()
        }
    }
}
