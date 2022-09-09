package com.example.jetpackdemo.FileDemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.jetpackdemo.BaseFragment
import com.example.jetpackdemo.FileDemo.ViewModel.FileDemoViewMoldel
import com.example.jetpackdemo.FileDemo.ViewModel.FileDemoViewmodelFactory
import com.example.jetpackdemo.MVVMDemo.ViewModel.DemoViewModel
import com.example.jetpackdemo.MVVMDemo.ViewModel.Factory.DemoViewModelFactory

import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.ViewModelShareDemo.ShareDemoViewModel
import com.example.jetpackdemo.databinding.FragmentFirst2Binding
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Demo1Fragment : BaseFragment() {

    private var _binding: FragmentFirst2Binding? = null

    private lateinit var lyParent : ConstraintLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel : FileDemoViewMoldel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirst2Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var viewModelFactory = FileDemoViewmodelFactory(fileDir = requireContext().filesDir)

        viewModel = ViewModelProvider(this,viewModelFactory).get(FileDemoViewMoldel::class.java)


         lyParent =  binding.lyParent


        // on below line we are creating a variable for our pdf view url.
        var pdfUrl = "https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf"



        viewModel.isFileReadyToObserve.observe(this.viewLifecycleOwner,{

            if(!it){
                showSnackBar(lyParent,"Failed to download PDF")
            }else{

                try{

   //                 binding.pdfView.fromUri(FileProvider.getUriForFile(
//
//                        requireContext().applicationContext,
//                        "com.example.jetpackdemo.fileprovider",
//                        viewModel.getPdfFileUrl()))
//                        .load()
                    showSnackBar(lyParent,"Pdf Created")
                   viewModel.getUriFile?.let {

                       binding.pdfView.fromUri(it).load()

//                       val file = DocumentFileCompat.fromUri(context, treeUri);
//                       file.absolutePath // => e.g. /storage/emulated/0/MyFolder/MyFile.mp4
//                       file.basePath // => e.g. MyFolder/MyFile.mp4

                       val shareIntent = Intent().apply {

                           action = Intent.ACTION_SEND

                           putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(

                               requireContext().applicationContext,
                               "com.example.jetpackdemo.fileprovider",
                               it.toFile()

                           ))

                           flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                           type = "application/pdf"

                       }

                       val sendIntent = Intent.createChooser(shareIntent,null)
                       startActivity(sendIntent)
                   }

                }catch (ex :Exception){
                    showSnackBar(lyParent,ex.message.toString())

                    Log.d(Constant.TAG_Coroutine,"Error: "+ ex.message.toString())
                }
            }
        })

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_Second2Fragment)
        }

        binding.btnDownloadFile.setOnClickListener {




           // viewModel.downloadPdfWithMediaStore(requireContext())

            var strUrl = "http://mgfm.co.in/invoice/Invoice9019044.pdf"
            viewModel.saveFileUsingMediaStore(context = requireContext(),
                url = strUrl,
                fileName = "dummy"
             )
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}