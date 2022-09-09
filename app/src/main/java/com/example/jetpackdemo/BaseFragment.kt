package com.example.jetpackdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar


open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun showSnackBar(view : View, strMessage: String){

        Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT).show()

    }

    fun showAlert(msg : String){

        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Alert")
        builder.setMessage(msg)
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->

        }


        builder.show()
    }

}