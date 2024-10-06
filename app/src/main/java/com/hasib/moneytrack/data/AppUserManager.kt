package com.hasib.moneytrack.data

import com.hasib.moneytrack.models.Account
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUserManager @Inject constructor() {
    var defaultAccount: Account = AppData.accounts[0]
}