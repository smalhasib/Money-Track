package com.hasib.moneytrack.data

import com.hasib.moneytrack.models.Account
import javax.inject.Inject

class AppUserManager @Inject constructor() {
    var defaultAccount: Account = AppData.accounts[0]
}