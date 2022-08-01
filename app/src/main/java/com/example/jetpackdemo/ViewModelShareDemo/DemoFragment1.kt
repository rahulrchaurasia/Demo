package com.example.jetpackdemo.ViewModelShareDemo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentDemo1Binding


class DemoFragment1 : Fragment() {

   // private var shareDemoViewModelInstance : ShareDemoViewModel ? = null

    // this is smallest way
    // activityViewModels : because it coonected with activity. if connected with fragment than directly used viewModels()
    private  val shareDemoViewModelInstance : ShareDemoViewModel by activityViewModels()


    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private  var _binding : FragmentDemo1Binding ? = null
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
       // val view =  inflater.inflate(R.layout.fragment_demo1, container, false)
        _binding = FragmentDemo1Binding.inflate(inflater,container,false)

        // as soon as the button is clicked
        // send the data to ViewModel
        // and the Live data will take care of
        // updating the data inside another Fragment
        binding.sendButtonFragment1.setOnClickListener{

            shareDemoViewModelInstance?.setData(binding.editTextFromFragment1.text.toString())


        }

        // Note : requireActivity() because we need activity instance. if we use this than it used frag
        // Activity instance is required because DemoViewmodelActivity has both the fragmnet.

        // Todo : we used diff way to create viewmodel
      //  shareDemoViewModelInstance = ViewModelProvider(requireActivity()).get(ShareDemoViewModel::class.java)


        // observe the data inside the view model that is mutable
        // live of type CharSequence and set the data for edit text

        if(viewLifecycleOwner != null) {
            shareDemoViewModelInstance!!.getData().observe(viewLifecycleOwner, Observer { etFrag2 ->

                binding.editTextFromFragment1.setText(etFrag2.toString())
            })
        }

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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




