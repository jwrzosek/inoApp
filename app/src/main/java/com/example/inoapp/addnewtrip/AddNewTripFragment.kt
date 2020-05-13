package com.example.inoapp.addnewtrip


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.inoapp.R
import com.example.inoapp.databinding.FragmentAddNewTripBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class AddNewTripFragment : Fragment() {

    private val viewModel: AddNewTripViewModel by navGraphViewModels(R.id.addNewTripNavigation) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentAddNewTripBinding>(inflater,
            R.layout.fragment_add_new_trip, container,false)

        binding.addNewTripViewModel = viewModel

        binding.lifecycleOwner = this

        //The complete onClickListener with Navigation
        binding.addNewTripAddNewPoint.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_addNewTripFragment_to_addNewPointFragment)
        }

        binding.addNewTripAddButton.setOnClickListener { view : View ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }


}
