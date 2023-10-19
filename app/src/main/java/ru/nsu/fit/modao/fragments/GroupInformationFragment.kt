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
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentGroupInformationBinding
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants
import ru.nsu.fit.modao.viewmodels.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class GroupInformationFragment : Fragment() {
    private var _binding: FragmentGroupInformationBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var app: App
    private val args by navArgs<GroupInformationFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getGroupInfo(args.group.id!!)
        initView()
        initButton()
        initObserver()
    }
    private fun initView() {
        val time = LocalDateTime.parse(args.group.time)
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        binding.dataCreation.text = time.format(pattern)
        if (args.group.isOrganizer == null) {
            mainViewModel.getListOrganizers(args.group.id!!)
        } else if (args.group.isOrganizer!!) {
            binding.archiveGroup.visibility = View.VISIBLE
            binding.deleteGroup.visibility = View.VISIBLE
            binding.titleGroupUuid.visibility = View.VISIBLE
            binding.groupUuid.visibility = View.VISIBLE
        }

    }
    private fun initButton() {
        if (args.group.typeGroup == 1) {
            binding.archiveGroup.setText(R.string.makeGroupActive)
            binding.archiveGroup.setOnClickListener {
                mainViewModel.makeGroupActive(args.group.id!!)
            }
        } else {
            binding.archiveGroup.setOnClickListener {
                mainViewModel.archiveGroup(args.group.id!!)
            }
        }
        binding.deleteGroup.setOnClickListener {
            mainViewModel.deleteGroup(args.group.id!!)
        }
    }

    private fun initObserver() {
        mainViewModel.organizers.observe(viewLifecycleOwner) {
            val isOrganizer = it.any { org -> org.id == app.userId }
            args.group.isOrganizer = isOrganizer
            if (isOrganizer) {
                binding.archiveGroup.visibility = View.VISIBLE
                binding.deleteGroup.visibility = View.VISIBLE
                binding.titleGroupUuid.visibility = View.VISIBLE
                binding.groupUuid.visibility = View.VISIBLE
            }
        }
        mainViewModel.groupInfo.observe(viewLifecycleOwner) {
            binding.groupName.text = it.groupName
            binding.groupUuid.text = it.uuid
            binding.groupDescription.text = it.description
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            when (it) {
                "Deleted" -> findNavController().navigate(
                    GroupInformationFragmentDirections
                        .actionGlobalNestedGroups()
                )

                "Archived" -> findNavController().navigate(
                    GroupInformationFragmentDirections
                        .actionGlobalNestedGroups()
                )

                Constants.SUCCESS -> findNavController().navigate(
                    GroupInformationFragmentDirections
                        .actionGlobalNestedGroups()
                )

                else -> Log.d("MyTag", "Unknown message $it")
            }
        }
    }
}
