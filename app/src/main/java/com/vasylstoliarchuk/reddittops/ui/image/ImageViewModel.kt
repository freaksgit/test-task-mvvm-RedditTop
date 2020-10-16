package com.vasylstoliarchuk.reddittops.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ImageViewModel @Inject constructor(private val imageHelper: ImageHelper) : ViewModel() {

    private val _saveImageResult = MutableLiveData<Boolean>()
    val saveImageResult: LiveData<Boolean> = _saveImageResult

    fun saveImage(request: SaveImageRequest) {
        viewModelScope.launch {
            try {
                imageHelper.saveImage(request)
                _saveImageResult.value = true
            } catch (e: IOException) {
                _saveImageResult.value = false
            }
        }
    }

}