package com.example.jetpackdemo.ViewModelShareDemo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentDemo2Binding


class DemoFragment2 : Fragment() {


    private var shareDemoViewModelInstance : ShareDemoViewModel ? = null

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null

    private var _binding : FragmentDemo2Binding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_demo2, container, false)
        _binding = FragmentDemo2Binding.inflate(inflater,container,false)

        return  binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareDemoViewModelInstance = ViewModelProvider(requireActivity()).get(ShareDemoViewModel::class.java)

        binding.sendButtonFragment2.setOnClickListener{

            shareDemoViewModelInstance?.setData(binding.editTextFromFragment2.text.toString())
        }


        // observe the data inside the view model that is mutable
        // live of type CharSequence and set the data for edit text
        if(viewLifecycleOwner != null) {
            shareDemoViewModelInstance!!.getData().observe(viewLifecycleOwner, Observer { etFrag2 ->

                binding.editTextFromFragment2.setText(etFrag2.toString())

            })
        }


    }

    companion object {

        @JvmStatic
        fun newInstance() = DemoFragment1()

    }



    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}