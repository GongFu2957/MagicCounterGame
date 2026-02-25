package com.gongfu.a260223_trialmvisetup.di

import com.gongfu.a260223_trialmvisetup.presentation.ButtonViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ButtonViewModel() }
}