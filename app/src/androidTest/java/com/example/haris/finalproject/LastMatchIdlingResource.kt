package com.example.haris.finalproject

import android.support.test.espresso.IdlingResource

class LastMatchIdlingResource(private val  waitingTime: Long) : IdlingResource {
    private val startTime: Long
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    init {
        this.startTime = System.currentTimeMillis()
    }
    override fun getName(): String {
        return LastMatchIdlingResource::class.java.name+":"+waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle){
            resourceCallback!!.onTransitionToIdle()

        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

}