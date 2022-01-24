package com.overplay.test.feature.main

import com.overplay.test.common.ui.base.BaseActivity
import com.overplay.test.common.ui.ext.scale
import com.overplay.test.common.ui.ext.viewBindingInflate
import com.overplay.test.feature.main.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val viewBinding: ActivityMainBinding by viewBindingInflate()
    private val viewModel: MainViewModel by viewModel()

    override fun configureUi() {
        // Stub.
    }

    override fun configureObserver() = with(viewModel) {
        sessionCounterLiveData.observe {
            viewBinding.txtMainSessionCounter.text = it.toString()
        }

        yRotationLiveData.observe {
            viewBinding.rotationViewMainSessionIndicator.value = it.toDouble()
        }

        textScaleLiveData.observe {
            viewBinding.txtMainSessionCounter.scale(it)
        }
    }
}