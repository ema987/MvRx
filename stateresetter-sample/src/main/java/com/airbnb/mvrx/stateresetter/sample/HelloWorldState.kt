package com.airbnb.mvrx.stateresetter.sample

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.stateresetter.annotations.ResettableProperty
import com.airbnb.mvrx.stateresetter.annotations.ResettableState

@ResettableState
data class HelloWorldState(@ResettableProperty("DEFAULT_TITLE") val title: String = DEFAULT_TITLE,
                           @ResettableProperty("DEFAULT_DESCRIPTION") val description: Async<String> = DEFAULT_DESCRIPTION) : MvRxState {

    companion object {
        const val DEFAULT_TITLE = "Default title"
        val DEFAULT_DESCRIPTION = Uninitialized
    }

}