package com.astronout.tmc.modules.admin.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.BuildConfig
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityProfileAdminBinding
import com.astronout.tmc.modules.admin.view.UpdateProfileAActivity.Companion.EXTRA_UPDATE_ADMIN
import com.astronout.tmc.modules.admin.viewmodel.ProfileAdminViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast

class ProfileAdminActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileAdminBinding
    private lateinit var viewModel: ProfileAdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_admin)
        viewModel = ViewModelProvider(this).get(ProfileAdminViewModel::class.java)
        binding.profileAdmin = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.adminModel.observe(this, Observer {
            if (it != null) {
                binding.fullname.text = it.adminName
                binding.username.text = it.userName
                binding.gender.text = it.adminGender
                binding.dob.text = it.adminBirthday
                binding.phoneNumber.text = it.adminPhone
                binding.address.text = it.adminAddress
                binding.city.text = it.adminCity
                binding.country.text = it.adminCountry

                if (it.adminStatus == Constants.STATUS_AKUN_AKTIF) {
                    binding.status.text = getString(R.string.aktif)
                } else {
                    binding.status.text = getString(R.string.non_aktif)
                }

                if (it.adminAvatar.isNullOrEmpty()) {
                    if (it.adminGender == Constants.PRIA) {
                        GlideApp.with(this)
                            .load(R.drawable.avatar_male)
                            .into(binding.profilePicture)
                    } else {
                        GlideApp.with(this)
                            .load(R.drawable.avatar_female)
                            .into(binding.profilePicture)
                    }
                } else {
                    GlideApp.with(this)
                        .load(BuildConfig.BASE_IMG_AVATAR + it.adminAvatar)
                        .into(binding.profilePicture)
                }
            }
        })

        viewModel.getProfile()

        setupProgressBar()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupProgressBar(){
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update_profile){
            val intent = Intent(this, UpdateProfileAActivity::class.java)
            intent.putExtra(EXTRA_UPDATE_ADMIN, viewModel.adminModel.value!!)
            startActivityForResult(intent, UpdateProfileAActivity.REQUEST_UPDATE_PROFILE)
        } else if (item.itemId == R.id.change_password) {
            startActivityForResult(
                Intent(this, ChangePasswordAActivity::class.java),
                ChangePasswordAActivity.REQUEST_CHANGE_PASSWORD)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UpdateProfileAActivity.REQUEST_UPDATE_PROFILE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data != null) {
            viewModel.getProfile()
//                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
//                }
        } else if (requestCode == ChangePasswordAActivity.REQUEST_CHANGE_PASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
//                    viewModel.getProfile()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}