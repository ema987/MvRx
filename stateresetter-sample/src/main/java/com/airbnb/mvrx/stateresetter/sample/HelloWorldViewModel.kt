package com.airbnb.mvrx.stateresetter.sample

import com.airbnb.mvrx.stateresetter.annotations.ResettableViewModel
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@ResettableViewModel
class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {

    fun onRandomButtonClicked() {
        val randomInt = Random.nextInt()
        val randomTitle = "RandomTitle $randomInt"
        setState {
            copy(title = randomTitle)
        }
        getDescription(randomInt).execute {
            copy(description = it)
        }
    }

    private fun getDescription(randomInt: Int): Single<String> {
        return Single.defer {
            val success = Random.nextBoolean()
            if(success) {
                val randomDescription = "RandomDescription $randomInt"
                Single.just(randomDescription)
            } else {
                Single.error(RuntimeException())
            }
        }.delay(1, TimeUnit.SECONDS)
    }

}