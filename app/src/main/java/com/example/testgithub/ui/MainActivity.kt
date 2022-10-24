package com.example.testgithub.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import com.example.testgithub.data.extensions.observe
import com.example.testgithub.data.extensions.rememberStateWithLifecycle
import com.example.testgithub.di.activityViewModel
import com.example.testgithub.ui.main.MainScreen
import com.google.gson.Gson
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by lazy {
        (applicationContext as DIAware).di
    }

    private val mainViewModel: MainViewModel by activityViewModel()
    private val gson: Gson by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by rememberStateWithLifecycle(mainViewModel.state)

            MainScreen(
                viewState = viewState,
                gson = gson
            )
        }

        mainViewModel.errorMessageFlow.observe(this) {
            showError(it)
        }
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }
}