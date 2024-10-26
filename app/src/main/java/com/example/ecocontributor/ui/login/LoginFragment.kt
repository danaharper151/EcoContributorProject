package com.example.ecocontributor.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecocontributor.R
import com.example.ecocontributor.databinding.FragmentHomeBinding
import com.example.ecocontributor.ui.home.HomeViewModel

class LoginFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_login, container)



        return root
    }




}