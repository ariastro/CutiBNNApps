package com.astronout.tmc.modules.admin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astronout.tmc.modules.admin.model.GetProfileAdminModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.manager.model.GetProfileManagerModel
import com.astronout.tmc.modules.manager.model.GetProfileManagerResponseModel
import com.astronout.tmc.modules.profile.model.GetEmployeeByIdResponseModel
import com.astronout.tmc.network.repositories.RemoteRepository
import com.astronout.tmc.utils.logDebug

class ProfileAdminViewModel(application: Application): AndroidViewModel(application) {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _avatar = MutableLiveData<String?>()
    val avatar: LiveData<String?>
        get() = _avatar

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _dob = MutableLiveData<String>()
    val dob: LiveData<String>
        get() = _dob

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _adminModel = MutableLiveData<GetProfileAdminModel>()
    val adminModel: LiveData<GetProfileAdminModel>
        get() = _adminModel

    private val _country = MutableLiveData<String>()
    val country: LiveData<String>
        get() = _country

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProfile() {
        setLoading(true)
        val repository = RemoteRepository(viewModelScope)
        repository.getAdminById(User.adminId, {
            if (it.status) {
                setName(it.getProfileAdminModel.adminName)
                setUsername(it.getProfileAdminModel.userName)
                setDob(it.getProfileAdminModel.adminBirthday)
                setAvatar(it.getProfileAdminModel.adminAvatar)
                setGender(it.getProfileAdminModel.adminGender)
                setAddress(it.getProfileAdminModel.adminAddress)
                setPhoneNumber(it.getProfileAdminModel.adminPhone)
                setCity(it.getProfileAdminModel.adminCity)
                setStatus(it.getProfileAdminModel.adminStatus)
                setCountry(it.getProfileAdminModel.adminCountry)
                setAdminModel(it.getProfileAdminModel)
            }
        }, {
            logDebug("getProfile: $it")
        }, {
            setLoading(false)
        })
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setStatus(status: String) {
        _status.value = status
    }

    fun setAdminModel(managerModel: GetProfileAdminModel) {
        _adminModel.value = managerModel
    }

    fun setAvatar(avatar: String?) {
        _avatar.value = avatar
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setDob(dob: String) {
        _dob.value = dob
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setCity(city: String) {
        _city.value = city
    }

    fun setCountry(country: String) {
        _country.value = country
    }

}