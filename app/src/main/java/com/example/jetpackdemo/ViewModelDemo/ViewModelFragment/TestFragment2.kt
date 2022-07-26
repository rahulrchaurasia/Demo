package com.example.jetpackdemo.ViewModelDemo.ViewModelFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ViewModelDemo.ViewModelDemo2
import com.example.jetpackdemo.ViewModelShareDemo.DemoFragment1
import com.example.jetpackdemo.databinding.FragmentTest2Binding


class TestFragment2 : Fragment() {

    private var _binding : FragmentTest2Binding? = null
    private val viewModelDemo2 : ViewModelDemo2 by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_test2, container, false)

        _binding = FragmentTest2Binding.inflate(inflater,container,false)

        viewModelDemo2.data.observe(viewLifecycleOwner,{

            binding.txtfrag2.setText(it)
        })

        return binding.root
    }

    companion object {


        @JvmStatic
        fun newInstance() = TestFragment2()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}