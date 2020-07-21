package com.airbnb.mvrx.stateresetter.sample

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.stateresetter.sample.annotation.ResettableProperty

data class HelloWorldState(@ResettableProperty("DEFAULT_TITLE") val title: String = DEFAULT_TITLE,
                           @ResettableProperty("DEFAULT_DESCRIPTION_0") val description0: Async<String> = DEFAULT_DESCRIPTION_0,
                           @ResettableProperty("DEFAULT_DESCRIPTION_1") val description1: Async<String> = DEFAULT_DESCRIPTION_1,
                           @ResettableProperty("DEFAULT_DESCRIPTION_2") val description2: Async<String> = DEFAULT_DESCRIPTION_2,
                           @ResettableProperty("DEFAULT_DESCRIPTION_3") val description3: Async<String> = DEFAULT_DESCRIPTION_3,
                           @ResettableProperty("DEFAULT_DESCRIPTION_4") val description4: Async<String> = DEFAULT_DESCRIPTION_4,
                           @ResettableProperty("DEFAULT_DESCRIPTION_5") val description5: Async<String> = DEFAULT_DESCRIPTION_5,
                           @ResettableProperty("DEFAULT_DESCRIPTION_6") val description6: Async<String> = DEFAULT_DESCRIPTION_6,
                           @ResettableProperty("DEFAULT_DESCRIPTION_7") val description7: Async<String> = DEFAULT_DESCRIPTION_7,
                           @ResettableProperty("DEFAULT_DESCRIPTION_8") val description8: Async<String> = DEFAULT_DESCRIPTION_8,
                           val description9: Async<String> = DEFAULT_DESCRIPTION_9) : MvRxState {

    companion object {
        const val DEFAULT_TITLE = "Default title"
        @JvmField
        val DEFAULT_DESCRIPTION_0 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_1 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_2 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_3 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_4 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_5 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_6 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_7 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_8 = Uninitialized
        @JvmField
        val DEFAULT_DESCRIPTION_9 = Uninitialized
    }

}