package com.airbnb.mvrx.stateresetter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import kotlinx.android.synthetic.main.fragment_hello_world.*

class HelloWorldFragment : BaseMvRxFragment() {

    companion object {
        fun newInstance() = HelloWorldFragment()
    }

    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hello_world, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomButton.setOnClickListener {
            viewModel.onRandomButtonClicked()
        }
        resetTitleButton.setOnClickListener {
            viewModel.reset(HelloWorldState::title)
        }
        resetDescriptionButton.setOnClickListener {
            viewModel.reset(HelloWorldState::description9)
        }
        resetAllButton.setOnClickListener {
            viewModel.reset()
        }
    }

    override fun invalidate() {
        withState(viewModel) {state ->
            stateTextView.text = state.toString()
        }
    }

}