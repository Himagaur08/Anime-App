package com.android.animeapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.android.animeapp.common.NetworkResponse
import com.android.animeapp.data.repoImpl.RetrofitInstance
import com.android.animeapp.domain.models.animecharactermodel.Character
import com.android.animeapp.domain.models.animeplanetmodel.Planets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(): ViewModel() {

    private val _allAnimeCharacter = MutableStateFlow<NetworkResponse<List<Character>>>(NetworkResponse.Loading)
    val allAnimeCharacter: StateFlow<NetworkResponse<List<Any>>> = _allAnimeCharacter

    private val _allPlanets = MutableStateFlow<NetworkResponse<List<Planets>>>(NetworkResponse.Loading)
    val allPlanets: StateFlow<NetworkResponse<List<Any>>> = _allPlanets

    var selectedCharacter by mutableStateOf<Character?>(null)

    var selectedPlanet by mutableStateOf<Planets?>(null)

    fun getAllCharacters(){
        viewModelScope.launch {
            _allAnimeCharacter.value = NetworkResponse.Loading

            try {
                val response = RetrofitInstance.api.getAllCharacter(limit = 50)
                if (response.isSuccessful && response.body() != null){
                    val characterList = response.body()?.items?:emptyList()
                    _allAnimeCharacter.value = NetworkResponse.Success(characterList)
                } else {
                    _allAnimeCharacter.value = NetworkResponse.Error("Error: ${response.message()}")
                }
            } catch (e: IOException){
                _allAnimeCharacter.value = NetworkResponse.Error("Server error: ${e.message}")
            } catch (e: Exception){
                _allAnimeCharacter.value = NetworkResponse.Error("Unexpected error: ${e.message}")
                println("General Exception: ${e.message}")
            }
        }
    }

    fun getACharacter(character: Character){
        selectedCharacter = character
    }

    fun getAPlanet(planet: Planets){
        selectedPlanet = planet
    }

    fun getAllPlanets(){
        viewModelScope.launch {
            _allPlanets.value = NetworkResponse.Loading
            try {
                val response = RetrofitInstance.api.getAllPlanets()
                if (response.isSuccessful && response.body() != null){
                    val planetList = response.body()?.items?: emptyList()
                    _allPlanets.value = NetworkResponse.Success(planetList)
                } else{
                    _allPlanets.value = NetworkResponse.Error("Error: ${response.message()}")
                }
            } catch (e: IOException){
                _allPlanets.value = NetworkResponse.Error("Network error, please check your connection")
            } catch (e: HttpException){
                _allPlanets.value = NetworkResponse.Error("Server error: ${e.message}")
            } catch (e:Exception){
               _allPlanets.value = NetworkResponse.Error("Unexpected error : ${e.message}")
                println("General Exception : ${e.message}")
            }
        }
    }

}