package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentCreateGroupBinding
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.viewmodels.MainViewModel
@AndroidEntryPoint
class CreateGroupFragment: Fragment() {
    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!
    private var group: Group? = null
    private val mainViewModel: MainViewModel by viewModels()
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

        mainViewModel.groupId.observe(viewLifecycleOwner) {
            mainViewModel.getGroupInfo(it)
        }
        mainViewModel.groupInfo.observe(viewLifecycleOwner) {
            findNavController().navigate(CreateGroupFragmentDirections
                .actionCreateGroupFragmentToGroupInfoFragment(it))
        }
        binding.buttonNext.setOnClickListener {
            val name = binding.nameText.text.toString()
            if (name == ""){
                binding.tipMessage.setText(R.string.enterData)
                return@setOnClickListener
            }
            group = Group(typeGroup = 0, groupName = name,
                description = binding.descriptionText.text.toString())
            mainViewModel.createGroup(group!!)
        }
    }
}