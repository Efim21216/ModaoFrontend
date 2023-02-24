package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentCreateGroupBinding
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.LoginViewModelFactory
import ru.nsu.fit.modao.viewmodels.MainViewModel

class CreateGroupFragment: Fragment() {
    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!
    private var app: App? = null
    private var group: Group? = null
    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = LoginViewModelFactory(repository)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        mainViewModel.groupId.observe(viewLifecycleOwner) {
            mainViewModel.getUser(app!!.userId)
            Log.d("MyTag", "HERE")
            findNavController().navigate(CreateGroupFragmentDirections.actionCreateGroupFragmentToGroupInfoFragment(group!!))
        }
        binding.buttonNext.setOnClickListener(){
            val name = binding.nameText.text.toString()
            if (name == ""){
                binding.tipMessage.setText(R.string.enterData)
                return@setOnClickListener
            }
            group = Group(typeGroup = 0, groupName = name,
                description = binding.descriptionText.text.toString())
            mainViewModel.createGroup(app!!.userId, group!!)
        }
    }
}