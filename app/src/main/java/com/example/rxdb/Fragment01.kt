package com.example.rxdb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rxdb.databinding.Fragment01Binding
import com.example.rxdb.persistence.UserDao
import com.example.rxdb.persistence.UsersDatabase
import com.example.rxdb.viewmodel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class Fragment01 : Fragment() {

    private val viewModel
            by lazy {
                ViewModelProviders.of(
                    this,
                    UserViewModel.Factory(
                        UsersDatabase.getInstance(this.context!!).userDao()
                    )
                ).get(UserViewModel::class.java)
            }

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Fragment01Binding.inflate(inflater, container, false)

        disposable.add(viewModel.userName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ binding.userName.text = it },
                { error -> Log.e(TAG, "Unable to get username", error) }))

        binding.updateUserButton.setOnClickListener {
            val userName = binding.userNameInput.text.toString()
            // Disable the update button until the user name update has been done
            binding.updateUserButton.isEnabled = false
            // Subscribe to updating the user name.
            // Enable back the button once the user name has been updated
            disposable.add(viewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ binding.updateUserButton.isEnabled = true },
                    { error -> Log.e(TAG, "Unable to update username", error) }))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }

    // インスタンス生成
    // 他クラスから利用する場合にこのメソッドを呼ぶ
    companion object {
        fun newInstance(): Fragment01 {
            // Fragemnt01 インスタンス生成
            val fragment01 = Fragment01()
            return fragment01
        }
        // クラス名
        private val TAG = Fragment01::class.java.simpleName
    }
}