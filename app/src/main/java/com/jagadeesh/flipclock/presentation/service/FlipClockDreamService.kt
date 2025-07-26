package com.jagadeesh.flipclock.presentation.service

import android.service.dreams.DreamService
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.jagadeesh.flipclock.domain.usecase.GetTimeStateUseCase
import com.jagadeesh.flipclock.presentation.screen.FlipClockViewModel
import com.jagadeesh.flipclock.presentation.screen.components.FlipClock
import com.jagadeesh.flipclock.ui.theme.FlipClockTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FlipClockDreamService : DreamService(), SavedStateRegistryOwner, ViewModelStoreOwner {

    @Inject
    lateinit var getTimeStateUseCase: GetTimeStateUseCase

    private val lifecycleRegistry = LifecycleRegistry(this)

    private val savedStateRegistryController = SavedStateRegistryController.create(this).apply {
        performAttach()
    }

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore = ViewModelStore()
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry
    private lateinit var viewModel: FlipClockViewModel

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        // Create ViewModel using custom factory
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FlipClockViewModel(getTimeStateUseCase) as T
            }
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this, factory)[FlipClockViewModel::class.java]
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isFullscreen = true
        isInteractive = false
        isScreenBright = false

        lifecycleRegistry.currentState = Lifecycle.State.STARTED

        setContent {
            FlipClockTheme {
                FlipClock(
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDreamingStopped() {
        super.onDreamingStopped()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        viewModelStore.clear()
    }

    private fun setContent(content: @Composable () -> Unit) {
        val view = ComposeView(this).apply {
            // Set composition strategy
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            // Inject dependencies normally added by appcompat activities
            setViewTreeLifecycleOwner(this@FlipClockDreamService)
            setViewTreeViewModelStoreOwner(this@FlipClockDreamService)
            setViewTreeSavedStateRegistryOwner(this@FlipClockDreamService)

            // Set content composable
            setContent(content)
        }

        // Set content view
        setContentView(view)
    }
}
