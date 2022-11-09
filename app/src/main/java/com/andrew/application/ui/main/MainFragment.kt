package com.andrew.application.ui.main

import android.os.Bundle
import com.andrew.application.R
import com.andrew.application.base.fragment.BaseFragmentDataBinding
import com.andrew.application.databinding.FragmentMainBinding
import com.andrew.library.base.AndrewViewModel
import com.andrew.library.extension.viewModel

class MainFragment(override val layoutId: Int = R.layout.fragment_main) :
    BaseFragmentDataBinding<FragmentMainBinding>() {

    override val vm by viewModel<MainViewModel>()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindingView.vm = vm
        bindingView.message.setOnClickListener {
            vm.refreshLoading()
        }
//        vm.contentSearchLiveData.observe(viewLifecycleOwner) {}
//        vm.contentSearchLiveData()?.observe(viewLifecycleOwner) {}
//        vm.contentSearchCoroutine()
        vm.contentSearch()
//        vm.getListProject()

    }

}