package com.example.jetpackdemo.ViewModelDemo.ViewModelFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ViewModelDemo.ViewModelDemo2
import com.example.jetpackdemo.databinding.FragmentTest1Binding
import org.w3c.dom.Text

class TestFragment1 : Fragment() {

    private var _binding : FragmentTest1Binding? = null
    private val viewModelDemo2 :ViewModelDemo2 by activityViewModels()  // we have to use activityViewModels bec viewModel share by activity and it give activity instance
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentTest1Binding.inflate(inflater,container,false)

        viewModelDemo2.data.observe(viewLifecycleOwner, {

            binding.txtFrag1.setText(it)
        })

      return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = TestFragment1()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}