package ru.n00ner.musicradioapp.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.n00ner.musicradioapp.datasource.RadioNetworkDatasource
import ru.n00ner.musicradioapp.datasource.network.RadioApiClient
import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.usecase.GetCurrentTrackUseCase
import ru.n00ner.musicradioapp.domain.usecase.GetStreamUseCase
import ru.n00ner.musicradioapp.domain.usecase.GetTrackHistoryUseCase
import ru.n00ner.musicradioapp.presentation.viewmodel.TrackHistoryViewModel
import ru.n00ner.musicradioapp.repository.NetworkRepositoryImpl

class KodeinContainers {

    companion object {
        @ExperimentalCoroutinesApi
        val diBaseProject = Kodein {
            bind<RadioApiClient>() with provider { RadioApiClient() }
            bind<RadioNetworkDatasource>() with provider { RadioNetworkDatasource(instance()) }
            bind<NetworkRepository>() with provider { NetworkRepositoryImpl(instance()) }
            bind<GetTrackHistoryUseCase>() with provider { GetTrackHistoryUseCase(instance()) }
            bind<GetStreamUseCase>() with provider { GetStreamUseCase(instance()) }
            bind<GetCurrentTrackUseCase>() with provider { GetCurrentTrackUseCase(instance()) }
            bind<TrackHistoryViewModel>() with provider { TrackHistoryViewModel(instance()) }
        }
    }
}