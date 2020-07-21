package com.airbnb.mvrx.stateresetter.sample

import io.reactivex.Single
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {

    fun onRandomButtonClicked() {
        val randomInt = Random.nextInt()
        val randomTitle = "RandomTitle $randomInt"
        setState {
            copy(title = randomTitle)
        }
        getDescription(randomInt).execute {
            copy(description0 = it, description1 = it, description2 = it, description3 = it, description4 = it, description5 = it, description6 = it,
                    description7 = it, description8 = it, description9 = it)
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