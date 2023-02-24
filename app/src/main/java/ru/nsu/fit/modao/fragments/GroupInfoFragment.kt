package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.nsu.fit.modao.databinding.FragmentGroupInfoBinding

class GroupInfoFragment : Fragment() {
    private var _binding: FragmentGroupInfoBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<GroupInfoFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.group.groupName == null) {
            binding.nameGroup.text = args.group.name
        } else {
            binding.nameGroup.text = args.group.groupName
        }

        binding.buttonGroupExpenses.setOnClickListener() {
            findNavController().navigate(GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupExpensesFragment())
        }
    }
}