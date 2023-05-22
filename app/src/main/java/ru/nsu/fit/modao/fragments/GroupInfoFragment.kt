package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.databinding.FragmentGroupInfoBinding
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject
@AndroidEntryPoint
class GroupInfoFragment : Fragment() {
    private var _binding: FragmentGroupInfoBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<GroupInfoFragmentArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
    private var first = true
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

        if (args.notification && first) {
            Log.d("MyTag", "group info to data")
            first = false
            findNavController().navigate(GroupInfoFragmentDirections
                .actionGroupInfoFragmentToDataConfirmationFragment(args.group))
        }
        binding.nameGroup.text = args.group.groupName

        mainViewModel.getListOrganizers(args.group.id!!)
        initObserver()
        initButton()

    }
    private fun initButton() {
        binding.buttonGroupExpenses.setOnClickListener {
            findNavController().navigate(
                GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupExpensesFragment(
                    args.group
                )
            )
        }
        binding.groupInformation.setOnClickListener {
            findNavController().navigate(
                GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupInformationFragment(
                    args.group
                )
            )
        }
        binding.buttonDataConfirmation.setOnClickListener {
            findNavController().navigate(
                GroupInfoFragmentDirections.actionGroupInfoFragmentToDataConfirmationFragment(
                    args.group
                )
            )
        }
        binding.buttonMembers.setOnClickListener {
            findNavController().navigate(
                GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupMembersFragment(
                    args.group
                )
            )
        }
        binding.archiveGroup.setOnClickListener {
            mainViewModel.archiveGroup(args.group.id!!)
        }
        binding.deleteGroup.setOnClickListener {
            mainViewModel.deleteGroup(args.group.id!!)
        }
    }
    private fun initObserver() {
        mainViewModel.organizers.observe(viewLifecycleOwner) {
            val isOrganizer = it.any { org -> org.id == app.userId }
            if (isOrganizer) {
                binding.buttonDataConfirmation.visibility = View.VISIBLE
                binding.textDataConfirmation.visibility = View.VISIBLE
            }
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            when (it) {
                "Deleted" -> findNavController().navigate(GroupInfoFragmentDirections
                    .actionGlobalNestedGroups())
                "Archived" -> findNavController().navigate(GroupInfoFragmentDirections
                    .actionGlobalNestedGroups())
                else -> Log.d("MyTag", "Unknown message $it")
            }
        }
    }
}