package com.hasib.moneytrack.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
